package frame;

import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.nio.file.Files;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.ValueSource;

import lab.DataCompressor;
import lab.HuffmanCodes;

/**
 * Do NOT change anything in this class!
 * 
 * The test cases defined by this class are used to test if your program
 * works correctly. This class is also responsible for outputting to the
 * console.
 * 
 */

@DisplayName("Exercise 6 - Huffman Codes")
class PublicTests {
	
	protected int correct = 0;
	protected Duration timeout = Duration.ofSeconds(10);
	
	protected byte[] readFile(String inputFile) {
		try {
			File f = new File(inputFile);
			return Files.readAllBytes(f.toPath());
		} catch (IOException e) {
			throw new RuntimeException("IOError");
		}
	}
	
	@Nested
	@TestInstance(Lifecycle.PER_CLASS)
	@DisplayName("A: Compression and Decompression tests")
	class A_CompressionDecompressionTests {
		
		@DisplayName("Test Compression and Decompression")
		@ParameterizedTest(name = "Compressing input: {0}")
		@ValueSource(strings = { "tests/public/veryshort.txt", "tests/public/rfc8446.txt", "tests/public/rfc8446.pdf", "tests/public/cat.jpg" })
		void a_test_compression(String inputFile) {
			System.out.println("Starting test with file "+inputFile+"...");
			byte[] fileData = readFile(inputFile);
			System.out.println("File size (uncompressed): "+fileData.length);
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			
			DataCompressor compressor = new DataCompressor();
			compressor.compress(fileData, outputStream);
			
			byte[] compressedData = outputStream.toByteArray();
			System.out.println("File size (compressed): "+compressedData.length);
			System.out.println("Compression rate: "+100*compressedData.length/fileData.length+"%");
			
			ByteArrayInputStream inputStream = new ByteArrayInputStream(compressedData);
			outputStream = new ByteArrayOutputStream();
			DataCompressor decompressor = new DataCompressor();
			decompressor.decompress(inputStream, outputStream);
			
			assertArrayEquals(fileData, outputStream.toByteArray());
			System.out.println("Success: Decompressed data matches input!\n");
		}
		
		@DisplayName("Test Compression quality: Text")
		@Test
		void test_compression_quality_a() {
			System.out.println("Starting compression quality test with text...");
			byte[] fileData = readFile("tests/public/rfc8446.txt");
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			
			DataCompressor compressor = new DataCompressor();
			compressor.compress(fileData, outputStream);
			
			byte[] compressedData = outputStream.toByteArray();
			System.out.println("File size (compressed): "+compressedData.length);
			System.out.println("Compression rate: "+100*compressedData.length/fileData.length+"%");
			assertTrue((100*compressedData.length/fileData.length) < 65, "Error: Compression rate should be at most 65%!");
			System.out.println("Test finished successfully\n");
		}
		
		@DisplayName("Test Compression quality: PDF")
		@Test
		void test_compression_quality_b() {
			System.out.println("Starting compression quality test with pdf...");
			byte[] fileData = readFile("tests/public/rfc8446.pdf");
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			
			DataCompressor compressor = new DataCompressor();
			compressor.compress(fileData, outputStream);
			
			byte[] compressedData = outputStream.toByteArray();
			System.out.println("File size (compressed): "+compressedData.length);
			System.out.println("Compression rate: "+100*compressedData.length/fileData.length+"%");
			assertTrue((100*compressedData.length/fileData.length) < 90, "Error: Compression rate should be at most 90%!");
			System.out.println("Test finished successfully\n");
		}
	}
	
	@Nested
	@TestInstance(Lifecycle.PER_CLASS)
	@DisplayName("B: Huffman tree tests")
	class B_HuffmanTreeTests {
		
		@DisplayName("Huffman tree size")
		@ParameterizedTest(name = "Compressing input: {0}")
		@ValueSource(strings = { "tests/public/veryshort.txt", "tests/public/rfc8446.txt", "tests/public/rfc8446.pdf", "tests/public/cat.jpg" })
		void a_test_size(String inputFile) {
			System.out.println("Starting test with file "+inputFile+"...");
			byte[] fileData = readFile(inputFile);
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			BitOutputStream bitOutput = new BitOutputStream(outputStream);
			
			HuffmanCodes codes = new HuffmanCodes();
			codes.buildFrequencyTable(fileData);
			codes.buildHuffmanTree();
			codes.buildHuffmanTable();
			
			codes.saveHuffmanTree(bitOutput);
			bitOutput.close();
			byte[] treeData = outputStream.toByteArray();
			System.out.println("Size of huffman tree: "+treeData.length);
			assertTrue(400 >= treeData.length, "Tree uses up too much space!");
			System.out.println("Tree representation small enough");
			
			HuffmanCodes codes2 = new HuffmanCodes();
			ByteArrayInputStream instream = new ByteArrayInputStream(treeData);
			BitInputStream bitIn = new BitInputStream(instream);
			codes2.loadHuffmanTree(bitIn);
			codes2.buildHuffmanTable();
			
			ArrayList<Integer>[] table1 = codes.getHuffmanTable();
			ArrayList<Integer>[] table2 = codes2.getHuffmanTable();
			for( int i=0; i<256; i++) {
				assertEquals(table1[i].size(), table2[i].size() );
				for( int j=0; j<table1[i].size(); j++) {
					assertEquals(table1[i].get(j), table2[i].get(j));
				}
			}
			System.out.println("Test successful.");
		}
	}
}
	
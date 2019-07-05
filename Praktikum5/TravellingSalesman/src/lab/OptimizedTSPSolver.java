package lab;

/**
 * Aufgabe H1d)-H1e)
 * 
 * Abgabe von: Philip Jonas Franz (2447302), Julian Imhof (2689225) und Nicolas Petermann (2918103)
 */

import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

import frame.City;

/**
 * This is an optimized TSP solver.
 */
public class OptimizedTSPSolver extends AbstractTSPSolver {

	boolean _useLengthPruning;
	boolean _useIntersectionPruning;
	
	/**
	 * Initialize the solver with the list of cities and decide which optimisations to use. 
	 */
	public OptimizedTSPSolver(LinkedList<City> cities, boolean useLengthPruning, boolean useIntersectionPruning) {
		super(cities);
		_useLengthPruning = useLengthPruning;
		_useIntersectionPruning = useIntersectionPruning;
	}

	@Override
	protected boolean prune(LinkedList<City> currentList, double currentLength) {
		if (_useLengthPruning) {
			currentLength += distance(currentList.getFirst(), currentList.getLast());
			if (length() != -1 && currentLength > length()) return true;
		}
		if (_useIntersectionPruning) {
			for (int i = 0; i < currentList.size()-1; i++) {
				for (int j = i +1; j < currentList.size()-1; j++) {
					double xa = currentList.get(i).x();
					double xb = currentList.get(j).x();
					double ya = currentList.get(i).y();
					double yb = currentList.get(j).y();
					double xc = currentList.getLast().x();
					double xd = currentList.get(currentList.size() - 2).x();
					double yc = currentList.getLast().y();
					double yd = currentList.get(currentList.size() - 2).y();
					double ta = ((yc - yd)*(xa - xc)+(xd - xc)*(ya - yc))/((xd - xc)*(ya - yb)-(xa - xb)*(yd - yc));
					double tb = ((ya - yb)*(xa - xc)+(xb - xa)*(ya - yc))/((xd - xc)*(ya - yb)-(xa - xb)*(yd - yc));
					if (0 < ta && ta < 1 && 0 < tb && tb < 1) return true;
				}
			}
		}
		
		return false;
	}

	@Override
	protected void notifyNewBest(LinkedList<City> goodSolution, double length) {
		// TODO
		//not needed as I can grab the length of the currently best solution by just calling length()?...
	}

}

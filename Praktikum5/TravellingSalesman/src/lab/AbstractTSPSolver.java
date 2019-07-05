package lab;

import java.util.LinkedList;
import java.util.ListIterator;

import frame.City;

/**
 * In this class, you should implement the backtracking algorithm for solving
 * the TSP. This is an abstract class - it can't be used directly, but classes
 * that inherit from this class and implement all the abstract methods can be
 * used. BasicTSPSolver is such a class.
 */
public abstract class AbstractTSPSolver {

	private LinkedList<City> _cities;
	private LinkedList<City> _solution;
	private double _length;
	private double[][] _distanceMap;

	/**
	 * Create a solver on the given set of cities.
	 */
	public AbstractTSPSolver(LinkedList<City> cities) {
		_cities = cities;
		_solution = null;
		_length = -1;
		_distanceMap = new double[cities.size()][cities.size()];
	}

	/**
	 * Return the cities this TSP is defined on.
	 */
	public LinkedList<City> cities() {
		return _cities;
	}

	/**
	 * Return the optimal roundtrip as a list of cities.
	 */
	public LinkedList<City> solution() {
		return _solution;
	}

	/**
	 * Return the length of the optimal solution.
	 */
	public double length() {
		return _length;
	}

	/**
	 * Pre-calculate a map for all distances between cities.
	 */
	public void buildDistanceMap() {
		for (City a : _cities) {
			for (City b : _cities) {
				_distanceMap[a.id()][b.id()] = Math.sqrt(Math.pow(a.x() - b.x(), 2) + Math.pow(a.y() - b.y(), 2));
			}
		}

		// TODO
	}

	/**
	 * Return the distance between City a and City b.
	 */
	public double distance(City a, City b) {
		return _distanceMap[a.id()][b.id()];
	}

	private LinkedList<City> _curr;
	private double _currLength = 0;

	@SuppressWarnings("unchecked")
	private void solveHelper(LinkedList<City> _check) {
		if (_check.isEmpty()) {
			_currLength += distance(_curr.getLast(), _curr.getFirst());
			if (_length == -1 || _currLength <= _length) {
				_length = _currLength;
				notifyNewBest(_curr, _currLength);
				_currLength -= distance(_curr.getLast(), _curr.getFirst());
				_solution = (LinkedList<City>) _curr.clone();
			} else
				_currLength -= distance(_curr.getLast(), _curr.getFirst());
			return;
		}
		for (City a : _check) {
			_curr.add(a);
			_currLength += distance(_curr.get(_curr.size() - 2), a);

			LinkedList<City> _restCities = (LinkedList<City>) _check.clone();
			_restCities.remove(a);
			
			if (prune(_curr, _currLength)) {
				_currLength -= _distanceMap[_curr.get(_curr.size()-2).id()][a.id()]; _curr.removeLast();
				continue;
			}
			solveHelper(_restCities);

			_currLength -= distance(_curr.get(_curr.size() - 2), a);
			_curr.removeLast();
		}
		return;
	}

	/**
	 * Solve the TSP.
	 */
	public void solve() {
		// TODO

		_curr = new LinkedList<City>();
		_curr.add(_cities.get(0));
		@SuppressWarnings("unchecked")
		LinkedList<City> _without0 = (LinkedList<City>) _cities.clone();
		_without0.removeFirst();

		solveHelper(_without0);

	}

	/**
	 * This should be called for every recursion step in the solver. DO NOT
	 * IMPLEMENT THIS METHOD HERE!
	 * 
	 * @param currentList   the current list of cities that should become the
	 *                      roundtrip
	 * @param currentLength the distance needed to travel through all the cities in
	 *                      the given order
	 * @return true if this direction of the backtracking should be stopped; false,
	 *         if it should continue
	 */
	protected abstract boolean prune(LinkedList<City> currentList, double currentLength);

	/**
	 * Always call this method if your solver found a solution that is better than
	 * all previous solutions. DO NOT IMPLEMENT THIS METHOD HERE!
	 * 
	 * @param goodSolution The roundtrip (should start at ID 0 and contain every
	 *                     city exactly once).
	 * @param length       The length of the roundtrip.
	 */
	protected abstract void notifyNewBest(LinkedList<City> goodSolution, double length);
}

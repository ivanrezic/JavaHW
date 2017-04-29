package hr.fer.zemris.bf.qmc;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.StringJoiner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import hr.fer.zemris.bf.model.ConstantNode;
import hr.fer.zemris.bf.model.Node;
import hr.fer.zemris.bf.parser.Parser;

/**
 * <code>Minimizer</code> represents Quine-McCluskey minimizer with
 * Pyne-McCluskey approach. It is used to retrieve all minimal forms from given
 * expression.
 *
 * @author Ivan Rezic
 */
public class Minimizer {

	/** Constant LOG which represents out logger. */
	private static final Logger LOG = Logger.getLogger("hr.fer.zemris.bf.qmc");

	/** Minterm set. */
	private Set<Integer> mintermSet;

	/** Dontcare set. */
	private Set<Integer> dontCareSet;

	/** Expression variables. */
	private List<String> variables;

	/** Minimal forms. */
	private List<Set<Mask>> minimalForms;

	/** Primary implicants. */
	private Set<Mask> primCover;

	/**
	 * Constructor which instantiates new minimizer and initializes minimization
	 * process.
	 *
	 * @param mintermSet
	 *            the minterm set
	 * @param dontCareSet
	 *            the dont care set
	 * @param variables
	 *            the variables
	 */
	public Minimizer(Set<Integer> mintermSet, Set<Integer> dontCareSet, List<String> variables) {
		double size = Math.pow(2, variables.size());

		if (!checkNonOverlapping(mintermSet, dontCareSet) || mintermSet.stream().anyMatch(e -> e > size)) {
			throw new IllegalArgumentException("Invalid arguments given");
		}

		this.mintermSet = mintermSet;
		this.dontCareSet = dontCareSet;
		this.variables = variables;

		if (mintermSet.size() > 0) {
			if (mintermSet.size() == size) {
				primCover = findPrimaryImplicants();
				minimalForms = new ArrayList<>(Arrays.asList(primCover));
			} else {
				primCover = findPrimaryImplicants();
				minimalForms = chooseMinimalCover(primCover);
			}

			if (LOG.isLoggable(Level.FINE)) {
				logMinimalForms(minimalForms);
			}
		}

	}

	/**
	 * Method used for getting property <code>MinimalForms</code>.
	 *
	 * @return minimal forms
	 */
	public List<Set<Mask>> getMinimalForms() {
		return minimalForms;
	}

	/**
	 * Checks if two sets overlap.
	 *
	 * @param mintermSet
	 *            the minterm set
	 * @param dontCareSet
	 *            the dont care set
	 * @return true, if it is successful, false otherwise
	 */
	private boolean checkNonOverlapping(Set<Integer> mintermSet, Set<Integer> dontCareSet) {
		return Collections.disjoint(mintermSet, dontCareSet);
	}

	/**
	 * Method which finds all primary implicants.
	 *
	 * @return The set of primary implicants.
	 */
	private Set<Mask> findPrimaryImplicants() {
		Set<Mask> primaryImplicants = new LinkedHashSet<>();

		List<Set<Mask>> column = createFirstColumn();
		while (column.size() != 0) {
			List<Set<Mask>> nextColumn = getNextColumn(column);
			logColumn(column);

			primaryImplicants.addAll(getAllPrimaryImplicants(column));
			column = nextColumn;
		}

		logAllPrimary(primaryImplicants);
		return primaryImplicants;
	}

	/**
	 * Helper method which gets the all primary implicants from given column and
	 * logs them.
	 *
	 * @param column
	 *            the column
	 * @return the all primary implicants
	 */
	private Collection<? extends Mask> getAllPrimaryImplicants(List<Set<Mask>> column) {
		Set<Mask> primary = new LinkedHashSet<>();

		for (Set<Mask> set : column) {
			for (Mask mask : set) {
				if (!mask.isCombined() && !mask.isDontCare()) {
					primary.add(mask);
					LOG.log(Level.FINEST, () -> "Pronašao primarni implikant: " + mask.toString());
				}
			}
		}

		if (primary.size() != 0) {
			LOG.log(Level.FINEST, () -> "");
		}

		return primary;

	}

	/**
	 * Helper method which logs all primary implicants.
	 *
	 * @param primaryImplicants
	 *            the primary implicants
	 */
	private void logAllPrimary(Set<Mask> primaryImplicants) {
		LOG.log(Level.FINE, () -> "");
		LOG.log(Level.FINE, () -> "Svi primarni implikanti:");

		for (Mask mask : primaryImplicants) {
			LOG.log(Level.FINE, mask::toString);
		}
	}

	/**
	 * Helper method which logs given column.
	 *
	 * @param column
	 *            the column
	 */
	private void logColumn(List<Set<Mask>> column) {
		if (!LOG.isLoggable(Level.FINER))
			return;

		LOG.log(Level.FINER, "Stupac tablice:");
		LOG.log(Level.FINER, "=================================");

		Iterator<Set<Mask>> iter = column.iterator();
		while (true) {
			for (Mask mask : iter.next()) {
				LOG.log(Level.FINER, mask.toString());
			}

			if (iter.hasNext()) {
				LOG.log(Level.FINER, "-------------------------------");
			} else {
				break;
			}
		}

		LOG.log(Level.FINER, "");
	}

	/**
	 * Method used for getting next column made from all combinations from
	 * previous column.
	 *
	 * @param column
	 *            the column
	 * @return next column
	 */
	private List<Set<Mask>> getNextColumn(List<Set<Mask>> column) {
		List<Set<Mask>> newColumn = new ArrayList<>();

		Iterator<Set<Mask>> iterator = column.iterator();
		Set<Mask> current = iterator.next();
		while (iterator.hasNext() && current.size() != 0) {
			Set<Mask> next = iterator.next();

			Set<Mask> help = combine(current, next);
			if (help.size() != 0) {
				newColumn.add(help);
			}
			current = next;
		}

		return newColumn;
	}

	/**
	 * Helper method which combines two mask and sets them as combined.
	 *
	 * @param current
	 *            the current mask
	 * @param next
	 *            the next mask
	 * @return the set containing new combinations
	 */
	private Set<Mask> combine(Set<Mask> current, Set<Mask> next) {
		Set<Mask> combined = new LinkedHashSet<>();

		for (Mask first : current) {
			for (Mask second : next) {
				Optional<Mask> comibnation = first.combineWith(second);

				if (comibnation.isPresent()) {
					combined.add(comibnation.get());
					first.setCombined(true);
					second.setCombined(true);
				}
			}
		}

		return combined;
	}

	/**
	 * Helper method which creates new column from given minterms and dontcares.
	 *
	 * @return the list of sets of masks
	 */
	private List<Set<Mask>> createFirstColumn() {
		int size = variables.size();
		Map<Integer, Set<Mask>> mapByOnes;
		Set<Mask> minterms;
		Set<Mask> dontcares;

		minterms = mintermSet.stream().map(e -> new Mask(e, size, false))
				.collect(Collectors.toCollection(LinkedHashSet::new));
		dontcares = dontCareSet.stream().map(e -> new Mask(e, size, true))
				.collect(Collectors.toCollection(LinkedHashSet::new));

		minterms.addAll(dontcares);
		mapByOnes = minterms.stream()
				.collect(Collectors.groupingBy(Mask::countOfOnes, Collectors.toCollection(LinkedHashSet::new)));

		return new ArrayList<>(mapByOnes.values());
	}

	/**
	 * Method which finds and picks all minimal covers from primary implicants
	 * leftovers.
	 *
	 * @param primCover
	 *            all primary implicants
	 * @return the list of minimal covers
	 */
	private List<Set<Mask>> chooseMinimalCover(Set<Mask> primCover) {
		if (primCover.size() == 1) {
			return new ArrayList<>(Arrays.asList(primCover));

		}

		// Izgradi polja implikanata i minterma (rub tablice):
		Mask[] implicants = primCover.toArray(new Mask[primCover.size()]);
		Integer[] minterms = mintermSet.toArray(new Integer[mintermSet.size()]);

		// Mapiraj minterm u stupac u kojem se nalazi:
		Map<Integer, Integer> mintermToColumnMap = new HashMap<>();
		for (int i = 0; i < minterms.length; i++) {
			Integer index = minterms[i];
			mintermToColumnMap.put(index, i);
		}

		// Napravi praznu tablicu pokrivenosti:
		boolean[][] table = buildCoverTable(implicants, minterms, mintermToColumnMap);

		// Donji redak tablice: koje sam minterme pokrio?
		boolean[] coveredMinterms = new boolean[minterms.length];

		// Pronađi primarne implikante...
		Set<Mask> importantSet = selectImportantPrimaryImplicants(implicants, mintermToColumnMap, table,
				coveredMinterms);

		// Izgradi funkciju pokrivenosti:
		List<Set<BitSet>> pFunction = buildPFunction(table, coveredMinterms);

		// Pronađi minimalne dopune:
		Set<BitSet> minset = findMinimalSet(pFunction);

		// Izgradi minimalne zapise funkcije:
		List<Set<Mask>> minimalForms = new ArrayList<>();
		for (BitSet bs : minset) {
			Set<Mask> set = new LinkedHashSet<>(importantSet);
			bs.stream().forEach(i -> set.add(implicants[i]));
			minimalForms.add(set);
		}

		return minimalForms;
	}

	/**
	 * Helper method which logs minimal covers.
	 *
	 * @param minimalForms
	 *            the minimal forms
	 */
	private void logMinimalForms(List<Set<Mask>> minimalForms) {
		LOG.log(Level.FINE, "");
		LOG.log(Level.FINE, "Minimalni oblici funkcije su:");

		int i = 1;
		for (Set<Mask> set : minimalForms) {
			LOG.log(Level.FINE, i++ + ". " + set.toString());
		}
	}

	/**
	 * Helper method which finds minimal set from given p function.
	 *
	 * @param pFunction
	 *            the function made by Pyne-McCluskey approach
	 * @return the sets the
	 */
	private Set<BitSet> findMinimalSet(List<Set<BitSet>> pFunction) {
		ArrayDeque<Set<BitSet>> newSet = new ArrayDeque<>(pFunction);

		while (newSet.size() > 1) {
			Set<BitSet> first = newSet.poll();
			Set<BitSet> second = newSet.poll();

			Set<BitSet> temporary = new LinkedHashSet<>();
			for (BitSet bitSet1 : first) {
				for (BitSet bitSet2 : second) {
					BitSet help = (BitSet) bitSet1.clone();
					help.or(bitSet2);
					temporary.add(help);
				}
			}
			newSet.addFirst(temporary);
		}

		if (LOG.isLoggable(Level.FINER)) {
			logSumOfProducts(newSet);
		}

		return filterNewSet(newSet.poll());
	}

	/**
	 * Helper method which returns minimal size minimal covers.
	 *
	 * @param set
	 *            the set of minimal covers
	 * @return the set of filtered ones
	 */
	private Set<BitSet> filterNewSet(Set<BitSet> set) {
		Integer min = set.stream().min((a, b) -> Integer.compare(a.cardinality(), b.cardinality()))
				.map(BitSet::cardinality).get();
		Set<BitSet> result = set.stream().filter(e -> e.cardinality() == min)
				.collect(Collectors.toCollection(LinkedHashSet::new));

		if (LOG.isLoggable(Level.FINER)) {
			LOG.log(Level.FINER, "");
			LOG.log(Level.FINER, " Minimalna pokrivanja još trebaju:");
			LOG.log(Level.FINER, result.toString());
		}

		return result;
	}

	/**
	 * Helper method which logs sum of products.
	 *
	 * @param newSet
	 *            sum of products
	 */
	private void logSumOfProducts(ArrayDeque<Set<BitSet>> newSet) {
		LOG.log(Level.FINER, "");
		LOG.log(Level.FINER, "Nakon prevorbe p-funkcije u sumu produkata:");
		LOG.log(Level.FINER, newSet.toString());
	}

	/**
	 * Helper method which builds the P function.
	 *
	 * @param table
	 *            the table
	 * @param coveredMinterms
	 *            the covered minterms
	 * @return the list which represents Pyne-McCluskey function
	 */
	private List<Set<BitSet>> buildPFunction(boolean[][] table, boolean[] coveredMinterms) {
		List<Set<BitSet>> pFunction = new ArrayList<>();

		for (int i = 0; i < coveredMinterms.length; i++) {
			if (coveredMinterms[i])
				continue;

			Set<BitSet> set = new LinkedHashSet<>();
			for (int j = 0; j < table.length; j++) {
				if (table[j][i]) {
					BitSet bitSet = new BitSet();
					bitSet.set(j);

					set.add(bitSet);
				}
			}

			pFunction.add(set);
		}

		if (LOG.isLoggable(Level.FINER)) {
			logPFunction(pFunction);
		}

		return pFunction;
	}

	/**
	 * Helper method which logs Pyne-McCluskey function.
	 *
	 * @param pFunction
	 *            the function
	 */
	private void logPFunction(List<Set<BitSet>> pFunction) {
		LOG.log(Level.FINER, pFunction.toString());
	}

	/**
	 * Helper method which selects important primary implicants.
	 *
	 * @param implicants
	 *            the implicants
	 * @param mintermToColumnMap
	 *            the minterm to column map
	 * @param table
	 *            the table
	 * @param coveredMinterms
	 *            the covered minterms
	 * @return the set of important primary implicants
	 */
	private Set<Mask> selectImportantPrimaryImplicants(Mask[] implicants, Map<Integer, Integer> mintermToColumnMap,
			boolean[][] table, boolean[] coveredMinterms) {

		Set<Mask> important = new LinkedHashSet<>();

		for (int i = 0; i < coveredMinterms.length; i++) {
			if (coveredMinterms[i]) {
				continue;
			}

			int count = 0;
			Mask implicant = null;

			for (int j = 0; j < table.length; j++) {
				if (table[j][i]) {
					count++;
					implicant = implicants[j];
				}
			}

			if (count == 1) {
				important.add(implicant);
				for (Integer index : implicant.getIndexes()) {
					Integer minterm = mintermToColumnMap.get(index);
					if (minterm == null) {
						continue;
					} else {
						coveredMinterms[minterm] = true;
					}
				}
			}
		}

		if (LOG.isLoggable(Level.FINE)) {
			logImportant(important);
		}

		return important;
	}

	/**
	 * Helper method which logs important implicants.
	 *
	 * @param important
	 *            the important
	 */
	private void logImportant(Set<Mask> important) {
		LOG.log(Level.FINE, "");
		LOG.log(Level.FINE, "Bitni primarni implikanti su:");

		for (Mask mask : important) {
			LOG.log(Level.FINE, mask.toString());
		}
	}

	/**
	 * Builds the cover table.
	 *
	 * @param implicants
	 *            the implicants
	 * @param minterms
	 *            the minterms
	 * @param mintermToColumnMap
	 *            the minterm to column map
	 * @return covered table
	 */
	private boolean[][] buildCoverTable(Mask[] implicants, Integer[] minterms,
			Map<Integer, Integer> mintermToColumnMap) {

		boolean[][] table = new boolean[implicants.length][minterms.length];

		for (int i = 0; i < implicants.length; i++) {
			for (int j = 0; j < minterms.length; j++) {
				if (implicants[i].getIndexes().contains(minterms[j])) {
					table[i][j] = true;
				}
			}
		}

		return table;
	}

	/**
	 * Method used for getting minimal forms as expression.
	 *
	 * @return minimal forms as expressions
	 */
	public List<Node> getMinimalFormsAsExpressions() {
		List<Node> nodes = new ArrayList<>();
		if (mintermSet.size() == 0) {
			nodes.add(new ConstantNode(false));
			return nodes;
		}

		if (mintermSet.size() + dontCareSet.size() == Math.pow(variables.size(), 2)) {
			nodes.add(new ConstantNode(true));
			return nodes;
		}

		List<String> expressions = getMinimalFormsAsString();

		for (String expression : expressions) {
			Parser parser = new Parser(expression);
			nodes.add(parser.getExpression());
		}

		return nodes;
	}

	/**
	 * Method used for getting minimal forms as string.
	 *
	 * @return minimal forms as string
	 */
	public List<String> getMinimalFormsAsString() {
		List<String> formsAsString = new ArrayList<>();

		if (mintermSet.size() == 0) {
			formsAsString.add("Funkcija je kontradikcija.");
			return formsAsString;
		}

		if (mintermSet.size() + dontCareSet.size() == Math.pow(variables.size(), 2)) {
			formsAsString.add("Funkcija je tautologija.");
			return formsAsString;
		}

		for (Set<Mask> form : minimalForms) {
			StringJoiner string = new StringJoiner(" OR ");
			for (Mask mask : form) {
				string.add(maskAsString(mask));
			}
			formsAsString.add(string.toString());
		}

		return formsAsString;
	}

	/**
	 * Helper method which transforms mask into string.
	 *
	 * @param mask
	 *            the mask
	 * @return the char sequence representing mask
	 */
	private CharSequence maskAsString(Mask mask) {
		StringJoiner string = new StringJoiner(" AND ");

		for (int i = 0; i < variables.size(); i++) {
			byte bit = mask.getValueAt(i);
			if (bit == 1) {
				string.add(variables.get(i));
			} else if (bit == 0) {
				string.add("NOT " + variables.get(i));
			}

		}
		return string.toString();
	}

}

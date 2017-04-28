package hr.fer.zemris.bf.qmc;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import hr.fer.zemris.bf.model.Node;

public class Minimizer {

	private static final Logger LOG = Logger.getLogger("hr.fer.zemris.bf.qmc");

	private Set<Integer> mintermSet;
	private Set<Integer> dontCareSet;
	private List<String> variables;

	private List<Set<Mask>> minimalForms;

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
				Set<Mask> primCover = findPrimaryImplicants();
				minimalForms = new ArrayList<>(Arrays.asList(primCover));
				logMinimalForms(minimalForms);
			}else {
				Set<Mask> primCover = findPrimaryImplicants();
				minimalForms = chooseMinimalCover(primCover);
			}
		}
		
	}

	public List<Set<Mask>> getMinimalForms() {
		return minimalForms;
	}

	private boolean checkNonOverlapping(Set<Integer> mintermSet, Set<Integer> dontCareSet) {
		return Collections.disjoint(mintermSet, dontCareSet);
	}

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

	private void logAllPrimary(Set<Mask> primaryImplicants) {
		LOG.log(Level.FINE, () -> "");
		LOG.log(Level.FINE, () -> "Svi primarni implikanti:");

		for (Mask mask : primaryImplicants) {
			LOG.log(Level.FINE, mask::toString);
		}
	}

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

	private List<Set<Mask>> chooseMinimalCover(Set<Mask> primCover) {
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

		if (LOG.isLoggable(Level.FINE)) {
			logMinimalForms(minimalForms);
		}

		return minimalForms;
	}

	private void logMinimalForms(List<Set<Mask>> minimalForms) {
		LOG.log(Level.FINE, "");
		LOG.log(Level.FINE, "Minimalni oblici funkcije su:");

		int i = 1;
		for (Set<Mask> set : minimalForms) {
			LOG.log(Level.FINE, i++ + ". " + set.toString());
		}
	}

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
			logSumOfProduts(newSet);
		}

		return filterNewSet(newSet.poll());
	}

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

	private void logSumOfProduts(ArrayDeque<Set<BitSet>> newSet) {
		LOG.log(Level.FINER, "");
		LOG.log(Level.FINER, "Nakon prevorbe p-funkcije u sumu produkata:");
		LOG.log(Level.FINER, newSet.toString());
	}

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

	private void logPFunction(List<Set<BitSet>> pFunction) {
		LOG.log(Level.FINER, pFunction.toString());
	}

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
					coveredMinterms[mintermToColumnMap.get(index)] = true;
				}
			}
		}

		if (LOG.isLoggable(Level.FINE)) {
			logImportant(important);
		}

		return important;
	}

	private void logImportant(Set<Mask> important) {
		LOG.log(Level.FINE, "");
		LOG.log(Level.FINE, "Bitni primarni implikanti su:");

		for (Mask mask : important) {
			LOG.log(Level.FINE, mask.toString());
		}
	}

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
	
//	public List<Node> getMinimalFormsAsExpressions(){
//		
//	}

	public static void main(String[] args) {
		Set<Integer> minterms = new HashSet<>(Arrays.asList(0,1,3,10,11,14,15));
		Set<Integer> dontcares = new HashSet<>(Arrays.asList(4,6));
		Minimizer minimizer = new Minimizer(minterms, dontcares, Arrays.asList("A", "B", "C","D"));
	}

}

package hr.fer.zemris.bf.qmc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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

public class Minimizer {

	private static final Logger LOG = Logger.getLogger("hr.fer.zemris.bf.qmc");

	private Set<Integer> mintermSet;
	private Set<Integer> dontCareSet;
	private List<String> variables;

	public Minimizer(Set<Integer> mintermSet, Set<Integer> dontCareSet, List<String> variables) {
		double size = Math.pow(2, variables.size());

		if (!checkNonOverlapping(mintermSet, dontCareSet) || mintermSet.stream().anyMatch(e -> e > size)) {
			throw new IllegalArgumentException("Invalid arguments given");
		}

		this.mintermSet = mintermSet;
		this.dontCareSet = dontCareSet;
		this.variables = variables;

		Set<Mask> primCover = findPrimaryImplicants();
		List<Set<Mask>> minimalForms = chooseMinimalCover(primCover);
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

			for (Set<Mask> set : column) {
				for (Mask mask : set) {
					if (!mask.isCombined() && !mask.isDontCare()) {
						primaryImplicants.add(mask);
						LOG.log(Level.FINEST, "Pronašao primarni implikant: " + mask.toString());
					}
				}
			}

			column = nextColumn;
		}

		return primaryImplicants;
	}

	private void logColumn(List<Set<Mask>> column) {
		LOG.log(Level.FINER, "Stupac tablice:");
		LOG.log(Level.FINER, "=================================");
		for (Set<Mask> set : column) {
			for (Mask mask : set) {
				LOG.log(Level.FINER, mask.toString());
			}
			LOG.log(Level.FINER, "-------------------------------");
		}
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
				first.combineWith(second).ifPresent(combined::add);
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

		return null;
	}

	public static void main(String[] args) {
		Set<Integer> minterms = new HashSet<>(Arrays.asList(0, 1, 3, 10, 11, 14, 15));
		Set<Integer> dontcares = new HashSet<>(Arrays.asList(4, 6));
		Minimizer m = new Minimizer(minterms, dontcares, Arrays.asList("A", "B", "C", "D"));
	}

}

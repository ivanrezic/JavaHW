package hr.fer.zemris.bf.qmc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
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
			
			for (Set<Mask> set : column) {
				set.stream().filter(e -> !e.isCombined()).forEach(primaryImplicants::add);
			}
			for (Mask set : primaryImplicants) {
				LOG.log(Level.FINEST, set.toString());
			}
			
			column = nextColumn;
		}

		return primaryImplicants;
	}

	private List<Set<Mask>> getNextColumn(List<Set<Mask>> column) {
		List<Set<Mask>> newColumn = new ArrayList<>();
		
		Iterator<Set<Mask>> iterator = column.iterator();
		Set<Mask> current = iterator.next();
		while (iterator.hasNext()) {
			Set<Mask> next = iterator.next();
			newColumn.add(combine(current,next));
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

		minterms = mintermSet.stream()
				.map(e -> new Mask(e, size, false))
				.collect(Collectors.toCollection(LinkedHashSet::new));
		dontcares = dontCareSet.stream()
				.map(e -> new Mask(e, size, true))
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
//		List<Set<Mask>> mList = m.createFirstColumn();
//
//		for (Set<Mask> elem : mList) {
//			System.out.println("======================================");
//			for (Mask mask : elem) {
//				System.out.println(mask);
//			}
//		}
		
		Set<Mask> implik = m.findPrimaryImplicants();
		for (Mask mask : implik) {
			System.out.println(mask);
		}
	}

}

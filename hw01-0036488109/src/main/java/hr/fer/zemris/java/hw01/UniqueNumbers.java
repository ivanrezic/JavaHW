package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * Program koji popunjava stablo po principu lijevo manji desno veci, za uneseni
 * element te nudi uvid u te elemente kao i u broj elemenata.
 * 
 * @author Ivan
 *
 */
public class UniqueNumbers {

	/**
	 * Struktura jednog elementa stabla.
	 *
	 */
	static class TreeNode {
		int value;
		TreeNode right = null;
		TreeNode left = null;;
	}

	/**
	 * Metoda koja dodaje element u stablo.
	 * 
	 * @param glava
	 *            glava stabla
	 * @param vrijednost
	 *            vrijednost koju zelimo dodati
	 * @return glavu stabla
	 */
	public static TreeNode addNode(TreeNode glava, int vrijednost) {

		if (glava == null) {
			glava = new TreeNode();
			glava.value = vrijednost;
			return glava;
		} else if (glava.left == null && vrijednost < glava.value) {
			glava.left = new TreeNode();
			glava.left.value = vrijednost;
		} else if (glava.right == null && vrijednost > glava.value) {
			glava.right = new TreeNode();
			glava.right.value = vrijednost;
		}

		if (vrijednost < glava.value) {
			addNode(glava.left, vrijednost);
		} else if (vrijednost > glava.value) {
			addNode(glava.right, vrijednost);
		}
		return glava;
	}

	/**
	 * Metoda koja vraca broj elemenata u stablu.
	 * 
	 * @param glava
	 *            glava stabla
	 * @return broj elemenata u stablu
	 */
	public static int treeSize(TreeNode glava) {
		if (glava == null) {
			return 0;
		}
		if (glava.left == null && glava.right == null) {
			return 1;
		}
		return treeSize(glava.left) + treeSize(glava.right) + 1;
	}

	/**
	 * Metoda koja provjerava za dani element da li se nalazi u stablu.
	 * 
	 * @param glava
	 *            glava stabla
	 * @param vrijednost
	 *            vrijednost koju provjeravamo
	 * @return <code>true</code> ako se nalazi u stablu ili <code>false</code>
	 *         ako se ne nalazi
	 */
	public static boolean containsValue(TreeNode glava, int vrijednost) {
		if (glava == null) {
			return false;
		}
		if (glava.value == vrijednost) {
			return true;
		}
		if (vrijednost < glava.value) {
			return containsValue(glava.left, vrijednost);
		}else {
			return containsValue(glava.right, vrijednost);
		}

	}

	/**
	 * Metoda koja se pokreće prilikom pokretanja programa.
	 * 
	 * @param args
	 *            Argumenti komandne linije. U ovom se zadatku ne koristi.
	 */
	public static void main(String[] args) {

		TreeNode glava = null;
		glava = addNode(glava, 42);
		glava = addNode(glava, 76);
		glava = addNode(glava, 21);
		glava = addNode(glava, 76);
		glava = addNode(glava, 35);

		int velicina = treeSize(glava);
		System.out.println(velicina);

		if (containsValue(glava, 76)) {
			System.out.println("da");
		}

		TreeNode head = null;

		System.out.println("Unesite broj > ");
		Scanner sc = new Scanner(System.in);

		String sljedeciToken;

		while (sc.hasNext()) {
			sljedeciToken = sc.next();
			if (sljedeciToken.equals("kraj")) {
				break;
			}
			try {
				int temp = Integer.parseInt(sljedeciToken);
				head = addNode(head, temp);
			} catch (NumberFormatException ex) {
				System.out.println("'" + sljedeciToken + "' nije cijeli broj.");
			}

		}
		sc.close();

		ispisiOdNajmanjeg(head);
		System.out.println();
		ispisiOdNajveceg(head);

	}

	/**
	 * Metoda koja za dano stablo ispisuje elemente sortirane silazno.
	 * 
	 * @param head
	 *            glava stabla
	 */
	private static void ispisiOdNajveceg(TreeNode head) {
		if (head == null) {
			return;
		}
		ispisiOdNajveceg(head.right);
		System.out.print(head.value);
		ispisiOdNajveceg(head.left);

	}

	/**
	 * Metoda koja za dano stablo ispisuje elemente sortirane uzlazno.
	 * 
	 * @param head
	 *            glava stabla
	 */
	private static void ispisiOdNajmanjeg(TreeNode head) {
		if (head == null) {
			return;
		}
		ispisiOdNajmanjeg(head.left);
		System.out.print(head.value);
		ispisiOdNajmanjeg(head.right);

	}

}

package hr.fer.zemris.java.hw01;

import java.util.Scanner;

public class UniqueNumbers {

	static class TreeNode {
		int value;
		TreeNode right;
		TreeNode left;
	}

	public static TreeNode addNode(TreeNode glava, int vrijednost) {

		if (glava == null) {
			glava = new TreeNode();
			glava.value = vrijednost;
			glava.right = null;
			glava.left = null;
		}
		if (glava.left == null && vrijednost < glava.value) {
			glava.left = new TreeNode();
			glava.left.value = vrijednost;
			glava.left.left = null;
			glava.left.right = null;
		} else if (glava.right == null && vrijednost > glava.value) {
			glava.right = new TreeNode();
			glava.right.value = vrijednost;
			glava.right.left = null;
			glava.right.right = null;
		}
		if (vrijednost < glava.value) {
			addNode(glava.left, vrijednost);
		} else if (vrijednost > glava.value) {
			addNode(glava.right, vrijednost);
		} else {
			return glava;
		}

		return glava;
	}

	public static int treeSize(TreeNode glava) {
		if (glava == null) {
			return 0;
		}
		if (glava.left == null && glava.right == null && glava.value != 0) {
			return 1;
		}
		return treeSize(glava.left) + treeSize(glava.right) + 1;
	}

	public static boolean containsValue(TreeNode glava, int vrijednost) {
		if (glava == null) {
			return false;
		}
		if (glava.value == vrijednost) {
			return true;
		} else if (glava.left != null && glava.right == null) {
			return containsValue(glava.left, vrijednost);
		} else if (glava.right != null && glava.left == null) {
			return containsValue(glava.right, vrijednost);
		}else if (glava.left == null && glava.right == null && glava.value != vrijednost) {
			return false;
		}
		return containsValue(glava.left, vrijednost) || containsValue(glava.right, vrijednost);
	}

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

	private static void ispisiOdNajveceg(TreeNode head) {
		if (head == null) {
			return;
		}
		ispisiOdNajveceg(head.right);
		System.out.print(head.value);
		ispisiOdNajveceg(head.left);
		
	}

	private static void ispisiOdNajmanjeg(TreeNode head) {
		if (head == null) {
			return;
		}
		ispisiOdNajmanjeg(head.left);
		System.out.print(head.value);
		ispisiOdNajmanjeg(head.right);
		
	}

}

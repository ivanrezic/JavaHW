package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * Program cija je zadaca racunati faktorijele unesenih brojeva sve dok su
 * brojevi u segmentu [1,20] ili dok se ne unese rijec "kraj".
 * 
 * @author Ivan
 *
 */
public class Factorial {

	/**
	 * Metoda koja se pokrece prilikom pokretanja programa.
	 * 
	 * @param args
	 *            Argumenti komandne linije. U ovom zadatku se ne koriste.
	 */
	public static void main(String[] args) {

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

				if (temp < 1 || temp > 20) {
					System.out.println("Uneseni broj mora biti u segmentu [1,20]");
					continue;
				}

				System.out.println(Factorial.izracunaj(temp));
			} catch (NumberFormatException ex) {
				System.out.println("'" + sljedeciToken + "' nije cijeli broj.");
			}

		}
		System.out.println("Doviđenja.");
		sc.close();
	}

	/**
	 * Metoda koja racuna faktorijelu danog broja.
	 * 
	 * @param broj
	 *            Broj koji je manji ili jednak nuli ciju faktorijelu treba
	 *            izracunati.
	 * @return Izracunatu faktorijelu danog broja.
	 */
	public static long izracunaj(int broj) {
		if (broj <= 1) {
			return 1;
		} else {
			return broj * izracunaj(broj - 1);
		}
	}

}

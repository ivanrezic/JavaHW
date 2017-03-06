package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * Program koji prima sirinu i visinu stranica preko naredbenog retka ili preko
 * standardnog ulaza te racuna povrsinu i opseg tog pravokutnika.
 * 
 * @author Ivan
 *
 */
public class Rectangle {

	/**
	 * Metoda koja se pokrece prilikom pokretanja programa.
	 * 
	 * @param args
	 *            2 prirodna broja kao argumenti komandne linije ili se ne
	 *            koristi.
	 */
	public static void main(String[] args) {
		float sirina, visina;

		if (args.length == 1 || args.length > 2) {
			System.out.println("Preko naredbenog retka moraju biti unesena točno 2 argumenta!");
		} else if (args.length == 2) {
			try {
				sirina = Float.parseFloat(args[0]);
				visina = Float.parseFloat(args[1]);

				if (visina < 0 || sirina < 0) {
					System.out.println("Brojevi moraju biti veci od ili jednaki 0");
				}
				System.out.println("Pravokutnik širine " + sirina + " i visine " + visina
						+ " ima površinu " + sirina * visina + " te opseg "
						+ (2 * sirina + 2 * visina));
			} catch (NumberFormatException e) {
				System.out.println("Argumenti moraju biti brojevi!");
			}
		} else {

			Scanner scanner = new Scanner(System.in);

			sirina = postaviStranicu("sirinu", scanner);
			visina = postaviStranicu("visinu", scanner);

			System.out.println("Pravokutnik širine " + sirina + " i visine " + visina
					+ " ima površinu " + sirina * visina + " te opseg "
					+ (2 * sirina + 2 * visina));

			scanner.close();
		}

	}

	/**
	 * Metoda koja vraca stranicu u decimalnom obliku te provjerava jel
	 * zadovoljava zadane kriterije
	 * 
	 * @param stranica
	 *            Argument koji oblije pitanje prilikom pokretanja programa npr.
	 *            ako je stranica = "visinu" program ce ispisivati "Unesite
	 *            visinu"
	 * @param sc
	 *            referenca na objekt Scanner
	 * @return decimalni broj koji zadovoljava uvjete iz zadatka
	 */
	public static float postaviStranicu(String stranica, Scanner sc) {
		float rezultat;
	
		
		do {
			System.out.println("Unesite " + stranica + " > ");
			String sljedeciToken = sc.next();

			try {
				rezultat = Float.parseFloat(sljedeciToken);
				if (rezultat < 0) {
					System.out.println("Unijeli ste negativnu vrijednost.");
					continue;
				} else {
					break;
				}
			} catch (NumberFormatException e) {
				System.out.println(sljedeciToken + " se ne moze protumaciti kao broj.");
			}
		} while (true);

		return rezultat;

	}

}

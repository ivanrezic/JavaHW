package hr.fer.zemris.java.hw06.crypto;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Crypto {

	private static final int BUFFER_SIZE = 4096;

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		try {
			switch (args.length) {
			case 2:
				digest(args, scanner);
				break;
			case 3:
				crypt(args, scanner);
				break;
			default:
				System.err.println("There should be 2 or 3 arguments provided");
			}
		} catch (Exception e) {
			System.err.println("Ups.. something went wrong during calculation.");
		}

		scanner.close();

	}

	private static void digest(String[] args, Scanner scanner) throws Exception {
		if (!args[0].equals("checksha")) {
			System.err.println("First argument should be \"digest\".");
			System.exit(1);
		}
		MessageDigest sha = MessageDigest.getInstance("SHA-256");
		System.out.println("Please provide expected sha-256 digest for hw06part2.pdf:");
		String expected = scanner.nextLine();

		byte[] buffer = new byte[BUFFER_SIZE];
		int read = 0;
		try (FileInputStream input = new FileInputStream(args[1])) {
			while ((read = input.read(buffer)) != -1)
				sha.update(buffer, 0, read);
		} catch (IOException e) {
			System.err.println("Can not read from given file.");
			System.exit(1);
		}

		String actual = Util.bytetohex(sha.digest());
		if (actual.equals(expected)) {
			System.out.printf("Digesting completed. Digest of %s matches expected digest.", args[1]);
		} else {
			System.out.printf("Digesting completed. Digest of %s does not match the expected digest.%nDigest was:%s ",
					args[1], actual);
		}

	}

	private static void crypt(String[] args, Scanner scanner) throws Exception {
		if (!args[0].equals("encrypt") && !args[0].equals("decrypt")) {
			System.err.println("First argument should be \"encrypt\" or \"decrypt\".");
			System.exit(1);
		}
		boolean encrypt = args[0].equals("encrypt") ? true : false;

		System.out.println("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):");
		String keyText = scanner.nextLine();
		System.out.println("Please provide initialization vector as hex-encoded text (32 hex-digits):");
		String ivText = scanner.nextLine();

		SecretKeySpec keySpec = new SecretKeySpec(Util.hextobyte(keyText), "AES");
		AlgorithmParameterSpec paramSpec = new IvParameterSpec(Util.hextobyte(ivText));
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);

		getCipherFromFile(args[1], cipher, args[2]);
		if (encrypt) {
			System.out.printf("Encryption completed. Generated file %s based on file %s.", args[2], args[1]);
		} else {
			System.out.printf("Decryption completed. Generated file %s based on file %s.", args[2], args[1]);
		}
	}

	private static void getCipherFromFile(String inputFile, Cipher cipher, String outputFile) throws Exception {

		byte[] buffer = new byte[BUFFER_SIZE];
		byte[] data;
		int read = 0;
		try (FileInputStream input = new FileInputStream(inputFile);
				FileOutputStream output = new FileOutputStream(outputFile)) {

			while ((read = input.read(buffer)) != -1) {
				data = cipher.update(buffer, 0, read);
				output.write(data, 0, data.length);
			}

			data = cipher.doFinal();
			output.write(data, 0, data.length);
		} catch (IOException e) {
			System.out.println("Input or output file not valid.");
			System.exit(1);
		}
	}

}
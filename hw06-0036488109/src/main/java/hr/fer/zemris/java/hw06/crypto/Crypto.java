package hr.fer.zemris.java.hw06.crypto;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * <code>Crypto</code> class represents program that allows user to
 * encrypt/derypt given file using AES cryptoalgorithm with 128-bit encryption
 * key or calculate and check SHA-256 file digest.
 *
 * @author Ivan Rezic
 */
public class Crypto {

	/** Constant BUFFER_SIZE. */
	private static final int BUFFER_SIZE = 4096;

	/**
	 * The main method of this class, used for demonstration purposes.
	 *
	 * @param args
	 *            the arguments from command line, valid arguments are:
	 *            "checksha" , "encrypt" or "decrypt" following paths to
	 *            appropriate files
	 */
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
			System.err.println("Ups.. something went wrong during calculations.");
		}

		scanner.close();

	}

	/**
	 * Digest method calculates SHA-256 digest for wanted file and checks if
	 * given SHA-256 matches calculated one. Usually, digest are integral part
	 * of digital signature -a mechanism which is today broadly used online as a
	 * replacement for persons physical signature.
	 *
	 * @param args
	 *            String array containing default string "checksha" and path to
	 *            file
	 * @param scanner
	 *            standard input scanner
	 * @throws NoSuchAlgorithmException
	 *             if MessageDigest instance could not be instantiated
	 */
	private static void digest(String[] args, Scanner scanner) throws NoSuchAlgorithmException {
		if (!args[0].equals("checksha")) {
			System.err.println("First argument should be \"checksha\".");
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

	/**
	 * Crypt is method used for encryption/decryption. It takes two files and
	 * depending on wanted operation conducts it from first given file to other.
	 * Usually there are two families of cryptography: symmetric and asymmetric.
	 * In this method symmetric key is used, which means that same key(128-bit)
	 * is used for both ecryption and decryption.
	 *
	 * @param args
	 *            String array containing "encrypt" or "decrypt"and paths to
	 *            input and output file
	 * @param scanner
	 *            standard input scanner
	 * @throws Exception
	 *             If cipher couldn't be instantiated, initialized, if there is
	 *             problem with buffer space or block size is not valid
	 */
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

	/**
	 * Helper method used for retrieving crypted/decrypted parts of file. Each
	 * part has size of 4096 bytes, which is optimal for reading/writing.
	 *
	 * @param inputFile
	 *            the input file
	 * @param cipher
	 *            object used for encryption/decryption
	 * @param outputFile
	 *            the output file
	 * @return cipher from file
	 * @throws Exception
	 *             if cipher couldn't fill empty buffer space or if block size
	 *             is not valid
	 */
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
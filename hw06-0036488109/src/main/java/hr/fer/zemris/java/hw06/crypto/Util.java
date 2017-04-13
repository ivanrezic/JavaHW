package hr.fer.zemris.java.hw06.crypto;

/**
 * <code>Util</code> represents collection of byte and hex conversion methods.
 *
 * @author Ivan Rezic
 */
public class Util {

	/**
	 * Method which transforms hexadecimal string to byte array. Given
	 * hexadecimal string shlouldn't be odd length and must consist of only 0-9
	 * digits or a-f,A-F characters.
	 *
	 * @param hex
	 *            the hexadecimal number
	 * @return byte array consisting byte values of each hex number
	 */
	public static byte[] hextobyte(String hex) {
		// Safe with leading zeros (unlike BigInteger) and with negative byte
		// values (unlike Byte.parseByte)
		// Doesn't convert the String into a char[], or create StringBuilder and
		// String objects for every single byte.
		int len = hex.length();
		if (len % 2 != 0 || !hex.matches("[0-9a-fA-F]*")) {
			throw new IllegalArgumentException("Invalid hex-encoded string.");
		}

		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4) + Character.digit(hex.charAt(i + 1), 16));
		}
		return data;
	}

	/**
	 * Method which transform byte array to hexadecimal string.
	 *
	 * @param byteArray
	 *            wanted byte array
	 * @return the string which represents hexadecimal number
	 */
	public static String bytetohex(byte[] byteArray) {
		StringBuilder hex = new StringBuilder();

		for (int i = 0; i < byteArray.length; i++) {
			hex.append(Character.forDigit((byteArray[i] >> 4) & 0xF, 16));
			hex.append(Character.forDigit((byteArray[i] & 0xF), 16));
		}

		return hex.toString();
	}
}

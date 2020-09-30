package br.com.vhb.jcracker;

/**
 * Class responsible to generate all possibilities of an alphabet.
 * 
 * 
 * @author victor
 *
 */
public class StringPermutation implements KeyGenerator {

	private char[] alphabet;
	private String lastWord;

	public StringPermutation(String alphabet) {
		this.alphabet = alphabet.toCharArray();
		char c = this.alphabet[0];
		this.lastWord = String.valueOf((char) --c);
	}

	/**
	 * 
	 * @return returns next possibility based on the last word generated
	 */
	@Override
	public String next() {
		lastWord = next(lastWord);
		return lastWord;
	}

	private String next(String s) {
		int length = s.length();
		char c = s.charAt(length - 1);

		if (c == alphabet[alphabet.length - 1]) {
			char firstChar = alphabet[0];
			return length > 1 ? next(s.substring(0, length - 1)) + firstChar : "" + firstChar + firstChar;
		}

		for (int i = 0; i < alphabet.length; i++) {
			if (alphabet[i] == c) {
				return s.substring(0, length - 1) + (alphabet[i + 1]);
			}
		}
		return "" + alphabet[0];
	}

}

package br.com.vhb.jwtcracker;

public class StringPermutation {

	private char[] alphabet;
	private int maxLength;
	private String lastWord;

	public StringPermutation(String alphabet, int maxLength) {
		this.alphabet = alphabet.toCharArray();
		this.maxLength = maxLength;
		char c = this.alphabet[0];
		this.lastWord = String.valueOf((char) --c);
	}

	public String next() {
		String next = next(lastWord);
		lastWord = next;
		if ((maxLength + 1) == lastWord.length()) {
			return null;
		}
		return next;
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

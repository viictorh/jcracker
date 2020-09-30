package br.com.vhb.jcracker;

import java.util.regex.Pattern;

public enum PatternCheck {

	JWT("[a-zA-Z0-9\\-_]+?\\.[a-zA-Z0-9\\-_]+?\\.([a-zA-Z0-9\\-_]+)$");

	private Pattern pattern;

	private PatternCheck(String pattern) {
		this.pattern = Pattern.compile(pattern);
	}

	public boolean isOk(String check) {
		return pattern.matcher(check).matches();
	}

	public Pattern pattern() {
		return pattern;
	}

}

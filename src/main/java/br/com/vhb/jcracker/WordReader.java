package br.com.vhb.jcracker;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class WordReader implements KeyGenerator {

	private Path file;
	private long lastLine;
	private boolean endFile;

	public WordReader(String fileStr) {
		file = Paths.get(fileStr);
		if (!file.toFile().canRead()) {
			throw new RuntimeException("File not found or not accessible");
		}
	}

	@Override
	public String next() {
		if (endFile) {
			return null;
		}

		try (Stream<String> lines = Files.lines(file)) {
			String line = lines.skip(lastLine++).findFirst().orElse(null);
			endFile = line == null;
			return line;
		} catch (IOException e) {
			throw new RuntimeException("It was not possible to read the file", e);
		}
	}

}

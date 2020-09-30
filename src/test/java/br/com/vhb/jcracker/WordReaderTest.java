package br.com.vhb.jcracker;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class WordReaderTest {

	private WordReader wordReader;
	@ClassRule
	public static TemporaryFolder folder = new TemporaryFolder();
	private static String filestr;
	private static List<String> fileContent;

	@BeforeClass
	public static void initiateFile() throws IOException {
		List<Object> content = IntStream.range(1, 300).boxed().map(i -> "value_" + i).collect(Collectors.toList());
		filestr = folder.newFile("wordlist.txt").getAbsolutePath();
		fileContent = content.stream().map(Object::toString).collect(Collectors.toList());
		Files.write(Paths.get(filestr), String.join(System.lineSeparator(), fileContent).getBytes());
	}

	@Before
	public void init() throws IOException {
		wordReader = new WordReader(filestr);
	}

	@Test(expected = RuntimeException.class)
	public void whenFileNotFound_thenThrowsException() {
		new WordReader("FileNotExists.txt");
	}

	@Test
	public void whenCallNext_thenReturnFirstFileLine() {
		assertThat(wordReader.next(), is(fileContent.get(0)));
	}

	@Test
	public void whenCallNextAndFileFinishes_thenReturnsNull() {
		String next = "";
		int i = 0;
		do {
			next = wordReader.next();
			i++;
		} while (i <= fileContent.size());
		assertThat(next, is(nullValue()));
	}

	@Test
	public void whenCallNextAfterFileFinishes_thenReturnsNull() {
		String next = "";
		int i = 0;
		do {
			next = wordReader.next();
			i++;
		} while (i <= fileContent.size());
		next = wordReader.next();
		assertThat(next, is(nullValue()));
	}
}

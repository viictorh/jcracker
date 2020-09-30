package br.com.vhb.jcracker;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.com.vhb.jcracker.StringPermutation;

public class StringPermutationTest {
	private static final String ALPHABET = "Vb3";
	private StringPermutation stringPermutation;

	@Before
	public void init() {
		stringPermutation = new StringPermutation(ALPHABET);
	}

	@Test
	public void whenUsesAlphabet_thenReturnsAllPossibilitiesIndefinitely() {
		List<String> expectedResult = asList("V", "b", "3", "VV", "Vb", "V3", "bV", "bb", "b3", "3V", "3b", "33");
		List<String> list = new ArrayList<>();

		while (list.size() < expectedResult.size()) {
			list.add(stringPermutation.next());
		}
		assertThat(list, hasSize(expectedResult.size()));
		assertThat(list, is(expectedResult));
	}

}

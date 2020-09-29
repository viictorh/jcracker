package br.com.vhb.jwtcracker;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class StringPermutationTest {
	private static final String ALPHABET = "Vb3";
	private StringPermutation stringPermutation;

	@Before
	public void init() {
		stringPermutation = new StringPermutation(ALPHABET, 2);
	}

	@Test
	public void whenUsesAlphabet_thenReturnsAllPossibilities() {
		List<String> expectedResult = asList("V", "b", "3", "VV", "Vb", "V3", "bV", "bb", "b3", "3V", "3b", "33");
		List<String> list = new ArrayList<>();
		String next = null;
		while (true) {
			next = stringPermutation.next();
			if (next == null) {
				break;
			}
			list.add(next);
		}
		assertThat(list, hasSize(expectedResult.size()));
		assertThat(list, is(expectedResult));
	}

}

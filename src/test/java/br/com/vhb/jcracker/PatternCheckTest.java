package br.com.vhb.jcracker;

import static org.hamcrest.MatcherAssert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class PatternCheckTest {

	@Test
	@Parameters(method = "getToken")
	public void whenTestJWT_thenReturnsExpected(boolean expectedResult, String token) {
		assertThat(PatternCheck.JWT.isOk(token), Matchers.is(expectedResult));
	}

	public List<Object[]> getToken() {
		List<Object[]> tokens = new ArrayList<>();
		tokens.add(new Object[] { false, "invalid" });
		tokens.add(new Object[] { false, "invalid.invalid" });
		tokens.add(new Object[] { false, "invalid.invalid.invalid.invalid" });
		tokens.add(new Object[] { true, "valid.valid.valid" });
		tokens.add(new Object[] { true,
				"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.cq-uoLxOu3V4RjxnbUAFZ36aSZ24BXiAH8RFDYVA6XU" });
		return tokens;
	}

}

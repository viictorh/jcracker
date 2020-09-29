package br.com.vhb.jwtcracker;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;

public class GenerateSignatureTest {

	private GenerateSignature generateSignature;

	@Before
	public void init() {
		generateSignature = new GenerateSignature();
	}

	@Test
	public void whenTriesAlphabet_returnsCorrectSignature() {
		String header = "{\"alg\":\"HS256\",\"typ\":\"JWT\"}";
		String payload = "{\"sub\":\"1234567890\",\"name\":\"John Doe\",\"iat\":1516239022}";
		String expectedSignature = "nZ86hUWPdG43W6HVSGFy6DJnDVOZhx8a73LhQ3gIxY8";
		String signature = generateSignature.signatureBase64(header, payload, "abc");
		assertThat(signature, Matchers.is(expectedSignature));
	}

}

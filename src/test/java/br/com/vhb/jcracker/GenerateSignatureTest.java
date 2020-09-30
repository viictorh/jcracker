package br.com.vhb.jcracker;

import static org.hamcrest.MatcherAssert.assertThat;

import org.hamcrest.Matchers;
import org.junit.Test;

import br.com.vhb.jcracker.GenerateSignature;

public class GenerateSignatureTest {

	@Test
	public void whenCorrectSignature_returnsCorrectSignature() {
		String header = "{\"alg\":\"HS256\",\"typ\":\"JWT\"}";
		String payload = "{\"sub\":\"1234567890\",\"name\":\"John Doe\",\"iat\":1516239022}";
		String expectedSignature = "nZ86hUWPdG43W6HVSGFy6DJnDVOZhx8a73LhQ3gIxY8";
		String signature = GenerateSignature.signatureBase64(header, payload, "abc");
		assertThat(signature, Matchers.is(expectedSignature));
	}

	@Test
	public void whenConvertToBase64AndUsesCorrectSignature_returnsCorrectSignature() {
		String header = GenerateSignature.encode("{\"alg\":\"HS256\",\"typ\":\"JWT\"}".getBytes());
		String payload = GenerateSignature
				.encode("{\"sub\":\"1234567890\",\"name\":\"John Doe\",\"iat\":1516239022}".getBytes());
		String expectedSignature = "nZ86hUWPdG43W6HVSGFy6DJnDVOZhx8a73LhQ3gIxY8";
		String signature = GenerateSignature.signature(header, payload, "abc");
		assertThat(signature, Matchers.is(expectedSignature));
	}

	@Test
	public void whenWrongSignature_returnsWrongSignature() {
		String header = "{\"alg\":\"HS256\",\"typ\":\"JWT\"}";
		String payload = "{\"sub\":\"1234567890\",\"name\":\"John Doe\",\"iat\":1516239022}";
		String expectedSignature = "nZ86hUWPdG43W6HVSGFy6DJnDVOZhx8a73LhQ3gIxY8";
		String signature = GenerateSignature.signatureBase64(header, payload, "efg");
		assertThat(signature, Matchers.is(Matchers.not(expectedSignature)));
	}

}

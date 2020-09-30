package br.com.vhb.jcracker;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * Create JWT signture
 * 
 * @author victor
 *
 */
public class GenerateSignature {
	private GenerateSignature() {
	}

	/**
	 * Returns jwt signature <b>without using</b> base64 encode on each parameter
	 * 
	 * @param header
	 *            - jwt's header
	 * @param payload
	 *            - jwt's payload
	 * @param key
	 *            - jwt's key
	 * @return signature created
	 */
	public static String signature(String header, String payload, String key) {
		return hmacSha256(header + "." + payload, key);
	}

	/**
	 * Returns jwt signature <b>using</b> base64 encode on each parameter, except
	 * the key
	 * 
	 * @param header
	 *            - jwt's header
	 * @param payload
	 *            - jwt's payload
	 * @param key
	 *            - jwt's key
	 * @return signature created
	 */
	public static String signatureBase64(String header, String payload, String key) {
		return hmacSha256(encode(header.getBytes()) + "." + encode(payload.getBytes()), key);
	}

	public static String encode(byte[] bytes) {
		return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
	}

	private static String hmacSha256(String data, String secret) {
		try {
			byte[] hash = secret.getBytes(StandardCharsets.UTF_8);
			Mac sha256Hmac = Mac.getInstance("HmacSHA256");
			SecretKeySpec secretKey = new SecretKeySpec(hash, "HmacSHA256");
			sha256Hmac.init(secretKey);
			byte[] signedBytes = sha256Hmac.doFinal(data.getBytes(StandardCharsets.UTF_8));
			return encode(signedBytes);
		} catch (NoSuchAlgorithmException | InvalidKeyException ex) {
			return null;
		}
	}

}

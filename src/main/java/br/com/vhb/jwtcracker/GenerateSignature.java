package br.com.vhb.jwtcracker;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class GenerateSignature {

	public String signature(String header, String payload, String key) {
		return hmacSha256(header + "." + payload, key);
	}

	public String signatureBase64(String header, String payload, String key) {
		return hmacSha256(encode(header.getBytes()) + "." + encode(payload.getBytes()), key);
	}

	private String hmacSha256(String data, String secret) {
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

	private String encode(byte[] bytes) {
		return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
	}

}
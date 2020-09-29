package br.com.vhb.jwtcracker;

import java.io.File;
import java.text.NumberFormat;
import java.util.function.Predicate;

import org.apache.log4j.Logger;

public class JWTCracker {
	private static final Logger LOG = Logger.getLogger(JWTCracker.class.getName());

	private static final NumberFormat NUMBER = NumberFormat.getInstance();
	private KeyGenerator keyGenerator;
	private Predicate<String> stopCondition;

	private JWTCracker(KeyGenerator keyGenerator, Predicate<String> stopCondition) {
		this.keyGenerator = keyGenerator;
		this.stopCondition = stopCondition;
	}

	public static JWTCracker byStringPermutation(String alphabet, Integer maxLength) {
		return new JWTCracker(new StringPermutation(alphabet), (word) -> word.length() > maxLength);
	}

	public static JWTCracker byWordList(String fileStr) {
		File file = new File(fileStr);
//		return new JWTCracker(null, (word) -> word == null);
		throw new RuntimeException("Not implemented...");
	}

	public void crack(String token) {
		String[] jwtTokens = token.split("\\.");
		String header = jwtTokens[0];
		String payload = jwtTokens[1];
		String signature = jwtTokens[2];

		String next = keyGenerator.next();
		long attempts = 0;
		boolean found = false;
		while (stopCondition.test(next)) {
			attempts++;
			String check = GenerateSignature.signature(header, payload, next);
			LOG.debug("[" + attempts + "] - [" + next + "]");
			if (check.equalsIgnoreCase(signature)) {
				LOG.info("FOUND KEY [" + next + "] - [" + NUMBER.format(attempts) + "] " + check);
				found = true;
				break;
			}
			if (attempts % 1000000 == 0) {
				LOG.info("Attempts: [" + NUMBER.format(attempts) + "]");
			}
			next = keyGenerator.next();
		}

		if (!found) {
			LOG.info("NOTHING Found: [" + NUMBER.format(attempts) + "] ");
		}

	}

}

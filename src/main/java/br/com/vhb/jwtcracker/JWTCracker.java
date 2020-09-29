package br.com.vhb.jwtcracker;

import java.text.NumberFormat;
import java.time.Duration;
import java.time.Instant;

public class JWTCracker {

	public static void main(String[] args) {
		args = new String[] {
				"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWV9.27IdDqX6PfoeotXOJ_gScnr8VZvU9mU4KIqsfhoR5ls" };
		String[] jwtTokens = args[0].split("\\.");
		String header = jwtTokens[0];
		String payload = jwtTokens[1];
		String signature = jwtTokens[2];
		GenerateSignature generateSignature = new GenerateSignature();
		StringPermutation stringPermutation = new StringPermutation("abcdefghijklmnopqrstuwxyz0123456789!", 10);
		NumberFormat instance = NumberFormat.getInstance();

		Instant start = Instant.now();
		String next = stringPermutation.next();
		long attempts = 0;
		boolean found = false;
		while (next != null) {
			attempts++;
			String check = generateSignature.signature(header, payload, next);

			if (check.equalsIgnoreCase(signature)) {
				System.out.println("FOUND KEY [" + next + "] - [" + instance.format(attempts) + "] " + check);
				found = true;
				break;
			}
			if (attempts % 1000000 == 0) {
				System.out.println("Attempts: [" + instance.format(attempts) + "]");
			}
			next = stringPermutation.next();
		}
		Instant end = Instant.now();
		System.out.println(Duration.between(start, end)); // prints PT1M3.553S
		if (!found) {
			System.out.println("NOTHING Found: [" + instance.format(attempts) + "] ");
		}

	}

}

package br.com.vhb.jwtcracker;

import java.time.Duration;
import java.time.Instant;
import java.util.ResourceBundle;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class Main {
	private static final String DEFAULT_MAX_LENGTH = "14";
	private static final String DEFAULT_ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	private static final ResourceBundle BUNDLE = ResourceBundle.getBundle("version");
	private static final Logger LOG = Logger.getLogger(Main.class.getName());

	public static void main(String[] args) throws ParseException {
		LOG.setLevel(Level.ALL);
		LOG.info(BUNDLE.getString("flag"));
		LOG.setLevel(Level.INFO);
		Instant start = Instant.now();
		Settings options = readOptions(args);
		if (StringUtils.isNotBlank(options.fileStr)) {
			JWTCracker.byWordList(options.fileStr).crack(options.token);
		} else {
			JWTCracker.byStringPermutation(options.alphabet, options.maxLength).crack(options.token);
		}
		LOG.info("TIME SPENT: " + Duration.between(start, Instant.now()));
	}

	private static Settings readOptions(String[] args) {
		Options options = new Options();
		options.addRequiredOption("t", "token", true, "Full JWT");
		options.addOption("a", "alphabet", true,
				"Alphabet to be used to brute force (default = " + DEFAULT_ALPHABET + ")");
		options.addOption(new Option("b", "base64", true,
				"Use '0' to tries plaintext key. '1' to tries base64 key or 'b' for both. Default = 0"));
		options.addOption(new Option("f", "file", true, "Uses a file as wordlist"));
		options.addOption(new Option("m", "max-length", true,
				"Max alphabet length (use with alphabet. default = " + DEFAULT_MAX_LENGTH + ")"));
		options.addOption(
				new Option("v", "verbose", false, "Show all runing activity. It can slow down the application"));
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp("java -jar jwtCracker.jar ", options, true);
		CommandLineParser parser = new DefaultParser();
		CommandLine cmd = null;
		try {
			cmd = parser.parse(options, args);
		} catch (ParseException e) {
			LOG.info("Please, read the documentation and fill the parameters correctly");
			System.exit(0);
		}

		return new Settings(cmd);

	}

	private static class Settings {
		String token;
		String base64;
		String verbose;
		String fileStr;
		String alphabet;
		Integer maxLength;

		public Settings(CommandLine cmd) {
			token = cmd.getOptionValue("t");
			base64 = cmd.getOptionValue("b");
			verbose = cmd.getOptionValue("v");
			fileStr = cmd.getOptionValue("f");
			alphabet = cmd.getOptionValue("a");
			String maxLength = cmd.getOptionValue("m");

			if (StringUtils.isBlank(base64))
				base64 = "0";
			if (StringUtils.isBlank(alphabet))
				alphabet = DEFAULT_ALPHABET;
			if (StringUtils.isBlank(maxLength))
				maxLength = DEFAULT_MAX_LENGTH;
			if (StringUtils.isNotBlank(verbose))
				LOG.setLevel(Level.ALL);

			this.maxLength = Integer.parseInt(maxLength);

		}

	}

}

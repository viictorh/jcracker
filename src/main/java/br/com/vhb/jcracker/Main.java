package br.com.vhb.jcracker;

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
	public static final Logger LOG = Logger.getLogger("JWTCracker");

	public static void main(String[] args) throws ParseException {
		LOG.setLevel(Level.ALL);
		LOG.info(BUNDLE.getString("flag"));
		Instant start = Instant.now();
		Settings settings = readOptions(args);
		LOG.info(settings);
		if (StringUtils.isNotBlank(settings.fileStr)) {
			JWTCracker.byWordList(settings.fileStr).crack(settings.token);
		} else {
			JWTCracker.byStringPermutation(settings.alphabet, settings.maxLength).crack(settings.token);
		}
		LOG.info("TIME SPENT: " + Duration.between(start, Instant.now()));
	}

	private static Settings readOptions(String[] args) {
		Options options = new Options();
		options.addRequiredOption("t", "token", true, "Full JWT");
		options.addOption("a", "alphabet", true,
				"Alphabet to be used to brute force (default = " + DEFAULT_ALPHABET + ")");
		options.addOption(new Option("f", "file", true, "Uses a file as wordlist"));
		options.addOption(new Option("m", "max-length", true,
				"Max alphabet length (use with alphabet. default = " + DEFAULT_MAX_LENGTH + ")"));
		options.addOption(
				new Option("v", "verbose", false, "Show all runing activity. It can slow down the application"));
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp("java -jar jcracker.jar ", options, true);
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
		boolean verbose;
		String fileStr;
		String alphabet;
		Integer maxLength;

		public Settings(CommandLine cmd) {
			token = cmd.getOptionValue("t");
			verbose = cmd.hasOption("v");
			fileStr = cmd.getOptionValue("f");
			alphabet = cmd.getOptionValue("a");
			String maxLength = cmd.getOptionValue("m");

			if (!PatternCheck.JWT.isOk(token))
				throw new RuntimeException("Invalid JWT");
			if (StringUtils.isBlank(alphabet))
				alphabet = DEFAULT_ALPHABET;
			if (StringUtils.isBlank(maxLength))
				maxLength = DEFAULT_MAX_LENGTH;
			if (!verbose)
				LOG.setLevel(Level.INFO);

			this.maxLength = Integer.parseInt(maxLength);
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append("\nCracking: ").append(token).append(System.lineSeparator());
			if (StringUtils.isNotBlank(fileStr))
				sb.append("Using file: ").append(fileStr).append(System.lineSeparator());
			else
				sb.append("Using alphabet: ").append(alphabet).append(" With length: ").append(maxLength)
						.append(System.lineSeparator());
			return sb.toString();
		}

	}

}

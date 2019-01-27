package be.vdab.euroconverter.restclients;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import be.vdab.euroconverter.exceptions.KoersClientException;

@Component 
class FixerKoersClient implements KoersClient {
	private static final Logger LOGGER = LoggerFactory.getLogger(FixerKoersClient.class);
	private final URL url;

	FixerKoersClient() {
		try {
			url = new URL("http://data.fixer.io/api/latest?access_key=51a6be8dcd0ce2c8de95d9103a07daed&symbols=USD");
		} catch (MalformedURLException ex) {
			String fout = "Fixer URL is verkeerd.";
			LOGGER.error(fout, ex);
			throw new KoersClientException(fout);
		}
	}
	
	@Override
	public BigDecimal getDollarKoers() {
		try (Scanner scanner = new Scanner(url.openStream())) {
			String lijn = scanner.nextLine();
			int beginPositieKoers = lijn.indexOf("USD") + 5;
			int accoladePositie = lijn.indexOf('}', beginPositieKoers);
			LOGGER.info("koers gelezen via Fixer");
			return new BigDecimal(lijn.substring(beginPositieKoers, accoladePositie));
		} catch (IOException | NumberFormatException ex) {
			String fout = "kan koers niet lezen via Fixer";
			LOGGER.error(fout, ex);
			throw new KoersClientException(fout);
		}
	}
}


package be.vdab.euroconverter.services;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.stereotype.Service;

import be.vdab.euroconverter.restclients.KoersClient;

@Service
class EuroService {

	private final KoersClient koersClient;

	EuroService(KoersClient koersClient) {
		this.koersClient = koersClient;
	}

	BigDecimal naarDollar(BigDecimal euro) {
		return euro.multiply(koersClient.getDollarKoers()).setScale(2, RoundingMode.HALF_UP);
	}

}

package br.com.alst.softwares.cambioservice.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alst.softwares.cambioservice.model.Cambio;
import br.com.alst.softwares.cambioservice.repository.CambioRepository;

@RestController
@RequestMapping("cambio-service")
public class CambioController {
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private CambioRepository repository;

	@GetMapping(value = "/{value}/{from}/{to}")
	public Cambio getCambio(@PathVariable("value") BigDecimal value, @PathVariable("from") String from, @PathVariable("to") String to) {
		
		var cambio = repository.findByFromAndTo(from, to);
		if (cambio == null)
			throw new RuntimeException("Currency unsupported!");
		var port = environment.getProperty("local.server.port");
		BigDecimal convertedValue = cambio.getConversionFactor().multiply(value);
		cambio.setConvertedValue(convertedValue.setScale(2, RoundingMode.CEILING));
		cambio.setEnvironment(port);
		return cambio;
	}
}

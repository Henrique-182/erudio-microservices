package br.com.erudio.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.erudio.model.Cambio;
import br.com.erudio.repository.CambioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Cambio endpoint")
@RestController
@RequestMapping(path = "/cambio-service")
public class CambioController {
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private CambioRepository repository;

	@Operation(
		summary = "Get cambio from currency"
	)
	@GetMapping(path = "/{amount}/{from}/{to}")
	public Cambio getCambio(
		@PathVariable(name = "amount") BigDecimal amount,
		@PathVariable(name = "from") String from,
		@PathVariable(name = "to") String to
	) {
		var cambio = repository.findByFromAndTo(from, to);
		
		if (cambio == null) throw new RuntimeException("Curreny Unsupported");
		
		var port = environment.getProperty("local.server.port");
		
		BigDecimal conversionFactor = cambio.getConversionFactor();
		BigDecimal convertedValue = conversionFactor.multiply(amount);
		cambio.setConvertedValue(convertedValue.setScale(2, RoundingMode.CEILING));
		cambio.setEnvironment(port);
		
		return cambio;
	}
	
}

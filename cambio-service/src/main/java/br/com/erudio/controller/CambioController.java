package br.com.erudio.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.erudio.model.Cambio;

@RestController
@RequestMapping(path = "/cambio-service")
public class CambioController {
	
	@Autowired
	private Environment environment;

	@GetMapping(path = "/{amount}/{from}/{to}")
	public Cambio getCambio(
		@PathVariable(name = "amount") BigDecimal amount,
		@PathVariable(name = "from") String from,
		@PathVariable(name = "to") String to
	) {
		var port = environment.getProperty("local.server.port");
		
		return new Cambio(1L, from, to, BigDecimal.ONE, BigDecimal.ONE, port);
	}
	
}

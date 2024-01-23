package br.com.erudio.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.erudio.response.Cambio;

@FeignClient(name = "cambio-service")
public interface CambioProxy {

	@GetMapping(path = "/cambio-service/{amount}/{from}/{to}")
	public Cambio getCambio(
		@PathVariable(name = "amount") Double amount,
		@PathVariable(name = "from") String from,
		@PathVariable(name = "to") String to
	);
	
}

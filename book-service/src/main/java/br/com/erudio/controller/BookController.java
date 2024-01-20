package br.com.erudio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.erudio.model.Book;
import br.com.erudio.proxy.CambioProxy;
import br.com.erudio.repository.BookRepository;

@RestController
@RequestMapping(path = "/book-service")
public class BookController {
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private BookRepository repository;
	
	@Autowired
	private CambioProxy proxy;

	@GetMapping(path = "/{id}/{currency}")
	public Book findBook(
		@PathVariable(name = "id") Long id,
		@PathVariable(name = "currency") String currency
	) {
		var book = repository.findById(id).orElseThrow(() -> new RuntimeException("Book not fond"));
		
		var cambio = proxy.getCambio(book.getPrice(), "USD", currency);
		
		var port = environment.getProperty("local.server.port");
		
		book.setEnvironment("FEIGN: " + port);
		book.setPrice(cambio.getConvertedValue());
		
		return book;
	}
	
}

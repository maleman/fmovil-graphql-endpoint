package ni.app.nica.fmovil.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import ni.app.nica.fmovil.entity.Product;
import ni.app.nica.fmovil.repository.ProductRepository;

@Controller
public class ProductController {
	
	private final ProductRepository repository;

	public ProductController(ProductRepository repository) {
		super();
		this.repository = repository;
		// TODO Auto-generated constructor stub
	}
	
	@QueryMapping
	Product productById(@Argument int id) {
		return repository.findById(id).orElseGet(null);
	}
	
	@QueryMapping
	List<Product> getProducts(@Argument int count) {
		var Products = new ArrayList<Product>();
		Iterable <Product> it = repository.findAll();
		it.forEach(Products::add);
		return Products.stream().limit(count).toList();
	}
	
	@MutationMapping
	Product createProduct(@Argument String code, @Argument String name, @Argument String uom, @Argument Float price) {
		return repository.save(new Product(null,code,name,uom, price));
	}
}

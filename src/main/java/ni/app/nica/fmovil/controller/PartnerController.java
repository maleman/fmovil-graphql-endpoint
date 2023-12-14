package ni.app.nica.fmovil.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import ni.app.nica.fmovil.entity.Partner;
import ni.app.nica.fmovil.repository.PartnerRepository;

@Controller
public class PartnerController {

	private final PartnerRepository repository;

	public PartnerController(PartnerRepository repository) {
		this.repository = repository;

	}

	@QueryMapping
	Partner partnerById(@Argument int id) {
		return repository.findById(id).orElseGet(null);
	}
	
	@QueryMapping
	List<Partner> getPartners(@Argument int count) {
		var partners = new ArrayList<Partner>();
		Iterable <Partner> it = repository.findAll();
		it.forEach(System.out::println);
		it.forEach(partners::add);
		return partners.stream().limit(count).toList();
	}
	
	@MutationMapping
	Partner createPartner(@Argument String documentNumber, @Argument String firstName, @Argument String lastName) {
		return repository.save(new Partner(null, documentNumber, firstName,lastName, ""));
	}

}

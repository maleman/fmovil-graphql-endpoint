package ni.app.nica;

import java.time.ZonedDateTime;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import ni.app.nica.fmovil.controller.InvoiceController;
import ni.app.nica.fmovil.entity.Invoice;
import ni.app.nica.fmovil.entity.InvoiceLine;
import ni.app.nica.fmovil.entity.Partner;
import ni.app.nica.fmovil.entity.Product;
import ni.app.nica.fmovil.repository.InvoiceRepository;
import ni.app.nica.fmovil.repository.PartnerRepository;
import ni.app.nica.fmovil.repository.ProductRepository;

@Component
public class DataBaseLoader implements CommandLineRunner {

	private final PartnerRepository partnerRepository;
	private final ProductRepository productRepository;
	private final InvoiceRepository invoiceRepository;
	public DataBaseLoader(PartnerRepository partnerRepository, ProductRepository productRepository,
			InvoiceRepository invoiceRepository) {
		this.partnerRepository = partnerRepository;
		this.productRepository = productRepository;
		this.invoiceRepository = invoiceRepository;

	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub

		Set<Partner> partners = Set.of(new Partner(null, "00000-000", "Joe", "White", ""),
				new Partner(null, "00000-001", "Jhon", "Targaryen", ""),
				new Partner(null, "00000-002", "Samuel", "Tarly", ""));

		Set<Product> products = Set.of(new Product(null, "001", "Nitrogen fertilizers", "K/GR", 9.09F),
				new Product(null, "002", "Phosphorus fertilizers", "K/GR", 11.09F),
				new Product(null, "003", "Calcium, magnesium and sulphur Fertilizers", "K/GR", 19.09F));

		partners.forEach(partnerRepository::save);
		products.forEach(productRepository::save);

		var partner = partnerRepository.findById(1).orElse(null);
		var product = productRepository.findById(2).orElse(null);
		var product1 = productRepository.findById(1).orElse(null);

		var invoice = new Invoice(null, "00000-001", "Test Invoice",
				ZonedDateTime.now().format(InvoiceController.FORMATTER), 0.00f, 0.00f, 0.00f, partner);

		invoice.getInvoiceLine().add(new InvoiceLine(null, 5f, 9.09F, 0.00f, 45.45f, 45.45f, product, invoice));
		invoice.getInvoiceLine().add(new InvoiceLine(null, 10f, 9.09F, 0.00f, 90.90f, 90.90f, product1, invoice));

		invoice = invoiceRepository.save(invoice);

	}

}

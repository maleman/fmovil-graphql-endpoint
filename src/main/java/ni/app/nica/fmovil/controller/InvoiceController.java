package ni.app.nica.fmovil.controller;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import ni.app.nica.fmovil.entity.Invoice;
import ni.app.nica.fmovil.entity.InvoiceLine;
import ni.app.nica.fmovil.entity.Partner;
import ni.app.nica.fmovil.entity.Product;
import ni.app.nica.fmovil.inputObject.InputObjectInvoiceLine;
import ni.app.nica.fmovil.repository.InvoiceLineRepository;
import ni.app.nica.fmovil.repository.InvoiceRepository;
import ni.app.nica.fmovil.repository.PartnerRepository;
import ni.app.nica.fmovil.repository.ProductRepository;

@Controller
public class InvoiceController {
	
	private final InvoiceRepository repository;
	private final PartnerRepository partnerRepository;
	private final InvoiceLineRepository invoiceLineRepository;
	private final ProductRepository productRepository; 
	
	public static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd HH:mm:ss.SSSSSS Z");
	
	public InvoiceController(InvoiceRepository repository, PartnerRepository partnerRepository
			, InvoiceLineRepository invoiceLineRepository, ProductRepository productRepository) {
		this.repository = repository;
		this.partnerRepository = partnerRepository;
		this.invoiceLineRepository = invoiceLineRepository;
		this.productRepository = productRepository;
	}

	@QueryMapping
	Invoice invoiceById(@Argument int id) {
		return repository.findById(id).orElseGet(null);
	}
	
	@QueryMapping
	List<Invoice> getInvoices(@Argument int count) {
		var invoices = new ArrayList<Invoice>();
		Iterable <Invoice> it = repository.findAll();
		it.forEach(invoices::add);
		for(var inv : invoices) {
			var lines = invoiceLineRepository.findByInvoice(inv);
			inv.setInvoiceLine(lines);
		}
		return invoices.stream().limit(count).toList();
	}
	
	
	@QueryMapping
	List<InvoiceLine> getInvoiceLines(@Argument int id) {
		Invoice inv = repository.findById(id).orElseGet(null);
		return invoiceLineRepository.findByInvoice(inv);
	}
	
	@MutationMapping
	Invoice createInvoice(@Argument String documentNo, @Argument String description, @Argument Float grandTotal,@Argument int partnerId) {
		Partner partner = partnerRepository.findById(partnerId).orElse(null);	
		return repository.save(new Invoice(null,documentNo,description, ZonedDateTime.now().format(FORMATTER), 0.00f, 0.00f, 0.00f, partner));
	}
	
	@MutationMapping
	InvoiceLine createInvoiceLine(@Argument int invocieId, @Argument int productId, @Argument Float lineQty, @Argument Float productPrice) {
		
		Float lineNetAmt = lineQty * productPrice;
		Float lineTaxAmt = lineNetAmt * 0.15f;
		Float lineTotalAmt = lineNetAmt + lineTaxAmt; 
		
		Product product = productRepository.findById(productId).orElse(null);
		Invoice invoice = repository.findById(invocieId).orElse(null);
		try {
			InvoiceLine line = new InvoiceLine(null,lineQty, product.getPrice(), lineTaxAmt, lineNetAmt, lineTotalAmt,product,invoice);
			
			invoice.setTotalLines(invoice.getTotalLines() + lineNetAmt);
			invoice.setTaxAmt(invoice.getTaxAmt() + lineTaxAmt );
			invoice.setGrandTotal(invoice.getGrandTotal() + lineTotalAmt);
			
			line = invoiceLineRepository.save(line);
			repository.save(invoice);
			
			return line;
		}catch(Exception e) {
			
		}
		return null;
		
	}
	
	@MutationMapping
	Invoice createInvoiceFull(@Argument String documentNo, @Argument String description, @Argument Float grandTotal,
			@Argument int partnerId, @Argument List<InputObjectInvoiceLine> inLines) {
		
		try {
			Partner partner = partnerRepository.findById(partnerId).orElse(null);
			if(partner == null)
				throw new Exception("Partner not found.");
			
			var invoice = new Invoice(null,documentNo,description, ZonedDateTime.now().format(FORMATTER), 0.00f, 0.00f, 0.00f, partner);
			List<InvoiceLine> lines = new ArrayList<>();
			for(var in : inLines) {
				
				Product product = productRepository.findById(in.productId()).orElse(null);
				
				Float lineNetAmt = in.lineQty() * product.getPrice();
				Float lineTaxAmt = lineNetAmt * 0.15f;
				Float lineTotalAmt = lineNetAmt + lineTaxAmt; 
				
				
				var line = new InvoiceLine(null,in.lineQty(),product.getPrice(), lineTaxAmt,lineNetAmt,lineTotalAmt,product,invoice);
				lines.add(line);
			}
			invoice.setInvoiceLine(lines);
			invoice.setTotalLines(lines.stream().map(il -> il.getLineNetAmt()).reduce(0.0f, (a,b) -> a+b));
			invoice.setTaxAmt(lines.stream().map(il -> il.getLineTaxAmt()).reduce(0.0f, (a,b) -> a+b));
			invoice.setGrandTotal(lines.stream().map(il -> il.getLineTotalAmt()).reduce(0.0f, (a,b) -> a+b));
			invoice = repository.save(invoice);
			return invoice;
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	
	

}

package ni.app.nica.fmovil.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ni.app.nica.fmovil.entity.Invoice;
import ni.app.nica.fmovil.entity.InvoiceLine;

public interface InvoiceLineRepository extends CrudRepository<InvoiceLine, Integer>{
	
	List<InvoiceLine> findByInvoice(Invoice invoice);

}

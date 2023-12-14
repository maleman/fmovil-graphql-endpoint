package ni.app.nica.fmovil.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode
public class InvoiceLine {

	private 
	@Id @GeneratedValue 
	Integer id;

	private Float lineQty;
	private Float productPrice;
	private Float lineTaxAmt;
	private Float lineNetAmt;
	private Float lineTotalAmt;

	@ManyToOne
	@JoinColumn(name = "product_id", referencedColumnName = "id", nullable=false)
	private Product product;

	@ManyToOne
	@JoinColumn(name = "invoice_id", referencedColumnName = "id", nullable=false)
	private Invoice invoice;

	public InvoiceLine() {
		super();
		// TODO Auto-generated constructor stub
	}

	public InvoiceLine(Integer id) {
		this();
		this.id = id;
		// TODO Auto-generated constructor stub
	}

	public InvoiceLine(Integer id, Float lineQty,Float productPrice, Float lineTaxAmt, Float lineNetAmt, Float lineTotalAmt,
			Product product, Invoice invoice) {
		this(id);
		this.lineQty = lineQty;
		this.productPrice = productPrice;
		this.lineTaxAmt = lineTaxAmt;
		this.lineNetAmt = lineNetAmt;
		this.lineTotalAmt = lineTotalAmt;
		this.product = product;
		this.invoice = invoice;
	}
	

}

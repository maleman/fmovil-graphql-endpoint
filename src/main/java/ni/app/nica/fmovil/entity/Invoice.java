package ni.app.nica.fmovil.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Invoice {

	private @Id @GeneratedValue Integer id;
	
	@Column(nullable = false)
	private String documentNo;
	
	@Column(nullable = false)
	private String description;
	
	@Column(nullable = false)
	private String invoiceDate;
	
	@Column(nullable = false)
	private Float totalLines;
	
	@Column(nullable = false)
	private Float taxAmt;
	
	@Column(nullable = false)
	private Float grandTotal;

	// @OneToOne(cascade = CascadeType.MERGE)
	// @JoinColumn(name = "partner_id", referencedColumnName = "id")
	@ManyToOne
	private Partner partner;

	@OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<InvoiceLine> invoiceLine = new ArrayList<>();


	public Invoice(Integer id) {
		this();
		// TODO Auto-generated constructor stub
		this.id = id;
	}

	public Invoice(Integer id, String documentNo, String description, String invoiceDate, Float totalLines,
			Float taxAmt, Float grandTotal, Partner partner) {
		this(id);
		this.documentNo = documentNo;
		this.description = description;
		this.invoiceDate = invoiceDate;
		this.totalLines = totalLines;
		this.taxAmt = taxAmt;
		this.grandTotal = grandTotal;
		this.partner = partner;
	}

}

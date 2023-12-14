package ni.app.nica.fmovil.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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
public class Product {
	private @Id @GeneratedValue Integer id; 
	private String code;
	private String name;
	private String uom;
	private Float price;
	
	
	public Product(Integer id) {
		this();
		// TODO Auto-generated constructor stub
		this.id = id;
	}


	public Product(Integer id,String code, String name, String uom, Float price) {
		this(id);
		this.code = code;
		this.name = name;
		this.uom = uom;
		this.price = price;
	}
	
	
}

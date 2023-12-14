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
public class Partner {

	private @Id @GeneratedValue Integer id; 
	private String documentNumber;
	private String firstName;
	private String lastName;
	private String address;
	
	
	public Partner(Integer id) {
		this();
		this.id = id;
	}

	public Partner( Integer id, String documentNumber, String firstName, String lastName, String address) {
		this(id);
		this.documentNumber = documentNumber;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
	}
	
}

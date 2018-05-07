package au.com.adepto.demo;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Table(schema="TAKEAWAY_SHOP", name = "ROLES")
public class Role {

  @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String description;

  public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}

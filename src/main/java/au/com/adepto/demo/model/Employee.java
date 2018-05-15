package au.com.adepto.demo.model;

import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(schema = "TAKEAWAY_SHOP", name = "STAFF")
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(name = "max_weekly_hours")
	private int maxWeeklyHours;
	@JoinTable(schema = "TAKEAWAY_SHOP", name = "STAFF_ROLES", joinColumns = @JoinColumn(name = "id_employee", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "id_role", referencedColumnName = "id"))
	@OneToMany
	private List<Role> roles;

	@ElementCollection
	@CollectionTable(schema = "TAKEAWAY_SHOP", name = "STAFF_UNAVAILABILITY", joinColumns = @JoinColumn(name = "id_employee"))
	@Column(name = "day")
	private List<String> unavailableDays;

	public Employee() {
	};

	public Employee(int maxWeeklyHours, List<Role> roles, List<String> unavailableDays) {
		super();
		this.maxWeeklyHours = maxWeeklyHours;
		this.roles = roles;
		this.unavailableDays = unavailableDays;
	}

	public Employee(Long id, int maxWeeklyHours, List<Role> roles, List<String> unavailableDays) {
		super();
		this.id = id;
		this.maxWeeklyHours = maxWeeklyHours;
		this.roles = roles;
		this.unavailableDays = unavailableDays;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getMaxWeeklyHours() {
		return maxWeeklyHours;
	}

	public void setMaxWeeklyHours(int maxWeeklyHours) {
		this.maxWeeklyHours = maxWeeklyHours;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public List<String> getUnavailableDays() {
		return unavailableDays;
	}

	public void setUnavailableDays(List<String> unavailableDays) {
		this.unavailableDays = unavailableDays;
	}

}

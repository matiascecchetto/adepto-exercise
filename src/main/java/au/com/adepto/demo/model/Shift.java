package au.com.adepto.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(schema = "TAKEAWAY_SHOP", name = "SHIFTS")
public class Shift {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String day;
	private int hours;
	@OneToOne
	@JoinColumn(name = "req_role")
	private Role role;
	@OneToOne
	@JoinColumn(name = "assignee")
	private Employee assignee;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public int getHours() {
		return hours;
	}

	public void setHours(int hours) {
		this.hours = hours;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Employee getAssignee() {
		return assignee;
	}

	public void setAssignee(Employee assignee) {
		this.assignee = assignee;
	}

}

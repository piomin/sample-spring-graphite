package pl.piomin.graphite.service.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Person implements Serializable {

	private static final long serialVersionUID = 3214253910554454648L;

	@Id
	@GeneratedValue
	private Integer id;
	private String firstName;
	private String lastName;
	private String pesel;
	private int age;

	public Person() {

	}

	public Person(String firstName, String lastName, String pesel, int age) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.pesel = pesel;
		this.age = age;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPesel() {
		return pesel;
	}

	public void setPesel(String pesel) {
		this.pesel = pesel;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "Person [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", pesel=" + pesel + "]";
	}

}

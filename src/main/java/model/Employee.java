package model;

import java.util.Date;

public class Employee {
	
	
	private int id;
	private String name;
	private String email;
	private String phone;
	private Date birthday;
	
	public Employee(int id, String name, String email, String phone, Date birthday) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.birthday = birthday;
	}
	
	
	
	public Employee(String name, String email, String phone, Date birthday) {
		super();
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.birthday = birthday;
	}



	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

}

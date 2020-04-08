package com.safetynet.alerts.model;

import java.util.List;

import org.springframework.stereotype.Component;

@Component("endPointChildAlertDetail")
public class EndPointChildAlertDetail {

	private String firstName;
	private String lastName;
	private int age;
	private List<String> firstNameMembers;
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
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public List<String> getFirstNameMembers() {
		return firstNameMembers;
	}
	public void setFirstNameMembers(List<String> firstNameMembers) {
		this.firstNameMembers = firstNameMembers;
	}
}

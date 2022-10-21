package com.packt.cardatabase.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Ownner {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) // 此处不要用AUTO
	private long ownnerid;
	private String firstname, lastname;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="ownner")
	@JsonIgnore
	private List<Car> cars;
	
	public Ownner(String firstname, String lastname) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
	}
	public Ownner() {
	}
	
	public long getOwnerid() {
		return ownnerid;
	}
	public void setOwnerid(long ownerid) {
		this.ownnerid = ownerid;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public List<Car> getCars() {
		return cars;
	}
	public void setCars(List<Car> cars) {
		this.cars = cars;
	}
	
}

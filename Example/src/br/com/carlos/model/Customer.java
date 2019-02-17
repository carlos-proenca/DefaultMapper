package br.com.carlos.model;

import br.com.carlos.annotations.CreateMapper;
import br.com.carlos.annotations.MapTo;
import br.com.carlos.dto.CustomerDTO;

@CreateMapper(CustomerDTO.class)
public class Customer {
	
	@MapTo("otherName")
	private String name;

	@MapTo("otherEmail")
	private String mail;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	@Override
	public String toString() {
		return "Customer [name=" + name + ", mail=" + mail + "]";
	}
}

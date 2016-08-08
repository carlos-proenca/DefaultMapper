package br.com.carlos.defaultmapper.util;

import br.com.carlos.defaultmapper.anottations.Mapped;

public class OtherAddress {
	
	@Mapped("dtoStreet")
	private String street;
	
	@Mapped("dtoNumber")
	private int number;

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	@Override
	public String toString() {
		return "Address [street=" + street + ", number=" + number + "]";
	}
}

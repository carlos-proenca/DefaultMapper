package br.com.carlos.defaultmapper.util;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class CustomerDTO {

	private String dtoName;
	private String dtoPhone;
	private Long dtoDocument;
	private String dtoStreet;
	private int dtoNumber;
	
	private Address dtoAddress;

	public String getDtoName() {
		return dtoName;
	}

	public void setDtoName(String dtoName) {
		this.dtoName = dtoName;
	}

	public String getDtoPhone() {
		return dtoPhone;
	}

	public void setDtoPhone(String dtoPhone) {
		this.dtoPhone = dtoPhone;
	}

	public Long getDtoDocument() {
		return dtoDocument;
	}

	public void setDtoDocument(Long dtoDocument) {
		this.dtoDocument = dtoDocument;
	}

	public String getDtoStreet() {
		return dtoStreet;
	}

	public void setDtoStreet(String dtoStreet) {
		this.dtoStreet = dtoStreet;
	}

	public int getDtoNumber() {
		return dtoNumber;
	}

	public void setDtoNumber(int dtoNumber) {
		this.dtoNumber = dtoNumber;
	}

	public Address getDtoAddress() {
		return dtoAddress;
	}

	public void setDtoAddress(Address dtoAddress) {
		this.dtoAddress = dtoAddress;
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
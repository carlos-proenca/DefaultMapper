package br.com.carlos.defaultmapper.util;

import lombok.Data;

@Data
public class OtherCustomerDTO {

	private String otherDtoName;
	private String otherDtoPhone;
	private Long otherDtoDocument;
	private String otherDtoStreet;
	private int otherDtoNumber;
	
	private OtherAddress otherDtoAddress;
}
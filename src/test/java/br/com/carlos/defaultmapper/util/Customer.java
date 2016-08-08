package br.com.carlos.defaultmapper.util;

import lombok.Data;

@Data
public class Customer {

//	@Mapped("dtoName")
	private String name;
//	@Mapped("dtoPhone")
	private String phone;
//	@Mapped("dtoDocument")
	private Long document;
	
//	@EmbeddedMapped(isSameClass=true)
	private Address address;	
}
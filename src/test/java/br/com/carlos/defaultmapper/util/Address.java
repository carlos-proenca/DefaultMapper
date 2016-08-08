package br.com.carlos.defaultmapper.util;

import br.com.carlos.defaultmapper.anottations.Mapped;
import lombok.Data;

@Data
public class Address {
	
	@Mapped("dtoStreet")
	private String street;
	
	@Mapped("dtoNumber")
	private int number;
}
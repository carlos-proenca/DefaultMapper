package br.com.carlos.defaultmapper.util;

import br.com.carlos.defaultmapper.anottations.EmbeddedMappedFrom;
import br.com.carlos.defaultmapper.anottations.MappedFrom;
import lombok.Data;

@Data
public class CustomerDTO {

	@MappedFrom("name")
	private String dtoName;
	@MappedFrom("phone")
	private String dtoPhone;
	@MappedFrom("document")
	private long dtoDocument;
	
//	@EmbeddedMappedFrom(fieldName = "street", fieldTargetName="address", isSameClass=true)
	private String dtoStreet;

//	@EmbeddedMappedFrom(fieldName = "number", fieldTargetName="address", isSameClass=true)
	private int dtoNumber;
	
	@EmbeddedMappedFrom(fieldName = "address", isSameClass=false)
	private Address dtoAddress;
}
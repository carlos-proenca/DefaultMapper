package br.com.carlos.defaultmapper;


import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import br.com.carlos.defaultmapper.memory.DefaultMapperInMemory;
import br.com.carlos.defaultmapper.util.Address;
import br.com.carlos.defaultmapper.util.Customer;
import br.com.carlos.defaultmapper.util.CustomerDTO;

/**
 * The Default Mapper In MemoryTest Type
 * 
 * @author Carlos Proença (carlos_proenca@live.com)
 *
 */
public final class DefaultMapperInMemoryTest {
	Customer customer;
	
	@Before
	public void setUp(){
		customer = new Customer();
		customer.setName("Carlos");
		customer.setDocument(123456l);
		customer.setPhone("(12)988457878");

		Address address =  new Address();
		address.setStreet("Scultor");
		address.setNumber(140);

		customer.setAddress(address);
	}
	
	@Test
	public void mapSuccessTest(){
		DefaultMapper defaultMapper = new DefaultMapperInMemory();
		CustomerDTO customerDTO = defaultMapper.map(customer, CustomerDTO.class);
		assertThat(customerDTO, is(equalTo(getExpectedCustomerDTO())));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void mapInvalidParameterTest(){
		DefaultMapper defaultMapper = new DefaultMapperInMemory();
		defaultMapper.map(null, CustomerDTO.class);
	}

	@Test
	public void mapInvalidClassTypeTest(){
		DefaultMapper defaultMapper = new DefaultMapperInMemory();
		DefaultMapper defaultMapperInstance = defaultMapper.map(customer, DefaultMapper.class);
		assertThat(defaultMapperInstance, is(nullValue()));
	}
	
	
	private CustomerDTO getExpectedCustomerDTO(){
		CustomerDTO expectedCustomerDTO = new CustomerDTO();
		expectedCustomerDTO.setDtoName("Carlos");
		expectedCustomerDTO.setDtoPhone("(12)988457878");
		expectedCustomerDTO.setDtoDocument(123456l);
//		expectedCustomerDTO.setDtoStreet("Scultor");
//		expectedCustomerDTO.setDtoNumber(140);
		
		Address address =  new Address();
		address.setStreet("Scultor");
		address.setNumber(140);

		expectedCustomerDTO.setDtoAddress(address);

		return expectedCustomerDTO;
	}
}
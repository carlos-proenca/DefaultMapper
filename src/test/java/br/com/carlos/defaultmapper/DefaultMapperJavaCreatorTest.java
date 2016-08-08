package br.com.carlos.defaultmapper;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import br.com.carlos.defaultmapper.creator.DefaultMapperJavaCreator;
import br.com.carlos.defaultmapper.util.Address;
import br.com.carlos.defaultmapper.util.Customer;
import br.com.carlos.defaultmapper.util.CustomerDTO;

/**
 * The Default Mapper Java Creator Test Type
 * 
 * @author Carlos Proença (carlos_proenca@live.com)
 *
 */
public final class DefaultMapperJavaCreatorTest {
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
		DefaultMapper defaultMapper = new DefaultMapperJavaCreator();
		CustomerDTO customerDTO = defaultMapper.map(customer, CustomerDTO.class);
		assertThat(customerDTO, is(equalTo(getExpectedCustomerDTO())));
	}	
	
	private CustomerDTO getExpectedCustomerDTO(){
		CustomerDTO expectedCustomerDTO = new CustomerDTO();
		expectedCustomerDTO.setDtoName("Carlos");
		expectedCustomerDTO.setDtoPhone("(12)988457878");
		expectedCustomerDTO.setDtoDocument(123456l);
		
		Address address =  new Address();
		address.setStreet("Scultor");
		address.setNumber(140);

		return expectedCustomerDTO;
	}
}
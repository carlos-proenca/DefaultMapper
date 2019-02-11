package br.com.carlos;


import org.junit.Before;
import org.junit.Test;

/**
 * Test of Create Mapper Test class.
 * 
 * Tests business logic of annotation processor class creation
 * 
 * @author Carlos Proença (carlos_proenca@live.com)
 *
 */
public final class CreateMapperTest {
	
	@Before
	public void setUp(){
	}
	
	@Test
	public void mapSuccessTest(){
//		DefaultMapper defaultMapper = new DefaultMapperInMemory();
//		CustomerDTO customerDTO = defaultMapper.map(customer, CustomerDTO.class);
//		assertThat(customerDTO, is(equalTo(getExpectedCustomerDTO())));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void mapInvalidParameterTest(){
//		DefaultMapper defaultMapper = new DefaultMapperInMemory();
//		defaultMapper.map(null, CustomerDTO.class);
	}

	@Test
	public void mapInvalidClassTypeTest(){
//		DefaultMapper defaultMapper = new DefaultMapperInMemory();
//		DefaultMapper defaultMapperInstance = defaultMapper.map(customer, DefaultMapper.class);
//		assertThat(defaultMapperInstance, is(nullValue()));
	}
}
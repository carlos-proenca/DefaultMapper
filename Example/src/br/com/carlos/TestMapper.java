package br.com.carlos;

import br.com.carlos.dto.CustomerDTOMapper;
import br.com.carlos.model.Customer;


/**
 * This is a simple implementation demonstrate how to use the default mapper annotations
 * @author carlos_proenca@live.com
 *
 */
public class TestMapper {
	public static void main(String[] args) {
		Customer customer = new Customer();
		customer.setName("carlos");
		customer.setMail("carlos_proenca@live.com");
		System.out.println(" The customer Mapped is "+new CustomerDTOMapper().map(customer));
	}
}

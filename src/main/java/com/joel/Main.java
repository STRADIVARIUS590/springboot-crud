package com.joel;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
@RequestMapping("api/v1/customers")
public class Main {

	private final CustomerRepository customerRepository;
	
	public Main(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub 
		

		System.out.println("Hello");
		
		SpringApplication.run(Main.class, args);
	}
	
	
	@GetMapping()
	public List<Customer> getCustomers(){
		//return List.of();
		return customerRepository.findAll();
	}
	
	
	@PostMapping()
	public void addCustomer(@RequestBody NewCustomerRequest request) {
		Customer customer = new Customer();
		
		customer.setAge( request.age());
		customer.setEmail(request.email());
		customer.setName(request.name());
		
	 
		this.customerRepository.save(customer);
	}
	
	record NewCustomerRequest(String name, String email, int age) {}
	
	
	@DeleteMapping("{customerId}")
	public void deleteCustomer(@PathVariable("customerId") Integer id) {
		customerRepository.deleteById(id);
	}
	
	
	@PutMapping("{customerId}")
	public void updateCustomer(@RequestBody NewCustomerRequest request,@PathVariable("customerId") int id) {
		Optional<Customer> customer = customerRepository.findById(id);
		
		if(customer.isPresent()) {
			customer.get().setAge(request.age);
			customer.get().setEmail(request.email);
			customer.get().setName(request.name);
			
			customerRepository.save(customer.get());
		}
		
	}

/*	@GetMapping("/greet")
	public String greet() {
		return "<h1>Hello</h1>";
	}*/
/*	
	@GetMapping("/greet")
	public GreetResponse greet() {
		return new GreetResponse("Heello");
	}

	
	record GreetResponse(String saludo) {};

	
	@GetMapping("/greet2")
	public GreetResponse2 saludo2() {
		return new GreetResponse2("HOLA", Arrays.asList("java", "php", "javascript"), new Person("joel", 23));
	}
	
	
	record GreetResponse2(String saludo,List<String> favoriteProgrammingLang, Person persona) {}
	
	record Person(String name, int age) {};*/
}

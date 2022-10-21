package com.packt.cardatabase;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.packt.cardatabase.domain.Car;
import com.packt.cardatabase.domain.CarRepository;
import com.packt.cardatabase.domain.Ownner;
import com.packt.cardatabase.domain.OwnnerRepository;
import com.packt.cardatabase.domain.Userr;
import com.packt.cardatabase.domain.UserrRepository;

@SpringBootApplication
public class CardatabaseApplication implements CommandLineRunner {

	private static final Logger logger 
		= LoggerFactory.getLogger(CardatabaseApplication.class);
	
	@Autowired
	private CarRepository carRepository;
	@Autowired
	private OwnnerRepository ownnerRepository;
	@Autowired
	private UserrRepository userrRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(CardatabaseApplication.class, args);
		logger.warn("this is not an error log!");
	}
	@Override
	public void run(String... args) throws Exception {
		
		Ownner owner1 = new Ownner("John", "Johnson");
		Ownner owner2 = new Ownner("Mary", "Robinson");
		ownnerRepository.saveAll(Arrays.asList(owner1, owner2));
		
		// TODO Auto-generated method stub
		
		Car car1 = new Car("Ford", "Mustang", "Red", "ADF-1121", 2021, 59000, owner1);
		Car car2 = new Car("Nissan", "Leaf", "White", "SSJ-3002", 2019, 29000, owner2);
		Car car3 = new Car("Toyota", "Prius", "Silver", "KKO-0212", 2020, 39000, owner2);
		carRepository.saveAll(Arrays.asList(car1, car2, car3));
		
		for(Car car: carRepository.findByBrand("Toyota")) {
			logger.info(car.getBrand() + " " + car.getYear());
		}
		
		// Username: user, password: user
		userrRepository.save(new Userr("user", 
				"$2a$10$NVM0n8ElaRgg7zWO1CxUdei7vWoPg91Lz2aYavh9.f9q0e4bRadue", "USER"));
		// Username: admin, password: admin
		userrRepository.save(new Userr("admin",
				"$2a$10$8cjz47bjbR4Mn8GMg9IZx.vyjhLXR/SKKMSZ9.mP9vpMu0ssKi8GW", "ADMIN"));
		
	}

}

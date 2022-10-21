package com.packt.cardatabase.domain;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

//@RepositoryRestResource(path="vehicles")
@RepositoryRestResource
public interface CarRepository extends CrudRepository<Car, Long> {
	List<Car> findByColor(String color);
	List<Car> findByYear(@Param("year")int year);
	
	List<Car> findByBrandAndModel(String brand, String model);
	List<Car> findByBrandOrColor(String brand, String color);
	
	List<Car> findByBrandOrderByYearAsc(String brand);
	
	@Query("select c from Car c where c.brand = ?1")
	List<Car> findByBrand(String brand);
	
	@Query("select c from Car c where c.brand like %?1")
	List<Car> findByBrandEndsWith(String brand);
}

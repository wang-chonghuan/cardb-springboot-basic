package com.packt.cardatabase.domain;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface UserrRepository extends CrudRepository<Userr, Long> {
	Optional<Userr> findByUserrname(String userrname);
}

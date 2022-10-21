package com.packt.cardatabase.domain;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface OwnnerRepository extends CrudRepository<Ownner, Long> {
    Optional<Ownner> findByFirstname(String firstName);
}

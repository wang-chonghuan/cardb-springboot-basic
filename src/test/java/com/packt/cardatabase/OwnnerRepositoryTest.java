package com.packt.cardatabase;

import com.packt.cardatabase.domain.Ownner;
import com.packt.cardatabase.domain.OwnnerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class OwnnerRepositoryTest {
    @Autowired
    private OwnnerRepository repository;

    @Test
    void saveOwner() {
        repository.save(new Ownner("Lucy", "Smith"));
        //assertThat(true).isTrue();
        assertThat(repository.findByFirstname("Lucy").isPresent()).isTrue();
    }

    @Test
    void deleteOwners() {
        repository.save(new Ownner("Lisa", "Morrison"));
        repository.deleteAll();
        assertThat(repository.count()).isEqualTo(0);
    }
}

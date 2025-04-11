package com.sdg.countries.jpa.repositories;

import com.sdg.countries.jpa.entities.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
    Optional<Country> findByNameIgnoreCase(String name);

    List<Country> findAllByOrderByNameAsc();
}

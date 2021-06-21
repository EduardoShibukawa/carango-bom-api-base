package br.com.caelum.carangobom.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.caelum.carangobom.domain.Brand;


public interface BrandRepository extends JpaRepository<Brand, Long> {

    void delete(Brand brand);
    
    Brand save(Brand brand);
    
    Optional<Brand> findById(Long id);
    
    List<Brand> findAllByOrderByName();
    
}

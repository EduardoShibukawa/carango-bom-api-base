package br.com.caelum.carangobom.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.caelum.carangobom.domain.Brand;


public interface BrandRepository extends JpaRepository<Brand, Long> {

    public void delete(Brand brand);
    
    public Brand save(Brand brand);
    
    public Optional<Brand> findById(Long id);
    
    public List<Brand> findAllByOrderByName();
    
}

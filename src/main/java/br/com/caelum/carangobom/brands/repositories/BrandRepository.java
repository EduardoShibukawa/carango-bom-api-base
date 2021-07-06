package br.com.caelum.carangobom.brands.repositories;

import br.com.caelum.carangobom.brands.entities.Brand;
import br.com.caelum.carangobom.brands.exceptions.BrandNotFoundException;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;


public interface BrandRepository extends Repository<Brand, Long> {

    void delete(Brand brand);
    
    Brand save(Brand brand);
    
    Optional<Brand> findById(Long id);
    
    List<Brand> findAllByOrderByName();

    Optional<Brand> findByName(String name);

    default Brand findBrand(Long id){
        Optional<Brand> optional = findById(id);

        if (optional.isEmpty()) {
            throw new BrandNotFoundException();
        }

        return optional.get();
    }
}

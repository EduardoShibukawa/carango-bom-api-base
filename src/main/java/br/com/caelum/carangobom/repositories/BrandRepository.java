package br.com.caelum.carangobom.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.Repository;

import br.com.caelum.carangobom.domain.Brand;
import br.com.caelum.carangobom.exceptions.BrandNotFoundException;


public interface BrandRepository extends Repository<Brand, Long> {

    void delete(Brand brand);
    
    Brand save(Brand brand);
    
    Optional<Brand> findById(Long id);
    
    List<Brand> findAllByOrderByName();

    default Brand findBrand(Long id){
        Optional<Brand> optional = findById(id);

        if (optional.isEmpty()) {
            throw new BrandNotFoundException();
        }

        return optional.get();
    }
}

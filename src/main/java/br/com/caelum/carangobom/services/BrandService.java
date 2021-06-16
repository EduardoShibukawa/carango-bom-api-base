package br.com.caelum.carangobom.services;

import br.com.caelum.carangobom.domain.Brand;
import br.com.caelum.carangobom.repositories.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BrandService {

    private final BrandRepository brandRepository;

    @Autowired
    public BrandService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    public Optional<Brand> findById(Long id){
        return brandRepository.findById(id);
    }

    public Brand save(Brand brand){
        return brandRepository.save(brand);
    }

    public void delete(Brand brand){
        brandRepository.delete(brand);
    }

    public List<Brand> findAllByOrderByNome(){
        return brandRepository.findAllByOrderByNome();
    }
}

package br.com.caelum.carangobom.services;

import java.util.List;

import br.com.caelum.carangobom.dtos.BrandRequest;
import br.com.caelum.carangobom.dtos.BrandResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.caelum.carangobom.domain.Brand;
import br.com.caelum.carangobom.repositories.BrandRepository;

@Service
public class BrandService {

	private final BrandRepository brandRepository;

	@Autowired
	public BrandService(BrandRepository brandRepository) {
		this.brandRepository = brandRepository;
	}

	public BrandResponse findById(Long id) {
		return BrandResponse.fromModel(brandRepository.findBrand(id));
	}

	public Brand save(BrandRequest brandRequest) {
		return brandRepository.save(brandRequest.toModel());
	}

	public void delete(Long id) {
		brandRepository.delete(brandRepository.findBrand(id));
	}

	public List<Brand> findAllByOrderByName() {
		return brandRepository.findAllByOrderByName();
	}

	public Brand update(Long id, BrandRequest brandRequest) {
		Brand brand = brandRepository.findBrand(id);

		brand.setName(brandRequest.getName());

		return brandRepository.save(brand);
	}
}

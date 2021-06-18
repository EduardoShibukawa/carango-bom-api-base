package br.com.caelum.carangobom.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.caelum.carangobom.domain.Brand;
import br.com.caelum.carangobom.exceptions.BrandNotFoundException;
import br.com.caelum.carangobom.repositories.BrandRepository;

@Service
public class BrandService {

	private final BrandRepository brandRepository;

	@Autowired
	public BrandService(BrandRepository brandRepository) {
		this.brandRepository = brandRepository;
	}

	public Brand findById(Long id) {
		Optional<Brand> optional = brandRepository.findById(id);

		if (optional.isEmpty()) {
			throw new BrandNotFoundException();
		}

		return optional.get();
	}

	public Brand save(Brand brand) {
		return brandRepository.save(brand);
	}

	public void delete(Long id) {
		brandRepository.delete(findById(id));
	}

	public List<Brand> findAllByOrderByName() {
		return brandRepository.findAllByOrderByName();
	}

	public Brand update(Long id, Brand brandRequest) {
		Brand brand = findById(id);

		brand.setName(brandRequest.getName());

		return brandRepository.save(brand);
	}
}

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

	public Optional<Brand> findById(Long id) {
		return brandRepository.findById(id);
	}

	public Brand save(Brand brand) {
		return brandRepository.save(brand);
	}

	public void delete(Long id) {
		Optional<Brand> optional = findById(id);

		if (optional.isEmpty()) {
			throw new BrandNotFoundException();
		}

		brandRepository.delete(optional.get());
	}

	public List<Brand> findAllByOrderByNome() {
		return brandRepository.findAllByOrderByNome();
	}
}

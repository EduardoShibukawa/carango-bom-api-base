package br.com.caelum.carangobom.services;

import br.com.caelum.carangobom.domain.Brand;
import br.com.caelum.carangobom.dtos.BrandRequest;
import br.com.caelum.carangobom.dtos.BrandResponse;
import br.com.caelum.carangobom.repositories.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BrandService {

	private final BrandRepository brandRepository;

	@Autowired
	public BrandService(BrandRepository brandRepository) {
		this.brandRepository = brandRepository;
	}

	@Transactional(readOnly = true)
	public BrandResponse findById(Long id) {
		return BrandResponse
					.fromModel(brandRepository.findBrand(id));
	}

	public BrandResponse save(BrandRequest brandRequest) {
		return BrandResponse.fromModel(
			brandRepository.save(brandRequest.toModel())
		);
	}

	public void delete(Long id) {
		brandRepository.delete(brandRepository.findBrand(id));
	}

	@Transactional(readOnly = true)
	public List<BrandResponse> findAllByOrderByName() {
		return brandRepository
				.findAllByOrderByName()
				.stream()
				.map(b -> new BrandResponse(b.getId(), b.getName()))
				.collect(Collectors.toUnmodifiableList());
	}

	public BrandResponse update(Long id, BrandRequest brandRequest) {
		Brand brand = brandRepository.findBrand(id);

		brand.setName(brandRequest.getName());

		return BrandResponse.fromModel(brandRepository.save(brand));
	}
}

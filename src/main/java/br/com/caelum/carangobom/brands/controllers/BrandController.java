package br.com.caelum.carangobom.brands.controllers;

import br.com.caelum.carangobom.brands.exceptions.BrandNotFoundException;
import br.com.caelum.carangobom.brands.dtos.BrandRequest;
import br.com.caelum.carangobom.brands.dtos.BrandResponse;
import br.com.caelum.carangobom.brands.services.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("brands")
@CrossOrigin(origins = "*", maxAge = 3600)
public class BrandController {
	
    private final BrandService brandService;
    
    @Autowired
    public BrandController(BrandService brandService) {

        this.brandService = brandService;
    }

    @GetMapping
    @Cacheable(value = "listOfBrands")
    public List<BrandResponse> getAll() {
        return brandService.findAllByOrderByName();
    }

    @GetMapping("/{id}")
    @Cacheable(value = "brandById")
    public ResponseEntity<BrandResponse> findById(@PathVariable Long id) {
    	try {
    		return ResponseEntity.ok(brandService.findById(id));
    	} catch (BrandNotFoundException e) {
    		return ResponseEntity.notFound().build();
		}
    }

    @PostMapping
    @CacheEvict(value = {"brandById", "listOfBrands"}, allEntries = true)
    public ResponseEntity<BrandResponse> save(@Valid @RequestBody BrandRequest brandRequest, UriComponentsBuilder uriBuilder) {
    	BrandResponse brandResponse = brandService.save(brandRequest);
        
        URI uri = uriBuilder
        			.path("/brands/{id}")
        			.buildAndExpand(brandResponse.getId())
        			.toUri();
        
        return ResponseEntity.created(uri).body(brandResponse);
    }

    @PutMapping("/{id}")
    @CacheEvict(value = {"brandById", "listOfBrands"}, allEntries = true)
    public ResponseEntity<BrandResponse> update(@PathVariable Long id, @Valid @RequestBody BrandRequest brandRequest) {
    	try {
    		final BrandResponse brandResponse = brandService.update(id, brandRequest);
    		
    		return ResponseEntity.ok(brandResponse);
    	} catch (BrandNotFoundException e) {
    		return ResponseEntity.notFound().build();
		}
    }

    @DeleteMapping("/{id}")
    @CacheEvict(value = {"brandById", "listOfBrands"}, allEntries = true)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
    	try {
            brandService.delete(id);
            
            return ResponseEntity.ok().build();
    	}
    	catch (BrandNotFoundException e) {
    		return ResponseEntity.notFound().build();
		}
    }
}
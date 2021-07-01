package br.com.caelum.carangobom.controllers;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.caelum.carangobom.dtos.BrandRequest;
import br.com.caelum.carangobom.dtos.BrandResponse;
import br.com.caelum.carangobom.exceptions.BrandNotFoundException;
import br.com.caelum.carangobom.services.BrandService;

@RestController
@RequestMapping("brands")
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
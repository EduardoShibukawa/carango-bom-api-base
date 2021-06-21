package br.com.caelum.carangobom.controllers;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.caelum.carangobom.domain.Brand;
import br.com.caelum.carangobom.exceptions.BrandNotFoundException;
import br.com.caelum.carangobom.services.BrandService;
import br.com.caelum.carangobom.validation.OutPutParameterListErrorDto;
import br.com.caelum.carangobom.validation.OutputParameterErrorDto;

@Controller
@RequestMapping("brands")
public class BrandController {
	
    private  final BrandService brandService;
    
    @Autowired
    public BrandController(BrandService brandService) {

        this.brandService = brandService;
    }

    @GetMapping
    @ResponseBody
    @Transactional
    public List<Brand> getAll() {
        return brandService.findAllByOrderByName();
    }

    @GetMapping("/{id}")
    @ResponseBody
    @Transactional
    public ResponseEntity<Brand> findById(@PathVariable Long id) {
    	try {
    		return ResponseEntity.ok(brandService.findById(id));
    	} catch (BrandNotFoundException e) {
    		return ResponseEntity.notFound().build();
		}
    }

    @PostMapping
    @ResponseBody
    @Transactional
    public ResponseEntity<Brand> save(@Valid @RequestBody Brand brandRequest, UriComponentsBuilder uriBuilder) {
        Brand brand = brandService.save(brandRequest);
        
        URI uri = uriBuilder
        			.path("/brands/{id}")
        			.buildAndExpand(brandRequest.getId())
        			.toUri();
        
        return ResponseEntity.created(uri).body(brand);
    }

    @PutMapping("/{id}")
    @ResponseBody
    @Transactional
    public ResponseEntity<Brand> update(@PathVariable Long id, @Valid @RequestBody Brand brandRequest) {
    	try {
    		final Brand brand = brandService.update(id, brandRequest);
    		
    		return ResponseEntity.ok(brand);
    	} catch (BrandNotFoundException e) {
    		return ResponseEntity.notFound().build();
		}
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    @Transactional
    public ResponseEntity<Brand> delete(@PathVariable Long id) {
    	try {
            brandService.delete(id);
            
            return ResponseEntity.ok().build();
    	}
    	catch (BrandNotFoundException e) {
    		return ResponseEntity.notFound().build();
		}
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public OutPutParameterListErrorDto validate(MethodArgumentNotValidException exception) {
        List<OutputParameterErrorDto> paramExceptionList = new ArrayList<>();
        OutPutParameterListErrorDto exceptionList = new OutPutParameterListErrorDto();
        
        exception.getBindingResult()
        	.getFieldErrors()
        	.forEach(e -> {
	            OutputParameterErrorDto d = new OutputParameterErrorDto();
	            
	            d.setParameter(e.getField());
	            d.setMessage(e.getDefaultMessage());
	            
	            paramExceptionList.add(d);
	        });
        
        
        exceptionList.setErrors(paramExceptionList);
        
        return exceptionList;
    }
}
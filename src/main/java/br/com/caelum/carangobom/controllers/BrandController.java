package br.com.caelum.carangobom.controllers;

import br.com.caelum.carangobom.domain.Brand;
import br.com.caelum.carangobom.exceptions.BrandNotFoundException;
import br.com.caelum.carangobom.services.BrandService;
import br.com.caelum.carangobom.validacao.ErroDeParametroOutputDto;
import br.com.caelum.carangobom.validacao.ListaDeErrosOutputDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class BrandController {
    private  final BrandService brandService;
    @Autowired
    public BrandController(BrandService brandService) {

        this.brandService = brandService;
    }

    @GetMapping("/brands")
    @ResponseBody
    @Transactional
    public List<Brand> getAll() {
        return brandService.findAllByOrderByNome();
    }

    @GetMapping("/brands/{id}")
    @ResponseBody
    @Transactional
    public ResponseEntity<Brand> findById(@PathVariable Long id) {
        Optional<Brand> optional = brandService.findById(id);

        if (optional.isPresent()) {
            return ResponseEntity.ok(optional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/brands")
    @ResponseBody
    @Transactional
    public ResponseEntity<Brand> save(@Valid @RequestBody Brand brandRequest, UriComponentsBuilder uriBuilder) {
        Brand brand = brandService.save(brandRequest);
        URI h = uriBuilder.path("/brands/{id}").buildAndExpand(brandRequest.getId()).toUri();
        return ResponseEntity.created(h).body(brand);
    }

    @PutMapping("/brands/{id}")
    @ResponseBody
    @Transactional
    public ResponseEntity<Brand> update(@PathVariable Long id, @Valid @RequestBody Brand brandRequest) {
        Optional<Brand> optional = brandService.findById(id);
        if (optional.isPresent()) {
            Brand brand = optional.get();
            brand.setNome(brandRequest.getNome());
            return ResponseEntity.ok(brand);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/brands/{id}")
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
    public ListaDeErrosOutputDto validate(MethodArgumentNotValidException excecao) {
        List<ErroDeParametroOutputDto> paramExceptionList = new ArrayList<>();
        excecao.getBindingResult().getFieldErrors().forEach(e -> {
            ErroDeParametroOutputDto d = new ErroDeParametroOutputDto();
            d.setParametro(e.getField());
            d.setMensagem(e.getDefaultMessage());
            paramExceptionList.add(d);
        });
        ListaDeErrosOutputDto exceptionList = new ListaDeErrosOutputDto();
        exceptionList.setErros(paramExceptionList);
        return exceptionList;
    }
}
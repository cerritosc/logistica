package co.com.ingeneo.logistica.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import co.com.ingeneo.logistica.domain.Bodega;
import co.com.ingeneo.logistica.domain.Descuento;
import co.com.ingeneo.logistica.dto.DescuentoDTO;
import co.com.ingeneo.logistica.exception.ResourceNotFoundException;
import co.com.ingeneo.logistica.exception.UnprocessableEntityException;
import co.com.ingeneo.logistica.service.BodegaService;
import co.com.ingeneo.logistica.service.DescuentoService;
import co.com.ingeneo.logistica.common.Constants;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequestMapping("/descuento")
public class DescuentoController {

    @Autowired
    private DescuentoService descuentoService;
	
	@GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Descuento>> get() {
        return new ResponseEntity<>(descuentoService.get(), HttpStatus.OK); 
    }
	
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Descuento> get(@PathVariable(name = "id") Integer id) {
        return descuentoService.get(id) 
                .map(descuento -> new ResponseEntity<>(descuento, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
	
	@PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<DescuentoDTO> create(@RequestBody DescuentoDTO descuento) {
        try{
        	return new ResponseEntity<>(descuentoService.create(descuento), HttpStatus.CREATED);
        }catch (ResourceNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
        catch (UnprocessableEntityException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }
	
	@PutMapping()
    public ResponseEntity<DescuentoDTO> updateDescuento(@RequestBody DescuentoDTO descuento){
        try{
            return new ResponseEntity<>(descuentoService.update(descuento), HttpStatus.OK);
        }catch (ResourceNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
        catch (UnprocessableEntityException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

}

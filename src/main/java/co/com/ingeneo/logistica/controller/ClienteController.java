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
import co.com.ingeneo.logistica.domain.Cliente;
import co.com.ingeneo.logistica.exception.ResourceNotFoundException;
import co.com.ingeneo.logistica.exception.UnprocessableEntityException;
import co.com.ingeneo.logistica.service.BodegaService;
import co.com.ingeneo.logistica.service.ClienteService;
import co.com.ingeneo.logistica.common.Constants;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;
	
	@GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Cliente>> get() {
        return new ResponseEntity<>(clienteService.get(), HttpStatus.OK); 
    }
	
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Cliente> get(@PathVariable(name = "id") Integer id) {
        return clienteService.get(id) 
                .map(cliente -> new ResponseEntity<>(cliente, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
	
	@PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Cliente> create(@RequestBody Cliente cliente) {
        try{
        	return new ResponseEntity<>(clienteService.create(cliente), HttpStatus.CREATED);
        }catch (ResourceNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
        catch (UnprocessableEntityException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }
	
	@PutMapping()
    public ResponseEntity<Cliente> updateBodega(@RequestBody Cliente cliente){
        try{
            return new ResponseEntity<Cliente>(clienteService.update(cliente), HttpStatus.OK);
        }catch (ResourceNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
        catch (UnprocessableEntityException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }
}

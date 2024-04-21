package co.com.ingeneo.logistica.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import co.com.ingeneo.logistica.domain.Bodega;
import co.com.ingeneo.logistica.exception.ResourceNotFoundException;
import co.com.ingeneo.logistica.exception.UnprocessableEntityException;
import co.com.ingeneo.logistica.service.BodegaService;

import java.util.List;


@RestController
@RequestMapping("/bodega")
public class BodegaController {

    @Autowired
    private BodegaService bodegaService;
	
	@GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Bodega>> get() {
        return new ResponseEntity<>(bodegaService.get(), HttpStatus.OK); 
    }
	
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Bodega> get(@PathVariable(name = "id") Integer id) {
        return bodegaService.get(id) 
                .map(bodega -> new ResponseEntity<>(bodega, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
	
	@PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Bodega> create(@RequestBody Bodega bodega) {      
        try{
        	return new ResponseEntity<>(bodegaService.create(bodega), HttpStatus.CREATED);
        }catch (ResourceNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
        catch (UnprocessableEntityException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }
	
	@PutMapping()
    public ResponseEntity<Bodega> updateBodega(@RequestBody Bodega bodega){
        try{
            return new ResponseEntity<Bodega>(bodegaService.update(bodega), HttpStatus.OK);
        }catch (ResourceNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
        catch (UnprocessableEntityException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

}

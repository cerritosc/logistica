package co.com.ingeneo.logistica.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import co.com.ingeneo.logistica.domain.Producto;
import co.com.ingeneo.logistica.dto.ProductoDTO;
import co.com.ingeneo.logistica.exception.ResourceNotFoundException;
import co.com.ingeneo.logistica.exception.UnprocessableEntityException;
import co.com.ingeneo.logistica.service.ProductoService;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Controller
@RequestMapping("/producto")
public class ProductoController {

    @Autowired
    private ProductoService productoService;
	
	@GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Producto>> get() {
        return new ResponseEntity<>(productoService.get(), HttpStatus.OK); 
    }
	
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Producto> get(@PathVariable(name = "id") Integer id) {
        return productoService.get(id) 
                .map(bodega -> new ResponseEntity<>(bodega, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
	
	@PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ProductoDTO> create(@RequestBody ProductoDTO producto) {
        return new ResponseEntity<>(productoService.create(producto), HttpStatus.CREATED);
    }
	
	@PutMapping()
    public ResponseEntity<ProductoDTO> updateBodega(@RequestBody ProductoDTO producto){
        try{
            return new ResponseEntity<>(productoService.update(producto), HttpStatus.OK);
        }catch (ResourceNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
        catch (UnprocessableEntityException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

}

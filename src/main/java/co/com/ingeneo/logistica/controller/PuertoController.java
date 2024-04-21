package co.com.ingeneo.logistica.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import co.com.ingeneo.logistica.domain.Puerto;
import co.com.ingeneo.logistica.dto.PuertoDTO;
import co.com.ingeneo.logistica.exception.ResourceNotFoundException;
import co.com.ingeneo.logistica.exception.UnprocessableEntityException;
import co.com.ingeneo.logistica.service.PuertoService;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Controller
@RequestMapping("/puerto")
public class PuertoController {

    @Autowired
    private PuertoService puertoService;
	
	@GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Puerto>> get() {
        return new ResponseEntity<>(puertoService.get(), HttpStatus.OK); 
    }
	
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Puerto> get(@PathVariable(name = "id") Integer id) {
        return puertoService.get(id) 
                .map(bodega -> new ResponseEntity<>(bodega, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
	
	@PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PuertoDTO> create(@RequestBody PuertoDTO puertoDTO) {
        return new ResponseEntity<>(puertoService.create(puertoDTO), HttpStatus.CREATED);
    }
	
	@PutMapping()
    public ResponseEntity<PuertoDTO> updatePuerto(@RequestBody PuertoDTO puerto){
        try{
            return new ResponseEntity<PuertoDTO>(puertoService.update(puerto), HttpStatus.OK);
        }catch (ResourceNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
        catch (UnprocessableEntityException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

}

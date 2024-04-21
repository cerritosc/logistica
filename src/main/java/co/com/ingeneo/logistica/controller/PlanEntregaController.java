package co.com.ingeneo.logistica.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import co.com.ingeneo.logistica.domain.PlanEntrega;
import co.com.ingeneo.logistica.dto.PlanEntregaDTO;
import co.com.ingeneo.logistica.exception.ResourceNotFoundException;
import co.com.ingeneo.logistica.exception.UnprocessableEntityException;
import co.com.ingeneo.logistica.service.PlanEntregaService;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Controller
@RequestMapping("/planEntrega")
public class PlanEntregaController {

    @Autowired
    private PlanEntregaService planEntregaService;
	
	@GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<PlanEntrega>> get() {
        return new ResponseEntity<>(planEntregaService.get(), HttpStatus.OK); 
    }
	
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<PlanEntrega> get(@PathVariable(name = "id") Integer id) {
        return planEntregaService.get(id) 
                .map(bodega -> new ResponseEntity<>(bodega, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
	
	@PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PlanEntregaDTO> create(@RequestBody PlanEntregaDTO planEntrega) {
        return new ResponseEntity<>(planEntregaService.create(planEntrega), HttpStatus.CREATED);
    }
	
	@PutMapping()
    public ResponseEntity<PlanEntregaDTO> updateBodega(@RequestBody PlanEntregaDTO planEntrega){
        try{
            return new ResponseEntity<>(planEntregaService.update(planEntrega), HttpStatus.OK);
        }catch (ResourceNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
        catch (UnprocessableEntityException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

}

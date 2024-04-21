package co.com.ingeneo.logistica.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import co.com.ingeneo.logistica.domain.TipoLogistica;
import co.com.ingeneo.logistica.exception.ResourceNotFoundException;
import co.com.ingeneo.logistica.exception.UnprocessableEntityException;
import co.com.ingeneo.logistica.service.TipoLogisticaService;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Controller
@RequestMapping("/tipoLogistica")
public class TipoLogisticaController {

    @Autowired
    private TipoLogisticaService tipoLogisticaService;
	
	@GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<TipoLogistica>> get() {
        return new ResponseEntity<>(tipoLogisticaService.get(), HttpStatus.OK); 
    }
	
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<TipoLogistica> get(@PathVariable(name = "id") Integer id) {
        return tipoLogisticaService.get(id) 
                .map(bodega -> new ResponseEntity<>(bodega, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
	
	@PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<TipoLogistica> create(@RequestBody TipoLogistica tipoLogistica) {
        return new ResponseEntity<>(tipoLogisticaService.create(tipoLogistica), HttpStatus.CREATED);
    }
	
	@PutMapping()
    public ResponseEntity<TipoLogistica> updateTipoLogistica(@RequestBody TipoLogistica tipoLogistica){
        try{
            return new ResponseEntity<TipoLogistica>(tipoLogisticaService.update(tipoLogistica), HttpStatus.OK);
        }catch (ResourceNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
        catch (UnprocessableEntityException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

}

package co.com.ingeneo.logistica.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;

import co.com.ingeneo.logistica.domain.Pais;
import co.com.ingeneo.logistica.domain.Puerto;
import co.com.ingeneo.logistica.dto.PuertoDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.com.ingeneo.logistica.repository.PuertoRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import co.com.ingeneo.logistica.common.Constants;
import co.com.ingeneo.logistica.common.ServiceResponse;
import co.com.ingeneo.logistica.commons.exception.EntidadNoEncontradaException;

import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class PuertoServiceImpl implements PuertoService {

    
    private PuertoRepository puertoRepository;
    
    private ModelMapper mapper;
    
    @Autowired
    public PuertoServiceImpl(PuertoRepository puertoRepository, ModelMapper mapper) {
    	this.puertoRepository = puertoRepository;
    	this.mapper = mapper;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public Optional<Puerto> get(Integer skPuerto) {
            return puertoRepository.findById(skPuerto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Puerto> findBySkPuerto(Integer skPuerto) {
            return puertoRepository.findBySkPuerto(skPuerto);
    }

    @Override
    public PuertoDTO create(PuertoDTO puertoDTO) {
    	Pais pais = Pais.builder().skPais(puertoDTO.getIdPais()).build();
    	Puerto puerto =  Puerto.builder()
    			.descripcion(puertoDTO.getDescripcion())
    			.pais(pais)
    			.build();
    	puertoRepository.save(puerto);
        return mapper.map(puerto, PuertoDTO.class);
    }

    @Override
    public PuertoDTO update(PuertoDTO puertoDTO) {
    	Pais pais = Pais.builder().skPais(puertoDTO.getIdPais()).build();
    	Puerto puerto =  Puerto.builder()
    			.skPuerto(puertoDTO.getId())
    			.descripcion(puertoDTO.getDescripcion())
    			.pais(pais)
    			.build();
    	puertoRepository.save(puerto);
        return mapper.map(puerto, PuertoDTO.class);
    }

    @Override
    public ServiceResponse delete(Integer skPuerto) {
            ServiceResponse serviceResponse = new ServiceResponse(false, Constants.MSG_EXCEPCION_ACCION);
            Puerto puerto = get(skPuerto)
                    .orElseThrow(() -> new EntidadNoEncontradaException(skPuerto.toString()));
            puertoRepository.delete(puerto);

            serviceResponse.setMessage(Constants.MSG_ELIMINADO_EXITOSO);
            serviceResponse.setSuccess(true);
            serviceResponse.setData(null);
            return serviceResponse;
    }

    @Override
    public List<Puerto> get() {
        return puertoRepository.findAll();
    }

	// metodos para obtener data como lista
    @Override
    @Transactional(readOnly = true)
    public Slice<Puerto> getList(Integer page, Integer rows) {
            return puertoRepository.findAll(PageRequest.of(page - 1, rows));
    }
	
    @Override
    @Transactional(readOnly = true)
    public Slice<Puerto> getListByQ(String q, Pageable page) {
        if(!StringUtils.isBlank(q)) {
            return puertoRepository.findByDescripcionIgnoreCaseContaining(q, page);
        }
        else return puertoRepository.findAll(page);
    }
	
    // metodos para DataTable
}

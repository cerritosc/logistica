package co.com.ingeneo.logistica.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;

import co.com.ingeneo.logistica.domain.Descuento;
import co.com.ingeneo.logistica.domain.TipoLogistica;
import co.com.ingeneo.logistica.dto.DescuentoDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.com.ingeneo.logistica.repository.DescuentoRepository;
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
public class DescuentoServiceImpl implements DescuentoService {

    
    private DescuentoRepository descuentoRepository;
    
    private ModelMapper mapper;
    
    @Autowired
    public DescuentoServiceImpl(DescuentoRepository descuentoRepository, ModelMapper mapper) {
		this.descuentoRepository = descuentoRepository;
		this.mapper = mapper;
	}

	@Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public Optional<Descuento> get(Integer skDescuento) {
            return descuentoRepository.findById(skDescuento);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Descuento> findBySkDescuento(Integer skDescuento) {
            return descuentoRepository.findBySkDescuento(skDescuento);
    }

    @Override
    public DescuentoDTO create(DescuentoDTO descuentoDTO) {
    	TipoLogistica tipoLogistica = TipoLogistica.builder().
    			skTipoLogistica(descuentoDTO.getIdTipoLogistica())
    			.build();
    	Descuento descuento = Descuento.builder()
    			.cantMinProd(descuentoDTO.getCantMinProd())
    			.cantMaxProd(descuentoDTO.getCantMaxProd())
    			.porcDesc(descuentoDTO.getPorcDesc())
    			.tipoLogistica(tipoLogistica)
    			.build();
    	descuentoRepository.save(descuento);
        return mapper.map(descuento, DescuentoDTO.class);
    }

    @Override
    public DescuentoDTO update(DescuentoDTO descuentoDTO) {
    	TipoLogistica tipoLogistica = TipoLogistica.builder().
    			skTipoLogistica(descuentoDTO.getIdTipoLogistica())
    			.build();
    	Descuento descuento = Descuento.builder()
    			.skDescuento(descuentoDTO.getId())
    			.cantMinProd(descuentoDTO.getCantMinProd())
    			.cantMaxProd(descuentoDTO.getCantMaxProd())
    			.porcDesc(descuentoDTO.getPorcDesc())
    			.tipoLogistica(tipoLogistica)
    			.build();
    	descuentoRepository.save(descuento);
        return mapper.map(descuento, DescuentoDTO.class);
    }

    @Override
    public ServiceResponse delete(Integer skDescuento) {
            ServiceResponse serviceResponse = new ServiceResponse(false, Constants.MSG_EXCEPCION_ACCION);
            Descuento descuento = get(skDescuento)
                    .orElseThrow(() -> new EntidadNoEncontradaException(skDescuento.toString()));
            descuentoRepository.delete(descuento);

            serviceResponse.setMessage(Constants.MSG_ELIMINADO_EXITOSO);
            serviceResponse.setSuccess(true);
            serviceResponse.setData(null);
            return serviceResponse;
    }

    @Override
    public List<Descuento> get() {
        return descuentoRepository.findAll();
    }

	// metodos para obtener data como lista
    @Override
    @Transactional(readOnly = true)
    public Slice<Descuento> getList(Integer page, Integer rows) {
            return descuentoRepository.findAll(PageRequest.of(page - 1, rows));
    }
	
    @Override
    @Transactional(readOnly = true)
    public Slice<Descuento> getListByQ(String q, Pageable page) {
        if(!StringUtils.isBlank(q)) {
            return descuentoRepository.findAll(page);
        }
        else return descuentoRepository.findAll(page);
    }
	
    // metodos para DataTable
}

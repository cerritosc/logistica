package co.com.ingeneo.logistica.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import org.apache.commons.lang3.StringUtils;
import co.com.ingeneo.logistica.domain.Bodega;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.com.ingeneo.logistica.repository.BodegaRepository;
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
public class BodegaServiceImpl implements BodegaService {

    @Autowired
    private BodegaRepository bodegaRepository;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public Optional<Bodega> get(Integer skBodega) {
            return bodegaRepository.findById(skBodega);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Bodega> findBySkBodega(Integer skBodega) {
            return bodegaRepository.findBySkBodega(skBodega);
    }

    @Override
    public Bodega update(Bodega bodega) {
            return bodegaRepository.save(bodega);
    }

    @Override
    public Bodega create(Bodega bodega) {
            return bodegaRepository.save(bodega);
    }

    @Override
    public ServiceResponse delete(Integer skBodega) {
            ServiceResponse serviceResponse = new ServiceResponse(false, Constants.MSG_EXCEPCION_ACCION);
            Bodega bodega = get(skBodega)
                    .orElseThrow(() -> new EntidadNoEncontradaException(skBodega.toString()));
            bodegaRepository.delete(bodega);

            serviceResponse.setMessage(Constants.MSG_ELIMINADO_EXITOSO);
            serviceResponse.setSuccess(true);
            serviceResponse.setData(null);
            return serviceResponse;
    }

    @Override
    public List<Bodega> get() {
        return bodegaRepository.findAll();
    }

	// metodos para obtener data como lista
    @Override
    @Transactional(readOnly = true)
    public Slice<Bodega> getList(Integer page, Integer rows) {
            return bodegaRepository.findAll(PageRequest.of(page - 1, rows));
    }
	
    @Override
    @Transactional(readOnly = true)
    public Slice<Bodega> getListByQ(String q, Pageable page) {
        if(!StringUtils.isBlank(q)) {
            return bodegaRepository.findByDescripcionIgnoreCaseContaining(q, page);
        }
        else return bodegaRepository.findAll(page);
    }
	
}

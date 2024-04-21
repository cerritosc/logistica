package co.com.ingeneo.logistica.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import org.apache.commons.lang3.StringUtils;
import co.com.ingeneo.logistica.domain.TipoLogistica;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.com.ingeneo.logistica.repository.TipoLogisticaRepository;
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
public class TipoLogisticaServiceImpl implements TipoLogisticaService {

    @Autowired
    private TipoLogisticaRepository tipoLogisticaRepository;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public Optional<TipoLogistica> get(Integer skTipoLogistica) {
            return tipoLogisticaRepository.findById(skTipoLogistica);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TipoLogistica> findBySkTipoLogistica(Integer skTipoLogistica) {
            return tipoLogisticaRepository.findBySkTipoLogistica(skTipoLogistica);
    }

    @Override
    public TipoLogistica create(TipoLogistica tipoLogistica) {
            return tipoLogisticaRepository.save(tipoLogistica);
    }

    @Override
    public TipoLogistica update(TipoLogistica tipoLogistica) {
            return tipoLogisticaRepository.save(tipoLogistica);
    }

    @Override
    public ServiceResponse delete(Integer skTipoLogistica) {
            ServiceResponse serviceResponse = new ServiceResponse(false, Constants.MSG_EXCEPCION_ACCION);
            TipoLogistica tipoLogistica = get(skTipoLogistica)
                    .orElseThrow(() -> new EntidadNoEncontradaException(skTipoLogistica.toString()));
            tipoLogisticaRepository.delete(tipoLogistica);

            serviceResponse.setMessage(Constants.MSG_ELIMINADO_EXITOSO);
            serviceResponse.setSuccess(true);
            serviceResponse.setData(null);
            return serviceResponse;
    }

    @Override
    public List<TipoLogistica> get() {
        return tipoLogisticaRepository.findAll();
    }

	// metodos para obtener data como lista
    @Override
    @Transactional(readOnly = true)
    public Slice<TipoLogistica> getList(Integer page, Integer rows) {
            return tipoLogisticaRepository.findAll(PageRequest.of(page - 1, rows));
    }
	
    @Override
    @Transactional(readOnly = true)
    public Slice<TipoLogistica> getListByQ(String q, Pageable page) {
        if(!StringUtils.isBlank(q)) {
            return tipoLogisticaRepository.findByDescripcionIgnoreCaseContaining(q, page);
        }
        else return tipoLogisticaRepository.findAll(page);
    }
	
    // metodos para DataTable
}

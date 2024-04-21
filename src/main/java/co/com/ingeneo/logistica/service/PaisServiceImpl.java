package co.com.ingeneo.logistica.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import org.apache.commons.lang3.StringUtils;
import co.com.ingeneo.logistica.domain.Pais;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.com.ingeneo.logistica.repository.PaisRepository;
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
public class PaisServiceImpl implements PaisService {

    @Autowired
    private PaisRepository paisRepository;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public Optional<Pais> get(Integer skPais) {
            return paisRepository.findById(skPais);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Pais> findBySkPais(Integer skPais) {
            return paisRepository.findBySkPais(skPais);
    }

    @Override
    public Pais create(Pais pais) {
            return paisRepository.save(pais);
    }

    @Override
    public Pais update(Pais pais) {
            return paisRepository.save(pais);
    }

    @Override
    public ServiceResponse delete(Integer skPais) {
            ServiceResponse serviceResponse = new ServiceResponse(false, Constants.MSG_EXCEPCION_ACCION);
            Pais pais = get(skPais)
                    .orElseThrow(() -> new EntidadNoEncontradaException(skPais.toString()));
            paisRepository.delete(pais);

            serviceResponse.setMessage(Constants.MSG_ELIMINADO_EXITOSO);
            serviceResponse.setSuccess(true);
            serviceResponse.setData(null);
            return serviceResponse;
    }

    @Override
    public List<Pais> get() {
        return paisRepository.findAll();
    }

	// metodos para obtener data como lista
    @Override
    @Transactional(readOnly = true)
    public Slice<Pais> getList(Integer page, Integer rows) {
            return paisRepository.findAll(PageRequest.of(page - 1, rows));
    }
	
    @Override
    @Transactional(readOnly = true)
    public Slice<Pais> getListByQ(String q, Pageable page) {
        if(!StringUtils.isBlank(q)) {
            return paisRepository.findByDescripcionIgnoreCaseContaining(q, page);
        }
        else return paisRepository.findAll(page);
    }
	
    // metodos para DataTable
}

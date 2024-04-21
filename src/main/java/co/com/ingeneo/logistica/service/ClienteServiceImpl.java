package co.com.ingeneo.logistica.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import org.apache.commons.lang3.StringUtils;
import co.com.ingeneo.logistica.domain.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.com.ingeneo.logistica.repository.ClienteRepository;
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
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public Optional<Cliente> get(Integer skCliente) {
            return clienteRepository.findById(skCliente);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Cliente> findBySkCliente(Integer skCliente) {
            return clienteRepository.findBySkCliente(skCliente);
    }

    @Override
    public Cliente create(Cliente cliente) {
            return clienteRepository.save(cliente);
    }

    @Override
    public Cliente update(Cliente cliente) {
            return clienteRepository.save(cliente);
    }

    @Override
    public ServiceResponse delete(Integer skCliente) {
            ServiceResponse serviceResponse = new ServiceResponse(false, Constants.MSG_EXCEPCION_ACCION);
            Cliente cliente = get(skCliente)
                    .orElseThrow(() -> new EntidadNoEncontradaException(skCliente.toString()));
            clienteRepository.delete(cliente);

            serviceResponse.setMessage(Constants.MSG_ELIMINADO_EXITOSO);
            serviceResponse.setSuccess(true);
            serviceResponse.setData(null);
            return serviceResponse;
    }

    @Override
    public List<Cliente> get() {
        return clienteRepository.findAll();
    }

	// metodos para obtener data como lista
    @Override
    @Transactional(readOnly = true)
    public Slice<Cliente> getList(Integer page, Integer rows) {
            return clienteRepository.findAll(PageRequest.of(page - 1, rows));
    }
	
    @Override
    @Transactional(readOnly = true)
    public Slice<Cliente> getListByQ(String q, Pageable page) {
        if(!StringUtils.isBlank(q)) {
            return clienteRepository.findByCodigoIgnoreCaseContaining(q, page);
        }
        else return clienteRepository.findAll(page);
    }
	
    // metodos para DataTable
}

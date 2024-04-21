package co.com.ingeneo.logistica.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import co.com.ingeneo.logistica.common.ServiceResponse;
import co.com.ingeneo.logistica.domain.Cliente;

public interface ClienteService {
    Optional<Cliente> get(Integer skCliente);
    Cliente create(Cliente cliente);
    Cliente update(Cliente cliente);
    
    Optional<Cliente> findBySkCliente(Integer skCliente);
    ServiceResponse delete(Integer skCliente);
    List<Cliente> get();
	
	// metodos para obtener data como lista
    Slice<Cliente> getList(Integer page, Integer rows);
    Slice<Cliente> getListByQ(String q, Pageable page);
	
    // metodos para DatatTable
}

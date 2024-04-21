package co.com.ingeneo.logistica.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import co.com.ingeneo.logistica.common.ServiceResponse;
import co.com.ingeneo.logistica.domain.Descuento;
import co.com.ingeneo.logistica.dto.DescuentoDTO;

public interface DescuentoService {
    Optional<Descuento> get(Integer skDescuento);
    DescuentoDTO create(DescuentoDTO descuentoDTO);
    DescuentoDTO update(DescuentoDTO descuentoDTO);
    
    Optional<Descuento> findBySkDescuento(Integer skDescuento);
    ServiceResponse delete(Integer skDescuento);
    List<Descuento> get();
	
	// metodos para obtener data como lista
    Slice<Descuento> getList(Integer page, Integer rows);
    Slice<Descuento> getListByQ(String q, Pageable page);
	
    // metodos para DatatTable
}

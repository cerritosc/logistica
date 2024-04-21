package co.com.ingeneo.logistica.service;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import co.com.ingeneo.logistica.common.ServiceResponse;
import co.com.ingeneo.logistica.domain.Puerto;
import co.com.ingeneo.logistica.dto.PuertoDTO;

public interface PuertoService {
	
    Optional<Puerto> get(Integer skPuerto);
    PuertoDTO create(PuertoDTO puertoDTO);
    PuertoDTO update(PuertoDTO puertoDTO);
    
    Optional<Puerto> findBySkPuerto(Integer skPuerto);
    ServiceResponse delete(Integer skPuerto);
    List<Puerto> get();
	
	// metodos para obtener data como lista
    Slice<Puerto> getList(Integer page, Integer rows);
    Slice<Puerto> getListByQ(String q, Pageable page);
}

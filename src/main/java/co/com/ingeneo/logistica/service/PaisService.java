package co.com.ingeneo.logistica.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import co.com.ingeneo.logistica.common.ServiceResponse;
import co.com.ingeneo.logistica.domain.Pais;

public interface PaisService {
	
    Optional<Pais> get(Integer skPais);
    Pais create(Pais pais);
    Pais update(Pais pais);
    
    Optional<Pais> findBySkPais(Integer skPais);
    ServiceResponse delete(Integer skPais);
    List<Pais> get();
	
	// metodos para obtener data como lista
    Slice<Pais> getList(Integer page, Integer rows);
    Slice<Pais> getListByQ(String q, Pageable page);
    
}

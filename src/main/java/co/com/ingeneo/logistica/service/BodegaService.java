package co.com.ingeneo.logistica.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import co.com.ingeneo.logistica.common.ServiceResponse;
import co.com.ingeneo.logistica.domain.Bodega;

public interface BodegaService {
	
    Optional<Bodega> get(Integer skBodega);
    Bodega create(Bodega bodega);
    Bodega update(Bodega bodega);
    
    Optional<Bodega> findBySkBodega(Integer skBodega);
    ServiceResponse delete(Integer skBodega);
    List<Bodega> get();
	
	// metodos para obtener data como lista
    Slice<Bodega> getList(Integer page, Integer rows);
    Slice<Bodega> getListByQ(String q, Pageable page);
	
}

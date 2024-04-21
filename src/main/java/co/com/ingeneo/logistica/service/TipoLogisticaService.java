package co.com.ingeneo.logistica.service;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import co.com.ingeneo.logistica.common.ServiceResponse;
import co.com.ingeneo.logistica.domain.TipoLogistica;

public interface TipoLogisticaService {
	
    Optional<TipoLogistica> get(Integer skTipoLogistica);
    TipoLogistica create(TipoLogistica tipoLogistica);
    TipoLogistica update(TipoLogistica tipoLogistica);
    
    Optional<TipoLogistica> findBySkTipoLogistica(Integer skTipoLogistica);
    ServiceResponse delete(Integer skTipoLogistica);
    List<TipoLogistica> get();
	
	// metodos para obtener data como lista
    Slice<TipoLogistica> getList(Integer page, Integer rows);
    Slice<TipoLogistica> getListByQ(String q, Pageable page);
}

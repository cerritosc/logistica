package co.com.ingeneo.logistica.service;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import co.com.ingeneo.logistica.common.ServiceResponse;
import co.com.ingeneo.logistica.domain.PlanEntrega;
import co.com.ingeneo.logistica.dto.PlanEntregaDTO;

public interface PlanEntregaService {
	
    Optional<PlanEntrega> get(Integer skBodega);
    PlanEntregaDTO create(PlanEntregaDTO planEntregaDTO);
    PlanEntregaDTO update(PlanEntregaDTO planEntregaDTO);
    
    Optional<PlanEntrega> findBySkplanEntrega(Integer skPlanEntrega);
    ServiceResponse delete(Integer skPlanEntrega);
    List<PlanEntrega> get();
	
	// metodos para obtener data como lista
    Slice<PlanEntrega> getList(Integer page, Integer rows);
    Slice<PlanEntrega> getListByQ(String q, Pageable page);
}

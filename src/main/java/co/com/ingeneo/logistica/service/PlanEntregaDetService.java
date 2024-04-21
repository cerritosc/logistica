package co.com.ingeneo.logistica.service;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import co.com.ingeneo.logistica.common.ServiceResponse;
import co.com.ingeneo.logistica.domain.PlanEntregaDet;

public interface PlanEntregaDetService {
	
    Optional<PlanEntregaDet> get(Integer skPlanEntregaDet);
    PlanEntregaDet create(PlanEntregaDet planEntregaDet);
    PlanEntregaDet update(PlanEntregaDet planEntregaDet);
    
    Optional<PlanEntregaDet> findBySkPlanEntregaDet(Integer skPlanEntregaDet);
    ServiceResponse delete(Integer skPlanEntregaDet);
    List<PlanEntregaDet> get();
	
	// metodos para obtener data como lista
    Slice<PlanEntregaDet> getList(Integer page, Integer rows);
    Slice<PlanEntregaDet> getListByQ(String q, Pageable page);
}

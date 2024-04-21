package co.com.ingeneo.logistica.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import co.com.ingeneo.logistica.commons.datatables.repository.DataTablesRepository;
import co.com.ingeneo.logistica.domain.PlanEntrega;

//@JaversSpringDataAuditable
public interface PlanEntregaRepository extends DataTablesRepository<PlanEntrega, Integer> {
   
	Optional<PlanEntrega> findBySkPlanEntrega(Integer skPlanEntrega);

        Slice<PlanEntrega> findByPlacaVehiculoIgnoreCaseContaining(String q, Pageable page);
}

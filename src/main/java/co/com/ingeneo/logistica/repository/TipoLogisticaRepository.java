package co.com.ingeneo.logistica.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import co.com.ingeneo.logistica.commons.datatables.repository.DataTablesRepository;
import co.com.ingeneo.logistica.domain.TipoLogistica;

//@JaversSpringDataAuditable
public interface TipoLogisticaRepository extends DataTablesRepository<TipoLogistica, Integer> {
   
	Optional<TipoLogistica> findBySkTipoLogistica(Integer skTipoLogistica);

        Slice<TipoLogistica> findByDescripcionIgnoreCaseContaining(String q, Pageable page);
}

package co.com.ingeneo.logistica.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import co.com.ingeneo.logistica.commons.datatables.repository.DataTablesRepository;
import co.com.ingeneo.logistica.domain.Puerto;

//@JaversSpringDataAuditable
public interface PuertoRepository extends DataTablesRepository<Puerto, Integer> {
   
	Optional<Puerto> findBySkPuerto(Integer skPuerto);

        Slice<Puerto> findByDescripcionIgnoreCaseContaining(String q, Pageable page);
}

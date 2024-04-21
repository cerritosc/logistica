package co.com.ingeneo.logistica.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import co.com.ingeneo.logistica.commons.datatables.repository.DataTablesRepository;
import co.com.ingeneo.logistica.domain.Pais;

//@JaversSpringDataAuditable
public interface PaisRepository extends DataTablesRepository<Pais, Integer> {
   
	Optional<Pais> findBySkPais(Integer skPais);

        Slice<Pais> findByDescripcionIgnoreCaseContaining(String q, Pageable page);
}

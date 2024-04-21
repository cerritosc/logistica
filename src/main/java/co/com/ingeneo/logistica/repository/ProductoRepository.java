package co.com.ingeneo.logistica.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import co.com.ingeneo.logistica.commons.datatables.repository.DataTablesRepository;
import co.com.ingeneo.logistica.domain.Producto;

//@JaversSpringDataAuditable
public interface ProductoRepository extends DataTablesRepository<Producto, Integer> {
   
	Optional<Producto> findBySkProducto(Integer skProducto);

        Slice<Producto> findByCodigoIgnoreCaseContaining(String q, Pageable page);
}

package co.com.ingeneo.logistica.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.Query;

import co.com.ingeneo.logistica.commons.datatables.repository.DataTablesRepository;
import co.com.ingeneo.logistica.domain.Descuento;

//@JaversSpringDataAuditable
public interface DescuentoRepository extends DataTablesRepository<Descuento, Integer> {
   
	Optional<Descuento> findBySkDescuento(Integer skDescuento);
	
	@Query(value = "SELECT d FROM Descuento d WHERE d.cantMinProd<:cantidad AND (:cantidad<d.cantMaxProd OR d.cantMaxProd IS NULL) AND d.tipoLogistica.skTipoLogistica=:skTipoLogistica ")
	Optional<Descuento> findDescuentoByCantidad(BigDecimal cantidad, Integer skTipoLogistica);

}

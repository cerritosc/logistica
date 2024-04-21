package co.com.ingeneo.logistica.commons.datatables.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.io.Serializable;
import java.util.function.Function;

/**
 * Convenience interface to allow pulling in {@link PagingAndSortingRepository} and
 * {@link JpaSpecificationExecutor} functionality in one go.
 * 
 * @author Damien Arrachequesne
 */
@NoRepositoryBean
public interface DataTablesRepository<T, S extends Serializable>
    extends JpaRepository<T, S>, JpaSpecificationExecutor<T> {

}

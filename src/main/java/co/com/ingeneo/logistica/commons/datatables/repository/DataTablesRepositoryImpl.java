package co.com.ingeneo.logistica.commons.datatables.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import jakarta.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;
import java.util.function.Function;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DataTablesRepositoryImpl<T, S extends Serializable> extends SimpleJpaRepository<T, S>
        implements DataTablesRepository<T, S> {
	
	DataTablesRepositoryImpl(JpaEntityInformation<T, ?> entityInformation,
            EntityManager entityManager) {
		super(entityInformation, entityManager);
	}

}

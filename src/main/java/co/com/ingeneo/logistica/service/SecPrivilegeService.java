package co.com.ingeneo.logistica.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import co.com.ingeneo.logistica.common.ServiceResponse;
import co.com.ingeneo.logistica.domain.SecPrivilege;

public interface SecPrivilegeService {
    Optional<SecPrivilege> findById(Integer skPrivilege);
    Optional<SecPrivilege> findBySkPrivilege(Integer skPrivilege);
    ServiceResponse saveValidated(SecPrivilege secPrivilege);
    ServiceResponse delete(Integer skPrivilege);
    List<SecPrivilege> findAll();
	
	// metodos para obtener data como lista
    Slice<SecPrivilege> getList(Integer page, Integer rows);
    Slice<SecPrivilege> getListByQ(String q, Pageable page);
	
    // metodos para DatatTable

    Slice<SecPrivilege> getListByQPadre(String q, Pageable page);

    Map<Integer, SecPrivilege> findHijosBySkPrivilegeAsMap(Integer skPrivilege);
}

package co.com.ingeneo.logistica.service;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import co.com.ingeneo.logistica.common.ServiceResponse;
import co.com.ingeneo.logistica.domain.CcRol;
import co.com.ingeneo.logistica.domain.SecPrivilege;

public interface CcRolService {
    Optional<CcRol> findById(Integer skRol);
    Optional<CcRol> findBySkRol(Integer skRol);
    ServiceResponse saveValidated(CcRol ccRol);
    ServiceResponse delete(Integer skRol);
    List<CcRol> findAll();
	
	// metodos para obtener data como lista
    Slice<CcRol> getList(Integer page, Integer rows);
    Slice<CcRol> getListByQ(String q, Pageable page);
	
    // metodos para DatatTable

    ServiceResponse asociarPrivilegio(Integer skRol, Integer skPrivilege);

    ServiceResponse eliminarPrivilegio(Integer skRol, Integer skPrivilege);

    ServiceResponse activarRol(Integer skRol);

    ServiceResponse inactivarRol(Integer skRol);

    List<SecPrivilege> getPrivilegiosDeRol(Integer fkRol);
}

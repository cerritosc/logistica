package co.com.ingeneo.logistica.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import co.com.ingeneo.logistica.common.ServiceResponse;
import co.com.ingeneo.logistica.domain.SecRolePrivilege;

public interface SecRolePrivilegeService {
    Optional<SecRolePrivilege> findById(Integer skRolePrivilege);
    Optional<SecRolePrivilege> findBySkRolePrivilege(Integer skRolePrivilege);
    ServiceResponse saveValidated(SecRolePrivilege secRolePrivilege);
    ServiceResponse delete(Integer skRolePrivilege);
    List<SecRolePrivilege> findAll();
	
	// metodos para obtener data como lista
    Slice<SecRolePrivilege> getList(Integer page, Integer rows);
    Slice<SecRolePrivilege> getListByQ(String q, Pageable page);
	
    // metodos para DatatTable

    Optional<SecRolePrivilege> findBySkRolAndSkPrivilege(Integer skRol, Integer skPrivilege);

    void remove(SecRolePrivilege rolPrivilegioHijo);

    List<SecRolePrivilege> findPrivilegiosAsignados(Integer skRol, Set<Integer> privilegiosHijos);
}

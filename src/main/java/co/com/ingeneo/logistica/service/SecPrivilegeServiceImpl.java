package co.com.ingeneo.logistica.service;

import java.util.HashMap;
import co.com.ingeneo.logistica.domain.SecPrivilege;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.com.ingeneo.logistica.repository.SecPrivilegeRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import co.com.ingeneo.logistica.common.Constants;
import co.com.ingeneo.logistica.common.ServiceResponse;
import co.com.ingeneo.logistica.commons.exception.EntidadNoEncontradaException;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class SecPrivilegeServiceImpl implements SecPrivilegeService {

    @Autowired
    private SecPrivilegeRepository secPrivilegeRepository;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public Optional<SecPrivilege> findById(Integer skPrivilege) {
            return secPrivilegeRepository.findById(skPrivilege);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SecPrivilege> findBySkPrivilege(Integer skPrivilege) {
            return secPrivilegeRepository.findBySkPrivilege(skPrivilege);
    }

    @Override
    public ServiceResponse saveValidated(SecPrivilege secPrivilege) {
            ServiceResponse serviceResponse = new ServiceResponse(false, Constants.MSG_EXCEPCION_ACCION);
            SecPrivilege savedSecPrivilege = secPrivilegeRepository.save(secPrivilege);
            serviceResponse.setMessage(Constants.MSG_GUARDADO_EXITOSO);
            serviceResponse.setSuccess(true);
            serviceResponse.setData(savedSecPrivilege);

            return serviceResponse;
    }

    @Override
    public ServiceResponse delete(Integer skPrivilege) {
            ServiceResponse serviceResponse = new ServiceResponse(false, Constants.MSG_EXCEPCION_ACCION);
            SecPrivilege secPrivilege = findById(skPrivilege)
                    .orElseThrow(() -> new EntidadNoEncontradaException(skPrivilege.toString()));
            secPrivilegeRepository.delete(secPrivilege);

            serviceResponse.setMessage(Constants.MSG_ELIMINADO_EXITOSO);
            serviceResponse.setSuccess(true);
            serviceResponse.setData(null);
            return serviceResponse;
    }

    @Override
    public List<SecPrivilege> findAll() {
        return secPrivilegeRepository.findAll();
    }

	// metodos para obtener data como lista
    @Override
    @Transactional(readOnly = true)
    public Slice<SecPrivilege> getList(Integer page, Integer rows) {
            return secPrivilegeRepository.findAll(PageRequest.of(page - 1, rows));
    }
	
    @Override
    @Transactional(readOnly = true)
    public Slice<SecPrivilege> getListByQ(String q, Pageable page) {
            return secPrivilegeRepository.findByCodigoYNombrePrivilegio(q, page);
    }
	
    // metodos para DataTable

    @Override
    public Slice<SecPrivilege> getListByQPadre(String q, Pageable page) {
        return secPrivilegeRepository.findPadresByCodigoYNombrePrivilegio(q, page);
    }

    @Override
    public Map<Integer, SecPrivilege> findHijosBySkPrivilegeAsMap(Integer skPrivilege) {
        List<SecPrivilege> privilegiosHijos = secPrivilegeRepository.findHijosBySkPrivilege(skPrivilege);
        Map<Integer, SecPrivilege> privilegiosHijosAsMap = new HashMap<>();
        for(SecPrivilege privilegio : privilegiosHijos) {
            privilegiosHijosAsMap.put(privilegio.getSkPrivilege(), privilegio);
        }
        
        return privilegiosHijosAsMap;
    }
}

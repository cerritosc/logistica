package co.com.ingeneo.logistica.service;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import co.com.ingeneo.logistica.common.ServiceResponse;
import co.com.ingeneo.logistica.domain.CcEmpleado;

public interface CcEmpleadoService {
    Optional<CcEmpleado> findById(Integer skEmpleado);
    Optional<CcEmpleado> findBySkEmpleado(Integer skEmpleado);
    ServiceResponse saveValidated(CcEmpleado ccEmpleado);
    ServiceResponse delete(Integer skEmpleado);
    List<CcEmpleado> findAll();
	
	// metodos para obtener data como lista
    Slice<CcEmpleado> getList(Integer page, Integer rows);
    Slice<CcEmpleado> getListByQ(String q, Pageable page);
	
    // metodos para DatatTable

    ServiceResponse activarEmpleado(Integer skEmpleado);

    ServiceResponse inactivarEmpleado(Integer skEmpleado);

    Slice<CcEmpleado> findEmpleadoSinUsuario(Integer skEmpleado, String q, Pageable pagina);
    
    Slice<CcEmpleado> findEmpleadobyRol(String cdRol, Pageable pagina);
    
    public List<String[]> findEmpleadoBackOffice();
    
    Slice<CcEmpleado> findEmpleadosBackOfficeActivos(String q, Pageable pagina);
    
     public List<String[]> findEmpleadosOrdenTrabajo(Integer skEmpleado);
     
    public List<String[]> findEmpleadoBySupervisor(Integer skEmpleado);
}

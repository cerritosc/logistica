package co.com.ingeneo.logistica.service;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import co.com.ingeneo.logistica.common.ServiceResponse;
import co.com.ingeneo.logistica.domain.CcUsuario;

public interface CcUsuarioService {
    Optional<CcUsuario> findById(Integer skUsuario);
    Optional<CcUsuario> findBySkUsuario(Integer skUsuario);
    ServiceResponse saveValidated(CcUsuario ccUsuario);
    ServiceResponse delete(Integer skUsuario);
    List<CcUsuario> findAll();
	
	// metodos para obtener data como lista
    Slice<CcUsuario> getList(Integer page, Integer rows);
    Slice<CcUsuario> getListByQ(String q, Pageable page);

    ServiceResponse activarUsuario(Integer skUsuario);

    ServiceResponse desactivarUsuario(Integer skUsuario);
}

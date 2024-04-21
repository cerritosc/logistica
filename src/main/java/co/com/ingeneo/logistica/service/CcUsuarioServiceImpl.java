package co.com.ingeneo.logistica.service;

import java.time.LocalDateTime;
import co.com.ingeneo.logistica.domain.CcUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.com.ingeneo.logistica.repository.CcUsuarioRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import co.com.ingeneo.logistica.common.Constants;
import co.com.ingeneo.logistica.common.ServiceResponse;

import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class CcUsuarioServiceImpl implements CcUsuarioService {

    @Autowired
    private CcUsuarioRepository ccUsuarioRepository;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public Optional<CcUsuario> findById(Integer skUsuario) {
            return ccUsuarioRepository.findById(skUsuario);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CcUsuario> findBySkUsuario(Integer skUsuario) {
            return ccUsuarioRepository.findBySkUsuario(skUsuario);
    }

    @Override
    public ServiceResponse saveValidated(CcUsuario ccUsuario) {
            ServiceResponse serviceResponse = new ServiceResponse(false, Constants.MSG_EXCEPCION_ACCION);
            CcUsuario savedCcUsuario = ccUsuarioRepository.save(ccUsuario);
            serviceResponse.setMessage(Constants.MSG_GUARDADO_EXITOSO);
            serviceResponse.setSuccess(true);
            serviceResponse.setData(savedCcUsuario);

            return serviceResponse;
    }

    @Override
    public ServiceResponse delete(Integer skUsuario) {
        Optional<CcUsuario> optionalUsuario = ccUsuarioRepository.findById(skUsuario);
        if(optionalUsuario.isEmpty()) {
            return new ServiceResponse(false, USUARIO_NO_ENCONTRADO);
        }
        
        CcUsuario usuario = optionalUsuario.get();
        
        // solo permito eliminar si la fecha de inicio es nula, es decir no ha sido activado
        if(usuario.getFcInicio() != null) {
            return new ServiceResponse(false, "Este usuario no puede ser eliminado ya que ha sido previamente activado");
        }
        
        ccUsuarioRepository.delete(usuario);
        return new ServiceResponse(true, "Usuario eliminado de forma exitosa");
    }
    private static final String USUARIO_NO_ENCONTRADO = "Usuario no encontrado";

    @Override
    public List<CcUsuario> findAll() {
        return ccUsuarioRepository.findAll();
    }

	// metodos para obtener data como lista
    @Override
    @Transactional(readOnly = true)
    public Slice<CcUsuario> getList(Integer page, Integer rows) {
            return ccUsuarioRepository.findAll(PageRequest.of(page - 1, rows));
    }
	
    @Override
    @Transactional(readOnly = true)
    public Slice<CcUsuario> getListByQ(String q, Pageable page) {
            return ccUsuarioRepository.findBySkUsuarioIgnoreCaseContaining(q, page);
    }

    @Override
    public ServiceResponse activarUsuario(Integer skUsuario) {
        Optional<CcUsuario> optionalUsuario = ccUsuarioRepository.findById(skUsuario);
        if(optionalUsuario.isEmpty()) {
            return new ServiceResponse(false, USUARIO_NO_ENCONTRADO);
        }
        
        CcUsuario usuario = optionalUsuario.get();
        usuario.setFcInicio(LocalDateTime.now());
        usuario.setFcFin(null);
        
        ccUsuarioRepository.save(usuario);
        
        return new ServiceResponse(true, "Usuario activado de forma exitosa");
    }

    @Override
    public ServiceResponse desactivarUsuario(Integer skUsuario) {
        Optional<CcUsuario> optionalUsuario = ccUsuarioRepository.findById(skUsuario);
        if(optionalUsuario.isEmpty()) {
            return new ServiceResponse(false, USUARIO_NO_ENCONTRADO);
        }
        
        CcUsuario usuario = optionalUsuario.get();
        usuario.setFcFin(LocalDateTime.now());
        
        ccUsuarioRepository.save(usuario);
        
        return new ServiceResponse(true, "Usuario desactivado de forma exitosa");
    }
}

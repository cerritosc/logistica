package co.com.ingeneo.logistica.service;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;

import co.com.ingeneo.logistica.domain.Producto;
import co.com.ingeneo.logistica.dto.ProductoDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.com.ingeneo.logistica.repository.ProductoRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import co.com.ingeneo.logistica.common.Constants;
import co.com.ingeneo.logistica.common.ServiceResponse;
import co.com.ingeneo.logistica.commons.exception.EntidadNoEncontradaException;

import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class ProductoServiceImpl implements ProductoService {

    
    private ProductoRepository productoRepository;
    
    private ModelMapper mapper;
    
    @Autowired
    public ProductoServiceImpl(ProductoRepository productoRepository, ModelMapper mapper) {
    	this.productoRepository = productoRepository;
    	this.mapper = mapper;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public Optional<Producto> get(Integer skProducto) {
            return productoRepository.findById(skProducto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Producto> findBySkProducto(Integer skProducto) {
            return productoRepository.findBySkProducto(skProducto);
    }

    @Override
    public ProductoDTO create(ProductoDTO productoDTO) {
    	Producto producto = Producto.builder()
    			.codigo(productoDTO.getCodigo())
    			.descripcion(productoDTO.getDescripcion())
    			.estado(productoDTO.getEstado())
    			.precioEnvio(productoDTO.getPrecioEnvio())
    			.build();
    	productoRepository.save(producto);
        return mapper.map(producto, ProductoDTO.class);
    }

    @Override
    public ProductoDTO update(ProductoDTO productoDTO) {
    	Producto producto = Producto.builder()
    			.skProducto(productoDTO.getId())
    			.codigo(productoDTO.getCodigo())
    			.descripcion(productoDTO.getDescripcion())
    			.estado(productoDTO.getEstado())
    			.precioEnvio(productoDTO.getPrecioEnvio())
    			.build();
    	productoRepository.save(producto);
        return mapper.map(producto, ProductoDTO.class);
    }

    @Override
    public ServiceResponse delete(Integer skProducto) {
            ServiceResponse serviceResponse = new ServiceResponse(false, Constants.MSG_EXCEPCION_ACCION);
            Producto producto = get(skProducto)
                    .orElseThrow(() -> new EntidadNoEncontradaException(skProducto.toString()));
            productoRepository.delete(producto);

            serviceResponse.setMessage(Constants.MSG_ELIMINADO_EXITOSO);
            serviceResponse.setSuccess(true);
            serviceResponse.setData(null);
            return serviceResponse;
    }

    @Override
    public List<Producto> get() {
        return productoRepository.findAll();
    }

	// metodos para obtener data como lista
    @Override
    @Transactional(readOnly = true)
    public Slice<Producto> getList(Integer page, Integer rows) {
            return productoRepository.findAll(PageRequest.of(page - 1, rows));
    }
	
    @Override
    @Transactional(readOnly = true)
    public Slice<Producto> getListByQ(String q, Pageable page) {
        if(!StringUtils.isBlank(q)) {
            return productoRepository.findByCodigoIgnoreCaseContaining(q, page);
        }
        else return productoRepository.findAll(page);
    }
	
    // metodos para DataTable
}

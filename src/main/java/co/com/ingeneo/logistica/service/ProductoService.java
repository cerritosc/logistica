package co.com.ingeneo.logistica.service;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import co.com.ingeneo.logistica.common.ServiceResponse;
import co.com.ingeneo.logistica.domain.Producto;
import co.com.ingeneo.logistica.dto.ProductoDTO;

public interface ProductoService {
	
    Optional<Producto> get(Integer skProducto);
    ProductoDTO create(ProductoDTO productoDTO);
    ProductoDTO update(ProductoDTO productoDTO);
    
    Optional<Producto> findBySkProducto(Integer skProducto);
    ServiceResponse delete(Integer skProducto);
    List<Producto> get();
	
	// metodos para obtener data como lista
    Slice<Producto> getList(Integer page, Integer rows);
    Slice<Producto> getListByQ(String q, Pageable page);
}

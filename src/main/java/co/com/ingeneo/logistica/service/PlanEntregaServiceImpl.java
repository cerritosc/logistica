package co.com.ingeneo.logistica.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;

import co.com.ingeneo.logistica.domain.Bodega;
import co.com.ingeneo.logistica.domain.Cliente;
import co.com.ingeneo.logistica.domain.Descuento;
import co.com.ingeneo.logistica.domain.PlanEntrega;
import co.com.ingeneo.logistica.domain.PlanEntregaDet;
import co.com.ingeneo.logistica.domain.Producto;
import co.com.ingeneo.logistica.domain.Puerto;
import co.com.ingeneo.logistica.domain.TipoLogistica;
import co.com.ingeneo.logistica.dto.PlanEntregaDTO;
import co.com.ingeneo.logistica.dto.PlanEntregaDetDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.ingeneo.logistica.repository.DescuentoRepository;
import co.com.ingeneo.logistica.repository.PlanEntregaDetRepository;
import co.com.ingeneo.logistica.repository.PlanEntregaRepository;
import co.com.ingeneo.logistica.repository.ProductoRepository;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import co.com.ingeneo.logistica.common.Constants;
import co.com.ingeneo.logistica.common.ServiceResponse;
import co.com.ingeneo.logistica.commons.exception.EntidadNoEncontradaException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class PlanEntregaServiceImpl implements PlanEntregaService {
    
    private PlanEntregaRepository planEntregaRepository;
    
    private PlanEntregaDetRepository planEntregaDetRepository;
    
    private DescuentoRepository descuentoRepository;
    
    private ProductoRepository productoRepository;
    
    private ModelMapper mapper;
    
    @Autowired
    public PlanEntregaServiceImpl(PlanEntregaRepository planEntregaRepository, 
						    	  PlanEntregaDetRepository planEntregaDetRepository,
						    	  DescuentoRepository descuentoRepository,
						    	  ProductoRepository productoRepository,
						    	  ModelMapper mapper) {
		this.planEntregaRepository = planEntregaRepository;
		this.planEntregaDetRepository = planEntregaDetRepository;
		this.descuentoRepository = descuentoRepository;
		this.productoRepository = productoRepository;
		this.mapper = mapper;
	}

	@Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public Optional<PlanEntrega> get(Integer skPlanEntrega) {
            return planEntregaRepository.findById(skPlanEntrega);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PlanEntrega> findBySkplanEntrega(Integer skPlanEntrega) {
            return planEntregaRepository.findBySkPlanEntrega(skPlanEntrega);
    }

    @Override
    public PlanEntregaDTO create(PlanEntregaDTO planEntregaDTO) {
    	Cliente cliente = Cliente.builder().skCliente(planEntregaDTO.getIdCliente()).build(); 
    	TipoLogistica tipoLogistica = TipoLogistica.builder().skTipoLogistica(planEntregaDTO.getIdTipoLogistica()).build();
    	Bodega bodega = null;
    	Puerto puerto = null;
    	BigDecimal precioEnvioTotal = BigDecimal.ZERO;
    	List<PlanEntregaDetDTO> listDtoDet = planEntregaDTO.getPlanDet();
    	List<PlanEntregaDet> listPlanDet = new ArrayList<>();
    	
    	if(planEntregaDTO.getIdBodega()!=null) bodega = Bodega.builder().skBodega(planEntregaDTO.getIdBodega()).build();
    	if(planEntregaDTO.getIdPuerto()!=null) puerto = Puerto.builder().skPuerto(planEntregaDTO.getIdPuerto()).build();
    	
    	PlanEntrega planEntrega = PlanEntrega.builder() 
    			.precioEnvio(precioEnvioTotal)
    			.fechaRegistro(planEntregaDTO.getFechaRegistro())
    			.fechaEntrega(planEntregaDTO.getFechaEntrega())
    			.placaVehiculo(planEntregaDTO.getPlacaVehiculo())
    			.numeroFlota(planEntregaDTO.getNumeroFlota())
    			.numeroGuia(planEntregaDTO.getNumeroGuia())
    			.cliente(cliente)
    			.tipoLogistica(tipoLogistica)
    			.bodega(bodega)
    			.puerto(puerto)
    			.build();

    	planEntregaRepository.save(planEntrega);
    	
    	for(PlanEntregaDetDTO d: listDtoDet) {
    		Producto producto = productoRepository.findById(d.getIdProducto()).get();
    		BigDecimal subTotal = d.getCantidad().multiply(d.getPrecioEnvio()); 
        	BigDecimal descuento = getDescuentoByCantidad(d.getCantidad(), producto.getPrecioEnvio(), planEntrega.getTipoLogisticaIdDelegate());
        	BigDecimal total = subTotal.subtract(descuento).setScale(2,RoundingMode.HALF_UP);
        	precioEnvioTotal = precioEnvioTotal.add(total);
    		PlanEntregaDet planEntregaDet = PlanEntregaDet.builder()
    				.cantidad(d.getCantidad())
    				.precioEnvio(producto.getPrecioEnvio())
    				.subtotal(subTotal)
    				.descuento(descuento)
    				.total(total)
    				.producto(producto).planEntrega(planEntrega).build();
    		planEntregaDetRepository.save(planEntregaDet);
    		listPlanDet.add(planEntregaDet);
    	}
    	planEntrega.setPrecioEnvio(precioEnvioTotal);
    	planEntregaRepository.save(planEntrega);
        return mapper.map(planEntrega, PlanEntregaDTO.class);
    }
    
    private BigDecimal getDescuentoByCantidad(BigDecimal cantidad, BigDecimal precioEnvio, Integer idTipoLogistica) {
    	BigDecimal descuento = BigDecimal.ZERO;
    	BigDecimal porcDesc = BigDecimal.ZERO;
    	Optional<Descuento> descuentoOpt = descuentoRepository.findDescuentoByCantidad(cantidad, idTipoLogistica);
    	if(descuentoOpt.isPresent()) {
    		porcDesc = BigDecimal.valueOf(descuentoOpt.get().getPorcDesc());
        	descuento = cantidad
        			.multiply(precioEnvio)
        			.multiply(porcDesc.divide(Constants.CIEN, 8 , RoundingMode.HALF_UP))
        			.setScale(2,RoundingMode.HALF_UP);
    	}
    	return descuento;
    }

    @Override
    public PlanEntregaDTO update(PlanEntregaDTO planEntregaDTO) {
            return null;
    }

    @Override
    public ServiceResponse delete(Integer skPlanEntrega) {
            ServiceResponse serviceResponse = new ServiceResponse(false, Constants.MSG_EXCEPCION_ACCION);
            PlanEntrega planEntrega = get(skPlanEntrega)
                    .orElseThrow(() -> new EntidadNoEncontradaException(skPlanEntrega.toString()));
            planEntregaRepository.delete(planEntrega);

            serviceResponse.setMessage(Constants.MSG_ELIMINADO_EXITOSO);
            serviceResponse.setSuccess(true);
            serviceResponse.setData(null);
            return serviceResponse;
    }

    @Override
    public List<PlanEntrega> get() {
        return planEntregaRepository.findAll();
    }

	// metodos para obtener data como lista
    @Override
    @Transactional(readOnly = true)
    public Slice<PlanEntrega> getList(Integer page, Integer rows) {
            return planEntregaRepository.findAll(PageRequest.of(page - 1, rows));
    }
	
    @Override
    @Transactional(readOnly = true)
    public Slice<PlanEntrega> getListByQ(String q, Pageable page) {
        if(!StringUtils.isBlank(q)) {
            return planEntregaRepository.findByPlacaVehiculoIgnoreCaseContaining(q, page);
        }
        else return planEntregaRepository.findAll(page);
    }
	
    // metodos para DataTable
}

package co.com.ingeneo.logistica.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class PlanEntregaDTO {
	
	private Integer id;
	
    private LocalDate fechaRegistro; 
    
    private LocalDate fechaEntrega; 
    
    private BigDecimal precioEnvio; 
    
    private String placaVehiculo; 
    
    private String numeroFlota; 
    
    private String numeroGuia; 
    
    private Integer idCliente;
	
	private Integer idTipoLogistica;
	
	private Integer idBodega;
	
	private Integer idPuerto;
	
	private List<PlanEntregaDetDTO> planDet;

}

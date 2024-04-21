package co.com.ingeneo.logistica.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class PlanEntregaDetDTO {
	
	private Integer id;
	
	private BigDecimal cantidad;
	
	private BigDecimal precioEnvio; 
	
	private BigDecimal subtotal; 
	
	private BigDecimal descuento; 
	
	private BigDecimal total; 
	
	private Integer idPlanEntrega;
	
	private Integer idProducto;

}

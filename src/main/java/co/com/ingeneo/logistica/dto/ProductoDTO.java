package co.com.ingeneo.logistica.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ProductoDTO {
	
	private Integer id;
	
	private String codigo; 
	
	private String descripcion; 
	
	private Boolean estado;

    private BigDecimal precioEnvio; 
    
}

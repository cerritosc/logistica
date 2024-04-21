package co.com.ingeneo.logistica.dto;

import lombok.Data;

@Data
public class DescuentoDTO {
	
	private Integer id;
	
	private Integer cantMinProd; 
	
	private Integer cantMaxProd;
	
	private Integer porcDesc;
	
	private Integer idTipoLogistica;

}

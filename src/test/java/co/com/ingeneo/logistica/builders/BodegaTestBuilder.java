package co.com.ingeneo.logistica.builders;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonView;

import co.com.ingeneo.logistica.domain.Bodega;
import co.com.ingeneo.logistica.domain.PlanEntrega;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BodegaTestBuilder {
	
	private Integer skBodega; 
	
	private String codigo;
	
	private String descripcion; 

}

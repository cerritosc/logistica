package co.com.ingeneo.logistica.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

import co.com.ingeneo.logistica.common.Constants;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
@Table(name = "plan_entrega_det", schema="logistica")
public class PlanEntregaDet implements Serializable {
    @Getter
    private static final long serialVersionUID = 1L;

    @Id
    @Basic(fetch = FetchType.EAGER)
    @Column(name = "sk_plan_entrega_det")
    @EqualsAndHashCode.Include
    @JsonView
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "logistica.SEQ_plan_entrega_det")
    @SequenceGenerator(name = "logistica.SEQ_plan_entrega_det", sequenceName = "logistica.SEQ_plan_entrega_det", allocationSize = 1)
    private Integer skPlanEntregaDet; 

    @Column(name = "cantidad" , scale = 6 )
    @NotNull(message = "No puede estar vacio el campo cantidad")
    private BigDecimal cantidad; 

    @Column(name = "precio_envio" , scale = 2 )
    @NotNull(message = "No puede estar vacio el campo precioEnvio")
    private BigDecimal precioEnvio; 

    @Column(name = "subtotal" , scale = 2 )
    @NotNull(message = "No puede estar vacio el campo subtotal")
    private BigDecimal subtotal; 

    @Column(name = "descuento" , scale = 2 )
    private BigDecimal descuento; 

    @Column(name = "total" , scale = 2 )
    private BigDecimal total; 

    @Getter(onMethod = @__( @JsonIgnore))
    @JoinColumn(name = "sk_plan_entrega", referencedColumnName = "sk_plan_entrega")
    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private PlanEntrega planEntrega; 

    @Getter(onMethod = @__( @JsonIgnore))
    @JoinColumn(name = "sk_producto", referencedColumnName = "sk_producto")
    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Producto producto; 

    // delegates de ids
    public Integer getPlanEntregaIdDelegate() {
        if(planEntrega != null) {
            return planEntrega.getSkPlanEntrega();
        }
        else return null;
    } 

    public void setPlanEntregaIdDelegate(Integer skPlanEntrega) {
        if(skPlanEntrega != null) {
            this.planEntrega = new PlanEntrega();
            this.planEntrega.setSkPlanEntrega(skPlanEntrega);
        }
        else {
            this.planEntrega = null;
        }
    }
    
    public String getPlanEntregaStDescripcionDelegate() {
        if(planEntrega != null) {
            return planEntrega.getPlacaVehiculo();
        }
        else return "";
    }

    public String getPlanEntregaSelect2Delegate() {
        return String.valueOf(getPlanEntregaIdDelegate())
            + "|"
            + getPlanEntregaStDescripcionDelegate();
    }

    public Integer getProductoIdDelegate() {
        if(producto != null) {
            return producto.getSkProducto();
        }
        else return null;
    } 

    public void setProductoIdDelegate(Integer skProducto) {
        if(skProducto != null) {
            this.producto = new Producto();
            this.producto.setSkProducto(skProducto);
        }
        else {
            this.producto = null;
        }
    }
    
    public String getProductoStDescripcionDelegate() {
        if(producto != null) {
            return producto.getCodigo();
        }
        else return "";
    }

    public String getProductoSelect2Delegate() {
        return String.valueOf(getProductoIdDelegate())
            + "|"
            + getProductoStDescripcionDelegate();
    }

}

package co.com.ingeneo.logistica.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;
import java.util.Set;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.*;
import java.math.BigDecimal;


import co.com.ingeneo.logistica.common.Constants;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;


import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
@Table(name = "plan_entrega", schema="logistica")
public class PlanEntrega implements Serializable {
    @Getter
    private static final long serialVersionUID = 1L;

    @Id
    @Basic(fetch = FetchType.EAGER)
    @Column(name = "sk_plan_entrega")
    @EqualsAndHashCode.Include
    @JsonView
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "logistica.SEQ_plan_entrega")
    @SequenceGenerator(name = "logistica.SEQ_plan_entrega", sequenceName = "logistica.SEQ_plan_entrega", allocationSize = 1)
    private Integer skPlanEntrega; 

    @Column(name = "fecha_registro" )
    @NotNull(message = "No puede estar vacio el campo fechaRegistro")
    @DateTimeFormat(pattern = Constants.DATE_FORMAT)
    @JsonFormat(pattern = Constants.DATE_FORMAT)
    private LocalDate fechaRegistro; 

    @Column(name = "fecha_entrega" )
    @DateTimeFormat(pattern = Constants.DATE_FORMAT)
    @JsonFormat(pattern = Constants.DATE_FORMAT)
    private LocalDate fechaEntrega; 

    @Column(name = "precio_envio" , scale = 2 )
    @NotNull(message = "No puede estar vacio el campo precioEnvio")
    private BigDecimal precioEnvio; 

    @Column(name = "placa_vehiculo" )
    @Size(max = 6, message = "El campo placaVehiculo excede la longitud permitida")
    private String placaVehiculo; 

    @Column(name = "numero_flota" )
    @Size(max = 8, message = "El campo numeroFlota excede la longitud permitida")
    private String numeroFlota; 

    @Column(name = "numero_guia" )
    @Size(max = 10, message = "El campo numeroGuia excede la longitud permitida")
    private String numeroGuia; 

    @OneToMany(mappedBy = "planEntrega", cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
    @Getter(onMethod = @__(@JsonIgnore))
    @ToString.Exclude
    private Set<PlanEntregaDet> planEntregaDetes;

    @Getter(onMethod = @__( @JsonIgnore))
    @JoinColumn(name = "sk_cliente", referencedColumnName = "sk_cliente")
    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Cliente cliente; 

    @Getter(onMethod = @__( @JsonIgnore))
    @JoinColumn(name = "sk_tipo_logistica", referencedColumnName = "sk_tipo_logistica")
    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private TipoLogistica tipoLogistica; 

    @Getter(onMethod = @__( @JsonIgnore))
    @JoinColumn(name = "sk_bodega", referencedColumnName = "sk_bodega")
    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Bodega bodega; 

    @Getter(onMethod = @__( @JsonIgnore))
    @JoinColumn(name = "sk_puerto", referencedColumnName = "sk_puerto")
    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Puerto puerto; 

    // delegates de ids
    public Integer getClienteIdDelegate() {
        if(cliente != null) {
            return cliente.getSkCliente();
        }
        else return null;
    } 

    public void setClienteIdDelegate(Integer skCliente) {
        if(skCliente != null) {
            this.cliente = new Cliente();
            this.cliente.setSkCliente(skCliente);
        }
        else {
            this.cliente = null;
        }
    }
    
    public String getClienteStDescripcionDelegate() {
        if(cliente != null) {
            return cliente.getCodigo();
        }
        else return "";
    }

    public String getClienteSelect2Delegate() {
        return String.valueOf(getClienteIdDelegate())
            + "|"
            + getClienteStDescripcionDelegate();
    }

    public Integer getTipoLogisticaIdDelegate() {
        if(tipoLogistica != null) {
            return tipoLogistica.getSkTipoLogistica();
        }
        else return null;
    } 

    public void setTipoLogisticaIdDelegate(Integer skTipoLogistica) {
        if(skTipoLogistica != null) {
            this.tipoLogistica = new TipoLogistica();
            this.tipoLogistica.setSkTipoLogistica(skTipoLogistica);
        }
        else {
            this.tipoLogistica = null;
        }
    }
    
    public String getTipoLogisticaStDescripcionDelegate() {
        if(tipoLogistica != null) {
            return tipoLogistica.getDescripcion();
        }
        else return "";
    }

    public String getTipoLogisticaSelect2Delegate() {
        return String.valueOf(getTipoLogisticaIdDelegate())
            + "|"
            + getTipoLogisticaStDescripcionDelegate();
    }

    public Integer getBodegaIdDelegate() {
        if(bodega != null) {
            return bodega.getSkBodega();
        }
        else return null;
    } 

    public void setBodegaIdDelegate(Integer skBodega) {
        if(skBodega != null) {
            this.bodega = new Bodega();
            this.bodega.setSkBodega(skBodega);
        }
        else {
            this.bodega = null;
        }
    }
    
    public String getBodegaStDescripcionDelegate() {
        if(bodega != null) {
            return bodega.getDescripcion();
        }
        else return "";
    }

    public String getBodegaSelect2Delegate() {
        return String.valueOf(getBodegaIdDelegate())
            + "|"
            + getBodegaStDescripcionDelegate();
    }

    public Integer getPuertoIdDelegate() {
        if(puerto != null) {
            return puerto.getSkPuerto();
        }
        else return null;
    } 

    public void setPuertoIdDelegate(Integer skPuerto) {
        if(skPuerto != null) {
            this.puerto = new Puerto();
            this.puerto.setSkPuerto(skPuerto);
        }
        else {
            this.puerto = null;
        }
    }
    
    public String getPuertoStDescripcionDelegate() {
        if(puerto != null) {
            return puerto.getDescripcion();
        }
        else return "";
    }

    public String getPuertoSelect2Delegate() {
        return String.valueOf(getPuertoIdDelegate())
            + "|"
            + getPuertoStDescripcionDelegate();
    }
    
    public Integer getId() {
        return this.skPlanEntrega;
    } 

}

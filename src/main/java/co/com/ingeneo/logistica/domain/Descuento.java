package co.com.ingeneo.logistica.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

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
@Table(name = "descuento", schema="logistica")
public class Descuento implements Serializable {
    @Getter
    private static final long serialVersionUID = 1L;

    @Id
    @Basic(fetch = FetchType.EAGER)
    @Column(name = "sk_descuento")
    @EqualsAndHashCode.Include
    @JsonView
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "logistica.SEQ_descuento")
    @SequenceGenerator(name = "logistica.SEQ_descuento", sequenceName = "logistica.SEQ_descuento", allocationSize = 1)
    private Integer skDescuento; 

    @Column(name = "cant_min_prod" )
    @NotNull(message = "No puede estar vacio el campo cantMinProd")
    private Integer cantMinProd; 

    @Column(name = "cant_max_prod" )
    @NotNull(message = "No puede estar vacio el campo cantMaxProd")
    private Integer cantMaxProd; 

    @Column(name = "porc_desc" )
    @NotNull(message = "No puede estar vacio el campo porcDesc")
    private Integer porcDesc; 

    @Getter(onMethod = @__( @JsonIgnore))
    @JoinColumn(name = "sk_tipo_logistica", referencedColumnName = "sk_tipo_logistica")
    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private TipoLogistica tipoLogistica; 

    // delegates de ids

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
    
    public Integer getId() {
        return this.skDescuento;
    }
    
}

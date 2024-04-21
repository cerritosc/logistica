package co.com.ingeneo.logistica.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;
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
@Table(name = "puerto", schema="logistica")
public class Puerto implements Serializable {
    @Getter
    private static final long serialVersionUID = 1L;

    @Id
    @Basic(fetch = FetchType.EAGER)
    @Column(name = "sk_puerto")
    @EqualsAndHashCode.Include
    @JsonView
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "logistica.SEQ_puerto")
    @SequenceGenerator(name = "logistica.SEQ_puerto", sequenceName = "logistica.SEQ_puerto", allocationSize = 1)
    private Integer skPuerto; 

    @Column(name = "descripcion" )
    @NotBlank(message = "No puede estar vacio el campo descripcion")
    @Size(max = 128, message = "El campo descripcion excede la longitud permitida")
    private String descripcion; 



    @OneToMany(mappedBy = "puerto", cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
    @Getter(onMethod = @__(@JsonIgnore))
    @ToString.Exclude
    private Set<PlanEntrega> planEntregaes;


    @Getter(onMethod = @__( @JsonIgnore))
    @JoinColumn(name = "sk_pais", referencedColumnName = "sk_pais")
    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Pais pais; 



    // delegates de ids
    public Integer getPaisIdDelegate() {
        if(pais != null) {
            return pais.getSkPais();
        }
        else return null;
    } 

    public void setPaisIdDelegate(Integer skPais) {
        if(skPais != null) {
            this.pais = new Pais();
            this.pais.setSkPais(skPais);
        }
        else {
            this.pais = null;
        }
    }
    
    public String getPaisStDescripcionDelegate() {
        if(pais != null) {
            return pais.getDescripcion();
        }
        else return "";
    }

    public String getPaisSelect2Delegate() {
        return String.valueOf(getPaisIdDelegate())
            + "|"
            + getPaisStDescripcionDelegate();
    }
    
    public Integer getIdPais() {
        return getPaisIdDelegate();
    } 
    
    public Integer getId() {
        return this.skPuerto;
    } 

}

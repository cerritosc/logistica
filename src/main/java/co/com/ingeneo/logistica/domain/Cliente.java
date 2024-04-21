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
@Table(name = "cliente", schema="logistica")
public class Cliente implements Serializable {
    @Getter
    private static final long serialVersionUID = 1L;

    @Id
    @Basic(fetch = FetchType.EAGER)
    @Column(name = "sk_cliente")
    @EqualsAndHashCode.Include
    @JsonView
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "logistica.SEQ_cliente")
    @SequenceGenerator(name = "logistica.SEQ_cliente", sequenceName = "logistica.SEQ_cliente", allocationSize = 1)
    private Integer skCliente; 

    @Column(name = "codigo" )
    @NotBlank(message = "No puede estar vacio el campo codigo")
    @Size(max = 128, message = "El campo codigo excede la longitud permitida")
    private String codigo; 

    @Column(name = "nombres" )
    @NotBlank(message = "No puede estar vacio el campo nombres")
    @Size(max = 1024, message = "El campo nombres excede la longitud permitida")
    private String nombres; 

    @Column(name = "apellidos_razon_social" )
    @Size(max = 1024, message = "El campo apellidosRazonSocial excede la longitud permitida")
    private String apellidosRazonSocial; 

    @Column(name = "direccion" )
    @Size(max = 1024, message = "El campo direccion excede la longitud permitida")
    private String direccion; 

    @OneToMany(mappedBy = "cliente", cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
    @Getter(onMethod = @__(@JsonIgnore))
    @ToString.Exclude
    private Set<PlanEntrega> planEntregaes;

    // delegates de ids
}

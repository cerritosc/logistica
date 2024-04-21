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
@Table(name = "pais", schema="logistica")
public class Pais implements Serializable {
    @Getter
    private static final long serialVersionUID = 1L;

    @Id
    @Basic(fetch = FetchType.EAGER)
    @Column(name = "sk_pais")
    @EqualsAndHashCode.Include
    @JsonView
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "logistica.SEQ_pais")
    @SequenceGenerator(name = "logistica.SEQ_pais", sequenceName = "logistica.SEQ_pais", allocationSize = 1)
    private Integer skPais;  

    @Column(name = "codigo" )
    @NotBlank(message = "No puede estar vacio el campo codigo")
    @Size(max = 4, message = "El campo codigo excede la longitud permitida")
    private String codigo;

    @Column(name = "descripcion" )
    @NotBlank(message = "No puede estar vacio el campo descripcion")
    @Size(max = 128, message = "El campo descripcion excede la longitud permitida")
    private String descripcion; 



    @OneToMany(mappedBy = "pais", cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
    @Getter(onMethod = @__(@JsonIgnore))
    @ToString.Exclude
    private Set<Puerto> puertoes;




    // delegates de ids
}

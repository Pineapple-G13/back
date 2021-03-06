package com.example.vash.entity;
import com.example.vash.enums.Rol;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * En esta clase entidad generamos los atributos que va a tener nuestro entidad usuario, gracias a la dependencia
 * lombok los getter y setter se generar automaticamenete llamando a la anotacion@,creamos una lista de contantes para el rol.
 */
@Entity
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@SQLDelete(sql = "UPDATE usuario SET alta = false WHERE id = ?")
public class Usuario {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellido;

    @Column(nullable = false, unique = true)
    private String correo;

    @Column(nullable = false)
    private String clave;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Rol rol;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime creacion;

    @LastModifiedDate
    private LocalDateTime modificacion;

    private Boolean alta;

    @Override
    public String toString() {
        return String.format("USUARIO (id: %s, nombre: %s, apellido: %s, correo: %s, clave: %s)", id, nombre, apellido, correo, clave);
    }
}

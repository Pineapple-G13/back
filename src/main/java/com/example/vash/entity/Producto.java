package com.example.vash.entity;

import com.example.vash.enums.Categoria;
import com.example.vash.enums.Rol;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * La sección de Products deberá tener la siguiente información:
 * Listado de productos (name, product category, description, quantity, unit price, picture)
 */
@Entity
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
//@SQLDelete(sql = "UPDATE usuario SET alta = false WHERE id = ?")
public class Producto {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nombre;

    @Enumerated(EnumType.STRING)
    private Categoria categoria;

    private String descripcion;

    @Column(nullable = false)
    private Double cantidad;

    //por buena práctica este tipo se dice el adecuado para manejar dinero
    @Column(nullable = false)
    private BigDecimal precioUnitario;

    //foto

    @LastModifiedDate
    private LocalDateTime modificacion;
}

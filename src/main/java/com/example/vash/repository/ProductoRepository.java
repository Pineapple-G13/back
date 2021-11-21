package com.example.vash.repository;

import com.example.vash.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    @Override
    Optional<Producto> findById(Integer id);
    Optional<Producto> findBynombre(String nombre);

}

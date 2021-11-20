//Jpa nos provee el crud para que nosotros podamos gestionarlo a la base de datos,
//aparte podemos implementar querys o otras consultas que quisieramos .
//Una vez gestionado esto ,podemos realizar los metodos en el service
package com.example.vash.repository;
import com.example.vash.entity.Usuario;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByCorreo(String corre);

    boolean existsByCorreo(String correo);

    @Modifying
    @Query("UPDATE Usuario u SET u.alta = true WHERE u.id = :id")
    void habilitar(@Param("id") Integer id);
}

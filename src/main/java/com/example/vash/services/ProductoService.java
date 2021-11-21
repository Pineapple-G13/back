package com.example.vash.services;

import com.example.vash.entity.Producto;
import com.example.vash.entity.Usuario;
import com.example.vash.enums.Rol;
import com.example.vash.exception.SpringException;
import com.example.vash.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class ProductoService {
//no implementé UserDetailService por no ser un User, se buscará la implementación adecuada

    @Autowired
    private ProductoRepository productoRepository;

    private String mensaje = "Este producto no existe";

    @Transactional
    public void crear(Producto dto) throws SpringException {
        /*
            Ejemplo de Manejo de excepción
            if (UsarioRepository.existsByCorreo(dto.getCorreo())) {
            throw new SpringException("Ya existe un usuario asociado al correo ingresado");
        }*/
        Producto producto = new Producto();
        producto.setNombre(dto.getNombre());
        producto.setCategoria(dto.getCategoria());
        producto.setDescripcion(dto.getDescripcion());
        producto.setCantidad(dto.getCantidad());
        producto.setPrecioUnitario(dto.getPrecioUnitario());
        productoRepository.save(producto);

    }

    @Transactional
    public void modificar(Producto dto) throws SpringException {
        Producto producto =
                productoRepository.findById(dto.getId()).orElseThrow(() -> new SpringException(String.format(mensaje,
                dto.getId())));
        producto.setNombre(dto.getNombre());
        producto.setCategoria(dto.getCategoria());
        producto.setDescripcion(dto.getDescripcion());
        producto.setCantidad(dto.getCantidad());
        producto.setPrecioUnitario(dto.getPrecioUnitario());
        productoRepository.save(producto);
    }
    @Transactional
    public List<Producto>buscarTodos(){ return productoRepository.findAll();}

    @Transactional()//readOnly = true)
    public Producto buscarPorId(Integer id) throws SpringException {
        return productoRepository.findById(id).orElseThrow(() -> new SpringException(String.format(mensaje, id)));
    }

    @Transactional
    public void eliminar(Integer id) {
        productoRepository.deleteById(id);
    }
}

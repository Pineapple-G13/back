package com.example.vash.services;
import com.example.vash.entity.Usuario;
import com.example.vash.enums.Rol;
import com.example.vash.exception.SpringException;
import com.example.vash.repository.UsuarioRepository;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    private String mensaje = "No existe ningún usuario asociado con el ID %s";

    @Transactional
    public void crear(Usuario dto) throws SpringException {
        if (usuarioRepository.existsByCorreo(dto.getCorreo())) {
            throw new SpringException("Ya existe un usuario asociado al correo ingresado");
        }
        Usuario usuario = new Usuario();
        usuario.setNombre(dto.getNombre());
        usuario.setApellido(dto.getApellido());
        usuario.setCorreo(dto.getCorreo());
        usuario.setClave(encoder.encode(dto.getClave()));
        if (usuarioRepository.findAll().isEmpty()) {
            usuario.setRol(Rol.ADMIN);
        } else if (dto.getRol() == null) {
            usuario.setRol(Rol.USER);
        } else {
            usuario.setRol(dto.getRol());
        }
        usuario.setAlta(true);
        usuarioRepository.save(usuario);
    }

    @Transactional
    public void modificar(Usuario dto) throws SpringException {
        Usuario usuario = usuarioRepository.findById(dto.getId()).orElseThrow(() -> new SpringException(String.format(mensaje, dto.getId())));
        usuario.setNombre(dto.getNombre());
        usuario.setApellido(dto.getApellido());
        usuario.setCorreo(dto.getCorreo());
        usuario.setRol(dto.getRol());
        usuarioRepository.save(usuario);
    }

    @Transactional(readOnly = true)
    public List<Usuario> buscarTodos() {
        return usuarioRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Usuario buscarPorId(Integer id) throws SpringException {
        return usuarioRepository.findById(id).orElseThrow(() -> new SpringException(String.format(mensaje, id)));
    }

    @Transactional
    public void habilitar(Integer id) {
        usuarioRepository.habilitar(id);
    }

    @Transactional
    public void eliminar(Integer id) {
        usuarioRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
       Usuario usuario = usuarioRepository.findByCorreo(correo).orElseThrow(() -> new UsernameNotFoundException("no existe usuario asociado al correo"));
    GrantedAuthority authority=new SimpleGrantedAuthority("ROLE_"+usuario.getRol().name());
    return new User(usuario.getCorreo(),usuario.getClave(),Collections.singletonList(authority));
    }

}

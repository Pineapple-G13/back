//Aca creamos toda las rutas y se conecta con los templates.cada ruta redirige a un html
//creamos los get y los post.
package com.example.vash.controller;
import com.example.vash.entity.Usuario;
import com.example.vash.exception.SpringException;
import com.example.vash.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ModelAndView mostrar(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("usuarios");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);

        if (flashMap != null) {
            mav.addObject("exito", flashMap.get("exito"));
            mav.addObject("error", flashMap.get("error"));
        }

        mav.addObject("usuarios", usuarioService.buscarTodos());
        return mav;
    }

    @GetMapping("/crear")
    public ModelAndView crear(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("usuario-formulario");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);

        if (flashMap != null) {
            mav.addObject("error", flashMap.get("error"));
            mav.addObject("usuario", flashMap.get("usuario"));
        } else {
            mav.addObject("usuario", new Usuario());
        }

        mav.addObject("title", "Crear Usuario");
        mav.addObject("action", "guardar");
        return mav;
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editar(@PathVariable Integer id, HttpServletRequest request, RedirectAttributes attributes) {
        ModelAndView mav = new ModelAndView("usuario-formulario");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);

        try {
            if (flashMap != null) {
                mav.addObject("error", flashMap.get("error"));
                mav.addObject("usuario", flashMap.get("usuario"));
            } else {
                mav.addObject("usuario", usuarioService.buscarPorId(id));
            }

            mav.addObject("title", "Editar Usuario");
            mav.addObject("action", "modificar");
        } catch (SpringException e) {
            attributes.addFlashAttribute("error", e.getMessage());
            mav.setViewName("redirect:/usuario");
        }

        return mav;
    }

    @PostMapping("/guardar")
    public RedirectView guardar(@ModelAttribute Usuario usuario, RedirectAttributes attributes) {
        RedirectView redirectView = new RedirectView("/usuario");

        try {
            usuarioService.crear(usuario);
            attributes.addFlashAttribute("exito", "La creación ha sido realizada satisfactoriamente");
        } catch (SpringException e) {
            attributes.addFlashAttribute("usuario", usuario);
            attributes.addFlashAttribute("error", e.getMessage());
            redirectView.setUrl("/usuario/crear");
        }

        return redirectView;
    }

    @PostMapping("/modificar")
    public RedirectView modificar(@ModelAttribute Usuario usuario, RedirectAttributes attributes) {
        RedirectView redirectView = new RedirectView("/usuario");

        try {
            usuarioService.modificar(usuario);
            attributes.addFlashAttribute("exito", "La actualización ha sido realizada satisfactoriamente");
        } catch (SpringException e) {
            attributes.addFlashAttribute("usuario", usuario);
            attributes.addFlashAttribute("error", e.getMessage());
            redirectView.setUrl("/usuario/editar/" + usuario.getId());
        }

        return redirectView;
    }

    @PostMapping("/habilitar/{id}")
    public RedirectView habilitar(@PathVariable Integer id) {
        usuarioService.habilitar(id);
        return new RedirectView("/usuario");
    }

    @PostMapping("/eliminar/{id}")
    public RedirectView eliminar(@PathVariable Integer id) {
        usuarioService.eliminar(id);
        return new RedirectView("/usuario");
    }
}


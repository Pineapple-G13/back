package com.example.vash.controller;

import com.example.vash.entity.Producto;
import com.example.vash.entity.Usuario;
import com.example.vash.exception.SpringException;
import com.example.vash.services.ProductoService;
import com.example.vash.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/producto")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public ModelAndView mostrar(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("productos");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);

        if (flashMap != null) {
            mav.addObject("exito", flashMap.get("exito"));
            mav.addObject("error", flashMap.get("error"));
        }

        mav.addObject("productos", productoService.buscarTodos());
        return mav;
    }

    @GetMapping("/crear")
    public ModelAndView crear(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("producto-formulario");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);

        if (flashMap != null) {
            mav.addObject("error", flashMap.get("error"));
            mav.addObject("producto", flashMap.get("producto"));
        } else {
            mav.addObject("producto", new Producto());
        }

        mav.addObject("title", "Añadir Producto");
        mav.addObject("action", "guardar");
        return mav;
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editar(@PathVariable Integer id, HttpServletRequest request, RedirectAttributes attributes) {
        ModelAndView mav = new ModelAndView("producto-formulario");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);

        try {
            if (flashMap != null) {
                mav.addObject("error", flashMap.get("error"));
                mav.addObject("producto", flashMap.get("producto"));
            } else {
                mav.addObject("producto", productoService.buscarPorId(id));
            }

            mav.addObject("title", "Editar Producto");
            mav.addObject("action", "modificar");
        } catch (SpringException e) {
            attributes.addFlashAttribute("error", e.getMessage());
            mav.setViewName("redirect:/producto");
        }

        return mav;
    }

    @PostMapping("/guardar")
    public RedirectView guardar(@ModelAttribute Producto producto, RedirectAttributes attributes) {
        RedirectView redirectView = new RedirectView("/producto");

        try {
            productoService.crear(producto);
            attributes.addFlashAttribute("exito", "La creación ha sido realizada satisfactoriamente");
        } catch (SpringException e) {
            attributes.addFlashAttribute("producto", producto);
            attributes.addFlashAttribute("error", e.getMessage());
            redirectView.setUrl("/producto/crear");
        }

        return redirectView;
    }

    @PostMapping("/modificar")
    public RedirectView modificar(@ModelAttribute Producto producto, RedirectAttributes attributes) {
        RedirectView redirectView = new RedirectView("/producto");

        try {
            productoService.modificar(producto);
            attributes.addFlashAttribute("exito", "La actualización ha sido realizada satisfactoriamente");
        } catch (SpringException e) {
            attributes.addFlashAttribute("producto", producto);
            attributes.addFlashAttribute("error", e.getMessage());
            redirectView.setUrl("/producto/editar/" + producto.getId());
        }

        return redirectView;
    }

    @PostMapping("/eliminar/{id}")
    public RedirectView eliminar(@PathVariable Integer id) {
        productoService.eliminar(id);
        return new RedirectView("/usuario");
    }

}



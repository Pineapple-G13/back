package com.example.vash.services;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.util.List;

import org.assertj.core.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.vash.entity.Producto;
import com.example.vash.enums.Categoria;
import com.example.vash.repository.ProductoRepository;
import com.fasterxml.jackson.annotation.JacksonInject.Value;

public class ProductoServiceTest {
	
	
	@Mock
	private ProductoRepository productoRepository;
	
	@InjectMocks
	private ProductoService productoService;
	
	private Producto producto;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		
		producto = new Producto();
		producto.setNombre("pera");
		producto.setId(10);
		producto.setCantidad(100.);
		
		
		
	}

	@Test
	public void testBuscarTodos() {
		when(productoRepository.findAll()).thenReturn(Arrays.asList(producto));
	}

}

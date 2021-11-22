package com.example.vash.enums;

/**
 * Categorías fijas para hacer el ordenamiento de los productos. Implementada siguiendo las intrucciones de la
 * siguiente documentación:
 * https://www.baeldung.com/thymeleaf-enums
 */
public enum Categoria {
    FRUTAS_VERDURAS("Frutas y verduras"),
    ABARROTES("Abarrotes"),
    HIGIENE("Higiene Personal"),
    LIMPIEZA("Limpieza"),
    LICORES("Vinos y Licores"),
    LACTEOS("Lácteos"),
    CARNE("Carne y Salchichonería");

    private final String nombreCategoria;

    private Categoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    public String getNombreCategoria(){
        return nombreCategoria;
    }
}

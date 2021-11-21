package com.example.vash.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Categoria {
    FRUTAS_VERDURAS("Frutas y verduras"),
    ABARROTES("Abarrotes"),
    HIGIENE("Higiene Personal"),
    LIMPIEZA("Limpieza"),
    LICORES("Vinos y Licores"),
    LACTEOS("Lácteos"),
    CARNE("Carne y Salchichonería");

    private String nombreCategoria;
}

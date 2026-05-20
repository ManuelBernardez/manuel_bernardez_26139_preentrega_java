package com.techlab.app;

import com.techlab.service.*;
import com.techlab.domain.model.*;
import com.techlab.domain.repository.*;
import com.techlab.presentation.console.*;
import static com.techlab.utils.EntradaDatos.leerEntero;

import java.util.Scanner;


public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // Repositorios (Estructura de datos)
        Repositorio<Categoria> repoCategorias = new Repositorio<>();
        Repositorio<Producto> repoProductos = new Repositorio<>();

        // Servicios (Lógica)
        CategoriaService categoriaService = new CategoriaService(repoCategorias);
        ProductoService productoService = new ProductoService(repoProductos, repoCategorias);

        categoriaService.crear("Alimentos", "Estos productos tienen Vencimiento");
        categoriaService.crear("Electronica", "Estos productos tienen Garantía");

        // Menús (UI)
        MenuCategorias menuCategorias = new MenuCategorias(scanner, categoriaService);
        MenuProductos menuProductos = new MenuProductos(scanner, productoService);

        // ===== MENÚ PRINCIPAL =====
        int opcion;

        do {
            System.out.println("\n==========================");
            System.out.println("    SISTEMA DE GESTIÓN    ");
            System.out.println("==========================");
            System.out.println("1. Gestionar productos");
            System.out.println("2. Gestionar categorías");
            System.out.println("3. Salir");
            System.out.println("==========================");

            opcion = leerEntero(scanner, "Elegir opción (1-3): ");

            switch (opcion) {
                case 1:
                    menuProductos.ejecutar();
                    break;
                case 2:
                    menuCategorias.ejecutar();
                    break;
                case 3:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }

        } while (opcion != 3);

        scanner.close();
    }
}

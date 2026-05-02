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
        RepositorioGenerico<Categoria> repoCategorias = new RepositorioGenerico<>();
        RepositorioGenerico<Producto> repoProductos = new RepositorioGenerico<>();

        repoCategorias.agregar(new Categoria(1, "Alimentos", "Estos productos tienen vencimiento y afectan al precio final"));
        repoCategorias.agregar(new Categoria(2, "Electrónica", "Estos productos tienen garantía y afectan al precio final"));


        // Servicios (Lógica)
        CategoriaService categoriaService = new CategoriaService(repoCategorias);
        ProductoService productoService = new ProductoService(repoProductos, repoCategorias);

        // Menús (UI)
        MenuCategorias menuCategorias = new MenuCategorias(scanner, categoriaService);
        MenuProductos menuProductos = new MenuProductos(scanner, productoService);

        // TODO: Interfaz para repositorio, elegir temática del e-commerce, repensar los métodos de entrada de datos (scanner estático?)

        // ===== MENÚ PRINCIPAL =====
        int opcion;

        do {
            System.out.println("\n==========================");
            System.out.println("    SISTEMA DE GESTIÓN    ");
            System.out.println("==========================");
            System.out.println("1. Gestionar productos");
            System.out.println("2. Gestionar categorías");
            System.out.println("0. Salir");
            System.out.println("==========================");

            opcion = leerEntero(scanner,"Seleccione una opción: ");

            switch (opcion) {
                case 1:
                    menuProductos.ejecutar();
                    break;
                case 2:
                    menuCategorias.ejecutar();
                    break;
                case 0:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }

        } while (opcion != 0);

        scanner.close();
    }
}

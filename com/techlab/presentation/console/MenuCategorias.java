package com.techlab.presentation.console;

import static com.techlab.utils.EntradaDatos.*;
import com.techlab.service.CategoriaService;
import com.techlab.domain.model.Categoria;
import com.techlab.domain.exception.*;

import java.util.Scanner;

public class MenuCategorias extends Menu {
    private final CategoriaService categoriaService;

    public MenuCategorias(Scanner scanner, CategoriaService categoriaService) {
        super(scanner);
        this.categoriaService = categoriaService;
    }

    @Override
    public void mostrarMenu() {
        System.out.println("\n=========== MENÚ CATEGORÍAS ===========");
        System.out.println("1. Ingresar categoría");
        System.out.println("2. Listar categorías");
        System.out.println("3. Consultar categoría");
        System.out.println("4. Modificar categoría");
        System.out.println("5. Eliminar categoría");
        System.out.println("0. Volver");
    }

    @Override
    public void ejecutar() {
        int opcion;

        do {
            mostrarMenu();
            opcion = leerEntero(scanner, "Elija una opción(0-5): ");

            switch (opcion) {
                case 1:
                    System.out.println("\n--- NUEVA CATEGORÍA ---");
                    crear();
                    break;
                case 2:
                    System.out.println("\n--- LISTADO DE CATEGORÍAS ---");
                    listar();
                    break;
                case 3:
                    System.out.println("\n--- BUSCAR CATEGORÍA ---");
                    buscar();
                    break;
                case 4:
                    System.out.println("\n--- MODIFICAR CATEGORÍA ---");
                    modificar();
                    break;
                case 5:
                    System.out.println("\n--- ELIMINAR CATEGORÍA ---");
                    eliminar();
                    break;
                case 0:
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Error: Opción inválida");
            }

        } while (opcion != 0);
    }

    @Override
    protected void crear() {

        String nombre = leerTexto(scanner,"Ingrese el nombre: ");
        String descripcion = leerTexto(scanner, "Ingrese la descripción: ");

        try {
            categoriaService.crear(nombre, descripcion);
            System.out.println("Categoría creada correctamente");

        } catch (CategoriaDuplicadaException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override
    protected void listar() {
        for (Categoria c : categoriaService.listar()) {
            System.out.println(c);
        }
    }

    @Override
    protected void buscar() {

        int codigo = leerEntero(scanner, "Ingrese el código: ");

        try {
            Categoria c = categoriaService.buscar(codigo);
            System.out.println("Categoría encontrada:");
            System.out.println(c);

        } catch (CategoriaNoEncontradaException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override
    protected void modificar() {

        int codigo = leerEntero(scanner, "Código de la categoría: ");
        String nombre = leerTexto(scanner, "Nuevo nombre: ");
        String descripcion = leerTexto(scanner, "Nueva descripción: ");

        try {
            categoriaService.modificar(codigo, nombre, descripcion);
            System.out.println("Categoría modificada correctamente");
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    protected void eliminar() {

        int codigo = leerEntero(scanner, "Ingrese el código: ");

        if (leerSiNo(scanner, "¿Borrar definitivamente?")) {
            try {
                categoriaService.eliminar(codigo);
                System.out.println("Categoría eliminada correctamente");
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Operación cancelada");
        }
    }
}
package com.techlab.presentation.console;

import static com.techlab.utils.EntradaDatos.*;

import com.techlab.domain.model.Producto;
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
        System.out.println("6. Volver");
    }

    @Override
    public void ejecutar() {
        int opcion;

        do {
            mostrarMenu();
            opcion = leerEntero(scanner, "Elija una opción(1-6): ");

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
                    // Buscar() definido en la clase abstracta Menu
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
                case 6:
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Error: Opción inválida");
            }

        } while (opcion != 6);
    }

    @Override
    protected void crear() {

        String nombre = leerTexto(scanner,"Ingrese el nombre: ");
        String descripcion = leerTexto(scanner, "Ingrese la descripción: ");

        try {
            categoriaService.crear(nombre, descripcion);
            System.out.println("Categoría creada correctamente");

        } catch (CategoriaDuplicadaException e) {
            System.out.println("Error. " + e.getMessage());
        }
    }

    @Override
    protected void listar() {

        if (categoriaService.listar().isEmpty())
            System.out.println("No hay categorías cargadas");
        else
            for (Categoria c : categoriaService.listar())
                System.out.println(c);
    }

    @Override
    protected void buscarPorCodigo() {
        try {
            int codigo = leerEntero(scanner, "Ingrese el código: ");

            Categoria c = categoriaService.buscarPorCodigo(codigo);
            System.out.println("Categoría encontrada:" + c);

        } catch (CategoriaNoEncontradaException e) {
            System.out.println(e.getMessage());
        }
    }

    protected void buscarPorNombre() {
        try {
            String nombre = leerTexto(scanner, "Ingrese el nombre: ");

            Categoria c = categoriaService.buscarPorNombre(nombre);
            System.out.println("Categoría encontrada:" + c);

        } catch (CategoriaNoEncontradaException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    protected void modificar() {

        int c = leerEntero(scanner, "Código de la categoría: ");
        Categoria categoria = categoriaService.buscarPorCodigo(c);

        String nombre = "";
        if(leerSiNo(scanner, "¿Modificar nombre?"))
            nombre = leerTexto(scanner, "Nuevo nombre: ");

        String descripcion = leerTexto(scanner, "Nueva descripción: ");

        try{
            categoriaService.modificar(categoria, nombre, descripcion);
            System.out.println("Categoría modificada correctamente");

        } catch (CategoriaDuplicadaException e){
            System.out.println("Error al cambiar de nombre. " + e.getMessage());
        }
    }

    @Override
    protected void eliminar() {

        if (categoriaService.estaVacio()) {
            System.out.println("No hay categorías cargadas");
            return;
        }

        int codigo = leerEntero(scanner, "Ingrese el código: ");

        if (leerSiNo(scanner, "¿Borrar definitivamente?")) {
                categoriaService.eliminar(codigo);
                System.out.println("Categoría eliminada correctamente");
        }
        else
            System.out.println("Operación cancelada");
    }
}
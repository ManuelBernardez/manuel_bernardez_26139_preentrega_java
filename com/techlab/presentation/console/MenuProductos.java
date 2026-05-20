package com.techlab.presentation.console;

import static com.techlab.utils.EntradaDatos.*;

import com.techlab.domain.exception.CategoriaNoEncontradaException;
import com.techlab.domain.exception.ProductoDuplicadoException;
import com.techlab.domain.exception.ProductoNoEncontradoException;
import com.techlab.service.ProductoService;
import com.techlab.domain.model.Producto;

import java.util.Scanner;

public class MenuProductos extends Menu {

    private final ProductoService productoService;

    public MenuProductos(Scanner scanner, ProductoService productoService) {
        super(scanner);
        this.productoService = productoService;
    }

    @Override
    public void mostrarMenu() {
        System.out.println("\n--- MENÚ PRODUCTOS ---");
        System.out.println("1 - Ingresar producto");
        System.out.println("2 - Listar producto");
        System.out.println("3 - Consultar producto");
        System.out.println("4 - Modificar producto");
        System.out.println("5 - Eliminar producto");
        System.out.println("6 - Volver");
        System.out.println("----------------------");
    }

    @Override
    public void ejecutar() {
        int opcion;

        do {
            mostrarMenu();
            opcion = leerEntero(scanner, "\nElija una opción(1-6): ");

            switch (opcion) {
                case 1:
                    System.out.println("\n--- NUEVO PRODUCTO ---");
                    crear();

                    break;
                case 2:
                    System.out.println("\n--- LISTADO DE PRODUCTOS ---");
                    listar();

                    break;
                case 3:
                    System.out.println("\n--- CONSULTAR PRODUCTO ---");
                    // Buscar() definido en la clase abstracta Menu
                    buscar();

                    break;
                case 4:
                    System.out.println("\n--- MODIFICAR PRODUCTOS ---");
                    modificar();

                    break;
                case 5:
                    System.out.println("\n--- ELIMINAR PRODUCTOS ---");
                    eliminar();

                    break;
                case 6:
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Error: Opción inválida.");
            }

        } while (opcion != 6);
    }

    @Override
    protected void crear() {

        try {
            String nombre = leerTexto(scanner, "Ingrese nombre: ");
            double precio = leerDouble(scanner, "Ingrese precio: ");
            String categoria = leerTexto(scanner, "Nombre de categoría: ").trim();

            switch (categoria) {
                case "Alimentos":
                    int vencimiento = leerEntero(scanner, "Vence en (días): ");
                    productoService.crearAlimenticio(nombre, precio, categoria, vencimiento);
                    break;

                case "Electronica":
                    double garantia = leerDouble(scanner, "Meses de garantía: ");
                    productoService.crearElectronico(nombre, precio, categoria, garantia);
                    break;

                default:
                    System.out.println("Categoría inválida");
                    throw new CategoriaNoEncontradaException(categoria);
            }

            System.out.println("Producto creado correctamente");

        } catch (ProductoDuplicadoException | CategoriaNoEncontradaException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override
    protected void listar() {

        // Añadí mensaje de validación
        if (productoService.listar().isEmpty())
            System.out.println("No hay elementos cargados");
        else
            for (Producto p : productoService.listar())
                System.out.println(p);
    }

    @Override
    protected void buscarPorCodigo() {

        try {
            int codigo = leerEntero(scanner, "Ingrese el código: ");

            Producto p = productoService.buscarPorCodigo(codigo);
            System.out.println("Producto: " + p);

        } catch (ProductoNoEncontradoException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    protected void buscarPorNombre() {

        try {
            String nombre = leerTexto(scanner, "Ingrese el nombre: ");

            Producto p = productoService.buscarPorNombre(nombre);
            System.out.println("Encontrado: " + p);

        } catch (ProductoNoEncontradoException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    protected void modificar() {

        int codigo = leerEntero(scanner, "Código del producto: ");
        Producto producto = productoService.buscarPorCodigo(codigo);

        // Cambiar el nombre es opcional
        String nombre = "";
        if(leerSiNo(scanner, "¿Modificar nombre?"))
            nombre = leerTexto(scanner, "Nuevo nombre: ");

        double precio = leerDouble(scanner, "Nuevo precio: ");

        try{
            productoService.modificar(producto, nombre, precio);
            System.out.println("Producto modificado correctamente");

        } catch (ProductoDuplicadoException e){
            System.out.println("Error al cambiar de nombre. " + e.getMessage());
        }
    }

    @Override
    protected void eliminar() {

        int codigo = leerEntero(scanner, "Ingrese el código: ");

        if (leerSiNo(scanner, "¿Borrar definitivamente?")) {
            productoService.eliminar(codigo);
            System.out.println("Producto eliminado correctamente");
        }
        else {
            System.out.println("Operación cancelada");
        }
    }
}

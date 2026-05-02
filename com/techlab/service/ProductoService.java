package com.techlab.service;

import com.techlab.domain.repository.RepositorioGenerico;
import static com.techlab.utils.EntradaDatos.*;
import com.techlab.domain.exception.*;
import com.techlab.utils.Secuencias;
import com.techlab.domain.model.*;

import java.util.List;
import java.util.Scanner;


public class ProductoService {
    private final RepositorioGenerico<Producto> repoProductos;
    private final RepositorioGenerico<Categoria> repoCategorias;

    public ProductoService(RepositorioGenerico<Producto> repoProductos,
                           RepositorioGenerico<Categoria> repoCategorias) {
        this.repoProductos = repoProductos;
        this.repoCategorias = repoCategorias;
    }

    public void crearProducto(String nombre, double precio, int codigoCategoria) {
        Scanner scanner = new Scanner(System.in);

        for (Producto p : repoProductos.listado()) {
            if (p.getNombre().equalsIgnoreCase(nombre))
                throw new ProductoDuplicadoException(nombre);
        }

        Categoria c = repoCategorias.buscarPorCodigo(codigoCategoria);
        if (c == null)
            throw new CategoriaNoEncontradaException(codigoCategoria);

        int codigo = Secuencias.generarCodigoCategoria();
        Producto producto;

        switch (codigoCategoria) {
            case 1:
                int vencimiento = leerEntero(scanner, "Caduca en (x días): ");
                producto = new ProductoAlimenticio(codigo, nombre, precio, c, vencimiento);
                break;

            case 2:
                double mesesGarantia = leerDouble(scanner, "Meses de garantía: ");
                producto = new ProductoElectronico(codigo, nombre, precio, c, mesesGarantia);
                break;

            default:
                System.out.println("Tipo de producto inválido");
                return;
        }

        repoProductos.agregar(producto);
    }

    public List<Producto> listar() {
        return repoProductos.listado();
    }

    public Producto buscar(int codigo) {

        Producto p = repoProductos.buscarPorCodigo(codigo);

        if (p == null)
            throw new ProductoNoEncontradoException(codigo);

        return p;
    }

    public void modificar(int codigo, String nombre, double precio) {
        Producto p = buscar(codigo);
        p.setNombre(nombre);
        p.setPrecio(precio);
    }

    public void eliminar(int codigo) {
        Producto p = buscar(codigo);
        repoProductos.eliminar(p);
    }
}
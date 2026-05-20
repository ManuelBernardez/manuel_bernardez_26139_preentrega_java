package com.techlab.service;

import com.techlab.domain.repository.Repositorio;
import com.techlab.domain.exception.*;
import com.techlab.utils.Secuencias;
import com.techlab.domain.model.*;
import static com.techlab.utils.Validar.esVacio;

import java.util.List;

public class ProductoService {
    private final Repositorio<Producto> repoProductos;
    private final Repositorio<Categoria> repoCategorias;

    public ProductoService(Repositorio<Producto> repoProductos, Repositorio<Categoria> repoCategorias) {
        this.repoProductos = repoProductos;
        this.repoCategorias = repoCategorias;
    }

    public void crearAlimenticio(String nombre, double precio, String categoria, int vencimiento) {
        validarCategoria(categoria);
        validarDuplicado(nombre);

        Producto p = new ProductoAlimenticio(Secuencias.generarCodigoProducto(), nombre, precio, categoria, vencimiento);
        repoProductos.agregar(p);
    }

    public void crearElectronico(String nombre, double precio, String categoria, double garantiaMeses) {
        validarCategoria(categoria);
        validarDuplicado(nombre);

        Producto p = new ProductoElectronico(Secuencias.generarCodigoProducto(), nombre, precio, categoria, garantiaMeses);
        repoProductos.agregar(p);
    }

    public List<Producto> listar() {
        return repoProductos.listado();
    }

    public Producto buscarPorCodigo(int codigo) {

        Producto p = repoProductos.buscarPorCodigo(codigo);

        if (p == null)
            throw new ProductoNoEncontradoException(codigo);

        return p;
    }

    public Producto buscarPorNombre(String nombre) {

        Producto p = repoProductos.buscarPorNombre(nombre);

        if (p == null)
            throw new ProductoNoEncontradoException(nombre);

        return p;
    }

    public void modificar(Producto p, String nombre, double precio) {

        p.setPrecio(precio);

        // Si se quiere cambiar el nombre, verifico que el nuevo nombre sea distinto al de los productos existentes
        if (!esVacio(nombre)) {
            Producto existente = repoProductos.buscarPorNombre(nombre);

            if (existente != null)
                throw new ProductoDuplicadoException(nombre);

            p.setNombre(nombre);
        }
    }

    public void eliminar(int codigo) {

        Producto p = buscarPorCodigo(codigo);
        repoProductos.eliminar(p);
    }

    private void validarDuplicado(String nombre) {

        if (repoProductos.buscarPorNombre(nombre) != null)
            throw new ProductoDuplicadoException(nombre);

    }

    private void validarCategoria(String categoria) {

        if (repoCategorias.buscarPorNombre(categoria) == null)
            throw new CategoriaNoEncontradaException(categoria);
    }

}
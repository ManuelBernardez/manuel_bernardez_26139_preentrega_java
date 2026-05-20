package com.techlab.service;

import com.techlab.domain.repository.Repositorio;
import com.techlab.domain.model.Categoria;
import com.techlab.domain.exception.*;
import com.techlab.utils.Secuencias;

import java.util.List;

import static com.techlab.utils.Validar.esVacio;

public class CategoriaService {

    private final Repositorio<Categoria> repoCategorias;

    public CategoriaService(Repositorio<Categoria> repoCategorias) {
        this.repoCategorias = repoCategorias;
    }

    public void crear(String nombre, String descripcion) {

        if (repoCategorias.buscarPorNombre(nombre) != null)
            throw new CategoriaDuplicadaException(nombre);

        Categoria categoria = new Categoria(Secuencias.generarCodigoCategoria(), nombre, descripcion);

        repoCategorias.agregar(categoria);
    }

    public List<Categoria> listar() {
        return repoCategorias.listado();
    }

    public Categoria buscarPorCodigo(int codigo) {

        Categoria c = repoCategorias.buscarPorCodigo(codigo);

        if (c == null)
            throw new CategoriaNoEncontradaException(codigo);

        return c;
    }

    public Categoria buscarPorNombre(String nombre) {

        Categoria c = repoCategorias.buscarPorNombre(nombre);

        if (c == null)
            throw new CategoriaNoEncontradaException(nombre);

        return c;
    }

    public void modificar(Categoria categoria, String nombre, String descripcion) {

        categoria.setDescripcion(descripcion);

        // Si se quiere cambiar el nombre, verifico que el nuevo nombre sea distinto al de los productos existentes
        if (!esVacio(nombre)) {
            Categoria existente = repoCategorias.buscarPorNombre(nombre);

            if (existente != null)
                throw new CategoriaDuplicadaException(nombre);

            categoria.setNombre(nombre);
        }
    }

    public void eliminar(int codigo) {
        Categoria c = buscarPorCodigo(codigo);
        repoCategorias.eliminar(c);
    }

    public boolean estaVacio(){
        return repoCategorias.estaVacio();
    }
}

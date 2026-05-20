package com.techlab.domain.model;

import com.techlab.domain.interfaces.*;

public abstract class Producto implements Calculable, Identificable
{
    protected int codigo;
    protected String nombre;
    protected double precio;
    protected String nombreCategoria;

    public Producto(int codigo, String nombre, double precio, String nombreCategoria){
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.nombreCategoria = nombreCategoria;
    }

    @Override
    public int getCodigo() {
        return codigo;
    }

    public String getNombre(){
        return nombre;
    }

    public void setNombre(String nuevoNombre){
        this.nombre = nuevoNombre;
    }

    public void setPrecio(double nuevoPrecio){
        this.precio = nuevoPrecio;
    }

    public abstract double calcularPrecioFinal();

    public abstract String getTipoProducto();

    public abstract String getDetalleEspecifico();

    @Override
    public String toString() {
        return String.format(
                "ID [%d] | Nombre: %s | Precio: $%.2f | Categoría: %s | %s",
                codigo, nombre, calcularPrecioFinal(), nombreCategoria, getDetalleEspecifico()
        );
    }
}


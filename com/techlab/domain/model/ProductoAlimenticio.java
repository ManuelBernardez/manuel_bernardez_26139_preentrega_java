package com.techlab.domain.model;

public class ProductoAlimenticio extends Producto {

    private int diasParaVencimiento;

    public ProductoAlimenticio(int codigo, String nombre, double precio, String categoria, int diasParaVencimiento){
        super(codigo, nombre, precio, categoria);
        this.diasParaVencimiento = diasParaVencimiento;
    }

    public int getDiasParaVencimiento() {
        return diasParaVencimiento;
    }

    @Override
    public double calcularPrecioFinal() {

        if (diasParaVencimiento < 15)
            return precio * 0.8;

        return precio;
    }

    @Override
    public String getTipoProducto(){
        return "Alimenticio";
    }

    @Override
    public String getDetalleEspecifico(){
        return "Días para vencimiento: " + this.diasParaVencimiento;
    }
}

package com.techlab.utils;

import java.util.Scanner;

import static com.techlab.utils.Validar.esPositivo;
import static com.techlab.utils.Validar.esVacio;

public final class EntradaDatos {

    private EntradaDatos(){}

    public static int leerEntero(Scanner scanner, String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                int numero = Integer.parseInt(scanner.nextLine());

                if (esPositivo(numero))
                    return numero;

                System.out.println("Error: el número debe ser positivo");

            } catch (NumberFormatException e) {
                System.out.println("Error: debe ingresar un número entero válido");
            }
        }
    }

    public static double leerDouble(Scanner scanner, String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                double numero = Double.parseDouble(scanner.nextLine());

                if (esPositivo(numero))
                    return numero;

                System.out.println("Error: el número debe ser positivo");

            } catch (NumberFormatException e) {
                System.out.println("Error: debe ingresar un número decimal válido");
            }
        }
    }

    public static String leerTexto(Scanner scanner, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String texto = scanner.nextLine();

            if (!esVacio(texto)) {
                return texto.trim();
            }

            System.out.println("Error: el texto no puede estar vacío");
        }
    }

    public static boolean leerSiNo(Scanner scanner, String mensaje) {
        while (true) {
            String respuesta = leerTexto(scanner,mensaje + " (s/n): ").toLowerCase();

            if (respuesta.equals("s") || respuesta.equals("si")) return true;

            if (respuesta.equals("n") || respuesta.equals("no")) return false;

            System.out.println("Error: Responda 's' o 'n'");
        }
    }

}

package com.ecomarket.ecomarket.util;

public class utils {

    public static boolean esRutValido(String numeroRut, char dv) {
        if (numeroRut == null) {
            return false;
        }
        // Eliminar puntos y espacios
        numeroRut = numeroRut.replace(".", "").trim();
        String dvStr = ("" + dv).trim().toUpperCase();

        if (numeroRut.isEmpty() || dvStr.isEmpty()) {
            return false;
        }

        try {
            int rutNumerico = Integer.parseInt(numeroRut);
            char dvEsperado = calcularDv(rutNumerico);
            return dvStr.equalsIgnoreCase(String.valueOf(dvEsperado));
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static char calcularDv(int rut) {
        int m = 0, suma = 1;
        while (rut != 0) {
            suma = (suma + rut % 10 * (9 - m++ % 6)) % 11;
            rut /= 10;
        }
        return (suma == 0) ? 'K' : (char) (suma + 47); // 1 → '1', 10 → '0'
    }
}

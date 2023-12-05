package com.hotel.challenge.utils;

import java.util.Calendar;
import java.util.Date;

public class FechaUtil {
    
    public static boolean esMayorDeEdad(Date fecha, int edadMinima) {
        Calendar fechaActual = Calendar.getInstance();
        Calendar cFecha = Calendar.getInstance();

        cFecha.setTime(fecha);

        int edad = fechaActual.get(Calendar.YEAR) - cFecha.get(Calendar.YEAR);

        return (edad >= edadMinima);
    }

}

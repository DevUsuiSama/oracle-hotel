package com.hotel.challenge.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;

import javax.swing.JOptionPane;

public class ValidarUtil {

    public static boolean telefono(Object model, String nombreAtributo, int longitudMaxima) {
        Method method = null;
        nombreAtributo = nombreAtributo.replaceFirst("^.", String.valueOf(nombreAtributo.charAt(0)).toUpperCase());
        try {
            method = model.getClass().getMethod("get" + nombreAtributo);
        } catch (NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
            return false;
        }
        try {
            Object longitud = method.invoke(model);
            if (longitud == null)
                JOptionPane.showMessageDialog(
                        null,
                        "Debe agregar un valor numérico al teléfono",
                        "Annotation @Telefono",
                        JOptionPane.WARNING_MESSAGE);
            else {
                String dato = Long.toString((Long) longitud);

                if (dato.length() < longitudMaxima || dato.length() > longitudMaxima)
                    JOptionPane.showMessageDialog(
                            null,
                            "10 dígitos en total tiene que tener el nro de teléfono ingresado",
                            "Annotation @Telefono",
                            JOptionPane.ERROR_MESSAGE);
                else
                    return true;
            }
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public static boolean edad(Object model, String nombreAtributo, int edadMinima) {
        Method method = null;
        nombreAtributo = nombreAtributo.replaceFirst("^.", String.valueOf(nombreAtributo.charAt(0)).toUpperCase());
        try {
            method = model.getClass().getMethod("get" + nombreAtributo);
        } catch (NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
            return false;
        }
        try {
            Object fecha = method.invoke(model);
            if (fecha == null)
                JOptionPane.showMessageDialog(
                        null,
                        "Debe seleccionar una fecha",
                        "Annotation @Edad",
                        JOptionPane.WARNING_MESSAGE);
            else {
                if (FechaUtil.esMayorDeEdad((Date) fecha, edadMinima))
                    return true;
                else {
                    JOptionPane.showMessageDialog(
                            null,
                            "El usuario es menor a la edad configurada",
                            "Annotation @Edad",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

}

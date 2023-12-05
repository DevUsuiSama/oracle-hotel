package com.hotel.challenge.models;

import java.util.Date;

import com.hotel.challenge.annotations.Edad;
import com.hotel.challenge.annotations.Telefono;

public class HuespedModel extends Object {

    private int id;
    private String nombre;
    private String apellido;
    @Edad(minima = 18)
    private Date fechaDeNacimiento;
    private int nacionalidad;
    @Telefono(longitud = 10)
    private long telefono;
    private int numeroDeReserva;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Date getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public void setFechaDeNacimiento(Date fechaDeNacimiento) {
        this.fechaDeNacimiento = fechaDeNacimiento;
    }

    public int getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(int nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public long getTelefono() {
        return telefono;
    }

    public void setTelefono(long telefono) {
        this.telefono = telefono;
    }

    public int getNumeroDeReserva() {
        return numeroDeReserva;
    }

    public void setNumeroDeReserva(int numeroDeReserva) {
        this.numeroDeReserva = numeroDeReserva;
    }

    @Override
    public String toString() {
        return String.format(
                "{id: %d, nombre: %s, apellido: %s, fecha_de_nacimiento: %s, nacionalidad: %d, telefono: %d, numero_de_reserva: %d}",
                id,
                nombre,
                apellido,
                fechaDeNacimiento.toString(),
                nacionalidad,
                telefono,
                numeroDeReserva);
    }

}

package com.hotel.challenge.models;

import java.util.Date;

public class ReservaModel extends Object {

    private int id;
    private Date fechaDeEntrada;
    private Date fechaDeSalida;
    private double valorDeReserva;
    private int formaDePago;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getFechaDeEntrada() {
        return fechaDeEntrada;
    }

    public void setFechaDeEntrada(Date fechaDeEntrada) {
        this.fechaDeEntrada = fechaDeEntrada;
    }

    public Date getFechaDeSalida() {
        return fechaDeSalida;
    }

    public void setFechaDeSalida(Date fechaDeSalida) {
        this.fechaDeSalida = fechaDeSalida;
    }

    public double getValorDeReserva() {
        return valorDeReserva;
    }

    public void setValorDeReserva(double valorDeReserva) {
        this.valorDeReserva = valorDeReserva;
    }

    public int getFormaDePago() {
        return formaDePago;
    }

    public void setFormaDePago(int formaDePago) {
        this.formaDePago = formaDePago;
    }

    @Override
    public String toString() {
        return String.format(
                "{id: %d, fecha_de_entrada: %s, fecha_de_salida: %s, valor_de_reservas: %.2f, forma_de_pago: %d}",
                id,
                fechaDeEntrada.toString(),
                fechaDeSalida.toString(),
                valorDeReserva,
                formaDePago);
    }

}

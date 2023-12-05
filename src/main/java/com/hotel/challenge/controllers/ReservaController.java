package com.hotel.challenge.controllers;

import java.awt.Component;
import java.sql.SQLException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;

import com.hotel.challenge.dao.ReservaDAO;
import com.hotel.challenge.factories.ConnectionFactory;
import com.hotel.challenge.models.ReservaModel;

public class ReservaController {

    private Component view;

    public ReservaController(Component view) {
        this.view = view;
    }

    public double calcularValorDeReserva(Date fechaDeEntrada, Date fechaDeSalida) {
        if (fechaDeEntrada.getTime() >= fechaDeSalida.getTime())
            JOptionPane.showMessageDialog(
                    view,
                    "La fecha de entrada no puede ser mayor ni igual a la fecha de salida",
                    "Reservas Controller",
                    JOptionPane.WARNING_MESSAGE);
        else
            return TimeUnit.DAYS.convert(
                    fechaDeSalida.getTime() - fechaDeEntrada.getTime(),
                    TimeUnit.MILLISECONDS) * 1500.34;
        return 0;
    }

    public void guardar(ReservaModel reservaModel) throws SQLException {
        ReservaDAO reservasDAO = new ReservaDAO(new ConnectionFactory().recoverConnection());
        reservasDAO.guardar(reservaModel);
    }

}

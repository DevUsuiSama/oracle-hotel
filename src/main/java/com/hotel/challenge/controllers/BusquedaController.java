package com.hotel.challenge.controllers;

import java.sql.SQLException;
import java.util.HashMap;

import com.hotel.challenge.dao.BusquedaDAO;
import com.hotel.challenge.dao.ReservaDAO;
import com.hotel.challenge.factories.ConnectionFactory;
import com.hotel.challenge.models.HuespedModel;
import com.hotel.challenge.models.ReservaModel;

public class BusquedaController {

    public HashMap<HuespedModel, ReservaModel> listar() throws SQLException {
        BusquedaDAO busquedaDAO = new BusquedaDAO(new ConnectionFactory().recoverConnection());
        return busquedaDAO.listar();
    }

    public HashMap<HuespedModel, ReservaModel> listarPorNombre(String nombre) throws SQLException {
        BusquedaDAO busquedaDAO = new BusquedaDAO(new ConnectionFactory().recoverConnection());
        return busquedaDAO.listarPorNombre(nombre);
    }

    public int modificar(ReservaModel reservaModel) throws SQLException {
        ReservaDAO reservaDAO = new ReservaDAO(new ConnectionFactory().recoverConnection());
        return reservaDAO.modificar(reservaModel);
    }

    public int eliminar(int id) throws SQLException {
        ReservaDAO reservaDAO = new ReservaDAO(new ConnectionFactory().recoverConnection());
        return reservaDAO.eliminar(id);
    }

}

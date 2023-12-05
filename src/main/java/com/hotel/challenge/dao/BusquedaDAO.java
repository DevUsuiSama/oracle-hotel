package com.hotel.challenge.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import com.hotel.challenge.models.HuespedModel;
import com.hotel.challenge.models.ReservaModel;

public class BusquedaDAO {

    private Connection connection;

    public BusquedaDAO(Connection connection) {
        this.connection = connection;
    }

        public HashMap<HuespedModel, ReservaModel> listar() throws SQLException {
        HashMap<HuespedModel, ReservaModel> mapa = null;

        try {
            connection.setAutoCommit(false);

            try (Statement stm = connection.createStatement()) {
                final ResultSet resultSet = stm.executeQuery("SELECT * FROM huesped, reserva");
                try (resultSet) {
                    mapa = new HashMap<HuespedModel, ReservaModel>();
                    while (resultSet.next()) {
                        ReservaModel reserva = new ReservaModel();
                        HuespedModel huesped = new HuespedModel();
                        huesped.setId(resultSet.getInt(1));
                        huesped.setNombre(resultSet.getString(2));
                        huesped.setApellido(resultSet.getString(3));
                        huesped.setFechaDeNacimiento(resultSet.getDate(4));
                        huesped.setNacionalidad(resultSet.getInt(5));
                        huesped.setTelefono(resultSet.getLong(6));
                        huesped.setNumeroDeReserva(resultSet.getInt(7));
                        reserva.setId(resultSet.getInt(8));
                        reserva.setFechaDeEntrada(resultSet.getDate(9));
                        reserva.setFechaDeSalida(resultSet.getDate(10));
                        reserva.setValorDeReserva(resultSet.getDouble(11));
                        reserva.setFormaDePago(resultSet.getInt(12));
                        mapa.put(huesped, reserva);
                    }
                }
                connection.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
            connection.rollback();
        }
        return mapa;
    }

    public HashMap<HuespedModel, ReservaModel> listarPorNombre(String nombre) throws SQLException {
        HashMap<HuespedModel, ReservaModel> mapa = null;

        try {
            connection.setAutoCommit(false);

            PreparedStatement statement = connection
                    .prepareStatement("SELECT * FROM huesped, reserva WHERE huesped.nombre = ?");
            try (statement) {
                statement.setString(1, nombre);
                statement.execute();
                final ResultSet resultSet = statement.getResultSet();
                try (resultSet) {
                    mapa = new HashMap<HuespedModel, ReservaModel>();
                    while (resultSet.next()) {
                        ReservaModel reserva = new ReservaModel();
                        HuespedModel huesped = new HuespedModel();
                        huesped.setId(resultSet.getInt(1));
                        huesped.setNombre(resultSet.getString(2));
                        huesped.setApellido(resultSet.getString(3));
                        huesped.setFechaDeNacimiento(resultSet.getDate(4));
                        huesped.setNacionalidad(resultSet.getInt(5));
                        huesped.setTelefono(resultSet.getLong(6));
                        huesped.setNumeroDeReserva(resultSet.getInt(7));
                        reserva.setId(resultSet.getInt(8));
                        reserva.setFechaDeEntrada(resultSet.getDate(9));
                        reserva.setFechaDeSalida(resultSet.getDate(10));
                        reserva.setValorDeReserva(resultSet.getDouble(11));
                        reserva.setFormaDePago(resultSet.getInt(12));
                        mapa.put(huesped, reserva);
                    }
                }
                connection.commit();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
        }
        return mapa;
    }

}

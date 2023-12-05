package com.hotel.challenge.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.hotel.challenge.models.HuespedModel;

public class HuespedDAO {

    private Connection connection;

    public HuespedDAO(Connection connection) {
        this.connection = connection;
    }

    public List<HuespedModel> obtener() throws SQLException {
        List<HuespedModel> huespedesModel = new ArrayList<HuespedModel>();

        try {
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();

            try (statement) {
                String sql = "SELECT * FROM huesped";
                ResultSet rSet = statement.executeQuery(sql);
                while (rSet.next()) {
                    HuespedModel huespedModel = new HuespedModel();
                    huespedModel.setId(rSet.getInt("id"));
                    huespedModel.setNombre(rSet.getString("nombre"));
                    huespedModel.setApellido(rSet.getString("apellido"));
                    huespedModel.setFechaDeNacimiento(rSet.getDate("fecha_de_nacimiento"));
                    huespedModel.setNacionalidad(rSet.getInt("nacionalidad"));
                    huespedModel.setTelefono(rSet.getLong("telefono"));
                    huespedModel.setNumeroDeReserva(rSet.getInt("numero_de_reserva"));
                    huespedesModel.add(huespedModel);
                }
                return huespedesModel;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
        }
        return huespedesModel;
    }

    public void guardar(HuespedModel huespedModel) throws SQLException {
        try {
            connection.setAutoCommit(false);

            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO huesped" +
                            "(nombre, apellido, fecha_de_nacimiento, nacionalidad, telefono, numero_de_reserva) " +
                            "VALUES (?, ?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            try (statement) {
                registro(huespedModel, statement);
                connection.commit();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
        }
    }

    public int modificar(HuespedModel huespedModel) {
        try {
            final PreparedStatement statement = connection.prepareStatement(
                    "UPDATE huesped SET " +
                            "nombre = ?," +
                            "apellido = ?," +
                            "fecha_de_nacimiento = ?," +
                            "nacionalidad = ?," +
                            "telefono = ?," +
                            "numero_de_reserva = ? " +
                            "WHERE id = ?");

            try (statement) {
                statement.setString(1, huespedModel.getNombre());
                statement.setString(2, huespedModel.getApellido());
                statement.setDate(3, new Date(huespedModel.getFechaDeNacimiento().getTime()));
                statement.setInt(4, huespedModel.getNacionalidad());
                statement.setLong(5, huespedModel.getTelefono());
                statement.setInt(6, huespedModel.getNumeroDeReserva());
                statement.setInt(7, huespedModel.getId());

                statement.execute();

                int updateCount = statement.getUpdateCount();

                return updateCount;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int eliminar(int id) {
        try {
            final PreparedStatement statement = connection.prepareStatement("DELETE FROM huesped WHERE id = ?");
            try (statement) {
                statement.setInt(1, id);
                statement.execute();

                int updateCount = statement.getUpdateCount();

                return updateCount;
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    public void registro(HuespedModel huespedModel, PreparedStatement statement) throws SQLException {
        statement.setString(1, huespedModel.getNombre());
        statement.setString(2, huespedModel.getApellido());
        statement.setDate(3, new Date(huespedModel.getFechaDeNacimiento().getTime()));
        statement.setInt(4, huespedModel.getNacionalidad());
        statement.setLong(5, huespedModel.getTelefono());
        statement.setInt(6, huespedModel.getNumeroDeReserva());

        statement.execute();

        ResultSet resultSet = statement.getGeneratedKeys();
        try (resultSet) {
            while (resultSet.next()) {
                huespedModel.setId(resultSet.getInt(1));
                System.out.println(String.format("Fue insertado el huesped %s", huespedModel));
            }
        }
    }

}

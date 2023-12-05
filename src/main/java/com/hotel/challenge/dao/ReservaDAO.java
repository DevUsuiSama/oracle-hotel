package com.hotel.challenge.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.hotel.challenge.models.ReservaModel;

public class ReservaDAO {

    private Connection connection;

    public ReservaDAO(Connection connection) {
        this.connection = connection;
    }

    public void guardar(ReservaModel reservaModel) throws SQLException {
        try {
            connection.setAutoCommit(false);

            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO reserva" +
                            "(fecha_de_entrada, fecha_de_salida, valor_de_reserva, forma_de_pago)" +
                            " VALUES(?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            try (statement) {
                registro(reservaModel, statement);
                connection.commit();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
        }
    }

    public int modificar(ReservaModel reservaModel) {
        try {
            final PreparedStatement statement = connection.prepareStatement(
                    "UPDATE reserva SET " +
                            "fecha_de_entrada = ?," +
                            "fecha_de_salida = ?," +
                            "valor_de_reserva = ?," +
                            "forma_de_pago = ? " +
                            "WHERE id = ?");

            try (statement) {
                statement.setDate(1, new Date(reservaModel.getFechaDeEntrada().getTime()));
                statement.setDate(2, new Date(reservaModel.getFechaDeSalida().getTime()));
                statement.setDouble(3, reservaModel.getValorDeReserva());
                statement.setInt(4, reservaModel.getFormaDePago());
                statement.setInt(5, reservaModel.getId());

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
            final PreparedStatement statement = connection.prepareStatement("DELETE FROM reserva WHERE id = ?");
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

    public void registro(ReservaModel reservaModel, PreparedStatement statement) throws SQLException {
        statement.setDate(1, new Date(reservaModel.getFechaDeEntrada().getTime()));
        statement.setDate(2, new Date(reservaModel.getFechaDeSalida().getTime()));
        statement.setDouble(3, reservaModel.getValorDeReserva());
        statement.setInt(4, reservaModel.getFormaDePago());

        statement.execute();

        ResultSet resultSet = statement.getGeneratedKeys();

        try (resultSet) {
            while (resultSet.next()) {
                reservaModel.setId(resultSet.getInt(1));
                System.out.println(String.format("Fue insertado la reserva %s", reservaModel));
            }
        }
    }

}

package com.hotel.challenge.controllers;

import java.sql.SQLException;
import java.util.List;

import javax.swing.JOptionPane;

import com.hotel.challenge.annotations.Validar;
import com.hotel.challenge.dao.HuespedDAO;
import com.hotel.challenge.factories.ConnectionFactory;
import com.hotel.challenge.models.HuespedModel;

public class HuespedController {

    private void mensaje(String atributo) {
        JOptionPane.showMessageDialog(null,
                "Ya se encuentra registrado el " + atributo);
    }

    public boolean guardar(@Validar HuespedModel huespedModel) {
        boolean estado = true;
        try {
            HuespedDAO huespedDAO = new HuespedDAO(new ConnectionFactory().recoverConnection());
            List<HuespedModel> huespedes = huespedDAO.obtener();

            for (HuespedModel item : huespedes) {
                if (huespedModel.getNombre().compareTo(item.getNombre()) == 0) {
                    mensaje("nombre");
                    estado = !estado;
                    break;
                }
                if (huespedModel.getApellido().compareTo(item.getApellido()) == 0) {
                    mensaje("apellido");
                    estado = !estado;
                    break;
                }
                if (huespedModel.getTelefono() == item.getTelefono()) {
                    mensaje("nro de telefono");
                    estado = !estado;
                    break;
                }
                if (huespedModel.getNumeroDeReserva() == item.getNumeroDeReserva()) {
                    mensaje("nro de reserva");
                    estado = !estado;
                    break;
                }
            }

            if (estado)
                huespedDAO.guardar(huespedModel);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return estado;
    }

}

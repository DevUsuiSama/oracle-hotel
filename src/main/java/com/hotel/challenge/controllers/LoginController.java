package com.hotel.challenge.controllers;

import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.hotel.challenge.views.Login;
import com.hotel.challenge.views.MenuUsuario;

public class LoginController {

    private Login login;

    public LoginController(Login login) {
        this.login = login;
    }

    public void login(JTextField txtUsuario, JPasswordField txtPassword) {
        String bdUsuario = "admin";
        String bdPassword = "admin";

        if (txtUsuario.getText().equals(bdUsuario) &&
                new String(txtPassword.getPassword()).equals(bdPassword)) {
            MenuUsuario menuUsuario = new MenuUsuario();
            menuUsuario.setVisible(true);
            login.dispose();
        } else
            JOptionPane.showMessageDialog(login, "Usuario o Contraseña no válido");
    }

}

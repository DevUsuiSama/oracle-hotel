package com.hotel.challenge.views;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.hotel.challenge.controllers.LoginController;
import com.hotel.challenge.interfaces.MouseInterface;
import com.hotel.challenge.utils.MouseUtil;

public class Login extends JFrame implements MouseInterface {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtUsuario;
    private JPasswordField txtPassword;
    private JLabel labelExit;
    private int xMouse, yMouse;

    public Login() {
        setResizable(false);
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 788, 527);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setBounds(0, 0, 788, 527);
        panel.setBackground(Color.WHITE);
        contentPane.add(panel);
        panel.setLayout(null);

        JPanel panel_1 = new JPanel();
        panel_1.setBackground(new Color(12, 138, 199));
        panel_1.setBounds(484, 0, 304, 527);
        panel.add(panel_1);
        panel_1.setLayout(null);

        JLabel imgHotel = new JLabel("");
        imgHotel.setBounds(0, 0, 304, 538);
        panel_1.add(imgHotel);
        imgHotel.setIcon(new ImageIcon(Login.class.getResource("../img/img-hotel-login-.png")));

        final JPanel btnexit = new JPanel();
        btnexit.setBounds(251, 0, 53, 36);
        panel_1.add(btnexit);
        btnexit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                boolean respuesta = JOptionPane.showConfirmDialog(
                        null,
                        "¿Desea salir del sistema?",
                        "Hotel", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
                if (respuesta)
                    System.exit(0);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                btnexit.setBackground(Color.red);
                labelExit.setForeground(Color.white);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnexit.setBackground(new Color(12, 18, 199));
                labelExit.setForeground(Color.white);
            }
        });
        btnexit.setBackground(new Color(12, 138, 199));
        btnexit.setLayout(null);
        btnexit.setCursor(new Cursor(Cursor.HAND_CURSOR));

        labelExit = new JLabel("X");
        labelExit.setBounds(0, 0, 53, 36);
        btnexit.add(labelExit);
        labelExit.setForeground(SystemColor.text);
        labelExit.setFont(new Font("Roboto", Font.PLAIN, 18));
        labelExit.setHorizontalAlignment(SwingConstants.CENTER);

        txtUsuario = new JTextField();
        txtUsuario.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (txtUsuario.getText().equals("Ingrese su nombre de usuario")) {
                    txtUsuario.setText("");
                    txtUsuario.setForeground(Color.black);
                }
                if (String.valueOf(txtPassword.getPassword()).isEmpty()) {
                    txtPassword.setText("**********");
                    txtPassword.setForeground(Color.gray);
                }
            }
        });
        txtUsuario.setFont(new Font("Roboto", Font.PLAIN, 16));
        txtUsuario.setText("Ingrese su nombre de usuario");
        txtUsuario.setBorder(BorderFactory.createEmptyBorder());
        txtUsuario.setForeground(SystemColor.activeCaptionBorder);
        txtUsuario.setBounds(65, 256, 324, 32);
        panel.add(txtUsuario);
        txtUsuario.setColumns(10);

        JSeparator separator = new JSeparator();
        separator.setBackground(new Color(0, 120, 215));
        separator.setBounds(65, 292, 324, 2);
        panel.add(separator);

        JLabel labelTitulo = new JLabel("INICIAR SESIÓN");
        labelTitulo.setForeground(SystemColor.textHighlight);
        labelTitulo.setFont(new Font("Roboto Black", Font.PLAIN, 26));
        labelTitulo.setBounds(65, 149, 202, 26);
        panel.add(labelTitulo);

        JSeparator separator_1 = new JSeparator();
        separator_1.setBackground(SystemColor.textHighlight);
        separator_1.setBounds(65, 393, 324, 2);
        panel.add(separator_1);

        txtPassword = new JPasswordField();
        txtPassword.setText("**********");
        txtPassword.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (String.valueOf(txtPassword.getPassword()).equals("**********")) {
                    txtPassword.setText("");
                    txtPassword.setForeground(Color.black);
                }
                if (txtUsuario.getText().isEmpty()) {
                    txtUsuario.setText("Ingrese su nombre de usuario");
                    txtUsuario.setForeground(Color.gray);
                }
            }
        });
        txtPassword.setForeground(SystemColor.activeCaptionBorder);
        txtPassword.setFont(new Font("ROBOTO", Font.PLAIN, 16));
        txtPassword.setBorder(BorderFactory.createEmptyBorder());
        txtPassword.setBounds(65, 353, 324, 32);
        panel.add(txtPassword);

        JLabel labelUsuario = new JLabel("USUARIO");
        labelUsuario.setForeground(SystemColor.textInactiveText);
        labelUsuario.setFont(new Font("Roboto Black", Font.PLAIN, 20));
        labelUsuario.setBounds(65, 219, 107, 26);
        panel.add(labelUsuario);

        JLabel lblPassword = new JLabel("CONTRASEÑA");
        lblPassword.setForeground(SystemColor.textInactiveText);
        lblPassword.setFont(new Font("Roboto Black", Font.PLAIN, 20));
        lblPassword.setBounds(65, 316, 140, 26);
        panel.add(lblPassword);

        final JPanel btnLogin = new JPanel();
        btnLogin.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnLogin.setBackground(new Color(0, 156, 223));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnLogin.setBackground(SystemColor.textHighlight);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                LoginController loginController = new LoginController(Login.this);
                loginController.login(txtUsuario, txtPassword);
            }
        });
        btnLogin.setBackground(SystemColor.textHighlight);
        btnLogin.setBounds(65, 431, 122, 44);
        panel.add(btnLogin);
        btnLogin.setLayout(null);
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel lblNewLabel = new JLabel("ENTRAR");
        lblNewLabel.setBounds(0, 0, 122, 44);
        btnLogin.add(lblNewLabel);
        lblNewLabel.setForeground(SystemColor.controlHighlight);
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setFont(new Font("Roboto", Font.PLAIN, 18));

        JLabel lblNewLabel_1 = new JLabel("");
        lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_1.setIcon(new ImageIcon(Login.class.getResource("../img/lOGO-50PX.png")));
        lblNewLabel_1.setBounds(65, 65, 48, 59);
        panel.add(lblNewLabel_1);

        JPanel header = new JPanel();
        header.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                MouseUtil.dragged(Login.this, e);
            }
        });
        header.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                MouseUtil.pressed(Login.this, e);
            }
        });
        header.setBackground(SystemColor.window);
        header.setBounds(0, 0, 784, 36);
        panel.add(header);
        header.setLayout(null);
    }

    @Override
    public void setLocation(int x, int y) {
        super.setLocation(x, y);
    }

    @Override
    public int getXMouse() {
        return xMouse;
    }

    @Override
    public int getYMouse() {
        return yMouse;
    }

    @Override
    public void setXMouse(int xMouse) {
        this.xMouse = xMouse;
    }

    @Override
    public void setYMouse(int yMouse) {
        this.yMouse = yMouse;
    }
    
}

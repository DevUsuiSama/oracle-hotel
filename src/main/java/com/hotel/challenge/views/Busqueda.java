package com.hotel.challenge.views;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.hotel.challenge.controllers.BusquedaController;
import com.hotel.challenge.interfaces.MouseInterface;
import com.hotel.challenge.models.HuespedModel;
import com.hotel.challenge.models.ReservaModel;
import com.hotel.challenge.utils.MouseUtil;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTabbedPane;
import java.awt.Toolkit;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;
import javax.swing.ListSelectionModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Optional;

public class Busqueda extends JFrame implements MouseInterface {

	private JPanel contentPane;
	private JTextField txtBuscar;
	private JTable tbHuespedes;
	private JTable tbReservas;
	private DefaultTableModel modelo;
	private DefaultTableModel modeloHuesped;
	private JLabel labelAtras;
	private JLabel labelExit;
	private int xMouse, yMouse;
	private BusquedaController busquedaController;

	private boolean tieneFilaElegida() {
		return tbReservas.getSelectedRowCount() == 0 || tbReservas.getSelectedColumnCount() == 0;
	}

	public void limpiarTabla() {
		modelo.getDataVector().clear();
		modeloHuesped.getDataVector().clear();
	}

	public void cargarTablaPorNombreHuesped(String nombre) {
		try {
			var mapa = busquedaController.listarPorNombre(nombre);
			var listaDeHuespedes = new ArrayList<HuespedModel>(mapa.keySet());
			var listaDeReservas = new ArrayList<ReservaModel>(mapa.values());

			if (listaDeReservas.isEmpty()) {
				JOptionPane.showMessageDialog(
						Busqueda.this,
						"No se encontro el nombre que buscabas",
						"System",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			} else {
				listaDeReservas.forEach(reserva -> {
					modelo.addRow(new Object[] {
							reserva.getId(),
							reserva.getFechaDeEntrada(),
							reserva.getFechaDeSalida(),
							reserva.getValorDeReserva(),
							reserva.getFormaDePago() });
				});

				listaDeHuespedes.forEach(huesped -> {
					modeloHuesped.addRow(new Object[] {
							huesped.getId(),
							huesped.getNombre(),
							huesped.getApellido(),
							huesped.getFechaDeNacimiento(),
							huesped.getNacionalidad(),
							huesped.getTelefono(),
							huesped.getNumeroDeReserva() });
				});
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	public void cargarTabla() {
		try {
			var mapa = this.busquedaController.listar();
			var listaDeHuespedes = new ArrayList<HuespedModel>(mapa.keySet());
			var listaDeReservas = new ArrayList<ReservaModel>(mapa.values());

			if (listaDeReservas.isEmpty()) {
				JOptionPane.showMessageDialog(
						Busqueda.this,
						"La base de datos esta vacía",
						"System",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			} else {
				listaDeReservas.forEach(reserva -> {
					modelo.addRow(new Object[] {
							reserva.getId(),
							reserva.getFechaDeEntrada(),
							reserva.getFechaDeSalida(),
							reserva.getValorDeReserva(),
							reserva.getFormaDePago() });
				});

				listaDeHuespedes.forEach(huesped -> {
					modeloHuesped.addRow(new Object[] {
							huesped.getId(),
							huesped.getNombre(),
							huesped.getApellido(),
							huesped.getFechaDeNacimiento(),
							huesped.getNacionalidad(),
							huesped.getTelefono(),
							huesped.getNumeroDeReserva() });
				});
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void modificar() {
		if (tieneFilaElegida()) {
			JOptionPane.showMessageDialog(this, "Por favor, elije un item");
			return;
		}

		Optional.ofNullable(modelo.getValueAt(tbReservas.getSelectedRow(), tbReservas.getSelectedColumn()))
				.ifPresentOrElse(fila -> {
					ReservaModel reservaModel = new ReservaModel();

					reservaModel.setId((Integer) modelo.getValueAt(tbReservas.getSelectedRow(), 0));

					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

					String fechaEntrada = modelo.getValueAt(tbReservas.getSelectedRow(), 1).toString();
					String fechaSalida = modelo.getValueAt(tbReservas.getSelectedRow(), 2).toString();

					try {
						reservaModel.setFechaDeEntrada(format.parse(fechaEntrada));
						reservaModel.setFechaDeSalida(format.parse(fechaSalida));
					} catch (ParseException e) {
						e.printStackTrace();
						return;
					}
					reservaModel.setValorDeReserva(
							Double.valueOf(modelo.getValueAt(tbReservas.getSelectedRow(), 3).toString()));
					reservaModel.setFormaDePago(
							Integer.valueOf(modelo.getValueAt(tbReservas.getSelectedRow(), 4).toString()));

					int filasModificadas = 0;
					try {
						filasModificadas = this.busquedaController.modificar(reservaModel);
					} catch (SQLException e) {
						e.printStackTrace();
					}

					JOptionPane.showMessageDialog(this,
							String.format("%d item modificado con éxito!", filasModificadas));
				}, () -> JOptionPane.showMessageDialog(this, "Por favor elija un item"));
	}

	private void eliminar() {
		if (tieneFilaElegida()) {
			JOptionPane.showMessageDialog(this, "Por favor, elije un item");
			return;
		}

		Optional.ofNullable(modelo.getValueAt(tbReservas.getSelectedRow(), tbReservas.getSelectedColumn()))
				.ifPresentOrElse(fila -> {
					int filasModificadas = 0;
					try {
						filasModificadas = this.busquedaController
								.eliminar((Integer) modelo.getValueAt(tbReservas.getSelectedRow(), 0));
					} catch (SQLException e) {
						e.printStackTrace();
					}

					JOptionPane.showMessageDialog(this,
							String.format("%d item modificado con éxito!", filasModificadas));
				}, () -> JOptionPane.showMessageDialog(this, "Por favor elija un item"));
	}

	public static void main(String[] args) {
		new Busqueda().setVisible(true);
	}

	public Busqueda() {
		this.busquedaController = new BusquedaController();

		setIconImage(Toolkit.getDefaultToolkit().getImage(Busqueda.class.getResource("../img/lupa2.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 910, 571);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		setUndecorated(true);

		txtBuscar = new JTextField();
		txtBuscar.setBounds(536, 127, 193, 31);
		txtBuscar.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		contentPane.add(txtBuscar);
		txtBuscar.setColumns(10);

		JLabel lblNewLabel_4 = new JLabel("SISTEMA DE BÚSQUEDA");
		lblNewLabel_4.setForeground(new Color(12, 138, 199));
		lblNewLabel_4.setFont(new Font("Roboto Black", Font.BOLD, 24));
		lblNewLabel_4.setBounds(331, 62, 280, 42);
		contentPane.add(lblNewLabel_4);

		JTabbedPane panel = new JTabbedPane(JTabbedPane.TOP);
		panel.setBackground(new Color(12, 138, 199));
		panel.setFont(new Font("Roboto", Font.PLAIN, 16));
		panel.setBounds(20, 169, 865, 328);
		contentPane.add(panel);

		tbReservas = new JTable();
		tbReservas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbReservas.setFont(new Font("Roboto", Font.PLAIN, 16));
		modelo = (DefaultTableModel) tbReservas.getModel();
		modelo.addColumn("Numero de Reserva");
		modelo.addColumn("Fecha Check In");
		modelo.addColumn("Fecha Check Out");
		modelo.addColumn("Valor");
		modelo.addColumn("Forma de Pago");
		JScrollPane scroll_table = new JScrollPane(tbReservas);
		panel.addTab("Reservas", new ImageIcon(Busqueda.class.getResource("../img/reservado.png")), scroll_table,
				null);
		scroll_table.setVisible(true);

		tbHuespedes = new JTable();
		tbHuespedes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbHuespedes.setFont(new Font("Roboto", Font.PLAIN, 16));
		modeloHuesped = (DefaultTableModel) tbHuespedes.getModel();
		modeloHuesped.addColumn("Número de Huesped");
		modeloHuesped.addColumn("Nombre");
		modeloHuesped.addColumn("Apellido");
		modeloHuesped.addColumn("Fecha de Nacimiento");
		modeloHuesped.addColumn("Nacionalidad");
		modeloHuesped.addColumn("Telefono");
		modeloHuesped.addColumn("Número de Reserva");
		JScrollPane scroll_tableHuespedes = new JScrollPane(tbHuespedes);
		panel.addTab("Huéspedes", new ImageIcon(Busqueda.class.getResource("../img/pessoas.png")),
				scroll_tableHuespedes, null);
		scroll_tableHuespedes.setVisible(true);

		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(Busqueda.class.getResource("../img/Ha-100px.png")));
		lblNewLabel_2.setBounds(56, 51, 104, 107);
		contentPane.add(lblNewLabel_2);

		JPanel header = new JPanel();
		header.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				MouseUtil.dragged(Busqueda.this, e);
			}
		});
		header.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				MouseUtil.pressed(Busqueda.this, e);
			}
		});
		header.setLayout(null);
		header.setBackground(Color.WHITE);
		header.setBounds(0, 0, 910, 36);
		contentPane.add(header);

		final JPanel btnAtras = new JPanel();
		btnAtras.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MenuUsuario usuario = new MenuUsuario();
				usuario.setVisible(true);
				dispose();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				btnAtras.setBackground(new Color(12, 138, 199));
				labelAtras.setForeground(Color.white);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnAtras.setBackground(Color.white);
				labelAtras.setForeground(Color.black);
			}
		});
		btnAtras.setLayout(null);
		btnAtras.setBackground(Color.WHITE);
		btnAtras.setBounds(0, 0, 53, 36);
		header.add(btnAtras);

		labelAtras = new JLabel("<");
		labelAtras.setHorizontalAlignment(SwingConstants.CENTER);
		labelAtras.setFont(new Font("Roboto", Font.PLAIN, 23));
		labelAtras.setBounds(0, 0, 53, 36);
		btnAtras.add(labelAtras);

		final JPanel btnexit = new JPanel();
		btnexit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MenuUsuario usuario = new MenuUsuario();
				usuario.setVisible(true);
				dispose();
			}

			@Override
			public void mouseEntered(MouseEvent e) { // Al usuario pasar el mouse por el botón este cambiará de color
				btnexit.setBackground(Color.red);
				labelExit.setForeground(Color.white);
			}

			@Override
			public void mouseExited(MouseEvent e) { // Al usuario quitar el mouse por el botón este volverá al estado
													// original
				btnexit.setBackground(Color.white);
				labelExit.setForeground(Color.black);
			}
		});
		btnexit.setLayout(null);
		btnexit.setBackground(Color.WHITE);
		btnexit.setBounds(857, 0, 53, 36);
		header.add(btnexit);

		labelExit = new JLabel("X");
		labelExit.setHorizontalAlignment(SwingConstants.CENTER);
		labelExit.setForeground(Color.BLACK);
		labelExit.setFont(new Font("Roboto", Font.PLAIN, 18));
		labelExit.setBounds(0, 0, 53, 36);
		btnexit.add(labelExit);

		JSeparator separator_1_2 = new JSeparator();
		separator_1_2.setForeground(new Color(12, 138, 199));
		separator_1_2.setBackground(new Color(12, 138, 199));
		separator_1_2.setBounds(539, 159, 193, 2);
		contentPane.add(separator_1_2);

		JPanel btnbuscar = new JPanel();
		btnbuscar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				limpiarTabla();
				cargarTablaPorNombreHuesped(txtBuscar.getText());
			}
		});
		btnbuscar.setLayout(null);
		btnbuscar.setBackground(new Color(12, 138, 199));
		btnbuscar.setBounds(748, 125, 122, 35);
		btnbuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnbuscar);

		JLabel lblBuscar = new JLabel("BUSCAR");
		lblBuscar.setBounds(0, 0, 122, 35);
		btnbuscar.add(lblBuscar);
		lblBuscar.setHorizontalAlignment(SwingConstants.CENTER);
		lblBuscar.setForeground(Color.WHITE);
		lblBuscar.setFont(new Font("Roboto", Font.PLAIN, 18));

		JPanel btnEditar = new JPanel();
		btnEditar.setLayout(null);
		btnEditar.setBackground(new Color(12, 138, 199));
		btnEditar.setBounds(635, 508, 122, 35);
		btnEditar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnEditar);
		btnEditar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				modificar();
				limpiarTabla();
				cargarTabla();
			}
		});

		JLabel lblEditar = new JLabel("EDITAR");
		lblEditar.setHorizontalAlignment(SwingConstants.CENTER);
		lblEditar.setForeground(Color.WHITE);
		lblEditar.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblEditar.setBounds(0, 0, 122, 35);
		btnEditar.add(lblEditar);

		JPanel btnEliminar = new JPanel();
		btnEliminar.setLayout(null);
		btnEliminar.setBackground(new Color(12, 138, 199));
		btnEliminar.setBounds(767, 508, 122, 35);
		btnEliminar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnEliminar);
		btnEliminar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				eliminar();
				limpiarTabla();
				cargarTabla();
			}
		});

		JLabel lblEliminar = new JLabel("ELIMINAR");
		lblEliminar.setHorizontalAlignment(SwingConstants.CENTER);
		lblEliminar.setForeground(Color.WHITE);
		lblEliminar.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblEliminar.setBounds(0, 0, 122, 35);
		btnEliminar.add(lblEliminar);
		setResizable(false);

		cargarTabla();
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

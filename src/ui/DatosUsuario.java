package ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.text.ParseException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JFormattedTextField;
import javax.swing.JButton;

public class DatosUsuario extends JFrame {

	private JPanel contentPane;
	private JTextField txtIDUsuario;
	private JPasswordField pwdPassword;
	private JTextField txtNombre;
	private JTextField txtApellido;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DatosUsuario frame = new DatosUsuario();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public DatosUsuario() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNombreUsuario = new JLabel("Nombre usuario");
		lblNombreUsuario.setBounds(10, 11, 121, 14);
		contentPane.add(lblNombreUsuario);
		
		txtIDUsuario = new JTextField();
		txtIDUsuario.setBounds(141, 8, 86, 20);
		contentPane.add(txtIDUsuario);
		txtIDUsuario.setColumns(10);
		
		JLabel lblContrasea = new JLabel("Contrase\u00F1a");
		lblContrasea.setBounds(10, 42, 84, 14);
		contentPane.add(lblContrasea);
		
		pwdPassword = new JPasswordField();
		pwdPassword.setBounds(141, 39, 84, 20);
		contentPane.add(pwdPassword);
		
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(10, 77, 46, 14);
		contentPane.add(lblNombre);
		
		txtNombre = new JTextField();
		txtNombre.setBounds(141, 74, 198, 20);
		contentPane.add(txtNombre);
		txtNombre.setColumns(10);
		
		JLabel lblApellido = new JLabel("Apellido");
		lblApellido.setBounds(10, 113, 46, 14);
		contentPane.add(lblApellido);
		
		txtApellido = new JTextField();
		txtApellido.setBounds(141, 110, 198, 20);
		contentPane.add(txtApellido);
		txtApellido.setColumns(10);
		
		JLabel lblFechaNacimiento = new JLabel("Fecha nacimiento");
		lblFechaNacimiento.setBounds(10, 149, 121, 14);
		contentPane.add(lblFechaNacimiento);
		
		JFormattedTextField mskFNac = new JFormattedTextField();
		mskFNac.setBounds(141, 146, 86, 20);
		contentPane.add(mskFNac);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setBounds(10, 189, 46, 14);
		contentPane.add(lblEmail);
		
		textField = new JTextField();
		textField.setBounds(141, 186, 198, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.setBounds(243, 237, 89, 23);
		contentPane.add(btnAceptar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(342, 237, 89, 23);
		contentPane.add(btnCancelar);
		
		
		
		try {
			MaskFormatter dateMask = new MaskFormatter("##/##/####");
			dateMask.install(mskFNac);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

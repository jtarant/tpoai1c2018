package vista;

import java.awt.EventQueue;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import controlador.AdminUsuarios;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JFormattedTextField;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DatosUsuario extends JDialog {

	private JPanel contentPane;
	private JTextField txtIDUsuario;
	private JPasswordField pwdPassword;
	private JTextField txtNombre;
	private JTextField txtApellido;
	private JFormattedTextField mskFNac;
	private JTextField txtEmail;
	private Boolean modoEdicion;
	private Boolean cancelado;

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
		setModal(true);
		setResizable(false);
		setTitle("Crear usuario");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 467, 312);
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
		
		try {
			mskFNac = new JFormattedTextField(new MaskFormatter("##/##/####"));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		mskFNac.setBounds(141, 146, 86, 20);
		contentPane.add(mskFNac);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setBounds(10, 189, 46, 14);
		contentPane.add(lblEmail);
		
		txtEmail = new JTextField();
		txtEmail.setBounds(141, 186, 198, 20);
		contentPane.add(txtEmail);
		txtEmail.setColumns(10);
		
		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				String msgErrorValidaciones = getValidacionesFallidas();
				if (msgErrorValidaciones.length() > 0)
					JOptionPane.showMessageDialog(null, msgErrorValidaciones);
				else
				{
					try
					{
						if (!modoEdicion)
						{
							AdminUsuarios.getInstancia().crear(
								txtIDUsuario.getText(), 
								pwdPassword.getPassword().toString(), 
								txtNombre.getText(), 
								txtApellido.getText(),
								new SimpleDateFormat("dd/MM/yyyy").parse(mskFNac.getText()),
								txtEmail.getText()
								);
						}
						else
						{
							AdminUsuarios.getInstancia().modificar(
									txtIDUsuario.getText(), 
									pwdPassword.getPassword().toString(), 
									txtNombre.getText(), 
									txtApellido.getText(),
									new SimpleDateFormat("dd/MM/yyyy").parse(mskFNac.getText()),
									txtEmail.getText()
									);
						}						
						setVisible(false);
					}
					catch (Exception e)
					{
						JOptionPane.showMessageDialog(null, "Error al guardar los cambios:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		
		btnAceptar.setBounds(243, 237, 89, 23);
		contentPane.add(btnAceptar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				cancelado = true;
				setVisible(false);
			}
		});
		btnCancelar.setBounds(342, 237, 89, 23);
		contentPane.add(btnCancelar);
		this.LimpiarCampos();
		cancelado = false;
		modoEdicion = false;
	}
	
	public void setIdUsuario(String usrId)
	{
		txtIDUsuario.setText(usrId);
		txtIDUsuario.setEnabled(false);
		this.setTitle("Modificar Usuario");
		modoEdicion = true;
	}
	public void setPassword(String pwd)
	{
		pwdPassword.setText(pwd);
	}
	public void setNombre(String n)
	{
		txtNombre.setText(n);
	}
	public void setApellido(String a)
	{
		txtApellido.setText(a);
	}
	public void setFNac(Date fn)
	{
		mskFNac.setText(new SimpleDateFormat("dd/MM/yyyy").format(fn));
	}
	public void setEmail(String e)
	{
		txtEmail.setText(e);
	}
	public Boolean getCancelado()
	{
		return cancelado;
	}

	private String getValidacionesFallidas()
	{
		StringBuilder msgError = new StringBuilder("");
		
		if (txtIDUsuario.getText().trim().length() == 0) msgError.append("* Debe ingresar un nombre (ID) de usuario.\n");
		if (pwdPassword.getPassword().length == 0) msgError.append("* Debe ingesar una contraseña\n");
		if (txtNombre.getText().trim().length() == 0) msgError.append("* Debe ingresar el nombre del usuario.\n");
		if (txtApellido.getText().trim().length() == 0) msgError.append("* Debe ingresar el apellido del usuario.\n");
		if (mskFNac.getText().trim().length() == 0) msgError.append("* Debe ingresar la fecha de nacimiento.\n");
		if (txtEmail.getText().trim().length() == 0) msgError.append("* Debe ingresar la direccion de email.\n");
		return msgError.toString();
	}
	
	private void LimpiarCampos()
	{
		txtIDUsuario.setText("");
		pwdPassword.setText("");
		txtNombre.setText("");
		txtApellido.setText("");
		mskFNac.setText(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
		txtEmail.setText("");
	}
}

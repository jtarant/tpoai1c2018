package vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controlador.AdminUsuarios;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.SwingConstants;

public class Login extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtIdUsuario;
	private JPasswordField pwdPassword;
	private JButton btnRegistrarme;

	public void habilitarRegistracion(Boolean r)
	{
		btnRegistrarme.setVisible(r);
	}
	
	/**
	 * Create the dialog.
	 */
	public Login() {
		setTitle("Login");
		setModal(true);
		setBounds(100, 100, 436, 169);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblUsuario = new JLabel("Usuario");
		lblUsuario.setBounds(10, 11, 117, 14);
		contentPanel.add(lblUsuario);
		
		txtIdUsuario = new JTextField();
		txtIdUsuario.setBounds(132, 8, 86, 20);
		contentPanel.add(txtIdUsuario);
		txtIdUsuario.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(10, 45, 117, 14);
		contentPanel.add(lblPassword);
		
		pwdPassword = new JPasswordField();
		pwdPassword.setBounds(132, 39, 117, 20);
		contentPanel.add(pwdPassword);
		
		JLabel lblMensajeError = new JLabel("");
		lblMensajeError.setForeground(Color.RED);
		lblMensajeError.setBounds(132, 70, 278, 14);
		contentPanel.add(lblMensajeError);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Aceptar");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) 
					{
						try 
						{
							if (!AdminUsuarios.getInstancia().autenticar(txtIdUsuario.getText().trim().toLowerCase(), String.valueOf(pwdPassword.getPassword())))
							{
								lblMensajeError.setText("Usuario o contraseña no validos.");
							}
							else
							{
								setVisible(false);
							}
						}
						catch (Exception e) 
						{
							e.printStackTrace();
							JOptionPane.showMessageDialog(null, "Error al autenticar:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
						}
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
				
				btnRegistrarme = new JButton("Registrarme");
				buttonPane.add(btnRegistrarme);
				btnRegistrarme.setVisible(false);
				btnRegistrarme.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) 
					{
						try 
						{
							DatosUsuario formDatosUsuario = new DatosUsuario();
							formDatosUsuario.setLocationRelativeTo(null);
							formDatosUsuario.setVisible(true);
							formDatosUsuario.dispose();
						} 
						catch (Exception e) 
						{
							e.printStackTrace();
							JOptionPane.showMessageDialog(null, "Error al guardar los datos:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
						}				
					}
				});
			}
			{
				JButton cancelButton = new JButton("Cancelar");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) 
					{
						setVisible(false);
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}

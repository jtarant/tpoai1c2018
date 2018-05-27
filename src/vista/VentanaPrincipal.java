package vista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controlador.AdminUsuarios;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import java.awt.Font;

public class VentanaPrincipal extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaPrincipal frame = new VentanaPrincipal();
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
	public VentanaPrincipal() {
		setTitle("Sistema de Gestion de Listas de Regalos");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 680, 499);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnUsuarios = new JMenu("Administracion");
		menuBar.add(mnUsuarios);
		
		JMenuItem mntmGestionDeUsuarios = new JMenuItem("Gestion de Usuarios");
		mntmGestionDeUsuarios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				ABMUsuarios abmUsuarios = new ABMUsuarios();
				abmUsuarios.setVisible(true);
			}
		});
		mnUsuarios.add(mntmGestionDeUsuarios);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNuevaLista = new JButton("Nueva Lista");
		btnNuevaLista.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnNuevaLista.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				DatosListaRegalos formListaRegalos = new DatosListaRegalos();
				formListaRegalos.setVisible(true);
			}
		});
		btnNuevaLista.setBounds(10, 11, 105, 23);
		contentPane.add(btnNuevaLista);
		
		// TODO: Deshardcodear haciendo ventana de Login
		try {
			AdminUsuarios.getInstancia().autenticar("jtarant", "1234");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}

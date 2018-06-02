package vista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import controlador.AdminListaRegalos;
import controlador.AdminUsuarios;
import controlador.ListaRegalosView;
import controlador.ListaResumenView;
import controlador.ParticipanteView;
import controlador.UsuarioView;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.JLabel;

public class VentanaPrincipal extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JButton btnNuevaLista;
	private JLabel lblNombreUsuario;
	private JButton btnModificar;
	private JButton btnSalir;
	private JButton btnEliminar;

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
		
		JMenuItem mntmLogincambiarDeUsuario = new JMenuItem("Login/Cambiar de usuario...");
		mntmLogincambiarDeUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				Login();
			}
		});
		mnUsuarios.add(mntmLogincambiarDeUsuario);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		btnNuevaLista = new JButton("Nueva Lista");
		btnNuevaLista.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnNuevaLista.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				DatosListaRegalos formListaRegalos = new DatosListaRegalos();
				formListaRegalos.setVisible(true);
				if (!formListaRegalos.getCancelado())
				{
					LlenarGrilla();
				}
				formListaRegalos.dispose();
			}
		});
		btnNuevaLista.setBounds(10, 11, 105, 23);
		contentPane.add(btnNuevaLista);
		
		table = new JTable();
		table.setBounds(10, 45, 644, 191);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setDefaultEditor(Object.class, null);
		table.setAutoscrolls(true);
		contentPane.add(table);
		
		btnSalir = new JButton("Salir");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				try
				{
					int opcion = JOptionPane.showConfirmDialog(null, "Seguro que queres darte de baja de la lista de regalos?", "Salir de la lista de regalos", JOptionPane.YES_NO_OPTION);
					if (opcion == JOptionPane.YES_OPTION)
					{
						int codigo = Integer.parseInt(table.getValueAt(table.getSelectedRow(), 0).toString());
						AdminListaRegalos.getInstancia().dejarDeParticipar(codigo);
						LlenarGrilla();
					}
				}
				catch (Exception e)
				{
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Error al salir de la lista:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}				
			}
		});
		btnSalir.setEnabled(false);
		btnSalir.setBounds(367, 247, 89, 23);
		contentPane.add(btnSalir);
		
		btnModificar = new JButton("Modificar");
		btnModificar.setEnabled(false);
		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				try
				{
					int codigo = Integer.parseInt(table.getValueAt(table.getSelectedRow(), 0).toString());
					ListaRegalosView lista = AdminListaRegalos.getInstancia().obtener(codigo);
							
					DatosListaRegalos formListaRegalos = new DatosListaRegalos();
					formListaRegalos.setCodigo(codigo);
					formListaRegalos.setNombreAgasajado(lista.getNombreAgasajado());
					formListaRegalos.setFechaAgasajo(lista.getFechaAgasajo());
					formListaRegalos.setMontoPorParticipante(lista.getMontoPorParticipante());
					formListaRegalos.setFechaInicio(lista.getFechaInicio());
					formListaRegalos.setFechaFin(lista.getFechaFin());
					List<String> participantes = new ArrayList<String>();
					for (ParticipanteView p : lista.getParticipantes())
					{
						participantes.add(p.getIdUsuario());
					}				
					formListaRegalos.setIdUsuariosParticipantes(participantes);
					formListaRegalos.setVisible(true);
					if (!formListaRegalos.getCancelado())
					{
						LlenarGrilla();	
					}
					formListaRegalos.dispose();
				}
				catch (Exception e)
				{
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Error al actualizar los datos:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnModificar.setBounds(466, 247, 89, 23);
		contentPane.add(btnModificar);
		
		btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				try
				{
					int opcion = JOptionPane.showConfirmDialog(null, "Seguro que queres eliminar la lista de regalos?", "Eliminar lista de regalos", JOptionPane.YES_NO_OPTION);
					if (opcion == JOptionPane.YES_OPTION)
					{
						int codigo = Integer.parseInt(table.getValueAt(table.getSelectedRow(), 0).toString());
						AdminListaRegalos.getInstancia().eliminar(codigo);
						LlenarGrilla();
					}
				}
				catch (Exception e)
				{
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Error al eliminar:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}				
			}
		});
		btnEliminar.setEnabled(false);
		btnEliminar.setBounds(565, 247, 89, 23);
		contentPane.add(btnEliminar);
		
		lblNombreUsuario = new JLabel("");
		lblNombreUsuario.setBounds(367, 11, 287, 18);
		contentPane.add(lblNombreUsuario);
		
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				if (table.getSelectedRow() != -1)
				{
					btnModificar.setEnabled(true);
					btnEliminar.setEnabled(true);
					btnSalir.setEnabled(true);
				}
				else
				{
					btnModificar.setEnabled(false);
					btnEliminar.setEnabled(false);
					btnSalir.setEnabled(false);
				}
			}
		});		
		
		try
		{
			if (AdminUsuarios.getInstancia().getCantidadUsuarios() == 0)
			{
				this.DesactivarGestionListas();
				JOptionPane.showMessageDialog(null, "No hay usuarios en el sistema. Cree uno y seleccione la opcion Login del menu Administacion.", "IMPORTANTE", JOptionPane.WARNING_MESSAGE);
			}
			else
			{
				this.Login();
				if (AdminUsuarios.getInstancia().getUsuarioLogueado() == null)
				{
					System.exit(0);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}		
	}
	
	private void Login()
	{
		Login formLogin = new Login();
		formLogin.setVisible(true);
		formLogin.dispose();
		
		UsuarioView usrLogueado = AdminUsuarios.getInstancia().getUsuarioLogueado();
		if (usrLogueado != null)
		{
			lblNombreUsuario.setText("Hola " + usrLogueado.getApellido() + ", " + usrLogueado.getNombre());
			btnNuevaLista.setEnabled(true);
			LlenarGrilla();
		}
		else
		{
			lblNombreUsuario.setText("");
			this.DesactivarGestionListas();
		}
	}
	
	private void DesactivarGestionListas()
	{
		btnNuevaLista.setEnabled(false);
		table.setEnabled(false);		
	}
	
	private void LlenarGrilla()
	{
		DefaultTableModel model = new DefaultTableModel();
		Object[] fila = new Object[8];
		
		table.removeAll();
		model.addColumn("Codigo");
		model.addColumn("Admin");
		model.addColumn("F. Agasajo");
		model.addColumn("Agasajado");
		model.addColumn("Monto");
		model.addColumn("F. Inicio");
		model.addColumn("F. Fin");
		model.addColumn("Estado");

		List<ListaResumenView> listas;
		try 
		{
			listas = AdminListaRegalos.getInstancia().listarMisListas(AdminUsuarios.getInstancia().getUsuarioLogueado().getIdUsuario());
			for(ListaResumenView l : listas) 
			{
				fila[0] = l.getCodigo();
				fila[1] = l.getAdmin();
				fila[2] = l.getFechaAgasajo();
				fila[3] = l.getNombreAgasajado();
				fila[4] = l.getMontoPorParticipante();
				fila[5] = l.getFechaInicio();
				fila[6] = l.getFechaFin();
				switch (l.getEstado())
				{
					case 0: fila[7] = "ABIERTA"; 
						break;
					case 1: fila[7] = "CERRADA"; 
						break;
					case 2: fila[7] = "PROXIMO_CIERRE"; 
						break;
				}
				model.addRow(fila);
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error al cargar los datos:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		table.setModel(model);
		table.validate();
	}
}

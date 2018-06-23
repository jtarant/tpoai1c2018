package vista;

import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import controlador.AdminDemonioListas;
import controlador.AdminDemonioPagos;
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
import javax.swing.SwingConstants;
import java.awt.FlowLayout;
import javax.swing.KeyStroke;
import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;

public class VentanaPrincipal extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JButton btnNuevaLista;
	private JLabel lblNombreUsuario;
	private JButton btnModificar;
	private JButton btnSalir;
	private JButton btnEliminar;
	private JButton btnPagos;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try 
				{
					AdminDemonioPagos.getInstancia().iniciar();
					AdminDemonioListas.getInstancia().iniciar();
					
					VentanaPrincipal frame = new VentanaPrincipal();
					frame.setVisible(true);					
				} 
				catch (Exception e) 
				{
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
		mntmGestionDeUsuarios.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, InputEvent.ALT_MASK));
		mntmGestionDeUsuarios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				if (AdminUsuarios.getInstancia().getUsuarioLogueado().getSysAdmin())
				{
					// Solo el sysadmin puede acceder al ABM de usuarios
					ABMUsuarios abmUsuarios = new ABMUsuarios();
					abmUsuarios.setLocationRelativeTo(null);
					abmUsuarios.setVisible(true);
				}
				else
				{
					// Los usuario no-syadmin solo pueden modificar su propio usuario
					try 
					{
						UsuarioView usr = AdminUsuarios.getInstancia().obtener(AdminUsuarios.getInstancia().getUsuarioLogueado().getIdUsuario());
						if (usr != null)
						{
							DatosUsuario formDatosUsuario = new DatosUsuario();
							formDatosUsuario.setIdUsuario(usr.getIdUsuario());
							formDatosUsuario.setPassword(usr.getPassword());
							formDatosUsuario.setApellido(usr.getApellido());
							formDatosUsuario.setNombre(usr.getNombre());
							formDatosUsuario.setFNac(usr.getFechaNac());
							formDatosUsuario.setEmail(usr.getEmail());
							formDatosUsuario.setSysAdmin(usr.getSysAdmin());
							formDatosUsuario.setLocationRelativeTo(null);
							formDatosUsuario.setVisible(true);
							formDatosUsuario.dispose();
						}
					} 
					catch (Exception e) 
					{
						e.printStackTrace();
						JOptionPane.showMessageDialog(null, "Error al actualizar los datos:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					}									
				}
			}
		});
		mnUsuarios.add(mntmGestionDeUsuarios);
		
		JMenuItem mntmLogincambiarDeUsuario = new JMenuItem("Login/Cambiar de usuario...");
		mntmLogincambiarDeUsuario.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.ALT_MASK));
		mntmLogincambiarDeUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				Login(false);
			}
		});
		mnUsuarios.add(mntmLogincambiarDeUsuario);
		
		JMenu mnAyuda = new JMenu("Ayuda");
		menuBar.add(mnAyuda);
		
		JMenuItem mntmAcercaDe = new JMenuItem("Acerca de...");
		mntmAcercaDe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				JOptionPane.showMessageDialog(null, "Grupo:\nTARANTINO, JOSE LUIS (LU 1072978)\nCALISI, LUCAS PABLO (LU 1069703)\n");
			}
		});
		mnAyuda.add(mntmAcercaDe);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panelSup = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panelSup.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		panelSup.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		contentPane.add(panelSup, BorderLayout.PAGE_START);
		JPanel panelInf = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panelInf.getLayout();
		flowLayout_1.setAlignment(FlowLayout.TRAILING);
		contentPane.add(panelInf, BorderLayout.PAGE_END);
		
		btnNuevaLista = new JButton("Nueva Lista");
		btnNuevaLista.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnNuevaLista.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				DatosListaRegalos formListaRegalos = new DatosListaRegalos();
				formListaRegalos.setLocationRelativeTo(null);
				formListaRegalos.setNombreAdmin(AdminUsuarios.getInstancia().getUsuarioLogueado().getIdUsuario());
				formListaRegalos.setVisible(true);
				if (!formListaRegalos.getCancelado())
				{
					LlenarGrilla();
				}
				formListaRegalos.dispose();
			}
		});
		panelSup.add(btnNuevaLista);
		
		table = new JTable();
		table.setFillsViewportHeight(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setDefaultEditor(Object.class, null);
		table.setAutoscrolls(true);
		JScrollPane scrollpane = new JScrollPane(table);
		scrollpane.add(table.getTableHeader());
		table.getTableHeader().setReorderingAllowed(false);
		contentPane.add(scrollpane, BorderLayout.CENTER);
		
		btnSalir = new JButton("Abandonar");
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
					JOptionPane.showMessageDialog(null, "Error al abandonar de la lista:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}				
			}
		});
		
		btnPagos = new JButton("Ver Pagos");
		btnPagos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				try
				{
					int codigo = Integer.parseInt(table.getValueAt(table.getSelectedRow(), 0).toString());
					ListaRegalosView lista = AdminListaRegalos.getInstancia().obtener(codigo);
					
					DatosPagos formPagos = new DatosPagos(lista);
					formPagos.setLocationRelativeTo(null);
					formPagos.setVisible(true);
					formPagos.dispose();
				}
				catch (Exception e)
				{
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Error al cargar los datos:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}				
			}
		});
		btnPagos.setEnabled(false);
		panelInf.add(btnPagos);
		btnSalir.setEnabled(false);
		panelInf.add(btnSalir);
		
		btnModificar = new JButton("Modificar");
		btnModificar.setEnabled(false);
		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				try
				{
					if ((AdminUsuarios.getInstancia().getUsuarioLogueado().getIdUsuario().equals(table.getValueAt(table.getSelectedRow(), 1).toString())) ||
					     AdminUsuarios.getInstancia().getUsuarioLogueado().getSysAdmin())
					{				
						int codigo = Integer.parseInt(table.getValueAt(table.getSelectedRow(), 0).toString());
						ListaRegalosView lista = AdminListaRegalos.getInstancia().obtener(codigo);
								
						DatosListaRegalos formListaRegalos = new DatosListaRegalos();
						formListaRegalos.setCodigo(codigo);
						formListaRegalos.setNombreAdmin(lista.getidUsuarioAdmin());
						formListaRegalos.setNombreAgasajado(lista.getNombreAgasajado());
						formListaRegalos.setFechaAgasajo(lista.getFechaAgasajo());
						formListaRegalos.setMontoPorParticipante(lista.getMontoPorParticipante());
						formListaRegalos.setFechaInicio(lista.getFechaInicio());
						formListaRegalos.setFechaFin(lista.getFechaFin());
						formListaRegalos.setMontoRecaudado(lista.getMontoRecaudado());
						List<String> participantes = new ArrayList<String>();
						for (ParticipanteView p : lista.getParticipantes())
						{
							participantes.add(p.getIdUsuario());
						}				
						formListaRegalos.setIdUsuariosParticipantes(participantes);
						formListaRegalos.setLocationRelativeTo(null);
						formListaRegalos.setVisible(true);
						if (!formListaRegalos.getCancelado())
						{
							LlenarGrilla();	
						}
						formListaRegalos.dispose();
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Solo el administrador de la lista o el sysadmin pueden modificarla.", "Error", JOptionPane.ERROR_MESSAGE);							
					}					
				}
				catch (Exception e)
				{
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Error al actualizar los datos:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		panelInf.add(btnModificar);
		
		btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				try
				{
					int opcion = JOptionPane.showConfirmDialog(null, "Seguro que queres eliminar la lista de regalos?", "Eliminar lista de regalos", JOptionPane.YES_NO_OPTION);
					if (opcion == JOptionPane.YES_OPTION)
					{
						if ((AdminUsuarios.getInstancia().getUsuarioLogueado().getIdUsuario().equals(table.getValueAt(table.getSelectedRow(), 1).toString())) ||
						     AdminUsuarios.getInstancia().getUsuarioLogueado().getSysAdmin())
						{
							int codigo = Integer.parseInt(table.getValueAt(table.getSelectedRow(), 0).toString());
							AdminListaRegalos.getInstancia().eliminar(codigo);
							LlenarGrilla();
						}
						else
						{
							JOptionPane.showMessageDialog(null, "Solo el administrador de la lista o el sysadmin pueden eliminarla.", "Error", JOptionPane.ERROR_MESSAGE);							
						}
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
		panelInf.add(btnEliminar);
		
		lblNombreUsuario = new JLabel("");
		lblNombreUsuario.setHorizontalAlignment(SwingConstants.CENTER);
		panelSup.add(lblNombreUsuario);
		
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				if (table.getSelectedRow() != -1)
				{
					btnModificar.setEnabled(true);
					btnEliminar.setEnabled(true);
					btnSalir.setEnabled(true);
					btnPagos.setEnabled(true);
				}
				else
				{
					btnModificar.setEnabled(false);
					btnEliminar.setEnabled(false);
					btnSalir.setEnabled(false);
					btnPagos.setEnabled(false);
				}
			}
		});		
		
		this.Login(true);
		if (AdminUsuarios.getInstancia().getUsuarioLogueado() == null)
		{
			System.exit(0);
		}
	}
	
	private void Login(Boolean habilitarRegistracion)
	{
		Login formLogin = new Login();
		formLogin.habilitarRegistracion(habilitarRegistracion);
		formLogin.setLocationRelativeTo(null);
		formLogin.setVisible(true);
		formLogin.dispose();
		
		UsuarioView usrLogueado = AdminUsuarios.getInstancia().getUsuarioLogueado();
		if (usrLogueado != null)
		{
			lblNombreUsuario.setText("Hola " + usrLogueado.getApellido() + ", " + usrLogueado.getNombre());
			btnNuevaLista.setEnabled(true);
			table.setEnabled(true);
			LlenarGrilla();
		}
		else
		{
			lblNombreUsuario.setText("");
			btnNuevaLista.setEnabled(false);
			table.setEnabled(false);		
		}
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

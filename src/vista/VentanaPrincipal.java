package vista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import controlador.AdminListaRegalos;
import controlador.AdminUsuarios;
import controlador.ListaRegalosView;
import controlador.ListaResumenView;
import controlador.ParticipanteView;
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

public class VentanaPrincipal extends JFrame {

	private JPanel contentPane;
	private JTable table;

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
		
		JButton btnSalir = new JButton("Salir");
		btnSalir.setEnabled(false);
		btnSalir.setBounds(367, 247, 89, 23);
		contentPane.add(btnSalir);
		
		JButton btnModificar = new JButton("Modificar");
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
					JOptionPane.showMessageDialog(null, "Error al cargar los datos:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnModificar.setBounds(466, 247, 89, 23);
		contentPane.add(btnModificar);
		
		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.setEnabled(false);
		btnEliminar.setBounds(565, 247, 89, 23);
		contentPane.add(btnEliminar);
		
		// TODO: Deshardcodear haciendo ventana de Login
		try {
			AdminUsuarios.getInstancia().autenticar("jtarant", "1234");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		LlenarGrilla();
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

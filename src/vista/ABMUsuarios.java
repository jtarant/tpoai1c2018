package vista;

import java.awt.EventQueue;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import controlador.AdminUsuarios;
import controlador.UsuarioIdNombreView;
import controlador.UsuarioView;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ABMUsuarios extends JFrame {

	private JPanel contentPane;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ABMUsuarios frame = new ABMUsuarios();
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
	public ABMUsuarios() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 477, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setBounds(10, 11, 346, 239);
		table.setDefaultEditor(Object.class, null);
		contentPane.add(table);
		
		JButton btnAgregar = new JButton("Agregar");
		btnAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try 
				{
					DatosUsuario formDatosUsuario = new DatosUsuario();
					formDatosUsuario.setVisible(true);
					if (!formDatosUsuario.getCancelado())
					{
						LlenarGrilla();	
					}
					formDatosUsuario.dispose();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		btnAgregar.setBounds(366, 11, 89, 23);
		contentPane.add(btnAgregar);
		
		JButton btnModificar = new JButton("Modificar");
		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try 
				{
					UsuarioView usr = AdminUsuarios.getInstancia().obtener(table.getValueAt(table.getSelectedRow(), 0).toString());
					if (usr != null)
					{
						DatosUsuario formDatosUsuario = new DatosUsuario();
						formDatosUsuario.setIdUsuario(usr.getIdUsuario());
						formDatosUsuario.setPassword(usr.getPassword());
						formDatosUsuario.setApellido(usr.getApellido());
						formDatosUsuario.setNombre(usr.getNombre());
						formDatosUsuario.setFNac(usr.getFechaNac());
						formDatosUsuario.setEmail(usr.getEmail());
						formDatosUsuario.setVisible(true);
						if (!formDatosUsuario.getCancelado())
						{
							LlenarGrilla();	
						}
						formDatosUsuario.dispose();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}				
			}
		});
		btnModificar.setEnabled(false);
		btnModificar.setBounds(366, 45, 89, 23);
		contentPane.add(btnModificar);
		
		JButton btnCerrar = new JButton("Cerrar");
		btnCerrar.setBounds(366, 227, 89, 23);
		contentPane.add(btnCerrar);
		
		this.LlenarGrilla();
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				btnModificar.setEnabled(true);
			}
		});		
	}
	
	private void LlenarGrilla()
	{
		table.removeAll();
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("Usuario");
		model.addColumn("Apellido");
		model.addColumn("Nombre");
		Object[] fila = new Object[3];

		try
		{
			List<UsuarioIdNombreView> lista = AdminUsuarios.getInstancia().listarIdNombre();

			for(UsuarioIdNombreView u : lista) {
				fila[0] = u.getIdUsuario();
				fila[1] = u.getApellido();
				fila[2] = u.getNombre();
				model.addRow(fila);
			}			
		}
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(null, "Error al cargar los datos:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		table.setModel(model);
		table.validate();
	}
}

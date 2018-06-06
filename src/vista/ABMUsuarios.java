package vista;

import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
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
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.FlowLayout;

public class ABMUsuarios extends JDialog {

	private JPanel contentPane;
	private JTable table;

	/**
	 * Create the frame.
	 */
	public ABMUsuarios() {
		setModal(true);
		setResizable(false);
		setTitle("Administracion de Usuarios");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 479, 335);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

	
		table = new JTable();
		table.setFillsViewportHeight(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setDefaultEditor(Object.class, null);
		contentPane.setLayout(new BorderLayout(0, 0));
		table.setAutoscrolls(true);
		JScrollPane scrollpane = new JScrollPane(table);
		scrollpane.add(table.getTableHeader());
		table.getTableHeader().setReorderingAllowed(false);
		contentPane.add(scrollpane, BorderLayout.CENTER);
		
		JPanel panelInf = new JPanel();
		panelInf.setBounds(100, 200, 100, 200);
		panelInf.setSize(900,900);
		contentPane.add(panelInf, BorderLayout.PAGE_END);
		
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
						formDatosUsuario.setSysAdmin(usr.getSysAdmin());
						formDatosUsuario.setLocationRelativeTo(null);
						formDatosUsuario.setVisible(true);
						if (!formDatosUsuario.getCancelado())
						{
							LlenarGrilla();	
						}
						formDatosUsuario.dispose();
					}
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Error al actualizar los datos:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}				
			}
		});
		
				JButton btnAgregar = new JButton("Agregar");
				btnAgregar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						try 
						{
							DatosUsuario formDatosUsuario = new DatosUsuario();
							formDatosUsuario.setLocationRelativeTo(null);
							formDatosUsuario.setVisible(true);
							if (!formDatosUsuario.getCancelado())
							{
								LlenarGrilla();	
							}
							formDatosUsuario.dispose();
						} 
						catch (Exception e) 
						{
							e.printStackTrace();
							JOptionPane.showMessageDialog(null, "Error al guardar los datos:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
						}
					}
				});
		btnModificar.setEnabled(false);
		
		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try 
				{
					int opcion = JOptionPane.showConfirmDialog(null, "Seguro que queres eliminar el usuario?", "Eliminar usuario", JOptionPane.YES_NO_OPTION);
					if (opcion == JOptionPane.YES_OPTION)
					{
						AdminUsuarios.getInstancia().eliminar(table.getValueAt(table.getSelectedRow(), 0).toString());
						LlenarGrilla();	
					}
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Error al actualizar los datos:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnEliminar.setEnabled(false);
		panelInf.setLayout(new FlowLayout(FlowLayout.TRAILING, 5, 5));
		panelInf.add(btnAgregar);
		panelInf.add(btnModificar);
		panelInf.add(btnEliminar);
				
		this.LlenarGrilla();
		
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				if (table.getSelectedRow() != -1)
				{
					btnModificar.setEnabled(true);
					btnEliminar.setEnabled(true);
				}
				else
				{
					btnModificar.setEnabled(false);
					btnEliminar.setEnabled(false);
				}
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
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error al cargar los datos:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		table.setModel(model);
		table.validate();
	}
}

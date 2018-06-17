package vista;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import javax.swing.text.NumberFormatter;

import controlador.AdminListaRegalos;
import controlador.AdminUsuarios;
import controlador.UsuarioIdNombreView;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JFormattedTextField;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JTable;

public class DatosListaRegalos extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtNombreAgasajado;
	private JTable tblParticipantes;
	private JTable tblNoParticipantes;
	private JFormattedTextField mskFechaAgasajo;
	private JFormattedTextField mskMonto;
	private JFormattedTextField mskFechaInicio;
	private JFormattedTextField mskFechaFin;
	private JButton btnAgregar;
	private JButton btnQuitar;
	private int codigo;
	private List<UsuarioIdNombreView> usuariosDisponibles;
	private List<String> participantes;
	private Boolean modoEdicion;
	private Boolean cancelado;
	private JLabel lblAdmin;
	private JLabel lblMontoRecaudado;

	/**
	 * Create the dialog.
	 */
	public DatosListaRegalos() {
		setModal(true);
		setResizable(false);
		setTitle("Crear lista de regalos");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 565, 479);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Nombre Agasajado");
		lblNewLabel.setBounds(10, 39, 124, 14);
		contentPanel.add(lblNewLabel);
		
		txtNombreAgasajado = new JTextField();
		txtNombreAgasajado.setBounds(187, 36, 229, 20);
		contentPanel.add(txtNombreAgasajado);
		txtNombreAgasajado.setColumns(10);
		
		JLabel lblFechaDelAgasajo = new JLabel("Fecha del Agasajo");
		lblFechaDelAgasajo.setBounds(10, 70, 138, 14);
		contentPanel.add(lblFechaDelAgasajo);
		
		try {
			mskFechaAgasajo = new JFormattedTextField(new MaskFormatter("##/##/####"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		mskFechaAgasajo.setBounds(187, 67, 81, 20);
		contentPanel.add(mskFechaAgasajo);
		
		JLabel lblMontoPorParticipante = new JLabel("Monto por participante ($)");
		lblMontoPorParticipante.setBounds(10, 101, 167, 14);
		contentPanel.add(lblMontoPorParticipante);
		
		mskMonto = new JFormattedTextField(new NumberFormatter());
		mskMonto.setBounds(187, 98, 81, 20);
		contentPanel.add(mskMonto);
		
		JLabel lblFechaInicio = new JLabel("Fecha inicio");
		lblFechaInicio.setBounds(10, 130, 138, 14);
		contentPanel.add(lblFechaInicio);
		
		try {
			mskFechaInicio = new JFormattedTextField(new MaskFormatter("##/##/####"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		mskFechaInicio.setBounds(187, 127, 81, 20);
		contentPanel.add(mskFechaInicio);
		
		JLabel lblFechaFin = new JLabel("Fecha fin");
		lblFechaFin.setBounds(10, 165, 138, 14);
		contentPanel.add(lblFechaFin);
		{
			JButton btnAceptar = new JButton("Aceptar");
			btnAceptar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					String msgErrorValidaciones = getValidacionesFallidas();
					if (msgErrorValidaciones.length() > 0)
						JOptionPane.showMessageDialog(null, msgErrorValidaciones);
					else
					{
						try
						{
							if (!modoEdicion)
							{
								AdminListaRegalos.getInstancia().crear(
										AdminUsuarios.getInstancia().getUsuarioLogueado().getIdUsuario(), 
										new SimpleDateFormat("dd/MM/yyyy").parse(mskFechaAgasajo.getText()), 
										txtNombreAgasajado.getText(), 
										Float.parseFloat(mskMonto.getText()), 
										new SimpleDateFormat("dd/MM/yyyy").parse(mskFechaInicio.getText()), 
										new SimpleDateFormat("dd/MM/yyyy").parse(mskFechaFin.getText()), 
										participantes
										);
							}
							else
							{
								AdminListaRegalos.getInstancia().modificar(
										codigo, 
										new SimpleDateFormat("dd/MM/yyyy").parse(mskFechaAgasajo.getText()), 
										txtNombreAgasajado.getText(), 
										Float.parseFloat(mskMonto.getText()), 
										new SimpleDateFormat("dd/MM/yyyy").parse(mskFechaInicio.getText()), 
										new SimpleDateFormat("dd/MM/yyyy").parse(mskFechaFin.getText()), 
										participantes
										);
							}						
							setVisible(false);
						}
						catch (Exception e)
						{
							e.printStackTrace();
							JOptionPane.showMessageDialog(null, "Error al guardar los cambios:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
						}
					}					
				}
			});
			btnAceptar.setBounds(350, 420, 101, 23);
			contentPanel.add(btnAceptar);
			btnAceptar.setActionCommand("OK");
			getRootPane().setDefaultButton(btnAceptar);
		}
		{
			JButton btnCancelar = new JButton("Cancelar");
			btnCancelar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) 
				{
					cancelado = true;
					setVisible(false);					
				}
			});
			btnCancelar.setBounds(460, 420, 89, 23);
			contentPanel.add(btnCancelar);
			btnCancelar.setActionCommand("Cancel");
		}
		
		JLabel lblParticipantes = new JLabel("Participantes");
		lblParticipantes.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblParticipantes.setBounds(10, 208, 166, 14);
		contentPanel.add(lblParticipantes);
		
		tblParticipantes = new JTable();
		tblParticipantes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblParticipantes.setDefaultEditor(Object.class, null);
		tblParticipantes.setAutoscrolls(true);
		tblParticipantes.setBounds(10, 233, 236, 176);
		contentPanel.add(tblParticipantes);
		
		tblParticipantes.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				if (tblParticipantes.getSelectedRow() != -1)
				{
					btnQuitar.setEnabled(true);
				}
				else
				{
					btnQuitar.setEnabled(false);
				}
			}
		});				
		
		JLabel lblNoParticipan = new JLabel("No participan");
		lblNoParticipan.setBounds(315, 208, 145, 14);
		contentPanel.add(lblNoParticipan);
		
		btnAgregar = new JButton("<=");
		btnAgregar.setEnabled(false);
		btnAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				String userId = tblNoParticipantes.getValueAt(tblNoParticipantes.getSelectedRow(), 1).toString();
				if (!participantes.contains(userId))
				{
					participantes.add(userId);
				}
				LlenarGrillas();
			}
		});
		btnAgregar.setBounds(256, 292, 49, 23);
		contentPanel.add(btnAgregar);
		
		btnQuitar = new JButton("=>");
		btnQuitar.setEnabled(false);
		btnQuitar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				String userId = tblParticipantes.getValueAt(tblParticipantes.getSelectedRow(), 1).toString();
				if (participantes.contains(userId))
				{
					participantes.remove(userId);
				}
				LlenarGrillas();				
			}
		});
		btnQuitar.setBounds(256, 326, 49, 23);
		contentPanel.add(btnQuitar);
		
		tblNoParticipantes = new JTable();
		tblNoParticipantes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblNoParticipantes.setDefaultEditor(Object.class, null);
		tblNoParticipantes.setAutoscrolls(true);
		tblNoParticipantes.setBounds(315, 233, 234, 176);
		contentPanel.add(tblNoParticipantes);
		
		tblNoParticipantes.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				if (tblNoParticipantes.getSelectedRow() != -1)
				{
					btnAgregar.setEnabled(true);
				}
				else
				{
					btnAgregar.setEnabled(false);
				}
			}
		});		
		
		try {
			mskFechaFin = new JFormattedTextField(new MaskFormatter("##/##/####"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		mskFechaFin.setBounds(187, 162, 81, 20);
		contentPanel.add(mskFechaFin);
		
		lblMontoRecaudado = new JLabel("");
		lblMontoRecaudado.setBounds(315, 101, 234, 14);
		contentPanel.add(lblMontoRecaudado);
		
		JLabel lblAdminDeLa = new JLabel("Admin. de la lista");
		lblAdminDeLa.setBounds(10, 11, 167, 14);
		contentPanel.add(lblAdminDeLa);
		
		lblAdmin = new JLabel("");
		lblAdmin.setBounds(187, 11, 229, 14);
		contentPanel.add(lblAdmin);
		cancelado = false;
		modoEdicion = false;
		LimpiarCampos();
	}
	
	@Override
	public void setVisible(boolean b)
	{
		if (b)
		{
			LlenarGrillas();
		}
		super.setVisible(b);
	}
	
	private void LimpiarCampos()
	{
		txtNombreAgasajado.setText("");
		mskFechaAgasajo.setText(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
		mskMonto.setValue(0);
		mskFechaInicio.setText(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
		mskFechaFin.setText(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
		try 
		{
			usuariosDisponibles = AdminUsuarios.getInstancia().listarIdNombre();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error al obtener la lista de usuarios:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		participantes = new ArrayList<String>();
		this.setMontoRecaudado(0);
	}
	
	private String getValidacionesFallidas()
	{
		StringBuilder msgError = new StringBuilder("");
		
		if (txtNombreAgasajado.getText().trim().length() == 0) msgError.append("* Debe ingresar el nombre del agasajado.\n");
		if (!ValidadorTexto.esFechaValida(mskFechaAgasajo.getText())) msgError.append("* Debe ingresar una fecha de agasajo valida.\n");
		if (!ValidadorTexto.esMonedaValida(mskMonto.getText())) msgError.append("* Debe ingresar un monto valido.\n");
		if (!ValidadorTexto.esFechaValida(mskFechaInicio.getText())) msgError.append("* Debe ingresar una fecha de inicio valida.\n");
		if (!ValidadorTexto.esFechaValida(mskFechaFin.getText())) msgError.append("* Debe ingresar una fecha de fin valida.\n");
		return msgError.toString();
	}
		
	private void LlenarGrillas()
	{
		DefaultTableModel model1, model2;
		Object[] fila = new Object[2];
		
		tblParticipantes.removeAll();
		model1 = new DefaultTableModel();
		model1.addColumn("Nombre");
		model1.addColumn("Usuario");
		tblNoParticipantes.removeAll();
		model2 = new DefaultTableModel();
		model2.addColumn("Nombre");
		model2.addColumn("Usuario");
		
		String usrAdmin;
		if (!modoEdicion)
		{
			usrAdmin = AdminUsuarios.getInstancia().getUsuarioLogueado().getIdUsuario();
		}
		else
		{
			usrAdmin = this.lblAdmin.getText();
		}
		usuariosDisponibles.removeIf(u -> u.getIdUsuario().equals(usrAdmin)); // El admin no puede ser participante
		
		for(UsuarioIdNombreView u : usuariosDisponibles) 
		{
			fila[0] = u.getApellido() + ", " + u.getNombre();
			fila[1] = u.getIdUsuario();
			
			if (participantes.contains(u.getIdUsuario()))
			{
				model1.addRow(fila);
			}
			else
			{
				model2.addRow(fila);
			}
		}
		tblParticipantes.setModel(model1);
		tblParticipantes.validate();
		tblNoParticipantes.setModel(model2);
		tblNoParticipantes.validate();
	}
	
	public void setCodigo(int codigo)
	{
		this.codigo = codigo;
		this.modoEdicion = true;
		this.setTitle("Modificar Lista de Regalos");
	}
	public void setNombreAdmin(String admin)
	{
		lblAdmin.setText(admin);
	}
	public void setNombreAgasajado(String nombre)
	{
		txtNombreAgasajado.setText(nombre);
	}
	public void setFechaAgasajo(Date fecha)
	{
		mskFechaAgasajo.setText(new SimpleDateFormat("dd/MM/yyyy").format(fecha));
	}
	public void setMontoPorParticipante(float monto)
	{
		mskMonto.setValue(monto);
	}
	public void setFechaInicio(Date fecha)
	{
		mskFechaInicio.setText(new SimpleDateFormat("dd/MM/yyyy").format(fecha));
	}
	public void setFechaFin(Date fecha)
	{
		mskFechaFin.setText(new SimpleDateFormat("dd/MM/yyyy").format(fecha));
	}
	public void setMontoRecaudado(float monto)
	{
		lblMontoRecaudado.setText("Monto recaudado ($): " + String.format("%.2f", monto));
	}
	public void setIdUsuariosParticipantes(List<String> participantes)
	{
		this.participantes = participantes;
	}
	public Boolean getCancelado() 
	{
		return cancelado;
	}
}

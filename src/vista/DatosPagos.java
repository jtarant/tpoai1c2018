package vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import controlador.AdminListaRegalos;
import controlador.AdminUsuarios;
import controlador.ListaRegalosView;
import controlador.ListaResumenView;
import controlador.ParticipanteView;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DatosPagos extends JDialog {
	private JTable table;
	private final JPanel contentPanel = new JPanel();
	private ListaRegalosView lista;

	/**
	 * Create the dialog.
	 */
	public DatosPagos(ListaRegalosView lista) {
		setModal(true);
		setBounds(100, 100, 491, 488);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));		
		
		table = new JTable();
		table.setFillsViewportHeight(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setDefaultEditor(Object.class, null);
		table.setAutoscrolls(true);
		JScrollPane scrollpane = new JScrollPane(table);
		scrollpane.add(table.getTableHeader());
		table.getTableHeader().setReorderingAllowed(false);
		contentPanel.add(scrollpane, BorderLayout.CENTER);
		
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
		this.lista = lista;
		this.setTitle("Detalle de Pagos - Agasajado: " + lista.getNombreAgasajado());
		LlenarGrilla();
	}

	private void LlenarGrilla()
	{
		DefaultTableModel model = new DefaultTableModel();
		Object[] fila = new Object[8];
		
		table.removeAll();
		model.addColumn("Usuario");
		model.addColumn("F. Pago");
		model.addColumn("Monto");
		for(ParticipanteView p : lista.getParticipantes()) 
		{
			fila[0] = p.getIdUsuario();
			fila[1] = p.getFechaPago();
			fila[2] = lista.getMontoPorParticipante();
			model.addRow(fila);
		}
		table.setModel(model);
		table.validate();
	}

}

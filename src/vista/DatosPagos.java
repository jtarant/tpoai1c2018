package vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import controlador.ListaRegalosView;
import controlador.ParticipanteView;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;

public class DatosPagos extends JDialog {
	private JTable table;
	private final JPanel contentPanel = new JPanel();
	private ListaRegalosView lista;
	private JLabel lblMontoRecaudado;

	/**
	 * Create the dialog.
	 */
	public DatosPagos(ListaRegalosView lista) {
		setResizable(false);
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
				
				lblMontoRecaudado = new JLabel("Monto recaudado");
				buttonPane.add(lblMontoRecaudado);
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
			if (p.getFechaPago() != null)
				fila[1] = new SimpleDateFormat("dd/MM/yyyy").format(p.getFechaPago());
			else
				fila[1] = "";
			fila[2] = p.getMontoPagado();
			model.addRow(fila);
		}
		lblMontoRecaudado.setText("Monto recaudado ($): " + String.format("%.2f", lista.getMontoRecaudado()));
		table.setModel(model);
		table.validate();
	}

}

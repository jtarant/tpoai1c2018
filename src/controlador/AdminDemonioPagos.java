package controlador;

import modelo.DemonioProcesadorPagosExternos;

public class AdminDemonioPagos 
{
	private static AdminDemonioPagos instancia;
	private DemonioProcesadorPagosExternos demonioPagos;

	private AdminDemonioPagos()
	{
	}

	public static AdminDemonioPagos getInstancia()
	{
		if (instancia == null)
			instancia = new AdminDemonioPagos();
		return instancia;
	}
	
	public void iniciar()
	{
		demonioPagos = new DemonioProcesadorPagosExternos();
		demonioPagos.start();		
	}	
}

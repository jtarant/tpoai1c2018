package controlador;

import modelo.DemonioProcesadorListasRegalos;

public class AdminDemonioListas 
{
	private static AdminDemonioListas instancia;
	private DemonioProcesadorListasRegalos demonioListas;

	private AdminDemonioListas()
	{
	}

	public static AdminDemonioListas getInstancia()
	{
		if (instancia == null)
			instancia = new AdminDemonioListas();
		return instancia;
	}
	
	public void iniciar()
	{
		demonioListas = new DemonioProcesadorListasRegalos();
		demonioListas.start();
	}
	
	public static void main(String args[])
	{
		AdminDemonioListas.getInstancia().iniciar();
	}
}

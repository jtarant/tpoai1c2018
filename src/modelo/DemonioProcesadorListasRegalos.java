package modelo;

import controlador.AdminListaRegalos;

public class DemonioProcesadorListasRegalos extends Thread {

	public void run()
	{
		System.out.println("* Demonio procesador de listas iniciado.");		
		while (true)
		{
			try 
			{
				Thread.sleep(1000*15);
				AdminListaRegalos.getInstancia().procesarProximasCierre();
			} 
			catch (InterruptedException e) 
			{
				System.out.println("* Demonio procesador de listas interrumpido.");
				e.printStackTrace();
			}
			catch (Exception ex)
			{
				System.out.println("* Error en demonio de listas: " + ex.getMessage());
				ex.printStackTrace();
			}
		}
	}
}

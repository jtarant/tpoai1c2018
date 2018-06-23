package modelo;

public class DemonioProcesadorPagosExternos extends Thread {

	public void run()
	{
		System.out.println("* Demonio procesador de pagos iniciado.");
		
		ProcesadorNovedadesPagos novedades = new ProcesadorNovedadesPagos();
		novedades.addObserver(new FachadaPagos());
		while (true)
		{
			try 
			{
				Thread.sleep(1000*10);
				novedades.procesarNovedades();
			} 
			catch (InterruptedException e) 
			{
				System.out.println("* Demonio procesador de pagos interrumpido.");
				e.printStackTrace();
			}
			catch (Exception ex)
			{
				System.out.println("* Error en demonio de pagos: " + ex.getMessage());
				ex.printStackTrace();
			}
		}
	}
}

package modelo;

public class SistemaPagos extends Thread {

	public void run()
	{
		System.out.println("* Thread sistema pagos iniciada");
		
		SistemaPagosNovedades novedades = new SistemaPagosNovedades();
		novedades.addObserver(new FachadaPagos());	// TODO: DUDA: El facade tiene que ser singleton?
		while (true)
		{
			try 
			{
				novedades.procesarNovedades();
				Thread.sleep(1000*30);
			} 
			catch (InterruptedException e) 
			{
				System.out.println("* Thread interrumpido.");
				e.printStackTrace();
			}
			catch (Exception ex)
			{
				ex.printStackTrace();				
			}
		}
	}
	
	public static void main(String[] args) {
		SistemaPagos sistemaPagos = new SistemaPagos();
		sistemaPagos.start();
	}
}

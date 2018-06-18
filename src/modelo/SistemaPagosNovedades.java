package modelo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Observable;

public class SistemaPagosNovedades extends Observable 
{
	public void procesarNovedades() throws Exception
	{
		File folder = new File("./pagos pendientes/");
		File[] listOfFiles = folder.listFiles();

		for (File file : listOfFiles) 
		{
		    if (file.isFile() && file.getName().toLowerCase().startsWith("pago") && file.getName().toLowerCase().endsWith(".txt")) 
		    {
		    	System.out.println("procesando: " + file.getName());
		        procesarArchivo(file.getName());
		        file.renameTo(new File("./pagos procesados/" + file.getName()));
		    }
		}
	}
	
	public void procesarArchivo(String filename) throws Exception
	{
		BufferedReader reader = null;
		SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
		Integer nroLinea = 1;
		try
		{
			reader = new BufferedReader(new FileReader(filename));
			String linea;
			while ((linea = reader.readLine()) != null)
			{
				String[] valores = linea.split(",[ ]*");
				if (valores.length < 3)
				{
					System.out.println("Linea " + nroLinea.toString() + " no valida (menos de 3 datos): " + linea);
				}
				else
				{
					try
					{
						int codigo = Integer.parseInt(valores[0]);
						String idUsuario = valores[1];
						Date fecha = dateFormatter.parse(valores[2]);
						
						// Notificar pago
						SistemaPagosDetallePagoDTO datosPago = new SistemaPagosDetallePagoDTO(codigo, idUsuario, fecha);
						setChanged();
						notifyObservers(datosPago);
					}
					catch (Exception e)
					{
						System.out.println("Linea " + nroLinea.toString() + ": " + e.getMessage());
					}
				}
				nroLinea =+ 1;
			}
		}
		catch (Exception e)
		{
			throw e;
		}
		finally
		{
			if (reader != null) reader.close();
		}
	}
}

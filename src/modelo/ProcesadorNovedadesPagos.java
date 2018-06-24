package modelo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Observable;

public class ProcesadorNovedadesPagos extends Observable 
{
	private String folderEntrada;
	private String folderSalida;
	
	public ProcesadorNovedadesPagos()
	{
		String pathActual = System.getProperty("user.dir");
		folderEntrada = pathActual + "\\pagos pendientes\\";
		folderSalida = pathActual + "\\pagos procesados\\";
		
		System.out.println("entrada: " + folderEntrada);
		System.out.println("salida: " + folderSalida);
		
		File dirEntrada = new File(folderEntrada);
		File dirSalida = new File(folderSalida);
		if (!dirEntrada.exists()) dirEntrada.mkdir();
		if (!dirSalida.exists()) dirSalida.mkdir();
	}
	
	public void procesarNovedades() throws Exception
	{		
		File folder = new File(folderEntrada);
		File[] listOfFiles = folder.listFiles();

		if (listOfFiles != null)
		{
			for (File file : listOfFiles) 
			{
			    if (file.isFile() && file.getName().toLowerCase().startsWith("pago") && file.getName().toLowerCase().endsWith(".txt")) 
			    {
			    	System.out.println("procesando: " + file.getName());
			    	
			        procesarArchivo(folderEntrada + file.getName());
			        
			        System.out.println("finalizado.");
			        File destino = new File(folderSalida + file.getName());
			        if (destino.exists())
			        {
			        	destino.delete();
			        }
			        file.renameTo(destino);
			    }
			}
		}
	}
	
	private void procesarArchivo(String filename) throws Exception
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
						PagoExterno datosPago = new PagoExterno(codigo, idUsuario, fecha);
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

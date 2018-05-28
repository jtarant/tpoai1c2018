package vista;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ValidadorTexto {
	final static String DATE_FORMAT = "dd/MM/yyyy";

	public static Boolean esFechaValida(String date) 
	{
	        try {
	            DateFormat df = new SimpleDateFormat(DATE_FORMAT);
	            df.setLenient(false);
	            df.parse(date);
	            return true;
	        } catch (ParseException e) {
	            return false;
	        }
	}
	
	public static Boolean esMonedaValida(String valor)
	{
		try 
		{
			NumberFormat.getInstance().parse(valor);
			return true;
		} catch (ParseException e) {
			return false;
		}
	}
}

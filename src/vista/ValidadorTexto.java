package vista;

import java.text.DateFormat;
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
			float monto = Float.parseFloat(valor);
			if (monto <= 0)
				return false;
			else
				return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}

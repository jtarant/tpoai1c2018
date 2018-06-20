package modelo;

import java.util.Date;
import java.util.Observable;
import java.util.Observer;

import controlador.AdminListaRegalos;

public class FachadaPagos implements Observer 
{
	// TODO: Duda, la fechada va en negocio o en controlador?
	public void registrarPago(int codigoLista, String idUsuario, Date fechaPago) throws Exception
	{
		AdminListaRegalos.getInstancia().registrarPago(codigoLista, idUsuario, fechaPago);
	}

	@Override
	public void update(Observable arg0, Object arg1) 
	{
		try
		{
			PagoExterno datosPago = (PagoExterno)arg1;
			
			this.registrarPago(datosPago.getCodigoLista(), datosPago.getIdUsuario(), datosPago.getFechaPago());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}

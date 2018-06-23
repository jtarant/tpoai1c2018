package modelo;

import java.util.List;

public class EmailProximoCierre extends Email
{
	private String agasajado;
	private float monto;

	public EmailProximoCierre(List<String> para, String agasajado, float monto) 
	{
		this.para = para;
		this.agasajado = agasajado;
		this.monto = monto;
		this.asunto = "Recordatorio de proximo cierre de lista de regalos";
	}

	@Override
	public String getTexto() 
	{
		return "La lista de regalos para " + agasajado + " esta proxima a cerrar.\nEl monto a aportar es de " + Float.toString(monto);
	}
}

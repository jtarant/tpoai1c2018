package modelo;

import java.util.List;

public class EmailInicioLista extends Email
{
	private String agasajado;
	private float monto;

	public EmailInicioLista(List<String> para, String agasajado, float monto) 
	{
		this.para = para;
		this.agasajado = agasajado;
		this.monto = monto;
		this.asunto = "Bienvenido a la lista de regalos";
	}

	@Override
	public String getTexto() 
	{
		return "bienvenido a la lista de regalos para agasajar a " + agasajado + "\nEl monto a aportar es de " + Float.toString(monto);
	}
}

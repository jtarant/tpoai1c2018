package modelo;

import java.util.ArrayList;

public class EmailAvisoRegalo extends Email
{
	private float monto;
	private String agasajado;

	public EmailAvisoRegalo(String para, String agasajado, float monto) 
	{
		ArrayList<String> dest = new ArrayList<String>();
		dest.add(para);
		this.para = dest;
		this.agasajado = agasajado;
		this.monto = monto;
		this.asunto = "Aviso de Regalo";
	}

	@Override
	public String getTexto() 
	{
		return "La lista de regalos para " + agasajado + " se ha cerrado. El monto disponible para el regalo es de " + Float.toString(monto);
	}
}

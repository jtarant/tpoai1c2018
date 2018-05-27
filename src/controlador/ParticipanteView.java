package controlador;

import java.util.Date;

public class ParticipanteView 
{
	private String idUsuario;
	private Date fechaPago;

	public ParticipanteView(String idUsuario, Date fechaPago)
	{
		this.idUsuario = idUsuario;
		this.fechaPago = fechaPago;
	}	
	public String getIdUsuario()
	{
		return idUsuario;
	}
	public Date getFechaPago()
	{
		return fechaPago;
	}
}

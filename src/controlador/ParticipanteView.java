package controlador;

import java.util.Date;

public class ParticipanteView 
{
	private String idUsuario;
	private Date fechaPago;
	private float montoPagado;

	public ParticipanteView(String idUsuario, Date fechaPago, float montoPagado)
	{
		this.idUsuario = idUsuario;
		this.fechaPago = fechaPago;
		this.montoPagado = montoPagado;
	}	
	public String getIdUsuario()
	{
		return idUsuario;
	}
	public Date getFechaPago()
	{
		return fechaPago;
	}
	public float getMontoPagado()
	{
		return montoPagado;
	}
}

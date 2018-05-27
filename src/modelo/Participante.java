package modelo;

import java.util.Date;

public class Participante 
{
	private Usuario usuario;
	private Date fechaPago;

	public void setFechaPago(Date fecha)
	{
		fechaPago = fecha;
	}
	public void setUsuario(Usuario usr)
	{
		usuario = usr;
	}
	
	public Participante(Usuario usuario)
	{
		setUsuario(usuario);
		setFechaPago(null);
	}
	
	public Participante(Usuario usuario, Date fechaPago)
	{
		setUsuario(usuario);
		setFechaPago(fechaPago);
	}
	
	public Usuario getUsuario()
	{
		return usuario;
	}
	public Boolean getPagoRealizado()
	{
		return (getFechaPago() != null);
	}
	public Date getFechaPago()
	{
		return fechaPago;
	}
}

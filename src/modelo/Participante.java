package modelo;

import java.util.Date;

public class Participante 
{
	private Usuario usuario;
	private Boolean estadoPagado;
	private Date fechaPago;

	public void setEstadoPagado(Boolean pagado)
	{
		estadoPagado = pagado;
	}
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
		setEstadoPagado(false);
		setFechaPago(null);
	}
	
	public Participante(Usuario usuario, Boolean pagado, Date fechaPago)
	{
		setUsuario(usuario);
		setEstadoPagado(pagado);
		setFechaPago(fechaPago);
	}
	
	public Usuario getUsuario()
	{
		return usuario;
	}
	public Boolean getEstadoPagado()
	{
		return estadoPagado;
	}
	public Date getFechaPago()
	{
		return fechaPago;
	}
}

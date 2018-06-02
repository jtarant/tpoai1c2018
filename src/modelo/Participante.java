package modelo;

import java.util.Date;

public class Participante 
{
	private ListaRegalos lista;
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
	public void setLista(ListaRegalos listaRegalos) 
	{
		this.lista = listaRegalos;
	}
	
	public Participante(Usuario usuario)
	{
		setUsuario(usuario);
		setFechaPago(null);
		setLista(null);
	}
	
	public Participante(Usuario usuario, Date fechaPago)
	{
		setUsuario(usuario);
		setFechaPago(fechaPago);
		setLista(null);
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
	public ListaRegalos getLista()
	{
		return lista;
	}
}

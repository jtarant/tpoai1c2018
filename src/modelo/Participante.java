package modelo;

import java.util.Date;

public class Participante 
{
	private ListaRegalos lista;
	private Usuario usuario;
	private Date fechaPago;
	private float montoPagado;	

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
	public void setMontoPagado(float monto)
	{
		this.montoPagado = monto;
	}
	
	public Participante(Usuario usuario)
	{
		setUsuario(usuario);
		setFechaPago(null);
		setLista(null);
		setMontoPagado(0);
	}
	
	public Participante(Usuario usuario, Date fechaPago, float montoPagado)
	{
		setUsuario(usuario);
		setFechaPago(fechaPago);
		setMontoPagado(montoPagado);
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
	public float getMontoPagado()
	{
		return montoPagado;
	}
	public ListaRegalos getLista()
	{
		return lista;
	}
}

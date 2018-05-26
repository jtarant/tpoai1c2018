package controlador;

public class UsuarioIDNombreView {
	private String idUsuario;
	private String nombre;
	private String apellido;
	
	public UsuarioIDNombreView(String idUsuario, String nombre, String apellido)
	{
		this.idUsuario = idUsuario;
		this.nombre = nombre;
		this.apellido = apellido;
	}
	
	public String getIdUsuario()
	{
		return idUsuario;
	}
	public String getNombre()
	{
		return nombre;
	}
	public String getApellido()
	{
		return apellido;
	}
}

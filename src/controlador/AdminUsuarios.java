package controlador;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import modelo.Usuario;

public class AdminUsuarios {
	private static AdminUsuarios instancia;
	private List<Usuario> usuarios;
	
	private AdminUsuarios()
	{
		usuarios = new ArrayList<Usuario>();
	}
	
	public static AdminUsuarios getInstancia()
	{
		if (instancia == null)
			instancia = new AdminUsuarios();
		return instancia;
	}
	
	public void Crear(String idUsuario, String password, String nombre, String apellido, Timestamp fechaNac, String email)
	{
		Usuario nuevoUsr = new Usuario(idUsuario, password, nombre, apellido, fechaNac, email);
		usuarios.add(nuevoUsr);
	}
}

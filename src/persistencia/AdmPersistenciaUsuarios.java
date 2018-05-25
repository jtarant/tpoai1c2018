package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Date;

import modelo.Usuario;

public class AdmPersistenciaUsuarios {
	private static AdmPersistenciaUsuarios instancia;
	
	private AdmPersistenciaUsuarios()
	{
	}

	public static AdmPersistenciaUsuarios getInstancia()
	{
		if (instancia == null)
			instancia = new AdmPersistenciaUsuarios();
		return instancia;
	}
	
	public void Insertar(Usuario usr) throws Exception
	{
		try
		{
			Connection cnx = PoolConexiones.getConexion().getConnection();
			PreparedStatement cmdSql = cnx.prepareStatement("INSERT INTO TPO_AI_TARANTINO_CALISI.dbo.USUARIOS (IdUsuario,Password,Nombre,Apellido,FechaNac,Email,Activo) VALUES (?,?,?,?,?,?,?)");
			cmdSql.setString(1, usr.getIdUsuario());
			cmdSql.setString(2, usr.getPassword());
			cmdSql.setString(3, usr.getNombre());
			cmdSql.setString(4, usr.getApellido());
			cmdSql.setTimestamp(5, new Timestamp(usr.getFechaNac().getTime()));
			cmdSql.setString(6, usr.getEmail());
			cmdSql.setBoolean(7, usr.getActivo());
			cmdSql.execute();
			PoolConexiones.getConexion().realeaseConnection(cnx);
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
			throw e;
		}	
	}

	public Usuario Buscar(String idUsuario) throws Exception
	{
		Usuario usr = null;
		try
		{
			Connection cnx = PoolConexiones.getConexion().getConnection();
			PreparedStatement cmdSql = cnx.prepareStatement("SELECT * FROM TPO_AI_TARANTINO_CALISI.dbo.USUARIOS WHERE idUsuario=?");
			cmdSql.setString(1, idUsuario);
			ResultSet result = cmdSql.executeQuery();
			if (result.next())
			{
				String idUsr = result.getString(1);
				String password = result.getString(2);
				String nombre = result.getString(3);
				String apellido = result.getString(4);
				Date fNac = result.getDate(5);
				String email = result.getString(6);
				Boolean activo = result.getBoolean(7);
				usr = new Usuario(idUsr,password,nombre,apellido,fNac,email,activo);
			}
			PoolConexiones.getConexion().realeaseConnection(cnx);
			return usr;
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
			throw e;
		}		
	}

}

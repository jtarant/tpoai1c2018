package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import modelo.ExceptionDeNegocio;
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
	
	public void insertar(Usuario usr) throws Exception
	{
		Connection cnx = null;
		try
		{
			cnx = PoolConexiones.getInstancia().getConnection();
			PreparedStatement cmdSql = cnx.prepareStatement("INSERT INTO TPO_AI_TARANTINO_CALISI.dbo.USUARIOS (IdUsuario,Password,Nombre,Apellido,FechaNac,Email,Activo,sysAdmin) VALUES (?,?,?,?,?,?,?,?)");
			cmdSql.setString(1, usr.getIdUsuario());
			cmdSql.setString(2, usr.getPassword());
			cmdSql.setString(3, usr.getNombre());
			cmdSql.setString(4, usr.getApellido());
			cmdSql.setTimestamp(5, new Timestamp(usr.getFechaNac().getTime()));
			cmdSql.setString(6, usr.getEmail());
			cmdSql.setBoolean(7, usr.getActivo());
			cmdSql.setBoolean(8, usr.getSysAdmin());
			cmdSql.execute();
			PoolConexiones.getInstancia().realeaseConnection(cnx);
		}
		catch (SQLException se)
		{
			if (se.getSQLState().startsWith("23")) throw new ExceptionDeNegocio("Ya existe otro usuario con ese nombre.");
			else throw se;
		}
		catch (Exception e)
		{
			throw e;
		}		
		finally
		{
			if (cnx != null) PoolConexiones.getInstancia().realeaseConnection(cnx); 
		}		
	}

	public Usuario buscar(String idUsuario) throws Exception
	{
		Connection cnx = null;
		Usuario usr = null;
		try
		{
			cnx = PoolConexiones.getInstancia().getConnection();
			PreparedStatement cmdSql = cnx.prepareStatement("SELECT * FROM TPO_AI_TARANTINO_CALISI.dbo.USUARIOS WHERE idUsuario=? AND Activo<>0");
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
				Boolean sysAdmin = result.getBoolean(8);
				usr = new Usuario(idUsr,password,nombre,apellido,fNac,email,activo,sysAdmin);
			}
			PoolConexiones.getInstancia().realeaseConnection(cnx);
			return usr;
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
			throw e;
		}		
		finally
		{
			if (cnx != null) PoolConexiones.getInstancia().realeaseConnection(cnx); 
		}		
	}

	public List<Usuario> listarIdNombre() throws Exception
	{
		Connection cnx = null;
		List<Usuario> lista = new ArrayList<Usuario>();
		try
		{
			cnx = PoolConexiones.getInstancia().getConnection();
			PreparedStatement cmdSql = cnx.prepareStatement("SELECT IdUsuario,Nombre,Apellido FROM TPO_AI_TARANTINO_CALISI.dbo.USUARIOS WHERE Activo<>0 ORDER BY IdUsuario");
			ResultSet result = cmdSql.executeQuery();
			while (result.next())
			{
				String idUsr = result.getString(1);
				String nombre = result.getString(2);
				String apellido = result.getString(3);
				Usuario usr = new Usuario(idUsr,null,nombre,apellido,null,null,true,false);
				lista.add(usr);
			}
			PoolConexiones.getInstancia().realeaseConnection(cnx);
			return lista;
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
			throw e;
		}				
		finally
		{
			if (cnx != null) PoolConexiones.getInstancia().realeaseConnection(cnx); 
		}
	}

	public void modificar(Usuario usr) throws Exception 
	{
		Connection cnx = null;
		try
		{
			cnx = PoolConexiones.getInstancia().getConnection();
			PreparedStatement cmdSql = cnx.prepareStatement("UPDATE TPO_AI_TARANTINO_CALISI.dbo.USUARIOS SET Password=?,Nombre=?,Apellido=?,FechaNac=?,Email=?,Activo=?,SysAdmin=? WHERE IdUsuario=?");
			cmdSql.setString(1, usr.getPassword());
			cmdSql.setString(2, usr.getNombre());
			cmdSql.setString(3, usr.getApellido());
			cmdSql.setTimestamp(4, new Timestamp(usr.getFechaNac().getTime()));
			cmdSql.setString(5, usr.getEmail());
			cmdSql.setBoolean(6, usr.getActivo());
			cmdSql.setBoolean(7, usr.getSysAdmin());
			cmdSql.setString(8, usr.getIdUsuario());		
			cmdSql.execute();
			PoolConexiones.getInstancia().realeaseConnection(cnx);
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
			throw e;
		}
		finally
		{
			if (cnx != null) PoolConexiones.getInstancia().realeaseConnection(cnx); 
		}
	}

	public void eliminar(Usuario usr) throws Exception 
	{
		Connection cnx = null;
		try
		{
			cnx = PoolConexiones.getInstancia().getConnection();
			PreparedStatement cmdSql;
			try 
			{
				// Intento eliminar fisicamente
				cmdSql = cnx.prepareStatement("DELETE FROM TPO_AI_TARANTINO_CALISI.dbo.USUARIOS WHERE IdUsuario=?");
				cmdSql.setString(1, usr.getIdUsuario());
				cmdSql.execute();
			}
			catch (SQLException se)
			{
				if (se.getSQLState().startsWith("23"))
				{
					// Si no puedo, es porque esta en uso, hago eliminacion logica
					cmdSql = cnx.prepareStatement("UPDATE TPO_AI_TARANTINO_CALISI.dbo.USUARIOS SET Activo=0 WHERE IdUsuario=?");
					cmdSql.setString(1, usr.getIdUsuario());
					cmdSql.execute();
				}
				else throw se;
			}
			PoolConexiones.getInstancia().realeaseConnection(cnx);
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
			throw e;
		}
		finally
		{
			if (cnx != null) PoolConexiones.getInstancia().realeaseConnection(cnx); 
		}
	}

	public int getCantidadUsuarios() throws Exception 
	{
		Connection cnx = null;
		try
		{
			cnx = PoolConexiones.getInstancia().getConnection();
			PreparedStatement cmdSql = cnx.prepareStatement("SELECT COUNT(*) FROM TPO_AI_TARANTINO_CALISI.dbo.USUARIOS WHERE Activo<>0");
			ResultSet result = cmdSql.executeQuery();
			PoolConexiones.getInstancia().realeaseConnection(cnx);
			result.next();
			return result.getInt(1);
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
			throw e;
		}
		finally
		{
			if (cnx != null) PoolConexiones.getInstancia().realeaseConnection(cnx); 
		}
	}
}

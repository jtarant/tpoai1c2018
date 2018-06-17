package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import controlador.ListaResumenView;
import modelo.EstadoListaRegalos;
import modelo.ListaRegalos;
import modelo.Participante;
import modelo.Usuario;

public class AdmPersistenciaListasRegalos {
	private static AdmPersistenciaListasRegalos instancia;
	
	private AdmPersistenciaListasRegalos()
	{
	}

	public static AdmPersistenciaListasRegalos getInstancia()
	{
		if (instancia == null)
			instancia = new AdmPersistenciaListasRegalos();
		return instancia;
	}
	
	public void insertar(ListaRegalos lista) throws Exception
	{
		Connection cnx = null;
		ResultSet clavesGeneradas = null;
		try
		{
			cnx = PoolConexiones.getInstancia().iniciarTransaccion();
			/* DATOS DE LA LISTA */
			PreparedStatement cmdSqlLista = cnx.prepareStatement("INSERT INTO TPO_AI_TARANTINO_CALISI.dbo.ListasRegalos (IdUsuarioAdmin,FechaAgasajo,NombreAgasajado,MontoPorParticipante,FechaInicio,FechaFin,Estado) VALUES (?,?,?,?,?,?,?);", Statement.RETURN_GENERATED_KEYS);
			cmdSqlLista.setString(1, lista.getAdmin().getIdUsuario());
			cmdSqlLista.setTimestamp(2, new Timestamp(lista.getFechaAgasajo().getTime()));
			cmdSqlLista.setString(3, lista.getNombreAgasajado());
			cmdSqlLista.setFloat(4, lista.getMontoPorParticipante());
			cmdSqlLista.setTimestamp(5, new Timestamp(lista.getFechaInicio().getTime()));
			cmdSqlLista.setTimestamp(6, new Timestamp(lista.getFechaFin().getTime()));
			cmdSqlLista.setInt(7, lista.getEstado().getValor());
			cmdSqlLista.execute();
			clavesGeneradas = cmdSqlLista.getGeneratedKeys();
			if (clavesGeneradas.next())
			{
				lista.setCodigo(clavesGeneradas.getInt(1));
			}
			
			/* PARTICIPANTES */
			for (Participante participante: lista.getParticipantes())
			{
				insertarParticipante(lista.getCodigo(), participante, cnx);
			}
			cnx.commit();
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
			if (cnx != null) cnx.rollback();
			throw e;
		}
		finally
		{
			if (cnx != null) PoolConexiones.getInstancia().finTransaccion(); 
		}
	}
		
	public List<ListaResumenView> listarMisListas(Usuario usr) throws Exception 
	{
		Connection cnx = null;
		List<ListaResumenView> listas = new ArrayList<ListaResumenView>();

		try
		{
			cnx = PoolConexiones.getInstancia().getConnection();
			String sql = "SELECT CodigoLista,IdUsuarioAdmin,FechaAgasajo,NombreAgasajado,MontoPorParticipante,FechaInicio,FechaFin,Estado " + 
					"FROM [TPO_AI_TARANTINO_CALISI].[dbo].[ListasRegalos] WHERE IdUsuarioAdmin=? " + 
					"UNION " + 
					"SELECT lst.CodigoLista,IdUsuarioAdmin,FechaAgasajo,NombreAgasajado,MontoPorParticipante,FechaInicio,FechaFin,Estado " + 
					"FROM [TPO_AI_TARANTINO_CALISI].[dbo].[ListasRegalos] AS lst " + 
					"INNER JOIN [TPO_AI_TARANTINO_CALISI].[dbo].[Participantes] as part ON part.CodigoLista=lst.CodigoLista " + 
					"WHERE part.IdUsuario=?";
			PreparedStatement cmdSql = cnx.prepareStatement(sql);
			cmdSql.setString(1, usr.getIdUsuario());
			cmdSql.setString(2, usr.getIdUsuario());
			ResultSet result = cmdSql.executeQuery();
			
			ListaResumenView lst;
			while (result.next())
			{
				lst = new ListaResumenView(result.getInt(1), result.getString(2), result.getDate(3), result.getString(4), result.getFloat(5), result.getDate(6), result.getDate(7), result.getInt(8));
				listas.add(lst);
			}
			PoolConexiones.getInstancia().realeaseConnection(cnx);
			return listas;
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

	public ListaRegalos buscar(int codigo) throws Exception 
	{
		Connection cnx = null;
		try
		{
			ListaRegalos lista = null;
			
			cnx = PoolConexiones.getInstancia().getConnection();
			/* DATOS DE LA LISTA */
			PreparedStatement cmdSqlLista = cnx.prepareStatement("SELECT CodigoLista,IdUsuarioAdmin,FechaAgasajo,NombreAgasajado,MontoPorParticipante,FechaInicio,FechaFin,Estado FROM TPO_AI_TARANTINO_CALISI.dbo.ListasRegalos WHERE CodigoLista=?");
			cmdSqlLista.setInt(1, codigo);
			ResultSet resultLista = cmdSqlLista.executeQuery();
			if (resultLista.next())
			{
				Usuario admin = AdmPersistenciaUsuarios.getInstancia().buscar(resultLista.getString(2));
				lista = new ListaRegalos(resultLista.getInt(1), admin, resultLista.getDate(3), resultLista.getString(4), resultLista.getFloat(5), resultLista.getDate(6), resultLista.getDate(7), EstadoListaRegalos.fromInt(resultLista.getInt(8)));
				
				/* PARTICIPANTES */
				PreparedStatement cmdSqlParticipante = cnx.prepareStatement("SELECT IdUsuario,FechaPago FROM TPO_AI_TARANTINO_CALISI.dbo.Participantes WHERE CodigoLista=?");
				cmdSqlParticipante.setInt(1, codigo);
				ResultSet resultParticipantes = cmdSqlParticipante.executeQuery();

				while (resultParticipantes.next())
				{
					Usuario p = AdmPersistenciaUsuarios.getInstancia().buscar(resultParticipantes.getString(1));
					if (p != null)
					{
						lista.agregarParticipante(new Participante(p, resultParticipantes.getDate(2)));
					}
				}
				lista.resetearCambiosParticipantes();
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

	public void eliminar(ListaRegalos lista) throws Exception 
	{
		Connection cnx = null;
		try
		{
			cnx = PoolConexiones.getInstancia().iniciarTransaccion();
			/* ELIMINO PARTICIPANTES */
			PreparedStatement cmdSqlParticipante = cnx.prepareStatement("DELETE FROM TPO_AI_TARANTINO_CALISI.dbo.Participantes WHERE CodigoLista=?");
			cmdSqlParticipante.setInt(1, lista.getCodigo());
			cmdSqlParticipante.execute();
			
			/* ELIMINO LA LISTA */
			PreparedStatement cmdSqlLista = cnx.prepareStatement("DELETE FROM TPO_AI_TARANTINO_CALISI.dbo.ListasRegalos WHERE CodigoLista=?");
			cmdSqlLista.setInt(1, lista.getCodigo());
			cmdSqlLista.execute();
			cnx.commit();
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
			if (cnx != null) cnx.rollback();
			throw e;
		}
		finally
		{
			if (cnx != null) PoolConexiones.getInstancia().finTransaccion(); 
		}				
	}

	public void actualizar(ListaRegalos lista) throws Exception 
	{
		Connection cnx = null;
		try
		{
			cnx = PoolConexiones.getInstancia().iniciarTransaccion();
			/* ACTUALIZACION DE PARTICIPANTES */
			for (Participante nuevo : lista.getParticipantesNuevos())
			{
				insertarParticipante(lista.getCodigo(), nuevo, cnx);
			}
			for (Participante modif : lista.getParticipantesModificados())
			{
				modificarParticipante(lista.getCodigo(), modif, cnx);
			}			
			for (Participante baja : lista.getParticipantesEliminados())
			{
				eliminarParticipante(baja, cnx);
			}
			/* ACTUALIZACION DE LA LISTA */
			PreparedStatement cmdSqlLista = cnx.prepareStatement("UPDATE TPO_AI_TARANTINO_CALISI.dbo.ListasRegalos SET FechaAgasajo=?, NombreAgasajado=?, MontoPorParticipante=?, FechaInicio=?, FechaFin=?, Estado=? WHERE CodigoLista=?");
			cmdSqlLista.setTimestamp(1, new Timestamp(lista.getFechaAgasajo().getTime()));
			cmdSqlLista.setString(2, lista.getNombreAgasajado());
			cmdSqlLista.setFloat(3, lista.getMontoPorParticipante());
			cmdSqlLista.setTimestamp(4, new Timestamp(lista.getFechaInicio().getTime()));
			cmdSqlLista.setTimestamp(5, new Timestamp(lista.getFechaFin().getTime()));
			cmdSqlLista.setInt(6, lista.getEstado().getValor());
			cmdSqlLista.setInt(7, lista.getCodigo());
			cmdSqlLista.execute();
			cnx.commit();
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
			if (cnx != null) cnx.rollback();
			throw e;
		}
		finally
		{
			if (cnx != null) PoolConexiones.getInstancia().finTransaccion(); 
		}						
	}
	
	private void insertarParticipante(int codigoLista, Participante p, Connection cnx) throws SQLException
	{
		PreparedStatement cmdSqlParticipante = cnx.prepareStatement("INSERT INTO TPO_AI_TARANTINO_CALISI.dbo.Participantes (CodigoLista,IdUsuario,FechaPago) VALUES (?,?,?);");
		cmdSqlParticipante.setInt(1, codigoLista);
		cmdSqlParticipante.setString(2, p.getUsuario().getIdUsuario());
		if (p.getPagoRealizado())
		{
			cmdSqlParticipante.setTimestamp(3, new Timestamp(p.getFechaPago().getTime()));
		}
		else
		{
			cmdSqlParticipante.setTimestamp(3, null);
		}
		cmdSqlParticipante.execute();		
	}	
	
	private void modificarParticipante(int codigoLista, Participante p, Connection cnx) throws SQLException
	{
		PreparedStatement cmdSqlParticipante = cnx.prepareStatement("UPDATE TPO_AI_TARANTINO_CALISI.dbo.Participantes SET FechaPago=? WHERE CodigoLista=? AND IdUsuario=?;");
		if (p.getPagoRealizado())
		{
			cmdSqlParticipante.setTimestamp(1, new Timestamp(p.getFechaPago().getTime()));
		}
		else
		{
			cmdSqlParticipante.setTimestamp(1, null);
		}
		cmdSqlParticipante.setInt(2, codigoLista);
		cmdSqlParticipante.setString(3, p.getUsuario().getIdUsuario());
		cmdSqlParticipante.execute();		
	}
	
	private void eliminarParticipante(Participante p, Connection cnx) throws SQLException
	{
		PreparedStatement cmdSqlParticipante = cnx.prepareStatement("DELETE FROM TPO_AI_TARANTINO_CALISI.dbo.Participantes WHERE CodigoLista=? AND IdUsuario=?");		
		cmdSqlParticipante.setInt(1, p.getLista().getCodigo());
		cmdSqlParticipante.setString(2, p.getUsuario().getIdUsuario());
		cmdSqlParticipante.execute();		
	}	
}

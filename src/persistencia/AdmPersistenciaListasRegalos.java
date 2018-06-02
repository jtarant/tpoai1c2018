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
			cnx = PoolConexiones.getConexion().getConnection();
			cnx.setAutoCommit(false); /* Abro TRANSACCION */
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
			PreparedStatement cmdSqlParticipante = cnx.prepareStatement("INSERT INTO TPO_AI_TARANTINO_CALISI.dbo.Participantes (CodigoLista,IdUsuario,FechaPago) VALUES (?,?,?);");
			cmdSqlParticipante.setInt(1, lista.getCodigo());
			for (Participante participante: lista.getParticipantes())
			{
				cmdSqlParticipante.setString(2, participante.getUsuario().getIdUsuario());
				if (participante.getPagoRealizado())
				{
					cmdSqlParticipante.setTimestamp(3, new Timestamp(participante.getFechaPago().getTime()));
				}
				else
				{
					cmdSqlParticipante.setTimestamp(3, null);
				}
				cmdSqlParticipante.execute();
			}
			cnx.commit();
			PoolConexiones.getConexion().realeaseConnection(cnx);
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
			if (cnx != null) cnx.rollback();
			throw e;
		}
		finally
		{
			if (cnx != null) PoolConexiones.getConexion().realeaseConnection(cnx); 
		}		
	}

	public List<ListaResumenView> listarMisListas(Usuario usr) throws Exception 
	{
		Connection cnx = null;
		List<ListaResumenView> listas = new ArrayList<ListaResumenView>();

		try
		{
			cnx = PoolConexiones.getConexion().getConnection();
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
			PoolConexiones.getConexion().realeaseConnection(cnx);
			return listas;
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
			throw e;
		}
		finally
		{
			if (cnx != null) PoolConexiones.getConexion().realeaseConnection(cnx); 
		}		
	}

	public ListaRegalos buscar(int codigo) throws Exception 
	{
		Connection cnx = null;
		try
		{
			ListaRegalos lista = null;
			
			cnx = PoolConexiones.getConexion().getConnection();
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
				cmdSqlParticipante.setInt(1, lista.getCodigo());
				ResultSet resultParticipantes = cmdSqlParticipante.executeQuery();

				while (resultParticipantes.next())
				{
					Usuario p = AdmPersistenciaUsuarios.getInstancia().buscar(resultParticipantes.getString(1));
					if (p != null)
					{
						lista.agregarParticipante(p, resultParticipantes.getDate(2));
					}
				}				
			}
			PoolConexiones.getConexion().realeaseConnection(cnx);
			return lista;
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
			throw e;
		}
		finally
		{
			if (cnx != null) PoolConexiones.getConexion().realeaseConnection(cnx); 
		}		
	}

	public void eliminar(ListaRegalos lista) throws SQLException 
	{
		Connection cnx = null;
		try
		{
			cnx = PoolConexiones.getConexion().getConnection();
			cnx.setAutoCommit(false); /* Abro TRANSACCION */
			/* ELIMINO PARTICIPANTES */
			PreparedStatement cmdSqlParticipante = cnx.prepareStatement("DELETE FROM TPO_AI_TARANTINO_CALISI.dbo.Participantes WHERE CodigoLista=?");
			cmdSqlParticipante.setInt(1, lista.getCodigo());
			cmdSqlParticipante.execute();
			/* ELIMINO LA LISTA */
			PreparedStatement cmdSqlLista = cnx.prepareStatement("DELETE FROM TPO_AI_TARANTINO_CALISI.dbo.ListasRegalos WHERE CodigoLista=?");
			cmdSqlLista.setInt(1, lista.getCodigo());
			cmdSqlLista.execute();
			cnx.commit();
			PoolConexiones.getConexion().realeaseConnection(cnx);
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
			if (cnx != null) cnx.rollback();
			throw e;
		}
		finally
		{
			if (cnx != null) PoolConexiones.getConexion().realeaseConnection(cnx); 
		}				
	}

	public void quitarParticipante(int codigo, String idUsuario) throws Exception 
	{
		Connection cnx = null;
		try
		{
			cnx = PoolConexiones.getConexion().getConnection();
			PreparedStatement cmdSqlParticipante = cnx.prepareStatement("DELETE FROM TPO_AI_TARANTINO_CALISI.dbo.Participantes WHERE CodigoLista=? AND IdUsuario=?");
			cmdSqlParticipante.setInt(1, codigo);
			cmdSqlParticipante.setString(2, idUsuario);
			cmdSqlParticipante.execute();
			PoolConexiones.getConexion().realeaseConnection(cnx);
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
			throw e;
		}
		finally
		{
			if (cnx != null) PoolConexiones.getConexion().realeaseConnection(cnx); 
		}		
	}
}

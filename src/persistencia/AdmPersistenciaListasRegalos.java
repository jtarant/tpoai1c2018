package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;

import modelo.ListaRegalos;
import modelo.Participante;

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
}

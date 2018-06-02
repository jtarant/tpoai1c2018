package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import modelo.Participante;
import modelo.Usuario;

public class AdmPersistenciaParticipantes 
{
	private static AdmPersistenciaParticipantes instancia;
	
	private AdmPersistenciaParticipantes()
	{
	}

	public static AdmPersistenciaParticipantes getInstancia()
	{
		if (instancia == null)
			instancia = new AdmPersistenciaParticipantes();
		return instancia;
	}
	
	public void insertar(int codigoLista, Participante p) throws Exception
	{
		Connection cnx = PoolConexiones.getConexion().getConnection();
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
		if (!PoolConexiones.getConexion().enTransaccion()) PoolConexiones.getConexion().realeaseConnection(cnx);
	}
	
	public List<Participante> obtenerDeLista(int codigoLista) throws Exception
	{
		List<Participante> resultado = new ArrayList<Participante>();
		
		Connection cnx = PoolConexiones.getConexion().getConnection();
		PreparedStatement cmdSqlParticipante = cnx.prepareStatement("SELECT IdUsuario,FechaPago FROM TPO_AI_TARANTINO_CALISI.dbo.Participantes WHERE CodigoLista=?");
		cmdSqlParticipante.setInt(1, codigoLista);
		ResultSet resultParticipantes = cmdSqlParticipante.executeQuery();

		while (resultParticipantes.next())
		{
			Usuario p = AdmPersistenciaUsuarios.getInstancia().buscar(resultParticipantes.getString(1));
			if (p != null)
			{
				resultado.add(new Participante(p, resultParticipantes.getDate(2)));
			}
		}
		if (!PoolConexiones.getConexion().enTransaccion()) PoolConexiones.getConexion().realeaseConnection(cnx);		
		return resultado;
	}
	
	public void eliminar(Participante p) throws SQLException
	{
		Connection cnx = PoolConexiones.getConexion().getConnection();
		PreparedStatement cmdSqlParticipante = cnx.prepareStatement("DELETE FROM TPO_AI_TARANTINO_CALISI.dbo.Participantes WHERE CodigoLista=? AND IdUsuario=?");		
		cmdSqlParticipante.setInt(1, p.getLista().getCodigo());
		cmdSqlParticipante.setString(2, p.getUsuario().getIdUsuario());
		cmdSqlParticipante.execute();
		if (!PoolConexiones.getConexion().enTransaccion()) PoolConexiones.getConexion().realeaseConnection(cnx);		
	}
		
	public void eliminarTodos(int codigoLista) throws Exception
	{
		Connection cnx = PoolConexiones.getConexion().getConnection();
		PreparedStatement cmdSqlParticipante = cnx.prepareStatement("DELETE FROM TPO_AI_TARANTINO_CALISI.dbo.Participantes WHERE CodigoLista=?");
		cmdSqlParticipante.setInt(1, codigoLista);
		cmdSqlParticipante.execute();
		if (!PoolConexiones.getConexion().enTransaccion()) PoolConexiones.getConexion().realeaseConnection(cnx);		
	}

}

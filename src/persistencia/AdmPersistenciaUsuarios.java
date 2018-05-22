package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
	
	public void Insertar(Usuario usr)
	{
		try
		{
			//Connection con = PoolConexiones.getPoolConnection().getConnection();
			//PreparedStatement s = con.prepareStatement("insert into A_Interactivas_01.dbo.Afiliados values (?,?,?,?,?,?,?,?,?,?,?)");
			//agregar campos
			//s.execute();
			//PoolConexiones.getPoolConnection().realeaseConnection(con);
		}
		catch (Exception e)
		{
			System.out.println();
		}	
	}
}

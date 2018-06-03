package persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Vector;

public class PoolConexiones
{
	private Vector <Connection> connections = new Vector<Connection>();
	protected String jdbc;
	protected String servidor;
	protected String usuario;
	protected String password;
	protected int cantCon;
	private static PoolConexiones pool;
	private static Connection cnxTransaccion;
	
	private PoolConexiones()
	{
		getConfiguration();
		cnxTransaccion = null;		
		// Abre n conexiones y las mete en el pool (array)
		for (int i= 0; i< cantCon;i++)
			connections.add(this.connect());
	}
	
	public static PoolConexiones getInstancia()
	{
		if (pool == null)
			pool = new PoolConexiones();
		return pool;
	}
	
	private Connection connect()
	{
		try
		{
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String dbConnectString = jdbc + servidor; 
            Connection con = DriverManager.getConnection(dbConnectString, usuario, password);
            return con;
		}
		catch (Exception ex)
		{
			System.out.println("Mensaje Error: " + ex.getMessage());
			return null;
		}
	}
	
	public void getConfiguration()
	{
	    Properties propiedades;	  
	    try 
	    {
	       propiedades = new Properties();
	       propiedades.load(getClass().getClassLoader().getResourceAsStream("ConfigDB.txt"));
	 
	       jdbc = propiedades.getProperty("jdbc"); 
	       servidor = propiedades.getProperty("servidor");
	       usuario = propiedades.getProperty("usuario");
	       password = propiedades.getProperty("password");
	       cantCon = Integer.parseInt(propiedades.getProperty("conexiones"));
	     } 
	    catch (Exception e) 
	     {
				System.out.println("Mensaje Error: " + e.getMessage());
	     }
	}
	
	public void closeConnections()
	{
		// cierra todas las conexiones
		for (int i=0; i<connections.size();i++)
		{
			try
			{
				connections.elementAt(i).close();
			}
			catch(Exception e)
			{
				System.out.println("Mensaje Error: " + e.getMessage());
			}
		}
		cnxTransaccion = null;
	}
	
	public Connection iniciarTransaccion() throws Exception
	{
		cnxTransaccion = getConnection();
		cnxTransaccion.setAutoCommit(false);
		return cnxTransaccion;
	}
	
	public void finTransaccion() throws SQLException
	{
		cnxTransaccion.setAutoCommit(true);
		realeaseConnection(cnxTransaccion);
		cnxTransaccion = null;
	}
	
	public Boolean enTransaccion()
	{
		return (cnxTransaccion != null);
	}
	
	public Connection getConnection()
	{
		Connection c = null;
		// Si estoy en una transaccion, me aseguro de devolver siempre la misma conexion
		if (enTransaccion())
		{
			return cnxTransaccion;
		}
		else
		{
			// Cuando piden conexion, devuelvo una del pool (y la quito del array)
			if (connections.size() > 0)
				c = connections.remove(0);
			else
			{
				c = connect();
				System.out.println("Se acabaron las conexiones disponibles en el pool. Se creo conexion fuera del pool.");
			}
			return c;
		}
	}
	
	public void realeaseConnection(Connection c)
	{
		// Devuelve la conexion al pool (array) para reutilizarla. No se cierra.
		connections.add(c);
	}
}


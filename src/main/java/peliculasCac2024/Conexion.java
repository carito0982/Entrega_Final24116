package peliculasCac2024;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Conexion 
{
	public String driver="com.mysql.cj.jdbc.Driver";
	
	public Connection getConnection() throws ClassNotFoundException
	{
		Connection conexion=null;
		try
		{
			Class.forName(driver);
			conexion=DriverManager.getConnection("jdbc:mysql://localhost:3306/usuario","root","");
			
		}
		catch(SQLException e)
		{
			System.out.println("hay un error: "+e);
		}		
		return conexion;
	}	
	
	

	public static void main(String[] args) throws ClassNotFoundException ,SQLException
	{
		Connection conexion=null;
		Conexion con=new Conexion();
		conexion=con.getConnection();
		
		PreparedStatement ps;
		ResultSet rs;
		
		ps=conexion.prepareStatement("select * from registro");		
		rs=ps.executeQuery();

		while(rs.next())
		{
			String nombre=rs.getString("nombre");
			System.out.println(nombre);			
		}
		
	}

}
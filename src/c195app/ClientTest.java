/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195app;

/**
 *
 * @author jonathankoerber
 */
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



public class ClientTest {
    
	public static void main(String[] args) throws SQLException {
          
		try (Connection connection = dbConnection.getDataSource().getConnection();
				Statement st = connection.createStatement();) {

			String SQL = "SELECT *FROM user";
			ResultSet rs = st.executeQuery(SQL);
			while (rs.next()) {
				int empId = rs.getInt("user_id");
				String uName = rs.getString("userName");
				String uPassword = rs.getString("password");
				

				System.out.println(empId + "\t" + uName  + "\t" + uPassword + "\t");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
    


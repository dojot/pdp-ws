package br.com.cpqd.authorization.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Connect {
	Connection con = null;

	public Connect() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		String url = "jdbc:postgresql://postgres:5432/dojot";
		String user = "postgres";
		String password = "postgres";

		Class.forName("org.postgresql.Driver").newInstance();
		con = DriverManager.getConnection(url, user, password);

		con.setSchema("dojot_authorization");
	}

	public void closeConnection() throws SQLException {
		con.close();
	}

	public Boolean getPermission(String action, String resource, String accessSubject) {
		try {
			PreparedStatement preparedStatement = con
					.prepareStatement("select resource, action from dojot_authorization.authorization where accessSubject = ?");
			preparedStatement.setString(1, accessSubject);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				String resourceAuthorizedPattern = rs.getString("resource");
				String actionAuthorizedPattern = rs.getString("action");


				try {
					Boolean resourceMatch = resource.matches(resourceAuthorizedPattern);
		      Boolean actionMatch = action.matches(actionAuthorizedPattern);
					if (resourceMatch && actionMatch) {
						return true;
					}
				} catch (java.util.regex.PatternSyntaxException ex) {
					Logger lgr = Logger.getLogger(Connect.class.getName());
					lgr.log(Level.SEVERE,"The policy (" + accessSubject + ") can ("	+ actionAuthorizedPattern + ") at resource (" + resourceAuthorizedPattern + ") have an invalid regexPattern");
					continue;
				}
			}

			return false;
		} catch (SQLException ex) {
			Logger lgr = Logger.getLogger(Connect.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
			return false;
		}
	}
}

package br.com.cpqd.authorization.api;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.JSONObject;

import br.com.cpqd.authorization.entity.Result;
import br.com.cpqd.authorization.persistence.Connect;

@Path("/entitlement")
public class PDP {
@POST
	@Path("/decision")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAuthorization(InputStream incomingData) {
		StringBuilder resultBuilder = new StringBuilder();
		
		try {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(incomingData));
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				resultBuilder.append(line);
			}

			// Convert the incomingData to JSON
			JSONObject incomingDataJSON = new JSONObject(resultBuilder.toString());

			// Extract the values of action, resource and accessSubject
			String action = incomingDataJSON.getJSONObject("Request").getJSONObject("Action").getJSONArray("Attribute").getJSONObject(0).getString("Value");
			String resource = incomingDataJSON.getJSONObject("Request").getJSONObject("Resource").getJSONArray("Attribute").getJSONObject(0).getString("Value");
			String accessSubject = incomingDataJSON.getJSONObject("Request").getJSONObject("AccessSubject").getJSONArray("Attribute").getJSONObject(0).getString("Value");

			// Get the permission in Database
			Connect connect = new Connect();
	        Boolean permission = connect.getPermission(action, resource, accessSubject);
	        connect.closeConnection();

	        Result result;
	        
	        if (permission) {
	        	result = new Result("permit");
	        } else {
	        	result = new Result("deny");
	        }
	        
	        return Response.status(200).entity(result).build();	     
		} catch (Exception e) {
			return Response.status(500).entity(e.toString()).build();
		}
	}	
}

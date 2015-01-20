package goal.controller.service.http;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Http {

	public String executeGET(String url) {
		HttpURLConnection connection = null;
		try {
			//Envoi de la requete
			connection = (HttpURLConnection)(new URL(url)).openConnection();
			connection.setUseCaches(false);
			connection.setDoInput(true);
			connection.setDoOutput(true);
			
			//Reception de la reponse
			InputStream inputStream = connection.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			
			//Preparation de la reponse
			String response = "";
			String line = "";
			while((line = bufferedReader.readLine()) != null) {
				response = response + line;
			}
			
			bufferedReader.close();
			return response;
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		finally {
			if(connection != null) {
				connection.disconnect();
			}
		}
	}
	
}

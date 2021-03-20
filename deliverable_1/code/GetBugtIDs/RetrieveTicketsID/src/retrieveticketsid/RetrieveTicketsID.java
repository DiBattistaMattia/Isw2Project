package retrieveticketsid;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class RetrieveTicketsID {

	   static Collection<JSONObject> items;
	   static JSONObject mainNode;
	   static String fields = "fields";

	   private static String readAll(Reader rd) throws IOException {
		      StringBuilder sb = new StringBuilder();
		      int cp;
		      while ((cp = rd.read()) != -1) {
		         sb.append((char) cp);
		      }
		      return sb.toString();
		   }

	   public static JSONArray readJsonArrayFromUrl(String url) throws IOException, JSONException {
	      InputStream is = new URL(url).openStream();
	      try (BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))){
	         String jsonText = readAll(rd);
	         return new JSONArray(jsonText);
	       } finally {
	         is.close();
	       }
	      

	   }

	   public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
	      InputStream is = new URL(url).openStream();
	      try ( BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))){
	         String jsonText = readAll(rd);
	         return new JSONObject(jsonText);
	       } finally {
	         is.close();
	       }
	   }


	   public static void writeJsonFile(JSONObject oldSampleObject) throws Exception {
		    
		   JSONObject sampleObject = new JSONObject();
		   sampleObject.put(fields, oldSampleObject.get(fields));
		   items.add(sampleObject);
		    
		}
	  
	  	@SuppressWarnings("null")
		public static void main(){
	  		   
	  		items = new ArrayList<>();
	  		mainNode = new JSONObject();
	  		String projName ="DAFFODIL";
	  		Integer j = 0;
	  		Integer i = 0;
	  		Integer total = 1;
	  		
	  		//Get JSON API for closed bugs w/ AV in the project
	  		do {
		         //Only gets a max of 1000 at a time, so must do this multiple times if bugs >1000
		         j = i + 1000;
		         String url = "https://issues.apache.org/jira/rest/api/2/search?jql=project=%22"
		                + projName + "%22AND%22issueType%22=%22Bug%22AND(%22status%22=%22closed%22OR"
		                + "%22status%22=%22resolved%22)AND%22resolution%22=%22fixed%22&fields=key,resolutiondate,versions,created&startAt="
		                + i.toString() + "&maxResults=" + j.toString();
		         
		         JSONObject json = null;
				try {
					json = readJsonFromUrl(url);
				} catch (IOException e) {
					e.printStackTrace();
				} catch (JSONException e) {
					e.printStackTrace();
				}
	
		         JSONArray issues = null;
				try {
					issues = json.getJSONArray("issues");
				} catch (JSONException e) {
					e.printStackTrace();
				}
		         try {
					total = json.getInt("total");
				} catch (JSONException e) {
					e.printStackTrace();
				}
		         
		         for (; i < total && i < j; i++) {
		            //Iterate through each bug
		            try {
						Logger logger = null;
						logger.log((LogRecord) issues.getJSONObject(i%1000).get(fields));
					} catch (JSONException e) {
						e.printStackTrace();
					}
		            try {
						writeJsonFile(issues.getJSONObject(i%1000));
					} catch (JSONException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
		          }  
	      
	  		}while (i < total);
	      
			      try {
					mainNode.put("Data", new JSONArray(items));
				} catch (JSONException e) {
					e.printStackTrace();
				}
				  try {
					Files.write(Paths.get("/home/mattia/Desktop/ingegneria_software_2/Falessi/Isw2Project/deliverable_1/data.json"), mainNode.toString().getBytes());
				} catch (IOException e) {
					e.printStackTrace();
				}

	  	}
}

	 
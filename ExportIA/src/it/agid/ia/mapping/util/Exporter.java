package it.agid.ia.mapping.util;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.cedarsoftware.util.io.JsonWriter;
import com.opencsv.CSVReader;

public class Exporter {
	
	public static void main(String[] argv) throws IOException
	{
		CSVReader reader = new CSVReader(new FileReader("ecosistema.csv"), ';');
		
		 String [] nextLine;
		 int dim = 0;
		 
	     JSONObject master = new JSONObject();
         
         master.put("incomplete_results", new Boolean(false));
         
         JSONArray items = new JSONArray();
		 
	     while ((nextLine = reader.readNext()) != null) {

		        JSONObject item = new JSONObject();
		        item.put("id",new Integer(dim)+1);
		        item.put("name", nextLine[1]); // Chi Vuoi Segnalare
		        item.put("full_name", NameExtractor.getDomainName(nextLine[2])); // Nome e Cognome del segnalante
		        
			        JSONObject owner = new JSONObject();
			        owner.put("login",nextLine[6]);
			        owner.put("id",new Integer(dim+100));
			        
			        String pic_url = ImageDownloader.exportPic(nextLine[5], ""+(new Integer(dim)+1));
			        owner.put("avatar_url", pic_url);

			        owner.put("html_url", nextLine[2]);
			        owner.put("type", nextLine[1]);
	        
			    item.put("owner", owner);
			    
			    item.put("html_url",nextLine[2]);
			    item.put("description", nextLine[3]);
			    item.put("created_at", randomDate()+"T18:31:17Z");
			    item.put("updated_at", randomDate()+"T18:31:17Z");
			    item.put("pushed_at", randomDate()+"T18:31:17Z");
			    item.put("homepage", nextLine[2]);
			    item.put("stargazers_count", (int)(Math.random() * 100));
			    item.put("watchers_count", (int)(Math.random() * 100));
			    item.put("forks_count", (int)(Math.random() * 100));
			    item.put("aziende", nextLine[24]+" - "+nextLine[25]+" - "+nextLine[26]);
			    item.put("aziendew", nextLine[27]+" - "+nextLine[28]+" - "+nextLine[29]);
	        
	        items.add(item);
	        
	        dim++;
	        
	     }
	     
	     master.put("total_count", new Integer(dim));
	     master.put("items", items);
	     String formattedJson = JsonWriter.formatJson(master.toJSONString());
	        
	        System.out.println("JSON - Generato");
	        
	        try (FileWriter file = new FileWriter("www/assets/data/dbmapping.json")) {
				file.write("modelData("+formattedJson+");");
	        }
	}
	
	public static String randomDate()
	{
		String ret = "";
		
		Random  rnd;
		Date    dt;
		long    ms;

		rnd = new Random();
		ms = -946771200000L + (Math.abs(rnd.nextLong()) % (70L * 365 * 24 * 60 * 60 * 1000));

		dt = new Date(ms);
		
		SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("YYYY-MM-dd");
        ret = DATE_FORMAT.format(dt);
		
		return ret;
	}

}


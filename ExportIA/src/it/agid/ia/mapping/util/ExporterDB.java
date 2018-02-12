package it.agid.ia.mapping.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringEscapeUtils;

import com.opencsv.CSVReader;
import com.opencsv.RFC4180Parser;
import com.opencsv.RFC4180ParserBuilder;

public class ExporterDB {
	
	private final static String TEMPLATE_DIR = "www/mappinglist/template/";
	private final static String DETAIL_DIR = "www/mappinglist/details/";
	private final static String IMAGE_DIR = "www/mappinglist/loghi/";
	
	private static void creaDettaglio(String id, String nome, String logo, String descrizione, String tecnologie, String tipologia, String sitoweb) throws FileNotFoundException, IOException
	{		
		String html = "";	
		
		try(FileInputStream inputStream = new FileInputStream(TEMPLATE_DIR+"detail.template")) {
			html = IOUtils.toString(inputStream);
		    html = html.replaceAll("\\{\\{NOME\\}\\}", StringEscapeUtils.escapeHtml4(nome));
		    html = html.replaceAll("\\{\\{LOGO\\}\\}", StringEscapeUtils.escapeHtml4(logo));
		    html = html.replaceAll("\\{\\{DESCRIZIONE\\}\\}", StringEscapeUtils.escapeHtml4(descrizione));
		    html = html.replaceAll("\\{\\{TECNOLOGIE\\}\\}", StringEscapeUtils.escapeHtml4(tecnologie));
		    html = html.replaceAll("\\{\\{TIPOLOGIA\\}\\}", StringEscapeUtils.escapeHtml4(tipologia));
		    html = html.replaceAll("\\{\\{SITOWEB\\}\\}", StringEscapeUtils.escapeHtml4(sitoweb));
		}
				
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(
	              new FileOutputStream(DETAIL_DIR+"detail_"+id+".html"), "UTF-8"))) {
				writer.write(html);
		}		
	}
	
	public static void generaDettagli() throws IOException
	{
		FileUtils.cleanDirectory(new File(DETAIL_DIR));
		CSVReader reader = new CSVReader(new InputStreamReader(new FileInputStream("dbmapping.csv")),';');	
		
		 String [] nextLine;
		 
		 while ((nextLine = reader.readNext()) != null) {
			 creaDettaglio(nextLine[0], nextLine[1], findPic(nextLine[0]), nextLine[4], nextLine[9], nextLine[2], nextLine[3]);
		 }
	}
	
	private static String findPic(String id)
	{
		String ret = "";
		
		File dir = new File(IMAGE_DIR);
		File[] foundFiles = dir.listFiles(new FilenameFilter() {
		    public boolean accept(File dir, String name) {
		        return (name.startsWith(id) && name.charAt((name.indexOf(id)+(id.length())))=='_');
		    }
		});

		for (File file : foundFiles) {
			

			return file.getName();
		}
		
		
		
		return ret;
		
	}

	
	public static void generaIndice() throws FileNotFoundException, IOException
	{
		String jsonData = "";		
		FileUtils.deleteQuietly(new File("www/mappinglist/index.html"));
		CSVReader reader = new CSVReader(new FileReader("dbmapping.csv"), ';');
		
		 String [] nextLine;
		 	 
		 while ((nextLine = reader.readNext()) != null) {
			 
				  jsonData = jsonData+ ("{"+
				"id:"+(nextLine[0])+","+
			    "tipologia:\""+(nextLine[2])+"\","+
			    "denominazione:\""+(nextLine[1])+"\","+
			    "website:\""+(nextLine[3])+"\","+
			    "citta:\""+(nextLine[5])+"\","+
			    "regione:\""+(nextLine[6])+"\","+
			    "settore:\""+(nextLine[7])+"\","+
			    "tecnologie:\""+(nextLine[9])+"\"}"
			    		
			    		+ ",\n");	
		 }
		
		String html = "";
		
		try(FileInputStream inputStream = new FileInputStream(TEMPLATE_DIR+"index.template")) {
			html = IOUtils.toString(inputStream);
		    html = html.replaceAll("\\{\\{DATI\\}\\}", jsonData);
		}
				
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(
	              new FileOutputStream("www/mappinglist/index.html")))) {
				writer.write(html);
		}
	}

	public static void main(String[] args) throws IOException {
		generaIndice();
		generaDettagli();
		
	}

}

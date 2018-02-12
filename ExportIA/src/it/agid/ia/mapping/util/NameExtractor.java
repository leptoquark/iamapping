package it.agid.ia.mapping.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.regex.Pattern;

public class NameExtractor
{
	public static void main (String[] argv) throws MalformedURLException
	{
		System.out.println("START");
		
		System.out.println(getDomainName("https://spitch.ch/it/"));
		System.out.println(getDomainName("http://www.dentsuaegisnetwork.it/"));
		
		System.out.println("END");
	}
	
	public static String getDomainName(String urlName) {
		URL url;
		try {
			url = new URL(urlName);
		
			String strDomain;
			String[] strhost = url.getHost().split(Pattern.quote("."));
			String[] strTLD = {"com","org","net","int","edu","gov","mil","arpa","ch","it"};
	
			if(Arrays.asList(strTLD).indexOf(strhost[strhost.length-1])>=0)
			    strDomain = strhost[strhost.length-2]+"."+strhost[strhost.length-1];
			else if(strhost.length>2)
			    strDomain = strhost[strhost.length-3]+"."+strhost[strhost.length-2]+"."+strhost[strhost.length-1];
			else
			    strDomain = strhost[strhost.length-2]+"."+strhost[strhost.length-1];
			return strDomain;
		} catch (MalformedURLException e) {

		}
		return urlName;
	}
}

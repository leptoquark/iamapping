package it.agid.ia.mapping.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
 
public class ImageDownloader
{
	// gestire pdf
	
	
    public static String exportPic(String url_path, String name)
    {
        BufferedImage image =null;
        String extension = "";
        
        String ret = "";
        
        try{
        	extension = "";

        	int i = url_path.lastIndexOf('.');
        	int p = Math.max(url_path.lastIndexOf('/'), url_path.lastIndexOf('\\'));

        	if (i > p) {
        	    extension = url_path.substring(i+1);
        	}
        	
        	String res = "";
        	for (int j=0; j<extension.length(); j++)
        	{
        		char aux = extension.charAt(j);
        		aux = Character.toLowerCase(aux);
        		if (aux>'a' && aux<'z')
        			res = res + extension.charAt(j);
        		else
        			break;
        	}
        	
        	extension = res;
        	
            URL url =new URL(url_path);
            image = ImageIO.read(url);
            ImageIO.write(image, extension,new File("www/assets/data/ia_pic/"+name+"."+extension));
            
           // System.out.println(name+" - url_path:"+url_path+" -> type: "+extension);
            
             ret = "assets/data/ia_pic/"+name+"."+extension;
 
        }catch(Exception e){
        	//System.err.println(name+" - url_path:"+url_path+" -> type: "+extension);
            //ret = url_path;
            ret = "assets/data/ia_pic/agid.png";
        }
        
       // System.out.println("ret: "+ret);
        

        return ret;
    }}
package com.iwinnerapps.rss.feed;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class RssFeed {

public static void main(String[] args) {
			System.out.println(readRSS("http://rss.cnn.com/rss/edition.rss"));
		//System.out.println(readRSS("https://news.google.com/news?ned=in&topic=h&output=rss"));
}	
public static String readRSS(String urlName){
	String sourceCode=null;
	try {
		URL url=new URL(urlName);
		try {
			BufferedReader br=new BufferedReader(new InputStreamReader(url.openStream()));
			String line;
			//<title>White House hosts Japan's PM Abe</title>
 			while((line=br.readLine())!=null){
            if(line.contains("<title>")){
            	int firstPost=line.indexOf("<title>");
            	String temp=line.substring(firstPost);
            	temp=temp.replace("<title>", "");
            	int lastPost=temp.indexOf("</title>");
            	temp=temp.substring(0,lastPost);
            	sourceCode+=temp+"\n";
            }
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	} catch (MalformedURLException e) {
		e.printStackTrace();
	}
	
	return sourceCode;
}
}

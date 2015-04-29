package com.changingworlds.newua;

import java.io.BufferedReader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class NewUADetector
  implements Job
{
  Logger jdField_int = Logger.getLogger(NewUADetector.class);
  private String a;
  private String jdField_byte;
  private String jdField_char;
  private String jdField_long;
  private String jdField_new;
  private String jdField_goto;
  private String jdField_void;
  private String jdField_case;
  private String jdField_for;
  private String jdField_try;
  private String jdField_do;
  private String jdField_if;
  private String jdField_else;
  
  public void execute(JobExecutionContext paramJobExecutionContext)
    throws JobExecutionException
  {
    Object localObject1 = new ArrayList();
    Object localObject2 = new ArrayList();
    
    long l1 = System.currentTimeMillis();
    
    loadConfigFile();
    
/*// a -->remoteUser -->cwapps
   //jdFiled_byte--remotePassword===>cwapps
*/    
 /*   this.a = PropertiesConfigurator.getProperties("sitemap.remoteUser");
    this.jdField_byte = PropertiesConfigurator.getProperties("sitemap.remotePassword");
    this.jdField_char = PropertiesConfigurator.getProperties("sitemap.host");
    this.jdField_long = PropertiesConfigurator.getProperties("sitemap.remoteFileName");
    this.jdField_new = PropertiesConfigurator.getProperties("sitemap.localFileName");
    this.jdField_goto = PropertiesConfigurator.getProperties("csvHandset.remoteUser");
    this.jdField_void = PropertiesConfigurator.getProperties("csvHandset.remotePassword");
    this.jdField_case = PropertiesConfigurator.getProperties("csvHandset.host");
    this.jdField_for = PropertiesConfigurator.getProperties("csvHandset.remoteFilePath");
    this.jdField_try = PropertiesConfigurator.getProperties("csvHandset.remoteFileName");
    this.jdField_do = PropertiesConfigurator.getProperties("csvHandset.localFileName");
    this.jdField_if = PropertiesConfigurator.getProperties("local.workingDirectory");
    this.jdField_else = PropertiesConfigurator.getProperties("local.outputDirectory");
 */    
    CopyFile localCopyFile1 = new CopyFile(this.a, this.jdField_byte, this.jdField_char, this.jdField_long, this.jdField_if + File.separator + this.jdField_new);
    localCopyFile1.scpCopyFile();
    

    String str = genCSVCacheFileName(-2);
    CopyFile localCopyFile2 = new CopyFile(this.jdField_goto, this.jdField_void, this.jdField_case, str, this.jdField_if + File.separator + this.jdField_do);
    localCopyFile2.scpCopyFile();
    
    localObject1 = readSiteMapUA();
    localObject2 = readBimCSVHandset();
    generateNewUAFile((List)localObject1, (List)localObject2);
    
    long l2 = System.currentTimeMillis();
    
    this.jdField_int.debug("This execution took : " + (float)(l2 - l1) / 1000.0F + " seconds");
  }
  
  public void loadConfigFile()
  {
    try
    {
      PropertiesConfigurator.init(new File("newuadata/config.properties"));
      
      this.a = PropertiesConfigurator.getProperties("sitemap.remoteUser");
      this.jdField_byte = PropertiesConfigurator.getProperties("sitemap.remotePassword");
      this.jdField_char = PropertiesConfigurator.getProperties("sitemap.host");
      this.jdField_long = PropertiesConfigurator.getProperties("sitemap.remoteFileName");
      this.jdField_new = PropertiesConfigurator.getProperties("sitemap.localFileName");
      this.jdField_goto = PropertiesConfigurator.getProperties("csvHandset.remoteUser");
      this.jdField_void = PropertiesConfigurator.getProperties("csvHandset.remotePassword");
      this.jdField_case = PropertiesConfigurator.getProperties("csvHandset.host");
      this.jdField_for = PropertiesConfigurator.getProperties("csvHandset.remoteFilePath");
      this.jdField_try = PropertiesConfigurator.getProperties("csvHandset.remoteFileName");
      this.jdField_do = PropertiesConfigurator.getProperties("csvHandset.localFileName");
      this.jdField_if = PropertiesConfigurator.getProperties("local.workingDirectory");
      this.jdField_else = PropertiesConfigurator.getProperties("local.outputDirectory");
    }
    catch (Exception localException)
    {
      this.jdField_int.error(localException);
      localException.printStackTrace();
    }
  }
  
  public String genCSVCacheFileName(int paramInt)
  {
    String str1 = "";
    String str2 = "";
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyyMMdd");
    
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.add(5, paramInt);
    str2 = localSimpleDateFormat.format(localCalendar.getTime());
    
    str1 = this.jdField_for + File.separator + str2 + File.separator + this.jdField_try;
    
    return str1;
  }
  
  public void generateNewUAFile(List paramList1, List paramList2)
  {
    String str1 = "";
    String str2 = "";
    int i = 0;
    List<String> localArrayList = new ArrayList();
    try
    {
      for (int j = 0; j < paramList2.size(); j++)
      {
        str1 = ((String)paramList2.get(j)).replaceAll(" ", "");
        for (int k = 0; k < paramList1.size(); k++)
        {
          str2 = (String)paramList1.get(k);
          if (str1.contains(str2)) {
            i = 1;
          }
        }
        if (i == 0) {
          localArrayList.add(str1);
        }
        i = 0;
      }
      SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyyMMdd");
      Date localDate = Calendar.getInstance().getTime();
      String str3 = "newUA_" + localSimpleDateFormat.format(localDate) + ".txt";
      
      this.jdField_int.debug("Generating final output to location : " + this.jdField_else + File.separator + str3);
      
      File localFile = new File(this.jdField_else + File.separator + str3);
      if (!localFile.exists()) {
        localFile.createNewFile();
      }
      FileWriter localFileWriter = new FileWriter(localFile);
      
      int m = 82240;
      BufferedWriter localBufferedWriter = new BufferedWriter(localFileWriter, m);
      
      this.jdField_int.debug("Total handset not found in sitemap = " + localArrayList.size());
      for (String str4 : localArrayList)
      {
        localBufferedWriter.write(str4);
        localBufferedWriter.write("\n");
      }
      localBufferedWriter.flush();
      localBufferedWriter.close();
    }
    catch (Exception localException)
    {
      this.jdField_int.debug("Exception while generating final output file : " + localException);
      localException.printStackTrace();
    }
  }
  
  public List readBimCSVHandset()
  {
    String str1 = "";
    
    int i = 0;
    int j = 0;
    ArrayList localArrayList = new ArrayList();
    try
    {
      BufferedReader localBufferedReader = new BufferedReader(new FileReader(this.jdField_if + File.separator + this.jdField_do));
      String str2 = "";
      int k = 0;
      while (((str2 = localBufferedReader.readLine()) != null) && (str2.length() > 0))
      {
        k++;
        if (k > 1) {
          if (str2.indexOf('"') == 0)
          {
            i = str2.substring(1, str2.length()).indexOf('"');
            str1 = str2.substring(1, i + 1);
            localArrayList.add(str1);
          }
          else
          {
            j = str2.indexOf(',');
            str1 = str2.substring(0, j);
            localArrayList.add(str1);
          }
        }
        str1 = "";
      }
    }
    catch (Exception localException)
    {
      this.jdField_int.error("Exception while reading csv file: " + localException);
    }
    this.jdField_int.debug("handsetArrayList size = " + localArrayList.size());
    return localArrayList;
  }
  
  public List readSiteMapUA()
  {
    ArrayList localArrayList = new ArrayList();
    try
    {
      DocumentBuilderFactory localDocumentBuilderFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder localObject = localDocumentBuilderFactory.newDocumentBuilder();
      Document localDocument = ((DocumentBuilder)localObject).parse(new File(this.jdField_if + File.separator + this.jdField_new));
      localDocument.getDocumentElement().normalize();

      NodeList localNodeList1 = localDocument.getElementsByTagName("map:selectors");
      int i = localNodeList1.getLength();
      if (i > 0)
      {
        Node localNode1 = localNodeList1.item(0);
        if (localNode1.getNodeType() == 1)
        {
          Element localElement1 = (Element)localNode1;
          NodeList localNodeList2 = localElement1.getElementsByTagName("map:selector");
          for (int j = 0; j < localNodeList2.getLength(); j++)
          {
            Node localNode2 = localNodeList2.item(j);
            if (localNode2.getNodeType() == 1)
            {
              Element localElement2 = (Element)localNode2;
              
              String str1 = localElement2.getAttribute("name");
              if (str1.equalsIgnoreCase("browser"))
              {
                NodeList localNodeList3 = localElement2.getElementsByTagName("browser");
                for (int k = 0; k < localNodeList3.getLength(); k++)
                {
                  Node localNode3 = localNodeList3.item(k);
                  if (localNode3.getNodeType() == 1)
                  {
                    Element localElement3 = (Element)localNode3;
                    String str2 = localElement3.getAttribute("useragent");
                    if ((str2 != null) && (!str2.equalsIgnoreCase(""))) {
                      localArrayList.add(str2);
                    }
                  }
                }
                this.jdField_int.debug("browserArrayList size = " + localArrayList.size());
              }
            }
          }
        }
      }
    }
    catch (SAXParseException localSAXParseException)
    {
      this.jdField_int.error("** Parsing error, line " + localSAXParseException.getLineNumber() + ", uri " + localSAXParseException.getSystemId());
      this.jdField_int.error(" " + localSAXParseException.getMessage());
    }
    catch (SAXException localSAXException)
    {
      this.jdField_int.error(localSAXException);
      Object localObject = localSAXException.getException();
      (localObject == null ? localSAXException : (Exception)localObject).printStackTrace();
    }
    catch (Exception localException)
    {
      this.jdField_int.error(localException);
      localException.printStackTrace();
    }
    return localArrayList;
  }
}

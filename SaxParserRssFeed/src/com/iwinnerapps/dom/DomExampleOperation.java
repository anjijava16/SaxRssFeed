package com.iwinnerapps.dom;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.iwinner.wap.operations.utils.OperationConstants;
import com.iwinner.wap.operations.utils.WsUtils;
import com.iwinner.wap.operations.vo.AditForm;

public class DomExampleOperation {

private static Logger LOGGER=Logger.getLogger(DomExampleOperation.class);

public AditForm getAditOperation(){
	LOGGER.info("##### Enter into the getAditOperation() #####");
	AditForm aditForm=new AditForm();
	String response=OperationConstants.ADIT_DEFAULT;
	 try {
			PostMethod method = new PostMethod(WsUtils.getAdItService());
			RequestEntity req = new StringRequestEntity(WsUtils.getAditRequest());
			method.setRequestEntity(req);
			HttpClient client = new HttpClient();
			client.getHttpConnectionManager().getParams().setSoTimeout(10000);
			 int returnCode = client.executeMethod(method);
			 if(returnCode==200){
				 response = method.getResponseBodyAsString();
			 }

			 int firstIndex = response.indexOf("<media>");
			   int lastIndex = response.indexOf("</response>");
			  if ((firstIndex != -1) && (lastIndex != -1) && (response != null))
			  {
				  response = response.substring(firstIndex, lastIndex);
			  }
			  File file=new File(OperationConstants.SOURCE_XML_FILE);
				if (!file.exists()) {
					file.createNewFile();
				}
				FileWriter fw = new FileWriter(file.getAbsoluteFile());
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(response);
				bw.close();
				aditForm=processingXML(file);
	 } catch (Exception e) {
		LOGGER.error("##### Error into the getAditOperation() #####"+e.getMessage());
		e.printStackTrace();
	}
	 LOGGER.info("##### Exit into the getAditOperation() #####");
	return aditForm;
}

public static AditForm processingXML(File file){
	
	AditForm adForm=new AditForm();
	try {
		  DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		  DocumentBuilder db = dbf.newDocumentBuilder();
		  Document doc = db.parse(file);
		  doc.getDocumentElement().normalize();
		  NodeList nodeLst = doc.getElementsByTagName("media");
		  for (int s = 0; s < nodeLst.getLength(); s++) {
			  Node fstNode = nodeLst.item(s);
			  Element fstElmnt = (Element) fstNode;
		      adForm.setAdId(fstElmnt.getElementsByTagName("adId").item(0).getChildNodes().item(0).getNodeValue());
		      adForm.setAdType(fstElmnt.getElementsByTagName("adType").item(0).getChildNodes().item(0).getNodeValue());
		      adForm.setChannelType(fstElmnt.getElementsByTagName("channelType").item(0).getChildNodes().item(0).getNodeValue());
		      adForm.setMediaName(fstElmnt.getElementsByTagName("mediaName").item(0).getChildNodes().item(0).getNodeValue());
              adForm.setMediaSize(fstElmnt.getElementsByTagName("mediaSize").item(0).getChildNodes().item(0).getNodeValue());
              adForm.setMediaUri(fstElmnt.getElementsByTagName("mediaUri").item(0).getChildNodes().item(0).getNodeValue());
              adForm.setCountMediaUri(fstElmnt.getElementsByTagName("countMediaUri").item(0).getChildNodes().item(0).getNodeValue());
              adForm.setDiscountMediaUri(fstElmnt.getElementsByTagName("discountMediaUri").item(0).getChildNodes().item(0).getNodeValue());
              adForm.setIndirectMediaUri(fstElmnt.getElementsByTagName("indirectMediaUri").item(0).getChildNodes().item(0).getNodeValue());
              adForm.setMediaClickDestinationUri(fstElmnt.getElementsByTagName("mediaClickDestinationUri").item(0).getChildNodes().item(0).getNodeValue());
              adForm.setAlternativeText(fstElmnt.getElementsByTagName("alternativeText").item(0).getChildNodes().item(0).getNodeValue());
              adForm.setAdTextClickDestinationUri(fstElmnt.getElementsByTagName("adTextClickDestinationUri").item(0).getChildNodes().item(0).getNodeValue());
              adForm.setAdText(fstElmnt.getElementsByTagName("adText").item(0).getChildNodes().item(0).getNodeValue());
		  }
		  } catch (Exception e) {
		    e.printStackTrace();
		  }
	return adForm;
}
}

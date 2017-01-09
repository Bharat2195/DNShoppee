package com.dnshoppee;

import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutionException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

public class XMLParser1 {
	// constructor
	ProgressDialog pDialog;
	GetResult2 xmlprs;
	public XMLParser1() 
	{
	}

	/**
	 * Getting XML from URL making HTTP request
	 * @param url string
	 * */
	public String getXmlFromUrl(String url) {
		String xml = null;
		try {
		 xmlprs=new GetResult2();
			xml=xmlprs.execute(url).get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// return XML
		return xml;
	}
	
	/**
	 * Getting XML DOM element
	 * @param XML string
	 * */
	public Document getDomElement(String xml){
		Document doc = null;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {

			DocumentBuilder db = dbf.newDocumentBuilder();

			InputSource is = new InputSource();
		        is.setCharacterStream(new StringReader(xml));
		        doc = db.parse(is); 

			} catch (ParserConfigurationException e) {
				Log.e("Error1: ", e.getMessage());
				return null;
			} catch (SAXException e) {
				Log.e("Error2: ", e.getMessage());
	            return null;
			} catch (IOException e) {
				Log.e("Error3: ", e.getMessage());
				return null;
			}

	        return doc;
	}
	
	/** Getting node value
	  * @param elem element
	  */
	 public final String getElementValue( Node elem ) {
	     Node child;
	     if( elem != null){
	         if (elem.hasChildNodes()){
	             for( child = elem.getFirstChild(); child != null; child = child.getNextSibling() ){
	                 if( child.getNodeType() == Node.TEXT_NODE  ){
	                     return child.getNodeValue();
	                 }
	             }
	         }
	     }
	     return "";
	 }
	 
	 /**
	  * Getting node value
	  * @param Element node
	  * @param key string
	  * */
	 public String getValue(Element item, String str) {		
			NodeList n = item.getElementsByTagName(str);		
			return this.getElementValue(n.item(0));
		}
	 
	 private class GetResult2 extends AsyncTask<String, Void, String> 
		{
			String line = " ";
			String line2 = " ";
			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				
			}


		    @Override
		    protected String doInBackground(String... urls) {
		        Log.v( ".doInBackground", "doInBackground method call");
		   
		        try {
		        	DefaultHttpClient httpClient = new DefaultHttpClient();
		            HttpPost httpPost = new HttpPost(urls[0]);
		            HttpResponse httpResponse = httpClient.execute(httpPost);
		            HttpEntity httpEntity = httpResponse.getEntity();
		            line = EntityUtils.toString(httpEntity);
		            Log.v("TAG 1", line);
		            String s[]=line.split("<!DOCTYPE");
		            line2=s[0];
		            Log.d("TAG XML", line2);
		            
		        } catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				
				}
		        
		        	return line2;
		       

		    }

		   

		    protected void onPostExecute(String result) 
		    {
		    super.onPostExecute(result) ;
		    
		    xmlprs.cancel(false);
			}
		}
}

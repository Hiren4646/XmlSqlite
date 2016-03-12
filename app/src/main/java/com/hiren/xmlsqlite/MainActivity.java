package com.hiren.xmlsqlite;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;



import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.Menu;
import android.widget.EditText;
import android.widget.RelativeLayout;

public class MainActivity extends Activity {
ArrayList<Bean> list=null;
static Context ct;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ct=getApplicationContext();
		new initDb().execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public class initDb extends AsyncTask<Void, Void, Void> {

		ProgressDialog   progressdialog  = new ProgressDialog(MainActivity.this);






		@Override
		protected void onPreExecute() {
			//progressdialog.setIndeterminateDrawable(getResources().getDrawable(R.drawable.progerss));
			progressdialog.setIndeterminate(true);
			progressdialog.setMessage("Conectando.......");
			progressdialog.show();
		}


		@Override
		protected Void doInBackground(Void... voids) {
			
list=writeMap("http://www.cicyzone.com/Lyrictest/Xmlstream.aspx?ID=61836&T=Bad+girl&ADJ=0&DB=ce");
System.out.println("********************"+list.size());
			return null;


		}

		@Override
		protected void onPostExecute(Void unused) {
			// Pass the result data back to the main activity

			for(int i=0;i<list.size();i++){
				System.out.println("********************"+list.get(i).getCaption());
			}
		
			progressdialog.dismiss();
		}

	} 

	
	
	
	public static ArrayList<Bean> writeMap(String urlForService) 
	{
		ArrayList<Bean> map = new ArrayList<Bean>();
		Bean maplist;

		try {

			URL u = new URL(urlForService); 

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder =dbf.newDocumentBuilder(); 

			Document doc = builder.parse(new InputSource(u.openStream()));
			 DocumentBuilderFactory mDocumentBuilderFactory = DocumentBuilderFactory
	                    .newInstance();
	            DocumentBuilder mDocumentBuilder = mDocumentBuilderFactory
	                    .newDocumentBuilder();
	            Document mDocument = mDocumentBuilder.parse(new InputSource(ct.getAssets().open("test.xml")));
			NodeList node=doc.getElementsByTagName("captions");
			System.out.println("*************************"+node.getLength());
			for(int i=0;i<node.getLength();i++)
			{	
				Element element=(Element)node.item(i);
				
				
				System.out.println("*************************"+element.getAttribute("caption"));
				
				
				//map.add(maplist);

			}

		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		return map;
	}
	
	
	
	
	
	
	
	
	
	
	private static String getCharacterDataFromElement(Element e) { 
		try { 
			Node child = e.getFirstChild(); 
			if(child instanceof CharacterData) { 
				CharacterData cd = (CharacterData) child; 
				return cd.getData(); 
			} 
		}catch(Exception ex) {} 
		return "";
	}
	protected float getFloat(String value) { 
		if(value != null && !value.equals(""))  
			return Float.parseFloat(value); 
		else 
			return 0; 
	} 
	protected static String getElementValue(Element parent,String label) { 
		return getCharacterDataFromElement((Element)parent.getElementsByTagName(label).item(0)); 
	}


}

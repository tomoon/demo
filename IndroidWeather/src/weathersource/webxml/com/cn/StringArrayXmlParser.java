package weathersource.webxml.com.cn;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class StringArrayXmlParser {
	private String mRes;
	
	public StringArrayXmlParser(String res){
		mRes = res;
	}
	
	public List<String> parser(){
		List<String> list = new ArrayList<String>();
		SAXParserFactory spf = SAXParserFactory.newInstance();
		try {
			SAXParser sp = spf.newSAXParser();
			CityXmlHandler handler = new CityXmlHandler();
			sp.parse(new ByteArrayInputStream(mRes.getBytes()), handler);
			list = handler.getResult();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return list;
	}
	
	private class CityXmlHandler extends DefaultHandler{
		final static String STRING = "string";
		List<String> list = new ArrayList<String>();
		boolean read = false;
		String tmp;
		
		public List<String> getResult(){
			return list;
		}

		@Override
		public void characters(char[] ch, int start, int length)
				throws SAXException {
			tmp += new String(ch, start, length);
		}

		@Override
		public void endElement(String uri, String localName, String qName)
				throws SAXException {
			if(STRING.equals(localName)){
				list.add(tmp);
				read = false;
			}
		}

		@Override
		public void startElement(String uri, String localName, String qName,
				Attributes attributes) throws SAXException {
			if(STRING.equals(localName)){
				tmp = "";
				read = true;
			}
		}

		
	}
}

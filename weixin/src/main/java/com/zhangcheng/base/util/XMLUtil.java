package com.zhangcheng.base.util;  

import java.io.ByteArrayOutputStream;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
  
public class XMLUtil {
	public static String getValueByTagName(Document doc, String tagName){  
        if(doc == null || !StringUtil.notEmpty(tagName)){  
            return "";  
        }  
        NodeList pl = doc.getElementsByTagName(tagName);  
        if(pl != null && pl.getLength() > 0){  
            return  pl.item(0).getTextContent();  
        }  
        return "";  
    } 
	
    //XML转字符串   原样取出  
    public static String getXmlString(Document doc){  
        TransformerFactory tf = TransformerFactory.newInstance();  
        try {  
            Transformer t = tf.newTransformer();  
            t.setOutputProperty(OutputKeys.ENCODING,"UTF-8");//解决中文问题，试过用GBK不行  
            t.setOutputProperty(OutputKeys.METHOD, "xml");    
            t.setOutputProperty(OutputKeys.VERSION, "4.0");    
            t.setOutputProperty(OutputKeys.INDENT, "yes");  
            t.setOutputProperty(OutputKeys.CDATA_SECTION_ELEMENTS, "yes");  
            ByteArrayOutputStream bos = new ByteArrayOutputStream();  
            t.transform(new DOMSource(doc), new StreamResult(bos));  
            return bos.toString();  
        } catch (TransformerConfigurationException e) {  
            e.printStackTrace();  
        } catch (TransformerException e) {  
            e.printStackTrace();  
        }  
        return "";  
    }  
}

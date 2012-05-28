package com.klik.core.datainit;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

import java.util.Arrays;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.klik.core.service.IUrlPatternService;


@Component("urlPatternDataInit")
public class AutoUrlPatternDataInit {
	
	@Autowired
	IUrlPatternService urlPatternService;
	
	public void initData() throws Exception{
		InputStream in = AutoUrlPatternDataInit.class.getResourceAsStream("/auto_url_init.txt");
		String content=stream2String(in,"utf-8");
		for(String line:content.split("\n")){
			line=line.trim();
			if (line.length()==0) continue;
			String[] items=line.split(",");
			String[] categories=Arrays.copyOfRange(items, 1, items.length);
			urlPatternService.addPattern(items[0], categories);
		}
	}
	
	 /** 
     * 文件转换为字符串 
     * 
     * @param in            字节流 
     * @param charset 文件的字符集 
     * @return 文件内容 
     */ 
    public  String stream2String(InputStream in, String charset) { 
            StringBuffer sb = new StringBuffer(); 
            try { 
                    Reader r = new InputStreamReader(in, charset); 
                    int length = 0; 
                    for (char[] c = new char[1024]; (length = r.read(c)) != -1;) { 
                            sb.append(c, 0, length); 
                    } 
                    r.close(); 
            } catch (UnsupportedEncodingException e) { 
                    e.printStackTrace(); 
            } catch (FileNotFoundException e) { 
                    e.printStackTrace(); 
            } catch (IOException e) { 
                    e.printStackTrace(); 
            } 
            return sb.toString(); 
    } 
	
	
	
	public static void main(String[] args)  throws Exception{
		AbstractApplicationContext ctx= new ClassPathXmlApplicationContext("classpath:spring/*.xml");
		ctx.registerShutdownHook();
		AutoUrlPatternDataInit urlPatternDataInit=(AutoUrlPatternDataInit)ctx.getBean("urlPatternDataInit");
		urlPatternDataInit.initData();
	}

}

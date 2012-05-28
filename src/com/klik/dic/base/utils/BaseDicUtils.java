package com.klik.dic.base.utils;

import java.io.File;
import java.io.RandomAccessFile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.klik.dic.base.dao.IBaseDicDao;
import com.klik.dic.base.dao.IBaseSynDicDao;
import com.klik.dic.base.mo.BaseDicCompositeColumn;
import com.netflix.astyanax.model.Row;
import com.netflix.astyanax.model.Rows;


@Component("baseDicUtils")
public class BaseDicUtils {
	
	@Autowired
	IBaseDicDao baseDicDao;
	@Autowired
	IBaseSynDicDao baseSynDicDao;
	
	public void makeCommodityDicFile(String fileName) throws Exception{
		
		String path=this.getClass().getClassLoader().getResource("").getPath().replace("classes/", "")+fileName;
		
		File file=new File(path);
		if(file.exists()){
			file.delete();
		}
		
		RandomAccessFile raf=new RandomAccessFile(path, "rw");
		int i=0;
		
		
		Rows<String, BaseDicCompositeColumn> rows=baseDicDao.getAllKeys();
		for(Row<String, BaseDicCompositeColumn> row:rows){
			String key=row.getKey();
			raf.write((key+"\n").getBytes("UTF-8"));
			raf.seek(raf.length());
			i++;
			System.out.println(i+key);
		}
		Rows<String,String> synWords=baseSynDicDao.getAllKeys();
		for(Row<String, String> row:synWords){
			String key=row.getKey();
			raf.write((key+"\n").getBytes("UTF-8"));
			raf.seek(raf.length());
			i++;
			System.out.println(i+key);
		}
		
		raf.close();
		
		System.out.println("Dic File make Complete: "+path);
	}
	
	public static void main(String[] args) throws Exception{
		AbstractApplicationContext ctx= new ClassPathXmlApplicationContext("classpath:spring/*.xml");
		ctx.registerShutdownHook();
		BaseDicUtils utils=(BaseDicUtils)ctx.getBean("baseDicUtils");
		utils.makeCommodityDicFile("baseDic.dic");
	}
	
}

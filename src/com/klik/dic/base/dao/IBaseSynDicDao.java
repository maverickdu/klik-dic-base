package com.klik.dic.base.dao;

import java.util.Map.Entry;

import com.netflix.astyanax.model.Rows;

public interface IBaseSynDicDao {
	public Entry<String, Integer> hasSynWord(String key) throws Exception;
	
	public Rows<String, String> getAllKeys() throws Exception;
	
}

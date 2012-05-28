package com.klik.dic.base.dao;

import java.util.Map.Entry;

import com.klik.dic.base.mo.BaseDicCompositeColumn;
import com.klik.dic.base.mo.BaseWord;
import com.netflix.astyanax.model.Rows;

public interface IBaseDicDao {
	public Integer getAmbigutyCount(String key) throws Exception;
	public BaseWord getBaseWord(String key) throws Exception;
	public BaseWord getBaseWord(String key,Integer ambigutyIdx) throws Exception;
	public Entry<String, Integer> getSynWord(String key) throws Exception;
	
	public Rows<String, BaseDicCompositeColumn>  getAllKeys() throws Exception;
	
}

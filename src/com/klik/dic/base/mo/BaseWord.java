package com.klik.dic.base.mo;

import java.util.HashMap;

public class BaseWord {
	
	
	private String key;

	private HashMap<Integer, String> categories;
	private HashMap<Integer, String> relateWords;
	private HashMap<Integer, String> commodityRelateWords;
	
	private Integer ambiguityCount;
	
	
	public BaseWord(){
		categories = new HashMap<Integer, String>();
		relateWords= new HashMap<Integer, String>();
		commodityRelateWords = new HashMap<Integer, String>();
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public HashMap<Integer, String> getCategories() {
		return categories;
	}
	public void addCategories(Integer idx,String categories){
		this.categories.put(idx, categories);
	}
	

	public HashMap<Integer, String> getRelateWords() {
		return relateWords;
	}
	public void addRelateWords(Integer idx,String relateWords){
		this.relateWords.put(idx, relateWords);
	}
	

	public HashMap<Integer, String> getCommodityRelateWords() {
		return commodityRelateWords;
	}
	public void addCommodityRelateWords(Integer idx,String relateWords){
		this.commodityRelateWords.put(idx, relateWords);
	}
	
	public Integer getAmbiguityCount() {
		return ambiguityCount;
	}

	public void setAmbiguityCount(Integer ambiguityCount) {
		this.ambiguityCount = ambiguityCount;
	}

	public boolean isWithAmbiguity() {
		return (relateWords.size()>1);
	}

	
	
	
}

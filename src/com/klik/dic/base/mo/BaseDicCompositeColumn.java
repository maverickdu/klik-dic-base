package com.klik.dic.base.mo;

import com.netflix.astyanax.annotations.Component;

public class BaseDicCompositeColumn {
	
	public static final String _COL_COMMODITY_RELATE="COMMODITY_RELATE";
	public static final String _COL_RELATE_WORD="RELATE_WORD";
	public static final String _COL_CATEGOIES="CATEGORIES";
	
	
	private @Component(ordinal=0) Integer ambiguityIdx;
	private @Component(ordinal=0) String  colName;
	
	public BaseDicCompositeColumn(){
		
	}
	public BaseDicCompositeColumn(Integer ambiguityIdx,String  colName){
		this.ambiguityIdx=ambiguityIdx;
		this.colName=colName;
	}
	
	public Integer getAmbiguityIdx() {
		return ambiguityIdx;
	}
	public void setAmbugityIdx(Integer ambiguityIdx) {
		this.ambiguityIdx = ambiguityIdx;
	}
	public String getColName() {
		return colName;
	}
	public void setColName(String colName) {
		this.colName = colName;
	}
	
	
	
}

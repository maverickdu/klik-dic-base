package com.klik.dic.base.dao.imp;

import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.klik.dic.base.dao.IBaseDicDao;
import com.klik.dic.base.dao.IBaseSynDicDao;
import com.klik.dic.base.mo.BaseDicCompositeColumn;
import com.klik.dic.base.mo.BaseWord;
import com.netflix.astyanax.Keyspace;
import com.netflix.astyanax.connectionpool.OperationResult;
import com.netflix.astyanax.model.Column;
import com.netflix.astyanax.model.ColumnFamily;
import com.netflix.astyanax.model.ColumnList;
import com.netflix.astyanax.model.Rows;
import com.netflix.astyanax.serializers.AnnotatedCompositeSerializer;
import com.netflix.astyanax.serializers.StringSerializer;

@Component("baseDicDao")
public class BaseDicDaoImp implements IBaseDicDao, InitializingBean {
	
	@Autowired
	protected Keyspace keyspace;
	@Autowired
	protected IBaseSynDicDao baseSynDicDao;
	
	
	protected ColumnFamily<String,BaseDicCompositeColumn> cf;
	
	Logger logger=LoggerFactory.getLogger(this.getClass());
	
	@Override
	public void afterPropertiesSet() throws Exception {
		AnnotatedCompositeSerializer<BaseDicCompositeColumn> baseDicSerializer
			=new AnnotatedCompositeSerializer<BaseDicCompositeColumn>(BaseDicCompositeColumn.class);
		cf = new ColumnFamily<String,BaseDicCompositeColumn>("BASE_DIC",
				StringSerializer.get(),baseDicSerializer);
	}
	
	
	@Override
	public Integer getAmbigutyCount(String key) throws Exception{
		
		Integer count=0;
		BaseDicCompositeColumn startColumn=new BaseDicCompositeColumn(0,"0");
		BaseDicCompositeColumn endColumn=new BaseDicCompositeColumn(Integer.MAX_VALUE,"þ");
		ColumnList<BaseDicCompositeColumn> cols=keyspace.prepareQuery(cf).getKey(key).withColumnRange(endColumn, startColumn, true, 1).execute().getResult();
		if (cols.size()>0){
			count=cols.getColumnByIndex(0).getName().getAmbiguityIdx();
		}
		return count;
	}
	
	@Override
	public BaseWord getBaseWord(String key) throws Exception{
		Entry<String, Integer> synWord=getSynWord(key);
		if (synWord!=null){
			return getBaseWord(synWord.getKey(),synWord.getValue());
		}
		return getBaseWord(key,null);
	}
	
	@Override
	public BaseWord getBaseWord(String key,Integer ambigutyIdx) throws Exception{
		Entry<String, Integer> synWord=getSynWord(key);
		if (synWord!=null){
			key=synWord.getKey();
			ambigutyIdx = synWord.getValue();
		}
		
		BaseWord word=new BaseWord();
		
		int startIdx;
		int endIdx;
		if (ambigutyIdx==null||ambigutyIdx<0){
			startIdx = 0;
			endIdx=Integer.MAX_VALUE;
		}else{
			startIdx=ambigutyIdx;
			endIdx=ambigutyIdx;
		}
		BaseDicCompositeColumn startColumn = new BaseDicCompositeColumn(startIdx,"0");
		BaseDicCompositeColumn endColumn = new BaseDicCompositeColumn(endIdx,"þ");
		
		OperationResult<ColumnList<BaseDicCompositeColumn>>  opResult=	keyspace.prepareQuery(cf).
				getKey(key).withColumnRange(startColumn, endColumn, false, Integer.MAX_VALUE).execute();
		ColumnList<BaseDicCompositeColumn>  cols=opResult.getResult();
		if (cols.size()==0){
			return null;
		}
		for(Column<BaseDicCompositeColumn> col:cols){
			if (col.getName().getColName().equals(BaseDicCompositeColumn._COL_CATEGOIES)){
				word.addCategories(col.getName().getAmbiguityIdx(), col.getStringValue());
			}if (col.getName().getColName().equals(BaseDicCompositeColumn._COL_COMMODITY_RELATE)){
				word.addCommodityRelateWords(col.getName().getAmbiguityIdx(), col.getStringValue());
			}if (col.getName().getColName().equals(BaseDicCompositeColumn._COL_RELATE_WORD)){
				word.addRelateWords(col.getName().getAmbiguityIdx(), col.getStringValue());
			}
		}
		word.setKey(key);
		word.setAmbiguityCount(getAmbigutyCount(key));
		return word;
	}
	
	@Override
	public Entry<String, Integer> getSynWord(String key) throws Exception{
		return baseSynDicDao.hasSynWord(key);
	}
	
	public Rows<String, BaseDicCompositeColumn>  getAllKeys() throws Exception{
		return keyspace.prepareQuery(cf).getAllRows().execute().getResult();
	}
	
}

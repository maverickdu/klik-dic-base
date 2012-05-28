package com.klik.dic.base.dao.imp;


import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import com.klik.dic.base.dao.IUrlPatternDao;
import com.klik.dic.base.mo.UrlPatternCompositeColumn;
import com.netflix.astyanax.ColumnListMutation;
import com.netflix.astyanax.Keyspace;
import com.netflix.astyanax.MutationBatch;
import com.netflix.astyanax.model.ColumnFamily;
import com.netflix.astyanax.model.ColumnList;
import com.netflix.astyanax.serializers.AnnotatedCompositeSerializer;
import com.netflix.astyanax.serializers.StringSerializer;

@Component("urlPatternDao")
public class UrlPatternDaoImp implements IUrlPatternDao, InitializingBean {

	@Autowired
	protected Keyspace keyspace;
	protected ColumnFamily<String,UrlPatternCompositeColumn> cf;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		AnnotatedCompositeSerializer<UrlPatternCompositeColumn> urlPatternSerializer=
				new AnnotatedCompositeSerializer<UrlPatternCompositeColumn>(UrlPatternCompositeColumn.class);
		
		cf = new ColumnFamily<String, UrlPatternCompositeColumn>("URL_PATTERN", 
				StringSerializer.get(), urlPatternSerializer);
	}
	
	@Override
	public void insert(String domain, String pattern, String... categories)
			throws Exception {
		
		MutationBatch m=keyspace.prepareMutationBatch();
		ColumnListMutation<UrlPatternCompositeColumn> clm=m.withRow(cf, domain);
		StringBuilder sb=new StringBuilder();
		for(String category:categories){
			sb.append(category+",");
		}
		clm.putColumn(new UrlPatternCompositeColumn(pattern), sb.toString(), null);
		
		m.execute();
	}

	@Override
	public ColumnList<UrlPatternCompositeColumn> queryDomainPatterns(String domain) throws Exception{
		UrlPatternCompositeColumn startColumn=new UrlPatternCompositeColumn(0,"0");
		UrlPatternCompositeColumn endColumn=new UrlPatternCompositeColumn(Integer.MAX_VALUE,"þ");
		
		ColumnList<UrlPatternCompositeColumn>  columns=keyspace.prepareQuery(cf).getKey(domain)
				.withColumnRange(endColumn,startColumn, true, Integer.MAX_VALUE).execute().getResult();
		return columns;
	}

	

}

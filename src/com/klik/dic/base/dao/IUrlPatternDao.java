package com.klik.dic.base.dao;


import com.klik.dic.base.mo.UrlPatternCompositeColumn;
import com.netflix.astyanax.model.ColumnList;

public interface IUrlPatternDao {
	
	public void insert(String domain,String pattern,String... categories) throws Exception;
	
	public ColumnList<UrlPatternCompositeColumn> queryDomainPatterns(String domain) throws Exception;
}

package com.klik.core.service.imp;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klik.common.util.CommonUtil;
import com.klik.core.service.IUrlPatternService;
import com.klik.dic.base.dao.IUrlPatternDao;
import com.klik.dic.base.mo.UrlPatternCompositeColumn;
import com.netflix.astyanax.model.Column;
import com.netflix.astyanax.model.ColumnList;

@Service("urlPatternService")
public class UrlPatternServiceImp implements IUrlPatternService {
	
	@Autowired
	IUrlPatternDao urlPatternDao;
	
	@Override
	public List<String> getCategries(String url) throws Exception {
		String level2Domain=CommonUtil.get2LevelDomain(url);
		
		ColumnList<UrlPatternCompositeColumn> cols=urlPatternDao.queryDomainPatterns(level2Domain);
		String pattern;
		for(Column<UrlPatternCompositeColumn> col:cols){
			pattern = col.getName().getPattern();
			if (url.matches(pattern)){
				return Arrays.asList(col.getStringValue().split(","));
			}
		}
		
		return null;
	}

	@Override
	public void addPattern(String domain,String... categories) throws Exception{
		String level2Domain=CommonUtil.get2LevelDomain(domain);
		String pattern=".*"+domain.replaceAll("[.]", "\\\\.")+".*";
		urlPatternDao.insert(level2Domain, pattern, categories);
	}
	
}

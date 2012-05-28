package com.klik.core.service;

import java.util.List;

public interface IUrlPatternService {
	
	public List<String> getCategries(String url) throws Exception;
	
	public void addPattern(String domain,String... categories) throws Exception;
	
}

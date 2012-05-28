package com.klik.dic.base.mo;

import java.util.ArrayList;
import java.util.List;

public class UrlPatternMO {
	
	private String domain;
	private String pattern;
	private List<String> categories;
	
	public UrlPatternMO(){
		categories = new ArrayList<String>();
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public List<String> getCategories() {
		return categories;
	}
	
	public void addCategory(String category){
		this.categories.add(category);
	}
	
}

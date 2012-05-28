package com.klik.dic.base.mo;

import com.netflix.astyanax.annotations.Component;

public class UrlPatternCompositeColumn {
	
	private @Component(ordinal=0) Integer patternLength;
	private @Component(ordinal=1) String  pattern;
	
	public UrlPatternCompositeColumn(){
		
	}
	
	public UrlPatternCompositeColumn(String pattern){
		this.patternLength = pattern.length();
		this.pattern=pattern;
	}

	public UrlPatternCompositeColumn(Integer length,String pattern){
		this.patternLength = length;
		this.pattern=pattern;
	}
	
	public Integer getPatternLength() {
		return patternLength;
	}

	public void setPatternLength(Integer patternLength) {
		this.patternLength = patternLength;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}
	
	
	
}

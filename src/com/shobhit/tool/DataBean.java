package com.shobhit.tool;

public class DataBean {
	private String attributeName;
	private String attributeType;
	private boolean unique;

	public DataBean() {
	}

	public DataBean(String attributeName, String attributeType, boolean unique) {
		this.attributeName = attributeName;
		this.attributeType = attributeType;
		this.unique = unique;
	}

	public String getAttributeName() {
		return attributeName;
	}

	public String getAttributeType() {
		return attributeType;
	}

	public boolean isUnique() {
		return unique;
	}
}
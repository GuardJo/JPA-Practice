package com.practice.jpa.chapter10.config;

import org.hibernate.dialect.H2Dialect;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.type.StandardBasicTypes;

public class CustomH2Dialect extends H2Dialect {
	public CustomH2Dialect() {
		registerFunction("test_concat", new StandardSQLFunction("group_concat", StandardBasicTypes.STRING));
	}
}

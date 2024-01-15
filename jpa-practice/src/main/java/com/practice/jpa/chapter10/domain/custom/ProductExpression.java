package com.practice.jpa.chapter10.domain.custom;

import com.mysema.query.annotations.QueryDelegate;
import com.mysema.query.types.expr.BooleanExpression;
import com.practice.jpa.chapter10.domain.Product10;
import com.practice.jpa.chapter10.domain.QProduct10;

public class ProductExpression {
	@QueryDelegate(Product10.class)
	public static BooleanExpression isExpensive(QProduct10 qProduct, Integer price) {
		return qProduct.price.gt(price);
	}
}

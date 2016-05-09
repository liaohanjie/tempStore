package com.ks.access;

import java.util.List;

public interface DBBeanParamMethod<T> {
	List<Object> getParams(T bean);
}
                                                  
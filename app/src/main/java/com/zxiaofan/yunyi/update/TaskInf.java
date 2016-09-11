package com.zxiaofan.yunyi.update;

import java.util.Map;

public interface TaskInf {
	void onPreExecute() ;
	
	void isSuccess(Map<String, Object> b);
}

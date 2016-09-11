/**============================================================
 * 版权： 久其软件 版权所有 (c) 
 * 包： com.jiuqi.njt.util
 * 修改记录：
 * 日期                作者           内容
 * ============================================================*/

package util;

import java.util.ArrayList;

/**
 * <p>返回对象，一般用在Validator和ValidateRule</p>
 * <p>如果<font color=green>result==ture认为通过</font>，无详细信息，调用data返回null</p>
 * <p>如果<font color=red>result==false认为不通过</font>，有详细信息，调用getErrorMessage()可以得到具体的错误信息，也可调用data得到字符串</p>
 */

public class ReturnObject{
	public String name;
	public boolean isSuccess;
	public Object data;
	public Throwable exception;
	public ReturnObject(String simpleName){
		this.name = simpleName;
	}

	public ReturnObject(){
	}

	@Override
	public String toString(){
		return "ReturnObject [name=" + name + ", isSuccess=" + isSuccess + ", data=" + data + "]";
	}

	/**
	 * 调用这个方法会很方便的得到错误信息内容，如果校验的时候调用validator.val("sometext", false);则会把所有的出错信息都返回，用“,”分隔
	 * 如果返回对象result为true，返回空字符串，如果为false，返回错误信息；如果对象不是ArrayList或字符串，返回null
	 * @return String
	 */
	public String getErrorMessage(){
		if(isSuccess) return "";
		if(data instanceof ArrayList){
			StringBuffer sb = new StringBuffer();
			ArrayList<ReturnObject> rlist = (ArrayList<ReturnObject>)data;
			for(ReturnObject o : rlist){
				sb.append(o.getErrorMessage()).append(",");
			}
			return sb.toString();
		}
		else if(data instanceof String){
			return (String)data;
		}
		return null;
	}
	
	public Throwable getException(){
	    return exception;
    }
	
	

}

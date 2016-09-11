package util;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

/**
 * Created by Administrator on 2016/1/21 0021.
 */
public class IStringRequest extends StringRequest {
    public IStringRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
    }
    public IStringRequest(String url,Response.Listener<String> listener,Response.ErrorListener errorListener){
        super(url,listener,errorListener);

    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        try{
            String type=response.headers.get("content-type");
            if(type==null){
                type="charset=UTF-8";
                response.headers.put("content-type",type);
            }else if (!type.contains("UTF-8")){
                type+=";"+"charset=UTF-8";
                response.headers.put("content-type",type);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return  super.parseNetworkResponse(response);
    }
}

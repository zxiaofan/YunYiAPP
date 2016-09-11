package com.zxiaofan.yunyi;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.os.AsyncTask;
import android.text.TextUtils;

/**
 * @项目名称：TestSoap
 * @类名称：SoapHelper
 * @类描述：
 * @创建人：zc
 * @创建时间：2016年6月3日 下午6:45:36
 */
public class SoapHelper {
    private static final String SERVER_URL = "http://zxiaofan.com:8080/MedicalAssistanService/apiService";
    private static final String NAME_SPACE = "http://service.MedicalAssistanService.zxiaofan.com/";

    private SoapHelper() {
    }

    public static boolean start(String action, TreeMap<String, String> params, Callback callback) {
        if (TextUtils.isEmpty(action) || params == null || params.isEmpty() || callback == null) {
            return false;
        }
        Request request = new Request();
        request.mAction = action;
        request.mParams = params;
        request.mCallback = callback;
        new RequestTask(request).execute();
        return true;
    }

    private static class Request {
        public String mAction = null;
        public TreeMap<String, String> mParams = null;
        public Callback mCallback = null;
    }

    private static class RequestTask extends AsyncTask<Void, Void, String> {

        private Request mRequest = null;

        /**
         * 创建一个新的实例 SoapHelper.RequestTask.
         *
         */
        public RequestTask(Request request) {
            if (TextUtils.isEmpty(request.mAction) || request.mParams == null || request.mParams.isEmpty() || request.mCallback == null) {
                throw new IllegalArgumentException("参数不能为空");
            }
            this.mRequest = request;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mRequest.mCallback.onStart();
        }

        @Override
        protected String doInBackground(Void... params) {
            String result = null;
            try {
                result = doRequest(mRequest);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            return result;
        }

        private String doRequest(Request request) {
            // 指定WebService的命名空间和调用方法
            SoapObject soapObject = new SoapObject(NAME_SPACE, request.mAction);
            Set<Map.Entry<String, String>> setParams = request.mParams.entrySet();
            for (Map.Entry<String, String> item : setParams) {
                soapObject.addProperty(item.getKey(), item.getValue());
            }
            // 生成调用WebService方法调用的soap信息，并且指定Soap版本
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            // 是否调用DotNet开发的WebService
            envelope.dotNet = false;
            envelope.setOutputSoapObject(soapObject);
            HttpTransportSE transport = new HttpTransportSE(SERVER_URL);
            try {
                transport.call(NAME_SPACE + request.mAction, envelope);
            } catch (HttpResponseException e) { e.printStackTrace();
            } catch (IOException e) { e.printStackTrace();
            } catch (XmlPullParserException e) { e.printStackTrace();
            }
            if(envelope.bodyIn instanceof SoapObject){
                SoapObject object = (SoapObject) envelope.bodyIn;// 获取返回的数据
                String result = object.getProperty(0).toString(); // 获取返回的结果
                return result;
            }else if(envelope.bodyIn instanceof SoapFault){
                SoapFault fault = (SoapFault)envelope.bodyIn;
                return fault.faultstring;
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (TextUtils.isEmpty(result)) {
                mRequest.mCallback.onError(-1);
                return;
            }
            mRequest.mCallback.onSuccess(result);
        }
    }

    public interface Callback {
        public void onStart();

        public void onError(int errNo);

        public void onSuccess(String text);
    }
}

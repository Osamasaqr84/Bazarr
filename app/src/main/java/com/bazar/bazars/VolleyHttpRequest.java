package com.bazar.bazars;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public class VolleyHttpRequest extends Request<String> {
	private int mStatusCode;

	public int getStatusCode() {
		return mStatusCode;
	}
	private AsyncTaskCompleteListener listener;
	private Map<String, String> params,headers;
	private int serviceCode;
	private static String TAG = "VolleyHttpRequest";

	public VolleyHttpRequest(int method, Map<String, String> params,
                             int serviceCode, AsyncTaskCompleteListener reponseListener,
                             ErrorListener errorListener,Map<String, String> headerss) {
		super(method, params.get("url"), errorListener);
		if (AppLog.isDebug) {
			for (String key : params.keySet()) {
				AppLog.Log(TAG, key + "  < === >  " + params.get(key));
			}
		}
		params.remove("url");
		setRetryPolicy(new DefaultRetryPolicy(600000,
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
		this.listener = reponseListener;
		this.params = params;
		this.serviceCode = serviceCode;
		this.headers = headerss;
	}
	public VolleyHttpRequest(int method, Map<String, String> params,
							 int serviceCode, AsyncTaskCompleteListener reponseListener,
							 ErrorListener errorListener) {
		super(method, params.get("url"), errorListener);
		if (AppLog.isDebug) {
			for (String key : params.keySet()) {
				AppLog.Log(TAG, key + "  < === >  " + params.get(key));
			}
		}
		params.remove("url");
		setRetryPolicy(new DefaultRetryPolicy(600000,
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
		this.listener = reponseListener;
		this.params = params;
		this.serviceCode = serviceCode;
	}
	@Override
	protected Map<String, String> getParams()
			throws com.android.volley.AuthFailureError {
		return params;
	}

	@Override
	public Map<String, String> getHeaders() throws AuthFailureError {
		return headers;
	}

	@Override
	protected void deliverResponse(String response) {
		listener.onTaskCompleted(response, serviceCode);
	}

	@Override
	protected Response<String> parseNetworkResponse(NetworkResponse response) {
		try {
			Log.d(TAG, "[raw json]: " + (new String(response.data)));
			Gson gson = new Gson();
			String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));

			String jsonString = new String(response.data,
					HttpHeaderParser.parseCharset(response.headers));
			return Response.success(jsonString, getCacheEntry());
		} catch (UnsupportedEncodingException e) {
			return Response.error(new ParseError(e));
		}
	}

}

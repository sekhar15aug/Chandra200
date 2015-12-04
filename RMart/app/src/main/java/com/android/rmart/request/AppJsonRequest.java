package com.android.rmart.request;

import com.android.rmart.interfaces.JsonResponseListener;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

/**
 * Created by Chandra on 02-12-2015.
 */
public class AppJsonRequest {

    private static AppJsonRequest request = new AppJsonRequest();
    private AppRequestQueue instance = null;

    private AppJsonRequest() {
        instance = AppRequestQueue.getInstance();
    }

    public static synchronized AppJsonRequest getInstance() {
        return request;
    }

    public void getJsonResponse(final int method, final String url, final JsonResponseListener listener) {

        JsonObjectRequest appReq = new JsonObjectRequest(method,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        if (null != listener)
                            listener.onSuccess(jsonObject);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (null != listener)
                    listener.onError(volleyError);
            }
        });
        if (null != instance)
        instance.addToRequestQueue(appReq);
    }
}

package com.android.rmart.interfaces;

import com.android.volley.VolleyError;

import org.json.JSONObject;

/**
 * Created by Chandra on 02-12-2015.
 */
public interface JsonResponseListener {

    public void onSuccess(JSONObject jsonObject);

    public void onError(VolleyError volleyError);
}

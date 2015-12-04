package com.android.rmart;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.rmart.interfaces.AppScrollListener;
import com.android.rmart.product.ProductManager;
import com.android.rmart.product.ProductsResponse;
import com.android.rmart.request.AppRequestQueue;
import com.android.rmart.util.AppConstants;
import com.android.rmart.util.AppUtils;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private int mIndex;
    private int maxProducts;
    private boolean loadData = false;
    private CatalogueAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private GridLayoutManager mLayoutManager;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case 0:
                    updateAdapter();
                    mProgressBar.setVisibility(View.GONE);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.catalogView);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        mLayoutManager = new DynamicColumnGridManager(getApplicationContext(), 300);

        if (!AppUtils.getInstance().isNetworkAvailable()) {
            return;
        }

        ProductManager.getInstance().removeAll();
        mAdapter = new CatalogueAdapter(ProductManager.getInstance().getProducts());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mIndex = 0;
        maxProducts = 0;
        loadProductsForUrl();
        mRecyclerView.addOnScrollListener(new AppScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore() {
                onLoadMoreData();
            }

            @Override
            public void notifyChanged() {
                updateAdapter();
            }
        });
    }


    private void loadProductsForUrl() {
        if (mIndex != 0) {
            RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) mProgressBar.getLayoutParams();
            rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            rlp.addRule(RelativeLayout.CENTER_IN_PARENT, 0);
            mProgressBar.setLayoutParams(rlp);
        }
        mProgressBar.setVisibility(View.VISIBLE);

        AppRequestQueue instance = AppRequestQueue.getInstance();
        JsonObjectRequest catalogReq = new JsonObjectRequest(Request.Method.GET,
                AppUtils.getInstance().getPaginationUrl(AppConstants.URL, mIndex, AppConstants.PAGINATION_PAGE_SIZE), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        loadData = false;
                        if (null != jsonObject) {

                            Gson gson = new Gson();
                            ProductsResponse response = gson.fromJson(jsonObject.toString(), ProductsResponse.class);
                            ProductManager.getInstance().updateProducts(response.getProducts());
                            maxProducts = response.getTotal();
                            mHandler.sendEmptyMessageDelayed(0, 100);
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                loadData = false;
                mProgressBar.setVisibility(View.INVISIBLE);
                Log.i(getClass().getSimpleName(), volleyError.getMessage());
                Toast.makeText(getApplicationContext(), "Url Req. error", Toast.LENGTH_SHORT).show();
            }
        });
        instance.addToRequestQueue(catalogReq);
    }

    private void onLoadMoreData() {
        if (!loadData) {
            loadData = true;
            if (mIndex + AppConstants.PAGINATION_PAGE_SIZE < maxProducts) {
                mIndex = mIndex + AppConstants.PAGINATION_PAGE_SIZE;
                loadProductsForUrl();
            } else {
                mProgressBar.setVisibility(View.GONE);
            }
        }
    }

    private void updateAdapter() {
        mAdapter.notifyDataSetChanged();
    }
}

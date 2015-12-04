package com.android.rmart.interfaces;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by chandra on 02/12/15.
 */
public abstract class AppScrollListener extends RecyclerView.OnScrollListener {
    private GridLayoutManager mLayoutManager;
    private int mTotalItemCount, mLastVisibleItem;

    public AppScrollListener(GridLayoutManager mLayoutManager) {
        this.mLayoutManager = mLayoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        mTotalItemCount = mLayoutManager.getItemCount();
        mLastVisibleItem = mLayoutManager.findLastVisibleItemPosition();

        if ((mTotalItemCount <= (mLastVisibleItem + 1))) {
            onLoadMore();
            notifyChanged();
        }
    }

    public abstract void onLoadMore();

    public abstract void notifyChanged();
}

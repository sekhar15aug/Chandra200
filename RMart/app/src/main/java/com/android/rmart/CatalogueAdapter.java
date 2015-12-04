package com.android.rmart;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.rmart.product.ProductManager;
import com.android.rmart.product.ProductsResponse;
import com.android.rmart.util.AppConstants;
import com.android.rmart.util.AppUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Chandra on 02-12-2015.
 */
public class CatalogueAdapter extends
        RecyclerView.Adapter<CatalogueAdapter.ViewHolder> {

    private Context mContext;
    private List<ProductsResponse.Product> mProducts;

    public CatalogueAdapter(List<ProductsResponse.Product> products) {
        mContext = AppUtils.getInstance().getAppContext();
        mProducts = products;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_catalogue_item, parent, false);
        return new ViewHolder(view, viewType);
    }

    @Override
    public synchronized void onBindViewHolder(CatalogueAdapter.ViewHolder holder, int position) {

        try {
            ProductsResponse.Product product = mProducts.get(position);
            ProductsResponse.Price price = product.getPricing();
            ProductsResponse.Images[] images = product.getImages();
            ProductsResponse.Measure measure = product.getMeasure();

            holder.catalogItemTitle.setText(product.getTitle());
            holder.catalogItemInfo.setText(measure.getWtorVol());

            holder.catalogItemPrice.setText("S$" + Double.toString(price.getPrice()));
            int result = Double.compare(price.getPromoPrice(), 0.0);
            holder.catalogItemPromoPrice.setVisibility(View.GONE);
            holder.catalogItemPrice.setPaintFlags(holder.catalogItemPromoPrice.getPaintFlags());

            if (result == 1) {
                holder.catalogItemPrice.setPaintFlags(holder.catalogItemPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                holder.catalogItemPromoPrice.setVisibility(View.VISIBLE);
            }
            holder.catalogItemPromoPrice.setText("S$" + Double.toString(price.getPromoPrice()));
            holder.catalogItemSavingsInfo.setText(price.getSavingsText());

            Picasso.with(AppUtils.getInstance().getAppContext()).load(AppConstants.BASE_URL + images[0].getName()).into(holder.imageView);
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        if (null != mProducts) {
            return mProducts.size();
        } else {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView catalogItemSavingsInfo;
        TextView catalogItemTitle;
        TextView catalogItemInfo;
        TextView catalogItemPromoPrice;
        TextView catalogItemPrice;
        ImageView imageView;

        public ViewHolder(View itemView, int ViewType) {
            super(itemView);

            catalogItemInfo = (TextView) itemView.findViewById(R.id.catalogItemInfo);
            catalogItemPrice = (TextView) itemView.findViewById(R.id.catalogItemPrice);
            catalogItemPromoPrice = (TextView) itemView.findViewById(R.id.catalogItemPromoPrice);
            catalogItemSavingsInfo = (TextView) itemView.findViewById(R.id.catalogItemSavingsInfo);
            catalogItemTitle = (TextView) itemView.findViewById(R.id.catalogItemTitle);
            imageView = (ImageView) itemView.findViewById(R.id.catalogImage);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int productPosition = getAdapterPosition();
                    if (ProductManager.getInstance().getProductsSize() > productPosition) {
                        ProductManager.getInstance().setSelectedProduct(productPosition);

                        Intent intent = new Intent(mContext, ItemDetailsActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(intent);
                    }
                }
            });
        }
    }

}

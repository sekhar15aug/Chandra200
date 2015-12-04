package com.android.rmart;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.rmart.product.ProductManager;
import com.android.rmart.product.ProductsResponse;
import com.android.rmart.util.AppConstants;
import com.squareup.picasso.Picasso;

public class ItemDetailsActivity extends AppCompatActivity {

    private ProductsResponse.Product mSelectedProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        TextView title = (TextView) toolbar.findViewById(R.id.title);
        setSupportActionBar(toolbar);

        mSelectedProduct = ProductManager.getInstance().getSelectedProduct();
        title.setText("Product Details");

        ImageView imageView = (ImageView) findViewById(R.id.imageForDetails);
        TextView itemInfo = (TextView) findViewById(R.id.itemInfo);
        TextView price = (TextView) findViewById(R.id.itemPrice);
        TextView promoPrice = (TextView) findViewById(R.id.itemPromoPrice);
        TextView itemTitle = (TextView) findViewById(R.id.itemTitle);
        TextView itemSaveInfo = (TextView) findViewById(R.id.itemSavingsInfo);
        TextView itemDetailsInfo = (TextView) findViewById(R.id.itemDetails);

        ProductsResponse.Images[] images = mSelectedProduct.getImages();

        if (null != images) {
            if (images.length > 0) {
                Picasso.with(getApplicationContext())
                        .load(AppConstants.BASE_URL + images[0].getName())
                        .into(imageView);
            }
        }

        try {
            price.setText("S$" + mSelectedProduct.getPricing().getPrice().toString());
            promoPrice.setText("S$" + mSelectedProduct.getPricing().getPromoPrice().toString());
            itemTitle.setText(mSelectedProduct.getTitle().toString());
            itemInfo.setText(mSelectedProduct.getDesc());

            itemSaveInfo.setText(mSelectedProduct.getPricing().getSavingsText());
            itemDetailsInfo.setText(mSelectedProduct.getMeasure().getWtorVol());

            promoPrice.setVisibility(View.GONE);
            price.setPaintFlags(promoPrice.getPaintFlags());
            int result = Double.compare(mSelectedProduct.getPricing().getPromoPrice(), 0.0);
            if (result == 1) {
                price.setPaintFlags(price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                promoPrice.setVisibility(View.VISIBLE);
            }
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }

    }
}

package com.android.rmart.product;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chandra on 02-12-2015.
 */
public class ProductManager {
    private static ProductManager ourInstance = new ProductManager();
    private ArrayList<ProductsResponse.Product> mProducts = new ArrayList<>();
    private ProductsResponse.Product mProduct;

    public static ProductManager getInstance() {
        return ourInstance;
    }

    private ProductManager() {
    }

    public void setProducts(ArrayList<ProductsResponse.Product> products) {
        mProducts = products;
    }

    public ArrayList<ProductsResponse.Product> getProducts() {
        return mProducts;
    }

    public int getProductsSize() {
        return mProducts.size();
    }

    public void updateProducts(List<ProductsResponse.Product> products) {
        mProducts.addAll(products);
    }

    public void removeAll() {
        mProducts.clear();
    }

    public void setSelectedProduct(int position) {
        if (mProducts.size() > position) {
            mProduct = mProducts.get(position);
        }
    }

    public ProductsResponse.Product getSelectedProduct() {
        return mProduct;
    }
}

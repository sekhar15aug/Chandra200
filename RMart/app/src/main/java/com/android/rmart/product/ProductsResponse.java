package com.android.rmart.product;

import java.util.List;

/**
 * Created by Chandra on 02-12-2015.
 */

public class ProductsResponse {

    private List<Product> products;
    private int total;

    public List<Product> getProducts(){
        return products;
    }

    public int getTotal(){
        return total;
    }

    public class Product {
        private String desc;
        private String title;
        private Images[] images;
        private Price pricing;
        private Measure measure;

        public String getDesc() {
            return desc;
        }

        public String getTitle() {
            return title;
        }

        public Images[] getImages() {
            return images;
        }

        public Price getPricing() {
            return pricing;
        }

        public Measure getMeasure() {
            return measure;
        }

    }
    public class Images {
        public String getName() {
            return name;
        }

        private String name;
    }

    public class Price {
        private Double price;
        private Double promo_price;
        private String savings_text;

        public String getSavingsText() {
            return savings_text;
        }

        public Double getPromoPrice() {
            return promo_price;
        }

        public Double getPrice() {
            return price;
        }

    }

    public class Measure {
        public String getWtorVol() {
            return wt_or_vol;
        }

        private String wt_or_vol;
    }


}
package com.android.rmart;


import android.support.v7.widget.RecyclerView;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import com.android.rmart.product.ProductManager;
import com.android.rmart.product.ProductsResponse;
import java.lang.System;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chandra on 03/12/2015.
 */

@Config(constants = BuildConfig.class, sdk = 21)
@RunWith(RobolectricGradleTestRunner.class)
public class ProductManagerTest {

    ProductManager manager;

    @Before
    public void setup() {
        manager = ProductManager.getInstance();
    }

    @Test
    public void instanceTest() {
        System.out.println("manager "+manager);
        Assert.assertNotNull(manager);
    }

    @Test
    public void getProductsTest() {
        Assert.assertNotNull(manager.getProducts());
    }

    @Test
    public void getProductsSizeTest() {
        Assert.assertSame(0, manager.getProductsSize());
    }
}

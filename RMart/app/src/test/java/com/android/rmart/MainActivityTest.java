package com.android.rmart;


import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.*;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.Shadows;

/**
 * Created by Chandra on 03/12/2015.
 */

@Config(constants = BuildConfig.class, sdk = 21)
@RunWith(RobolectricGradleTestRunner.class)
public class MainActivityTest {

    private MainActivity activity;

    @Before
    public void setup() {
        activity = Robolectric.buildActivity(MainActivity.class).create().get();
    }

    @Test
    public void activityNullTest() {
        Assert.assertNotNull(activity);
    }

    @Test
    public void actionBarTitleTest() {
        TextView title = (TextView) activity.findViewById(R.id.title);
        Assert.assertEquals("RMart", title.getText().toString());
    }

    @Test
    public void catalogueViewTest() {
        RecyclerView catalogView = (RecyclerView) activity.findViewById(R.id.catalogView);
        Assert.assertNotNull(catalogView);
    }
}

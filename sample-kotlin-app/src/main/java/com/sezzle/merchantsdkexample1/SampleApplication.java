package com.sezzle.merchantsdkexample1;

import android.app.Application;

import com.sezzle.sezzlemerchantsdkandroid.Sezzle;

public class SampleApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Sezzle.Companion.initialize(new Sezzle.Configuration(
                "qxorvmr7zbww7yw8vvhlxi8s3uko18q7",
                Sezzle.Environment.PRODUCTION, Sezzle.LOG_LEVEL_DEBUG,
                Sezzle.Location.US
        ));
    }
}

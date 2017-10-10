/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fernandocejas.android10.sample.data.net;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

import com.fernandocejas.android10.sample.data.utils.AppUtil;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;


/**
 * Api Connection class used to retrieve data from the cloud.
 * Implements {@link java.util.concurrent.Callable} so when executed asynchronously can
 * return a value.
 */
class ApiConnection implements Callable<String> {

    public static final String TAG = ApiConnection.class.getSimpleName();
    private static final String CONTENT_TYPE_LABEL = "Content-Type";
    private static final String CONTENT_TYPE_VALUE_JSON = "application/json; charset=utf-8";
    private static final String PLATFORM = "platform";
    private static final String PLATFORM_ANDROID = "android";
    private static final String VERSION_NAME = "versionName";
    private static final String PACKAGE_NAME = "packageName";

    private Context context;
    private URL url;
    private String response;

    private ApiConnection(Context context,String url) throws MalformedURLException {
        this.context = context.getApplicationContext();
        this.url = new URL(url);
    }

    static ApiConnection createGET(Context context,String url) throws MalformedURLException {
        return new ApiConnection(context,url);
    }

    /**
     * Do a request to an api synchronously.
     * It should not be executed in the main thread of the application.
     *
     * @return A string response
     */
    @Nullable
    String requestSyncCall() {
        connectToApi();
        return response;
    }



    private void connectToApi() {
        int localVersion = AppUtil.getLocalVersion(this.context);
        String localVersionName = AppUtil.getLocalVersionName(this.context);
        String localPackageName = AppUtil.getLocalPackageName(this.context);

        Log.d(TAG,"---VersionCode---"+localVersion);
        Log.d(TAG,"---VersionName---"+localVersionName);
        Log.d(TAG,"---PackageName---"+localPackageName);

        OkHttpClient okHttpClient = this.createClient();
        final Request request = new Request.Builder()
                .url(this.url)
                .addHeader(CONTENT_TYPE_LABEL, CONTENT_TYPE_VALUE_JSON)
                .addHeader(PLATFORM, PLATFORM_ANDROID)
                .addHeader(VERSION_NAME, localVersionName)
                .addHeader(PACKAGE_NAME, localPackageName)
                .get()
                .build();

        try {
            this.response = okHttpClient.newCall(request).execute().body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private OkHttpClient createClient() {
        final OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setReadTimeout(10000, TimeUnit.MILLISECONDS);
        okHttpClient.setConnectTimeout(15000, TimeUnit.MILLISECONDS);

        return okHttpClient;
    }

    @Override
    public String call() throws Exception {
        return requestSyncCall();
    }
}

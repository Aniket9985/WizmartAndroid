package com.wiz.wizmart.retrofit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitIntance {

    private static final String  BASE_URL="https://techvyassa.com/";
    public static ApiInterFace apiInterFace=null;
    public static Retrofit getRetrofit()
    {

            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();

            Retrofit retrofit = new Retrofit.Builder()
                    .addConverterFactory( GsonConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .build();
        return retrofit;
    }
    public static ApiInterFace getService(){
        ApiInterFace apiInterFace =  getRetrofit ().create ( ApiInterFace.class );

        return apiInterFace;
    }
}

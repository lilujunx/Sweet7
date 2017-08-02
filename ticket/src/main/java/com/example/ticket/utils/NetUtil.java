package com.example.ticket.utils;

import com.example.ticket.Constant;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by Administrator on 2016/11/18.
 */

public class NetUtil {


    public static <T> T getRetrofitCall (Class<T> service){
        Retrofit retrofit=new Retrofit.Builder().baseUrl(Constant.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return (T)retrofit.create(service);
    }
}

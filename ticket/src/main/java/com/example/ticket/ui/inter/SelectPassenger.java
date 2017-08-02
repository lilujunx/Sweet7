package com.example.ticket.ui.inter;

import com.example.ticket.Constant;
import com.example.ticket.entity.passenger.PassengerEntity;
import com.example.ticket.entity.user.User;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by Administrator on 2017/1/31.
 */

public interface SelectPassenger {
    @GET(Constant.PASSENGER_URL)
    public Call<PassengerEntity> doGet(@QueryMap Map<String, String> map);


}

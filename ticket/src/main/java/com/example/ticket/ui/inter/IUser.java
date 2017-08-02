package com.example.ticket.ui.inter;

import com.example.ticket.Constant;
import com.example.ticket.entity.ticket.TicketEntity;
import com.example.ticket.entity.user.User;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by Administrator on 2017/1/25.
 */

public interface IUser {
    @GET(Constant.IUSER_URL)
    public Call<User> doGet(@QueryMap Map<String, String> map);

}

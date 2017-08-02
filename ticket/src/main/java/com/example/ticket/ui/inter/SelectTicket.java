package com.example.ticket.ui.inter;

import com.example.ticket.Constant;
import com.example.ticket.entity.ticket.TicketEntity;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2017/1/23.
 */

public interface SelectTicket {
    @GET(Constant.SELECT_TICKET_URL)
    public Call<TicketEntity> doGet(@Query("date") String date, @Query("end") String end, @Query("timeLevel") String timeLevel);
}

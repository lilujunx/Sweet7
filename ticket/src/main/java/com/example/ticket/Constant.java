package com.example.ticket;

import com.example.ticket.entity.ticket.TicketEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SJ on 2016/1/3.
 */
public class Constant {
    public static  boolean isFirst=true;
    public static TicketEntity.TicketsBean TicketsBeanNow;
    public static  List<String> mCurrentEnd=new ArrayList<>();
    public static final String BASE_URL="http://192.168.3.112:8080/Ticket/";
    //http://192.168.1.103:8080/Ticket/image/x.png
    public static final String SELECT_TICKET_URL="selectticket";
    public static final String IUSER_URL="iuser";
    public static final String PASSENGER_URL="passenger";
   // public static final String ICON=BASE_URL+"image/";
    public static int passengerNum=0;

}

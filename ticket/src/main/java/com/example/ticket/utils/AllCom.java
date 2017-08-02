package com.example.ticket.utils;

import com.example.ticket.entity.ticket.TicketEntity;

import java.util.Comparator;

/**
 * Created by Administrator on 2017/1/25.
 */

public abstract class AllCom implements Comparator<TicketEntity.TicketsBean> {

}


class TimeComAes extends AllCom {
    //时间，升序
    @Override
    public int compare(TicketEntity.TicketsBean t1, TicketEntity.TicketsBean t2) {
        return t1.getTime().compareTo(t2.getTime());
    }
}

class TimeComDes extends AllCom {
    //时间，降序
    @Override
    public int compare(TicketEntity.TicketsBean t1, TicketEntity.TicketsBean t2) {
        return -t1.getTime().compareTo(t2.getTime());
    }
}

class TypeComAes extends AllCom {
    //类型，升序
    @Override
    public int compare(TicketEntity.TicketsBean t1, TicketEntity.TicketsBean t2) {
        return t1.getTime().compareTo(t2.getTime());
    }
}

class TypeComDes extends AllCom {
    //类型，降序
    @Override
    public int compare(TicketEntity.TicketsBean t1, TicketEntity.TicketsBean t2) {
        return -t1.getTime().compareTo(t2.getTime());
    }
}

class PriceComAes extends AllCom {
    //单价，升序
    @Override
    public int compare(TicketEntity.TicketsBean t1, TicketEntity.TicketsBean t2) {
        if (t2.getPrice() > t1.getPrice()) {
            return -1;
        }
        if (t2.getPrice() < t1.getPrice()) {
            return 1;
        }
        return 0;
    }
}

class PriceComDes extends AllCom {
    //单价，降序
    @Override
    public int compare(TicketEntity.TicketsBean t1, TicketEntity.TicketsBean t2) {
        if (t2.getPrice() > t1.getPrice()) {
            return 1;
        }
        if (t2.getPrice() < t1.getPrice()) {
            return -1;
        }
        return 0;
    }
}

class SurplusComAsc extends AllCom {
    //余票数量，升序
    @Override
    public int compare(TicketEntity.TicketsBean t1, TicketEntity.TicketsBean t2) {
        if (t2.getSurplus() > t1.getSurplus()) {
            return -1;
        }
        if (t2.getSurplus() < t1.getSurplus()) {
            return 1;
        }
        return 0;
    }
}

class SurplusComDes extends  AllCom{
    //余票数量，降序
    @Override
    public int compare(TicketEntity.TicketsBean t1, TicketEntity.TicketsBean t2) {
        if(t2.getSurplus()>t1.getSurplus()){
            return 1;
        }
        if(t2.getSurplus()<t1.getSurplus()){
            return -1;
        }
        return 0;
    }
}
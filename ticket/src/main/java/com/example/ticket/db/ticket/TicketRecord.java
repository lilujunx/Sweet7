package com.example.ticket.db.ticket;

/**
 * Created by Administrator on 2017/2/11.
 */

import com.example.ticket.entity.passenger.PassengerEntity;


import java.io.Serializable;
import java.util.List;

import xutils.db.annotation.Column;
import xutils.db.annotation.Table;

@Table(name = "ticket")
public class TicketRecord implements Serializable {
    @Column(name = "id", isId = true)
    private int id;
    @Column(name = "phone")
    private String phone;
    @Column(name = "date")
    private String date;
    @Column(name = "time")
    private String time;
    @Column(name = "type")
    private String type;
    @Column(name = "end")
    private String end;
    @Column(name = "price")
    private double price;
    @Column(name = "buyTime")
    private String buyTime;
    @Column(name = "state")
    private String state;
    @Column(name="passenger")
    private List<PassengerEntity.PassengerBean> passenger;
    @Column(name="seat")
    private String seat;

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }


    public String getBuyTime() {
        return buyTime;
    }

    public void setBuyTime(String buyTime) {
        this.buyTime = buyTime;
    }

    public List<PassengerEntity.PassengerBean> getPassenger() {
        return passenger;
    }

    public void setPassenger(List<PassengerEntity.PassengerBean> passenger) {
        this.passenger = passenger;
    }

    @Override
    public String toString() {
        return "TicketRecord{" +
                "id=" + id +
                ", phone='" + phone + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", type='" + type + '\'' +
                ", end='" + end + '\'' +
                ", price=" + price +
                ", buyTime='" + buyTime + '\'' +
                ", state='" + state + '\'' +
                ", passenger=" + passenger +
                ", seat='" + seat + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }


}
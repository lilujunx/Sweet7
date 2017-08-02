package com.example.ticket.db.ticket;

import com.example.ticket.entity.passenger.PassengerEntity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/2/16.
 */

public class ShowTicket implements Serializable {
    private int id;
    private String seat;
    private String end;
    private String date;
    private String time;
    private String type;
    private double price;
    private List<PassengerEntity.PassengerBean> mPassengerBeen;
    private double totalPrice;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<PassengerEntity.PassengerBean> getPassengerBeen() {
        return mPassengerBeen;
    }

    public void setPassengerBeen(List<PassengerEntity.PassengerBean> passengerBeen) {
        mPassengerBeen = passengerBeen;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "ShowTicket{" +
                "id=" + id +
                ", seat='" + seat + '\'' +
                ", end='" + end + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", type='" + type + '\'' +
                ", price=" + price +
                ", mPassengerBeen=" + mPassengerBeen +
                ", totalPrice=" + totalPrice +
                '}';
    }
}

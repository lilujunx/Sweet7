package com.example.ticket.entity.ticket;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/1/23.
 */

public class TicketEntity implements Serializable{


    /**
     * no : 1
     * tickets : [{"date":"2017-01-21","surplus":30,"totalNum":30,"price":10,"end":"x1","timeLevel":1,"time":"07:00","type":"大型高一"},{"date":"2017-01-21","surplus":30,"totalNum":30,"price":10,"end":"x1","timeLevel":1,"time":"07:01","type":"大型高一"},{"date":"2017-01-21","surplus":30,"totalNum":30,"price":10,"end":"x1","timeLevel":1,"time":"07:30","type":"大型高一"},{"date":"2017-01-21","surplus":30,"totalNum":30,"price":10,"end":"x1","timeLevel":1,"time":"08:50","type":"大型高一"},{"date":"2017-01-21","surplus":30,"totalNum":30,"price":10,"end":"x1","timeLevel":1,"time":"09:59","type":"大型高一"}]
     */

    private String no;
    /**
     * date : 2017-01-21
     * surplus : 30
     * totalNum : 30
     * price : 10
     * end : x1
     * timeLevel : 1
     * time : 07:00
     * type : 大型高一
     */

    private List<TicketsBean> tickets;

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public List<TicketsBean> getTickets() {
        return tickets;
    }

    public void setTickets(List<TicketsBean> tickets) {
        this.tickets = tickets;
    }

    public static class TicketsBean  implements Serializable{
        private String date;
        private int surplus;
        private int totalNum;
        private int price;
        private String end;
        private int timeLevel;
        private String time;
        private String type;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public int getSurplus() {
            return surplus;
        }

        public void setSurplus(int surplus) {
            this.surplus = surplus;
        }

        public int getTotalNum() {
            return totalNum;
        }

        public void setTotalNum(int totalNum) {
            this.totalNum = totalNum;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public String getEnd() {
            return end;
        }

        public void setEnd(String end) {
            this.end = end;
        }

        public int getTimeLevel() {
            return timeLevel;
        }

        public void setTimeLevel(int timeLevel) {
            this.timeLevel = timeLevel;
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

        @Override
        public String toString() {
            return "TicketsBean{" +
                    "date='" + date + '\'' +
                    ", surplus=" + surplus +
                    ", totalNum=" + totalNum +
                    ", price=" + price +
                    ", end='" + end + '\'' +
                    ", timeLevel=" + timeLevel +
                    ", time='" + time + '\'' +
                    ", type='" + type + '\'' +
                    '}';
//            return "surplus="+surplus;
        }
    }

    @Override
    public String toString() {
        return "TicketEntity{" +
                "no='" + no + '\'' +
                ", tickets=" + tickets +
                '}';
    }
}

package com.example.ticket.utils;

/**
 * Created by Administrator on 2017/1/25.
 */

public class ComFactory {
    public static ComFactory mComFactory;

    private ComFactory() {

    }


    public static ComFactory getComFactory() {
        if (mComFactory == null) {
            synchronized (ComFactory.class) {
                if (mComFactory == null) {
                    mComFactory = new ComFactory();
                }
            }
        }
        return mComFactory;
    }

    public AllCom getCom(String key) {
        AllCom allCom = null;
        switch (key) {
            case "timeComAes":
                allCom = new TimeComAes();
                break;
            case "timeComDes":
                allCom = new TimeComDes();
                break;
            case "typeComAes":
                allCom = new TypeComAes();
                break;
            case "typeComDes":
                allCom = new TypeComDes();
                break;
            case "priceComAes":
                allCom = new PriceComAes();
                break;
            case "priceComDes":
                allCom = new PriceComDes();
                break;
            case "surplusComAes":
                allCom = new SurplusComAsc();
                break;
            case "surplusComDes":
                allCom = new SurplusComDes();
                break;
        }
        return allCom;
    }


}

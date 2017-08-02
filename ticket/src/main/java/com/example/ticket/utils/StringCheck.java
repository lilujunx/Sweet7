package com.example.ticket.utils;

/**
 * Created by Administrator on 2016/10/9.
 */

public class StringCheck {
    public static boolean check(String str){
        String pa="\\w{6,15}";
        return str.matches(pa);
    }
    //^1[34578]\d{9}$
    public static boolean checkPhone(String str){
        String pa="^1[34578]\\d{9}$";
        return str.matches(pa);
    }

    public static boolean checkIdNum(String str){
        String pa="\\d{18}";
        return  str.matches(pa);
    }
}

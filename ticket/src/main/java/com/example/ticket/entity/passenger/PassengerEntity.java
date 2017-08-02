package com.example.ticket.entity.passenger;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/1/28.
 */

public class PassengerEntity implements Serializable {

    /**
     * no : 1
     * passenger : [{"pName":"阿斯五","phone":"18275417177","pIdNum":"53292319950825095X"},{"pName":"张三","phone":"18275417177","pIdNum":"1312341843846448484"},{"pName":"李四","phone":"18275417177","pIdNum":"4984849884456165164"},{"pName":"王五","phone":"18275417177","pIdNum":"4651448589445561656"},{"pName":"4","phone":"18275417177","pIdNum":"1312341843846448484"},{"pName":"5","phone":"18275417177","pIdNum":"4984849884456165164"},{"pName":"6","phone":"18275417177","pIdNum":"4984849884456165164"},{"pName":"7","phone":"18275417177","pIdNum":"4984849884456165164"},{"pName":"8","phone":"18275417177","pIdNum":"4984849884456165164"},{"pName":"9","phone":"18275417177","pIdNum":"4984849884456165164"},{"pName":"10","phone":"18275417177","pIdNum":"4984849884456165164"},{"pName":"11","phone":"18275417177","pIdNum":"4984849884456165164"},{"pName":"12","phone":"18275417177","pIdNum":"4984849884456165164"},{"pName":"13","phone":"18275417177","pIdNum":"4984849884456165164"},{"pName":"14","phone":"18275417177","pIdNum":"4984849884456165164"},{"pName":"15","phone":"18275417177","pIdNum":"4984849884456165164"},{"pName":"","phone":"18275417177","pIdNum":""},{"pName":"","phone":"18275417177","pIdNum":""}]
     */

    private String no;
    /**
     * pName : 阿斯五
     * phone : 18275417177
     * pIdNum : 53292319950825095X
     */

    private List<PassengerBean> passenger;

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public List<PassengerBean> getPassenger() {
        return passenger;
    }

    public void setPassenger(List<PassengerBean> passenger) {
        this.passenger = passenger;
    }

    public static class PassengerBean implements Serializable{
        private String pName;
        private String phone;
        private String pIdNum;
        private boolean isChecked;



        public boolean isChecked() {
            return isChecked;
        }

        public void setChecked(boolean checked) {
            isChecked = checked;
        }

        public String getPName() {
            return pName;
        }

        public void setPName(String pName) {
            this.pName = pName;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getPIdNum() {
            if (pIdNum != null) {
                String head = pIdNum.substring(0, 6);
                String end = pIdNum.substring(14, 17);
                return head + "**********" + end+pIdNum.substring(17);
            }

            return pIdNum;
        }

        public void setPIdNum(String pIdNum) {
            this.pIdNum = pIdNum;
        }

        @Override
        public String toString() {
            return "PassengerBean{" +
                    "pName='" + pName + '\'' +
                    ", phone='" + phone + '\'' +
                    ", pIdNum='" + pIdNum + '\'' +
                    ", isChecked=" + isChecked +
                    '}';
        }
    }
}

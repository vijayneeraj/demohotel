package com.example.neeraj.demohotel.rest.models;

import java.util.List;

/**
 * @author neeraj on 22/11/18.
 */
public class GuestModel {


    /**
     * result : 1
     * message : Guest Data
     * data : [{"id":"1","name":"Demo","room_no":"100","arrival_date_time":"2018-11-19 04:00:00","departure_date_time":"2018-11-20 04:30:00"},{"id":"2","name":"","room_no":"","arrival_date_time":"0000-00-00 00:00:00","departure_date_time":"0000-00-00 00:00:00"},{"id":"3","name":"Demo","room_no":"100","arrival_date_time":"2018-11-19 04:00:00","departure_date_time":"2018-11-20 04:30:00"}]
     */

    private String result;
    private String message;
    private List<DataBean> data;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1
         * name : Demo
         * room_no : 100
         * arrival_date_time : 2018-11-19 04:00:00
         * departure_date_time : 2018-11-20 04:30:00
         */

        private String id;
        private String name;
        private String room_no;
        private String arrival_date_time;
        private String departure_date_time;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRoom_no() {
            return room_no;
        }

        public void setRoom_no(String room_no) {
            this.room_no = room_no;
        }

        public String getArrival_date_time() {
            return arrival_date_time;
        }

        public void setArrival_date_time(String arrival_date_time) {
            this.arrival_date_time = arrival_date_time;
        }

        public String getDeparture_date_time() {
            return departure_date_time;
        }

        public void setDeparture_date_time(String departure_date_time) {
            this.departure_date_time = departure_date_time;
        }
    }
}

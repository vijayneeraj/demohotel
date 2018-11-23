package com.example.neeraj.demohotel.rest.models;

/**
 * @author neeraj on 19/11/18.
 */
public class SignupModel {

    /**
     * result : 1
     * message : Register Succesfull
     * data : {"id":7,"name":"myhotel","email":"hotel@hotel.com","phonenumber":"852588558","address":"ghddudcobo"}
     */

    private String result;
    private String message;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 7
         * name : myhotel
         * email : hotel@hotel.com
         * phonenumber : 852588558
         * address : ghddudcobo
         */

        private String id;
        private String name;
        private String email;
        private String phonenumber;
        private String address;

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

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhonenumber() {
            return phonenumber;
        }

        public void setPhonenumber(String phonenumber) {
            this.phonenumber = phonenumber;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }
}

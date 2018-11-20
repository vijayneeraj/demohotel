package com.example.neeraj.demohotel.rest.models;

/**
 * @author neeraj on 19/11/18.
 */
public class LoginModel {


    /**
     * result : 1
     * message : {"id":"4","name":"test","email":"test@gmail.com","phonenumber":"2147483647","address":"cdvfzsdffsfdsfsfdsfs"}
     */

    private String result;
    private MessageBean message;
    //String message;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public MessageBean getMessage() {
        return message;
    }

    public void setMessage(MessageBean message) {
        this.message = message;
    }

    public static class MessageBean {
        /**
         * id : 4
         * name : test
         * email : test@gmail.com
         * phonenumber : 2147483647
         * address : cdvfzsdffsfdsfsfdsfs
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

package com.example.myron.heyihui.com.example.myron.heyihui.Data;

import java.util.List;

/**
 * Created by Jason on 2017/11/2.
 */

public class User {
    public static String userName = "";
    private UserData data;
    private String message;
    private int status;
    private int total;
    public void setData(UserData data) {
        this.data = data;
    }
    public UserData getData() {
        return data;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    public int getStatus() {
        return status;
    }

    public void setTotal(int total) {
        this.total = total;
    }
    public int getTotal() {
        return total;
    }

    public class UserData {
        private String address;
        private String creationtime;
        private int creatorid;
        private int enable;
        private int id;
        private String k;  // 用户校验码，自动登录
        private String name;// 用户名
        private String numbers;
        private int orgid;
        private List<String> orgs;
        private String password;
        private String phone;  //手机号
        private String pushkey;
        private String r;
        private int type;
        private int updateid;
        private String updatetime;
        private int usertype;
        private List<String> usertypes;
        public void setAddress(String address) {
            this.address = address;
        }
        public String getAddress() {
            return address;
        }

        public void setCreationtime(String creationtime) {
            this.creationtime = creationtime;
        }
        public String getCreationtime() {
            return creationtime;
        }

        public void setCreatorid(int creatorid) {
            this.creatorid = creatorid;
        }
        public int getCreatorid() {
            return creatorid;
        }

        public void setEnable(int enable) {
            this.enable = enable;
        }
        public int getEnable() {
            return enable;
        }

        public void setId(int id) {
            this.id = id;
        }
        public int getId() {
            return id;
        }

        public void setK(String k) {
            this.k = k;
        }
        public String getK() {
            return k;
        }

        public void setName(String name) {
            this.name = name;
        }
        public String getName() {
            return name;
        }

        public void setNumbers(String numbers) {
            this.numbers = numbers;
        }
        public String getNumbers() {
            return numbers;
        }

        public void setOrgid(int orgid) {
            this.orgid = orgid;
        }
        public int getOrgid() {
            return orgid;
        }

        public void setOrgs(List<String> orgs) {
            this.orgs = orgs;
        }
        public List<String> getOrgs() {
            return orgs;
        }

        public void setPassword(String password) {
            this.password = password;
        }
        public String getPassword() {
            return password;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
        public String getPhone() {
            return phone;
        }

        public void setPushkey(String pushkey) {
            this.pushkey = pushkey;
        }
        public String getPushkey() {
            return pushkey;
        }

        public void setR(String r) {
            this.r = r;
        }
        public String getR() {
            return r;
        }

        public void setType(int type) {
            this.type = type;
        }
        public int getType() {
            return type;
        }

        public void setUpdateid(int updateid) {
            this.updateid = updateid;
        }
        public int getUpdateid() {
            return updateid;
        }

        public void setUpdatetime(String updatetime) {
            this.updatetime = updatetime;
        }
        public String getUpdatetime() {
            return updatetime;
        }

        public void setUsertype(int usertype) {
            this.usertype = usertype;
        }
        public int getUsertype() {
            return usertype;
        }

        public void setUsertypes(List<String> usertypes) {
            this.usertypes = usertypes;
        }
        public List<String> getUsertypes() {
            return usertypes;
        }

    }
    @Override
    public String toString() {
        return "User{" +
                "data=" + data +
                ", message='" + message + '\'' +
                ", status=" + status +
                ", total=" + total +
                '}';
    }
}
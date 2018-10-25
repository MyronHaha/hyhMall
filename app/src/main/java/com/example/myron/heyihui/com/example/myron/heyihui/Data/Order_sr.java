/**
 * Copyright 2017 bejson.com
 */
package com.example.myron.heyihui.com.example.myron.heyihui.Data;
import java.util.List;

/**
 * Auto-generated: 2017-11-08 11:14:47
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Order_sr {

    private List<Data> data;
    private String message;
    private int status;
    private int total;
    public void setData(List<Data> data) {
        this.data = data;
    }
    public List<Data> getData() {
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

    /**
     * Copyright 2017 bejson.com
     */
    /**
     * Auto-generated: 2017-11-08 11:14:47
     *
     * @author bejson.com (i@bejson.com)
     * @website http://www.bejson.com/java2pojo/
     */
    public class Data {

        private int accountId;
        private Double amount;
        private String creationTime;
        private int creatorId;
        private int direction;
        private int id;
        private String name;
        private String realTime;
        private String remark;
        private int s;
        private int sourceId;
        private int state;
        private int type;
        private int updateId;
        private String updateTime;
        private int userId;
        private String userName;
        public void setAccountId(int accountId) {
            this.accountId = accountId;
        }
        public int getAccountId() {
            return accountId;
        }

        public void setAmount(Double amount) {
            this.amount = amount;
        }
        public Double getAmount() {
            return amount;
        }

        public void setCreationTime(String creationTime) {
            this.creationTime = creationTime;
        }
        public String getCreationTime() {
            return creationTime;
        }

        public void setCreatorId(int creatorId) {
            this.creatorId = creatorId;
        }
        public int getCreatorId() {
            return creatorId;
        }

        public void setDirection(int direction) {
            this.direction = direction;
        }
        public int getDirection() {
            return direction;
        }

        public void setId(int id) {
            this.id = id;
        }
        public int getId() {
            return id;
        }

        public void setName(String name) {
            this.name = name;
        }
        public String getName() {
            return name;
        }

        public void setRealTime(String realTime) {
            this.realTime = realTime;
        }
        public String getRealTime() {
            return realTime;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }
        public String getRemark() {
            return remark;
        }

        public void setS(int s) {
            this.s = s;
        }
        public int getS() {
            return s;
        }

        public void setSourceId(int sourceId) {
            this.sourceId = sourceId;
        }
        public int getSourceId() {
            return sourceId;
        }

        public void setState(int state) {
            this.state = state;
        }
        public int getState() {
            return state;
        }

        public void setType(int type) {
            this.type = type;
        }
        public int getType() {
            return type;
        }

        public void setUpdateId(int updateId) {
            this.updateId = updateId;
        }
        public int getUpdateId() {
            return updateId;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }
        public String getUpdateTime() {
            return updateTime;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }
        public int getUserId() {
            return userId;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }
        public String getUserName() {
            return userName;
        }

    }
}
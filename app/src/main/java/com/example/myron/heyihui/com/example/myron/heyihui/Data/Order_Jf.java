/**
 * Copyright 2017 bejson.com
 */
package com.example.myron.heyihui.com.example.myron.heyihui.Data;
import java.util.List;

/**
 * Auto-generated: 2017-11-08 14:16:10
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Order_Jf {

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
    public class Data {

        private String code;
        private String content;
        private String creationTime;
        private int creatorId;
        private double credit; //积分
        private String dataDate;
        private int empId;
        private String empName;
        private String ext;
        private int id;
        private int infoId;
        private int itemId;
        private String itemName;
        private String name;
        private int sourceType;
        private int state;
        private int updateId;
        private String updateTime;
        public void setCode(String code) {
            this.code = code;
        }
        public String getCode() {
            return code;
        }

        public void setContent(String content) {
            this.content = content;
        }
        public String getContent() {
            return content;
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

        public void setCredit(double credit) {
            this.credit = credit;
        }
        public double getCredit() {
            return credit;
        }

        public void setDataDate(String dataDate) {
            this.dataDate = dataDate;
        }
        public String getDataDate() {
            return dataDate;
        }

        public void setEmpId(int empId) {
            this.empId = empId;
        }
        public int getEmpId() {
            return empId;
        }

        public void setEmpName(String empName) {
            this.empName = empName;
        }
        public String getEmpName() {
            return empName;
        }

        public void setExt(String ext) {
            this.ext = ext;
        }
        public String getExt() {
            return ext;
        }

        public void setId(int id) {
            this.id = id;
        }
        public int getId() {
            return id;
        }

        public void setInfoId(int infoId) {
            this.infoId = infoId;
        }
        public int getInfoId() {
            return infoId;
        }

        public void setItemId(int itemId) {
            this.itemId = itemId;
        }
        public int getItemId() {
            return itemId;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }
        public String getItemName() {
            return itemName;
        }

        public void setName(String name) {
            this.name = name;
        }
        public String getName() {
            return name;
        }

        public void setSourceType(int sourceType) {
            this.sourceType = sourceType;
        }
        public int getSourceType() {
            return sourceType;
        }

        public void setState(int state) {
            this.state = state;
        }
        public int getState() {
            return state;
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

    }
}
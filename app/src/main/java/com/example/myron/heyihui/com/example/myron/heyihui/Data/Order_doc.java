/**
 * Copyright 2017 bejson.com
 */
package com.example.myron.heyihui.com.example.myron.heyihui.Data;
import java.util.List;

//订单跟踪item
public class Order_doc {

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

        private String content;
        private String creationTime;
        private int creatorId;
        private String dataDate;
        private String ext;
        private int id;
        private int orderId;
        private int sourceId;
        private String sourceName;
        private String sourceParam;
        private int sourceType;
        private int state;
        private int type;
        private int updateId;
        private String updateTime;
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

        public void setDataDate(String dataDate) {
            this.dataDate = dataDate;
        }
        public String getDataDate() {
            return dataDate;
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

        public void setOrderId(int orderId) {
            this.orderId = orderId;
        }
        public int getOrderId() {
            return orderId;
        }

        public void setSourceId(int sourceId) {
            this.sourceId = sourceId;
        }
        public int getSourceId() {
            return sourceId;
        }

        public void setSourceName(String sourceName) {
            this.sourceName = sourceName;
        }
        public String getSourceName() {
            return sourceName;
        }

        public void setSourceParam(String sourceParam) {
            this.sourceParam = sourceParam;
        }
        public String getSourceParam() {
            return sourceParam;
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

    }
}
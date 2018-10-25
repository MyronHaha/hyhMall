/**
 * Copyright 2017 bejson.com
 */
package com.example.myron.heyihui.com.example.myron.heyihui.Data;
import java.util.List;

/**
 * Auto-generated: 2017-11-08 20:8:30
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Msg {

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
     * Auto-generated: 2017-11-08 20:8:30
     *
     * @author bejson.com (i@bejson.com)
     * @website http://www.bejson.com/java2pojo/
     */
    public class Data {
        private boolean ishow = false;
        private String content;
        private String creationTime;
        private int creatorId;
        private int id;
        private int msgState;
        private String msgStateText;
        private int msgType;
        private String readDate;
        private int receiveId;
        private String receiveName;
        private int sourceId;
        private String sourceParam;
        private int sourceType;
        private int state;
        private String stateText;
        private String title;
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

        public void setId(int id) {
            this.id = id;
        }
        public int getId() {
            return id;
        }

        public void setMsgState(int msgState) {
            this.msgState = msgState;
        }
        public int getMsgState() {
            return msgState;
        }

        public void setMsgStateText(String msgStateText) {
            this.msgStateText = msgStateText;
        }
        public String getMsgStateText() {
            return msgStateText;
        }

        public void setMsgType(int msgType) {
            this.msgType = msgType;
        }
        public int getMsgType() {
            return msgType;
        }

        public void setReadDate(String readDate) {
            this.readDate = readDate;
        }
        public String getReadDate() {
            return readDate;
        }

        public void setReceiveId(int receiveId) {
            this.receiveId = receiveId;
        }
        public int getReceiveId() {
            return receiveId;
        }

        public void setReceiveName(String receiveName) {
            this.receiveName = receiveName;
        }
        public String getReceiveName() {
            return receiveName;
        }

        public void setSourceId(int sourceId) {
            this.sourceId = sourceId;
        }
        public int getSourceId() {
            return sourceId;
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

        public void setStateText(String stateText) {
            this.stateText = stateText;
        }
        public String getStateText() {
            return stateText;
        }

        public void setTitle(String title) {
            this.title = title;
        }
        public String getTitle() {
            return title;
        }

        public boolean isIshow() {
            return ishow;
        }

        public void setIshow(boolean ishow) {
            this.ishow = ishow;
        }
    }
}
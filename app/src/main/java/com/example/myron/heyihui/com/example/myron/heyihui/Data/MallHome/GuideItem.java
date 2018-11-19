package com.example.myron.heyihui.com.example.myron.heyihui.Data.MallHome;

import java.util.Date;
import java.util.List;

import java.util.ArrayList;
import java.util.List;
public class GuideItem
{
    private List<Data> data;

    private String message;

    private int status;

    private int total;

    public void setData(List<Data> data){
        this.data = data;
    }
    public List<Data> getData(){
        return this.data;
    }
    public void setMessage(String message){
        this.message = message;
    }
    public String getMessage(){
        return this.message;
    }
    public void setStatus(int status){
        this.status = status;
    }
    public int getStatus(){
        return this.status;
    }
    public void setTotal(int total){
        this.total = total;
    }
    public int getTotal(){
        return this.total;
    }

    public static class Data
    {
        private List<String> categorys;

        private String creationTime;

        private int creatorId;

        private int id;

        private String img;

        private String name;

        private int sort;

        private int sourceId;

        private String sourceName;

        private int sourceType;

        private int state;

        private int updateId;

        private String updateTime;

        public void setCategorys(List<String> categorys){
            this.categorys = categorys;
        }
        public List<String> getCategorys(){
            return this.categorys;
        }
        public void setCreationTime(String creationTime){
            this.creationTime = creationTime;
        }
        public String getCreationTime(){
            return this.creationTime;
        }
        public void setCreatorId(int creatorId){
            this.creatorId = creatorId;
        }
        public int getCreatorId(){
            return this.creatorId;
        }
        public void setId(int id){
            this.id = id;
        }
        public int getId(){
            return this.id;
        }
        public void setImg(String img){
            this.img = img;
        }
        public String getImg(){
            return this.img;
        }
        public void setName(String name){
            this.name = name;
        }
        public String getName(){
            return this.name;
        }
        public void setSort(int sort){
            this.sort = sort;
        }
        public int getSort(){
            return this.sort;
        }
        public void setSourceId(int sourceId){
            this.sourceId = sourceId;
        }
        public int getSourceId(){
            return this.sourceId;
        }
        public void setSourceName(String sourceName){
            this.sourceName = sourceName;
        }
        public String getSourceName(){
            return this.sourceName;
        }
        public void setSourceType(int sourceType){
            this.sourceType = sourceType;
        }
        public int getSourceType(){
            return this.sourceType;
        }
        public void setState(int state){
            this.state = state;
        }
        public int getState(){
            return this.state;
        }
        public void setUpdateId(int updateId){
            this.updateId = updateId;
        }
        public int getUpdateId(){
            return this.updateId;
        }
        public void setUpdateTime(String updateTime){
            this.updateTime = updateTime;
        }
        public String getUpdateTime(){
            return this.updateTime;
        }
    }

}
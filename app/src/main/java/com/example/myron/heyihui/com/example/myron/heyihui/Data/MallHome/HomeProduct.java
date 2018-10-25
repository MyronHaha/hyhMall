package com.example.myron.heyihui.com.example.myron.heyihui.Data.MallHome;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

/**
 * Created by Jason on 2018/10/22.
 */

public class HomeProduct {
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
    public static class Data {
        private String content;
        private String creationtime;
        private int creatorid;
        private String enddate;
        private int hit;
        private int id;
        private String img;
        private String img1;
        private int iscommend;
        private String keys;
        private String labels;
        private String name;
        private String numbers;
        private int sort;
        private int sourceid;
        private String sourcename;
        private String sourceparam;
        private int sourcetype;
        private String startdate;
        private int state;
        private int type;
        private int updateid;
        private String updatetime;
        public void setContent(String content) {
            this.content = content;
        }
        public String getContent() {
            return content;
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

        public void setEnddate(String enddate) {
            this.enddate = enddate;
        }
        public String getEnddate() {
            return enddate;
        }

        public void setHit(int hit) {
            this.hit = hit;
        }
        public int getHit() {
            return hit;
        }

        public void setId(int id) {
            this.id = id;
        }
        public int getId() {
            return id;
        }

        public void setImg(String img) {
            this.img = img;
        }
        public String getImg() {
            return img;
        }

        public void setImg1(String img1) {
            this.img1 = img1;
        }
        public String getImg1() {
            return img1;
        }

        public void setIscommend(int iscommend) {
            this.iscommend = iscommend;
        }
        public int getIscommend() {
            return iscommend;
        }

        public void setKeys(String keys) {
            this.keys = keys;
        }
        public String getKeys() {
            return keys;
        }

        public void setLabels(String labels) {
            this.labels = labels;
        }
        public String getLabels() {
            return labels;
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

        public void setSort(int sort) {
            this.sort = sort;
        }
        public int getSort() {
            return sort;
        }

        public void setSourceid(int sourceid) {
            this.sourceid = sourceid;
        }
        public int getSourceid() {
            return sourceid;
        }

        public void setSourcename(String sourcename) {
            this.sourcename = sourcename;
        }
        public String getSourcename() {
            return sourcename;
        }

        public void setSourceparam(String sourceparam) {
            this.sourceparam = sourceparam;
        }
        public String getSourceparam() {
            return sourceparam;
        }

        public void setSourcetype(int sourcetype) {
            this.sourcetype = sourcetype;
        }
        public int getSourcetype() {
            return sourcetype;
        }

        public void setStartdate(String startdate) {
            this.startdate = startdate;
        }
        public String getStartdate() {
            return startdate;
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

    }
}

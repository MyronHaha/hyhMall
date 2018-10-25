package com.example.myron.heyihui.com.example.myron.heyihui.Data;

import java.util.List;

/**
 * Created by Jason on 2017/11/3.
 */

public class productSerilize {
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


    public class Data
    {
        private int hit;

        private int id;

        private String img;

        private String labels;

        private String name;

        private String numbers;

        private int parentId;

        private int sort;

        private int state;

        public void setHit(int hit){
            this.hit = hit;
        }
        public int getHit(){
            return this.hit;
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
        public void setLabels(String labels){
            this.labels = labels;
        }
        public String getLabels(){
            return this.labels;
        }
        public void setName(String name){
            this.name = name;
        }
        public String getName(){
            return this.name;
        }
        public void setNumbers(String numbers){
            this.numbers = numbers;
        }
        public String getNumbers(){
            return this.numbers;
        }
        public void setParentId(int parentId){
            this.parentId = parentId;
        }
        public int getParentId(){
            return this.parentId;
        }
        public void setSort(int sort){
            this.sort = sort;
        }
        public int getSort(){
            return this.sort;
        }
        public void setState(int state){
            this.state = state;
        }
        public int getState(){
            return this.state;
        }
    }
}

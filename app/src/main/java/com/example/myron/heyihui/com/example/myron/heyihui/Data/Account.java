package com.example.myron.heyihui.com.example.myron.heyihui.Data;

import java.util.List;

public class Account
{
    public class Data
    {
        private List<String> accountDetails;

        private double amount;

        private double amount1;

        private double balance;

        private int id;

        private int limit;

        private String name;

        private int userId;

        public void setAccountDetails(List<String> accountDetails){
            this.accountDetails = accountDetails;
        }
        public List<String> getAccountDetails(){
            return this.accountDetails;
        }
        public void setAmount(double amount){
            this.amount = amount;
        }
        public double getAmount(){
            return this.amount;
        }
        public void setAmount1(double amount1){
            this.amount1 = amount1;
        }
        public double getAmount1(){
            return this.amount1;
        }
        public void setBalance(double balance){
            this.balance = balance;
        }
        public double getBalance(){
            return this.balance;
        }
        public void setId(int id){
            this.id = id;
        }
        public int getId(){
            return this.id;
        }
        public void setLimit(int limit){
            this.limit = limit;
        }
        public int getLimit(){
            return this.limit;
        }
        public void setName(String name){
            this.name = name;
        }
        public String getName(){
            return this.name;
        }
        public void setUserId(int userId){
            this.userId = userId;
        }
        public int getUserId(){
            return this.userId;
        }
    }
    private Data data;

    private String message;

    private int status;

    private int total;

    public void setData(Data data){
        this.data = data;
    }
    public Data getData(){
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
}

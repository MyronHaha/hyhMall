/**
 * Copyright 2017 bejson.com
 */
package com.example.myron.heyihui.com.example.myron.heyihui.Data;

import java.util.List;

public class Order_Item_detail
{
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

    public class Data
    {
        private String amount;

        private String bidprice;

        private String brand;

        @Override
        public String toString() {
            return "Data{" +
                    "amount=" + amount +
                    ", bidprice='" + bidprice + '\'' +
                    ", brand='" + brand + '\'' +
                    ", catalog='" + catalog + '\'' +
                    ", categorys=" + categorys +
                    ", cg='" + cg + '\'' +
                    ", classifies=" + classifies +
                    ", classify='" + classify + '\'' +
                    ", code='" + code + '\'' +
                    ", companyId=" + companyId +
                    ", content='" + content + '\'' +
                    ", creationTime='" + creationTime + '\'' +
                    ", creatorId=" + creatorId +
                    ", cst='" + cst + '\'' +
                    ", cuname='" + cuname + '\'' +
                    ", id=" + id +
                    ", img='" + img + '\'' +
                    ", info='" + info + '\'' +
                    ", labels='" + labels + '\'' +
                    ", labelss=" + labelss +
                    ", levels=" + levels +
                    ", license='" + license + '\'' +
                    ", lines=" + lines +
                    ", manufacturer='" + manufacturer + '\'' +
                    ", name='" + name + '\'' +
                    ", numbers='" + numbers + '\'' +
                    ", packUint='" + packUint + '\'' +
                    ", packaging='" + packaging + '\'' +
                    ", price=" + price +
                    ", price2='" + price2 + '\'' +
                    ", productline='" + productline + '\'' +
                    ", productlineId=" + productlineId +
                    ", productname='" + productname + '\'' +
                    ", quantity='" + quantity + '\'' +
                    ", remark='" + remark + '\'' +
                    ", serialImg='" + serialImg + '\'' +
                    ", serialNumber='" + serialNumber + '\'' +
                    ", shortName='" + shortName + '\'' +
                    ", sort=" + sort +
                    ", spec='" + spec + '\'' +
                    ", taxRate='" + taxRate + '\'' +
                    ", unit='" + unit + '\'' +
                    ", updateId=" + updateId +
                    ", updateTime='" + updateTime + '\'' +
                    ", uuname='" + uuname + '\'' +
                    ", warrant='" + warrant + '\'' +
                    ", warrantDate='" + warrantDate + '\'' +
                    ", xs='" + xs + '\'' +
                    '}';
        }

        private String catalog;

        private List<String> categorys;

        private String cg;

        private List<String> classifies;

        private String classify;

        private String code;

        private int companyId;

        private String content;

        private String creationTime;

        private int creatorId;

        private String cst;

        private String cuname;

        private int id;

        private String img;

        private String info;

        private String labels;

        private List<String> labelss;

        private int levels;

        private String license;

        private List<String> lines;

        private String manufacturer;

        private String name;

        private String numbers;

        private String packUint;

        private String packaging;

        private double price;

        private String price2;

        private String productline;

        private int productlineId;

        private String productname;

        private String quantity;

        private String remark;

        private String serialImg;

        private String serialNumber;

        private String shortName;

        private int sort;

        private String spec;

        private String taxRate;

        private String unit;

        private int updateId;

        private String updateTime;

        private String uuname;

        private String warrant;

        private String warrantDate;

        private String xs;

        public void setAmount(String amount){
            this.amount = amount;
        }
        public String getAmount(){
            return this.amount;
        }
        public void setBidprice(String bidprice){
            this.bidprice = bidprice;
        }
        public String getBidprice(){
            return this.bidprice;
        }
        public void setBrand(String brand){
            this.brand = brand;
        }
        public String getBrand(){
            return this.brand;
        }
        public void setCatalog(String catalog){
            this.catalog = catalog;
        }
        public String getCatalog(){
            return this.catalog;
        }
        public void setCategorys(List<String> categorys){
            this.categorys = categorys;
        }
        public List<String> getCategorys(){
            return this.categorys;
        }
        public void setCg(String cg){
            this.cg = cg;
        }
        public String getCg(){
            return this.cg;
        }
        public void setClassifies(List<String> classifies){
            this.classifies = classifies;
        }
        public List<String> getClassifies(){
            return this.classifies;
        }
        public void setClassify(String classify){
            this.classify = classify;
        }
        public String getClassify(){
            return this.classify;
        }
        public void setCode(String code){
            this.code = code;
        }
        public String getCode(){
            return this.code;
        }
        public void setCompanyId(int companyId){
            this.companyId = companyId;
        }
        public int getCompanyId(){
            return this.companyId;
        }
        public void setContent(String content){
            this.content = content;
        }
        public String getContent(){
            return this.content;
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
        public void setCst(String cst){
            this.cst = cst;
        }
        public String getCst(){
            return this.cst;
        }
        public void setCuname(String cuname){
            this.cuname = cuname;
        }
        public String getCuname(){
            return this.cuname;
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
        public void setInfo(String info){
            this.info = info;
        }
        public String getInfo(){
            return this.info;
        }
        public void setLabels(String labels){
            this.labels = labels;
        }
        public String getLabels(){
            return this.labels;
        }
        public void setLabelss(List<String> labelss){
            this.labelss = labelss;
        }
        public List<String> getLabelss(){
            return this.labelss;
        }
        public void setLevels(int levels){
            this.levels = levels;
        }
        public int getLevels(){
            return this.levels;
        }
        public void setLicense(String license){
            this.license = license;
        }
        public String getLicense(){
            return this.license;
        }
        public void setLines(List<String> lines){
            this.lines = lines;
        }
        public List<String> getLines(){
            return this.lines;
        }
        public void setManufacturer(String manufacturer){
            this.manufacturer = manufacturer;
        }
        public String getManufacturer(){
            return this.manufacturer;
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
        public void setPackUint(String packUint){
            this.packUint = packUint;
        }
        public String getPackUint(){
            return this.packUint;
        }
        public void setPackaging(String packaging){
            this.packaging = packaging;
        }
        public String getPackaging(){
            return this.packaging;
        }
        public void setPrice(double price){
            this.price = price;
        }
        public double getPrice(){
            return this.price;
        }
        public void setPrice2(String price2){
            this.price2 = price2;
        }
        public String getPrice2(){
            return this.price2;
        }
        public void setProductline(String productline){
            this.productline = productline;
        }
        public String getProductline(){
            return this.productline;
        }
        public void setProductlineId(int productlineId){
            this.productlineId = productlineId;
        }
        public int getProductlineId(){
            return this.productlineId;
        }
        public void setProductname(String productname){
            this.productname = productname;
        }
        public String getProductname(){
            return this.productname;
        }
        public void setQuantity(String quantity){
            this.quantity = quantity;
        }
        public String getQuantity(){
            return this.quantity;
        }
        public void setRemark(String remark){
            this.remark = remark;
        }
        public String getRemark(){
            return this.remark;
        }
        public void setSerialImg(String serialImg){
            this.serialImg = serialImg;
        }
        public String getSerialImg(){
            return this.serialImg;
        }
        public void setSerialNumber(String serialNumber){
            this.serialNumber = serialNumber;
        }
        public String getSerialNumber(){
            return this.serialNumber;
        }
        public void setShortName(String shortName){
            this.shortName = shortName;
        }
        public String getShortName(){
            return this.shortName;
        }
        public void setSort(int sort){
            this.sort = sort;
        }
        public int getSort(){
            return this.sort;
        }
        public void setSpec(String spec){
            this.spec = spec;
        }
        public String getSpec(){
            return this.spec;
        }
        public void setTaxRate(String taxRate){
            this.taxRate = taxRate;
        }
        public String getTaxRate(){
            return this.taxRate;
        }
        public void setUnit(String unit){
            this.unit = unit;
        }
        public String getUnit(){
            return this.unit;
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
        public void setUuname(String uuname){
            this.uuname = uuname;
        }
        public String getUuname(){
            return this.uuname;
        }
        public void setWarrant(String warrant){
            this.warrant = warrant;
        }
        public String getWarrant(){
            return this.warrant;
        }
        public void setWarrantDate(String warrantDate){
            this.warrantDate = warrantDate;
        }
        public String getWarrantDate(){
            return this.warrantDate;
        }
        public void setXs(String xs){
            this.xs = xs;
        }
        public String getXs(){
            return this.xs;
        }
    }
}

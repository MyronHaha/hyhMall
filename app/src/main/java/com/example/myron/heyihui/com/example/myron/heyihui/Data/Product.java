package com.example.myron.heyihui.com.example.myron.heyihui.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 2017-11-03 11:49:55
 *          {
 "amount": null,
 "bidprice": null,
 "brand": "番禺万福",
 "catalog": "",
 "categorys": [],
 "cg": "",
 "classifies": [],
 "classify": "",
 "code": "20克",
 "companyId": 0,
 "content": "",
 "creationTime": null,
 "creatorId": 0,
 "cst": "",
 "cuname": "",
 "id": 4330,
 "img": "uploadFile/image/fffa6a1f-956b-41ac-8d29-e759d293766c.png",
 "info": null,
 "labels": "门诊,护理部,急诊科",
 "labelss": [],
 "levels": 3,
 "license": "",
 "lines": [],
 "manufacturer": "广州市番禺万福卫生用品有限公司",
 "name": "医用脱脂棉",
 "numbers": "G4330",
 "packUint": "",
 "packaging": "",
 "price": 0.34476,
 "price2": null,
 "productline": "",
 "productlineId": 0,
 "productname": "医用脱脂棉",
 "quantity": null,
 "remark": "",
 "serialImg": "",
 "serialNumber": "",
 "shortName": "医用脱脂棉",
 "sort": 0,
 "spec": "20包/中包 20中包 400包/箱",
 "taxRate": 0.17,
 "unit": "包",
 "updateId": 0,
 "updateTime": null,
 "uuname": "",
 "warrant": "",
 "warrantDate": null,
 "xs": ""
 },
 */
public class Product {

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
     * 2017-11-03 11:49:55
     */
    public class Data implements Serializable{

        private String amount;
        private String bidprice;
        private String brand;
        private String catalog;
        private List<String> categorys;
        private String cg;
        private List<String> classifies;
        private String classify;
        private String code;

        private int companyid;
        private String content;

        private String creationtime;

        private int creatorid;
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

        private String packuint;
        private String packaging;
        private double price;
        private String price2;
        private String productline;

        private int productlineid;
        private String productname;
        private String quantity;
        private String remark;

        private String serialimg;

        private String serialnumber;

        private String shortname;
        private int sort;
        private String spec;

        private double taxrate;
        private String unit;

        private int updateid;

        private String updatetime;
        private String uuname;
        private String warrant;
        private String warrantdate;
        private String xs;

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getAmount() {
            return amount;
        }

        public void setBidprice(String bidprice) {
            this.bidprice = bidprice;
        }

        public String getBidprice() {
            return bidprice;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public String getBrand() {
            return brand;
        }

        public void setCatalog(String catalog) {
            this.catalog = catalog;
        }

        public String getCatalog() {
            return catalog;
        }

        public void setCategorys(List<String> categorys) {
            this.categorys = categorys;
        }

        public List<String> getCategorys() {
            return categorys;
        }

        public void setCg(String cg) {
            this.cg = cg;
        }

        public String getCg() {
            return cg;
        }

        public void setClassifies(List<String> classifies) {
            this.classifies = classifies;
        }

        public List<String> getClassifies() {
            return classifies;
        }

        public void setClassify(String classify) {
            this.classify = classify;
        }

        public String getClassify() {
            return classify;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }

        public void setCompanyid(int companyid) {
            this.companyid = companyid;
        }

        public int getCompanyid() {
            return companyid;
        }

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

        public void setCst(String cst) {
            this.cst = cst;
        }

        public String getCst() {
            return cst;
        }

        public void setCuname(String cuname) {
            this.cuname = cuname;
        }

        public String getCuname() {
            return cuname;
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

        public void setInfo(String info) {
            this.info = info;
        }

        public String getInfo() {
            return info;
        }

        public void setLabels(String labels) {
            this.labels = labels;
        }

        public String getLabels() {
            return labels;
        }

        public void setLabelss(List<String> labelss) {
            this.labelss = labelss;
        }

        public List<String> getLabelss() {
            return labelss;
        }

        public void setLevels(int levels) {
            this.levels = levels;
        }

        public int getLevels() {
            return levels;
        }

        public void setLicense(String license) {
            this.license = license;
        }

        public String getLicense() {
            return license;
        }

        public void setLines(List<String> lines) {
            this.lines = lines;
        }

        public List<String> getLines() {
            return lines;
        }

        public void setManufacturer(String manufacturer) {
            this.manufacturer = manufacturer;
        }

        public String getManufacturer() {
            return manufacturer;
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

        public void setPackuint(String packuint) {
            this.packuint = packuint;
        }

        public String getPackuint() {
            return packuint;
        }

        public void setPackaging(String packaging) {
            this.packaging = packaging;
        }

        public String getPackaging() {
            return packaging;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice2(String price2) {
            this.price2 = price2;
        }

        public String getPrice2() {
            return price2;
        }

        public void setProductline(String productline) {
            this.productline = productline;
        }

        public String getProductline() {
            return productline;
        }

        public void setProductlineid(int productlineid) {
            this.productlineid = productlineid;
        }

        public int getProductlineid() {
            return productlineid;
        }

        public void setProductname(String productname) {
            this.productname = productname;
        }

        public String getProductname() {
            return productname;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }

        public String getQuantity() {
            return quantity;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getRemark() {
            return remark;
        }

        public void setSerialimg(String serialimg) {
            this.serialimg = serialimg;
        }

        public String getSerialimg() {
            return serialimg;
        }

        public void setSerialnumber(String serialnumber) {
            this.serialnumber = serialnumber;
        }

        public String getSerialnumber() {
            return serialnumber;
        }

        public void setShortname(String shortname) {
            this.shortname = shortname;
        }

        public String getShortname() {
            return shortname;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public int getSort() {
            return sort;
        }

        public void setSpec(String spec) {
            this.spec = spec;
        }

        public String getSpec() {
            return spec;
        }

        public void setTaxrate(double taxrate) {
            this.taxrate = taxrate;
        }

        public double getTaxrate() {
            return taxrate;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getUnit() {
            return unit;
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

        public void setUuname(String uuname) {
            this.uuname = uuname;
        }

        public String getUuname() {
            return uuname;
        }

        public void setWarrant(String warrant) {
            this.warrant = warrant;
        }

        public String getWarrant() {
            return warrant;
        }

        public void setWarrantdate(String warrantdate) {
            this.warrantdate = warrantdate;
        }

        public String getWarrantdate() {
            return warrantdate;
        }

        public void setXs(String xs) {
            this.xs = xs;
        }

        public String getXs() {
            return xs;
        }

    }
}
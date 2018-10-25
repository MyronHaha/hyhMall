package com.example.myron.heyihui.com.example.myron.heyihui.Data;

import java.util.List;

public class Order_Item {

    private Data data;
    private String message;
    private int status;
    private int total;
    public void setData(Data data) {
        this.data = data;
    }
    public Data getData() {
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
        /**
         */
        public class Details {

            private double amount;
            private String content;
            private int flag;
            private String goodsCode;
            private int goodsId;
            private String goodsName;
            private int id;
            private String manufacturer;
            private int orderId;
            private double price;
            private String price1;
            private String price2;
            private int qty;
            private String rebate;
            private String sales;
            private String spec;
            private String unit;
            public void setAmount(double amount) {
                this.amount = amount;
            }
            public double getAmount() {
                return amount;
            }

            public void setContent(String content) {
                this.content = content;
            }
            public String getContent() {
                return content;
            }

            public void setFlag(int flag) {
                this.flag = flag;
            }
            public int getFlag() {
                return flag;
            }

            public void setGoodsCode(String goodsCode) {
                this.goodsCode = goodsCode;
            }
            public String getGoodsCode() {
                return goodsCode;
            }

            public void setGoodsId(int goodsId) {
                this.goodsId = goodsId;
            }
            public int getGoodsId() {
                return goodsId;
            }

            public void setGoodsName(String goodsName) {
                this.goodsName = goodsName;
            }
            public String getGoodsName() {
                return goodsName;
            }

            public void setId(int id) {
                this.id = id;
            }
            public int getId() {
                return id;
            }

            public void setManufacturer(String manufacturer) {
                this.manufacturer = manufacturer;
            }
            public String getManufacturer() {
                return manufacturer;
            }

            public void setOrderId(int orderId) {
                this.orderId = orderId;
            }
            public int getOrderId() {
                return orderId;
            }

            public void setPrice(double price) {
                this.price = price;
            }
            public double getPrice() {
                return price;
            }

            public void setPrice1(String price1) {
                this.price1 = price1;
            }
            public String getPrice1() {
                return price1;
            }

            public void setPrice2(String price2) {
                this.price2 = price2;
            }
            public String getPrice2() {
                return price2;
            }

            public void setQty(int qty) {
                this.qty = qty;
            }
            public int getQty() {
                return qty;
            }

            public void setRebate(String rebate) {
                this.rebate = rebate;
            }
            public String getRebate() {
                return rebate;
            }

            public void setSales(String sales) {
                this.sales = sales;
            }
            public String getSales() {
                return sales;
            }

            public void setSpec(String spec) {
                this.spec = spec;
            }
            public String getSpec() {
                return spec;
            }

            public void setUnit(String unit) {
                this.unit = unit;
            }
            public String getUnit() {
                return unit;
            }

        }
        private String address;
        private int addressId;
        private double amount;
        private String belong;
        private int belongid;
        private String bmodel;
        private List<String> bmodels;
        private int companyId;
        private String companyName;
        private String content;
        private String creationTime;
        private int creatorId;
        private String credit;
        private String creditin;
        private String creditout;
        private int deliveryType;
        private List<Details> details;
        private List<String> docs;
        private String ext;
        private String feecso;
        private String feedist;
        private String feefin;
        private String feein;
        private int hascso;
        private int hasdist;
        private int hasfin;
        private int hasin;
        private int id;
        private String linkman;
        private String linkmanTel;
        private String numbers;
        private String orderDate;
        private int orgId;
        private String orgName;
        private String remark;
        private int state;
        private String stateText;
        private List<String> states;
        private String total;
        private int type;
        private int updateId;
        private String updateTime;
        private String userName;
        public void setAddress(String address) {
            this.address = address;
        }
        public String getAddress() {
            return address;
        }

        public void setAddressId(int addressId) {
            this.addressId = addressId;
        }
        public int getAddressId() {
            return addressId;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }
        public double getAmount() {
            return amount;
        }

        public void setBelong(String belong) {
            this.belong = belong;
        }
        public String getBelong() {
            return belong;
        }

        public void setBelongid(int belongid) {
            this.belongid = belongid;
        }
        public int getBelongid() {
            return belongid;
        }

        public void setBmodel(String bmodel) {
            this.bmodel = bmodel;
        }
        public String getBmodel() {
            return bmodel;
        }

        public void setBmodels(List<String> bmodels) {
            this.bmodels = bmodels;
        }
        public List<String> getBmodels() {
            return bmodels;
        }

        public void setCompanyId(int companyId) {
            this.companyId = companyId;
        }
        public int getCompanyId() {
            return companyId;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }
        public String getCompanyName() {
            return companyName;
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

        public void setCredit(String credit) {
            this.credit = credit;
        }
        public String getCredit() {
            return credit;
        }

        public void setCreditin(String creditin) {
            this.creditin = creditin;
        }
        public String getCreditin() {
            return creditin;
        }

        public void setCreditout(String creditout) {
            this.creditout = creditout;
        }
        public String getCreditout() {
            return creditout;
        }

        public void setDeliveryType(int deliveryType) {
            this.deliveryType = deliveryType;
        }
        public int getDeliveryType() {
            return deliveryType;
        }

        public void setDetails(List<Details> details) {
            this.details = details;
        }
        public List<Details> getDetails() {
            return details;
        }

        public void setDocs(List<String> docs) {
            this.docs = docs;
        }
        public List<String> getDocs() {
            return docs;
        }

        public void setExt(String ext) {
            this.ext = ext;
        }
        public String getExt() {
            return ext;
        }

        public void setFeecso(String feecso) {
            this.feecso = feecso;
        }
        public String getFeecso() {
            return feecso;
        }

        public void setFeedist(String feedist) {
            this.feedist = feedist;
        }
        public String getFeedist() {
            return feedist;
        }

        public void setFeefin(String feefin) {
            this.feefin = feefin;
        }
        public String getFeefin() {
            return feefin;
        }

        public void setFeein(String feein) {
            this.feein = feein;
        }
        public String getFeein() {
            return feein;
        }

        public void setHascso(int hascso) {
            this.hascso = hascso;
        }
        public int getHascso() {
            return hascso;
        }

        public void setHasdist(int hasdist) {
            this.hasdist = hasdist;
        }
        public int getHasdist() {
            return hasdist;
        }

        public void setHasfin(int hasfin) {
            this.hasfin = hasfin;
        }
        public int getHasfin() {
            return hasfin;
        }

        public void setHasin(int hasin) {
            this.hasin = hasin;
        }
        public int getHasin() {
            return hasin;
        }

        public void setId(int id) {
            this.id = id;
        }
        public int getId() {
            return id;
        }

        public void setLinkman(String linkman) {
            this.linkman = linkman;
        }
        public String getLinkman() {
            return linkman;
        }

        public void setLinkmanTel(String linkmanTel) {
            this.linkmanTel = linkmanTel;
        }
        public String getLinkmanTel() {
            return linkmanTel;
        }

        public void setNumbers(String numbers) {
            this.numbers = numbers;
        }
        public String getNumbers() {
            return numbers;
        }

        public void setOrderDate(String orderDate) {
            this.orderDate = orderDate;
        }
        public String getOrderDate() {
            return orderDate;
        }

        public void setOrgId(int orgId) {
            this.orgId = orgId;
        }
        public int getOrgId() {
            return orgId;
        }

        public void setOrgName(String orgName) {
            this.orgName = orgName;
        }
        public String getOrgName() {
            return orgName;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }
        public String getRemark() {
            return remark;
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

        public void setStates(List<String> states) {
            this.states = states;
        }
        public List<String> getStates() {
            return states;
        }

        public void setTotal(String total) {
            this.total = total;
        }
        public String getTotal() {
            return total;
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

        public void setUserName(String userName) {
            this.userName = userName;
        }
        public String getUserName() {
            return userName;
        }

    }
}
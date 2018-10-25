
package com.example.myron.heyihui.com.example.myron.heyihui.Data;
import java.util.List;
public class BannerData
{
    private List<Biz> biz;

    private String errorCode;

    private String msg;

    private boolean success;

    private int total;

    public void setBiz(List<Biz> biz){
        this.biz = biz;
    }
    public List<Biz> getBiz(){
        return this.biz;
    }
    public void setErrorCode(String errorCode){
        this.errorCode = errorCode;
    }
    public String getErrorCode(){
        return this.errorCode;
    }
    public void setMsg(String msg){
        this.msg = msg;
    }
    public String getMsg(){
        return this.msg;
    }
    public void setSuccess(boolean success){
        this.success = success;
    }
    public boolean getSuccess(){
        return this.success;
    }
    public void setTotal(int total){
        this.total = total;
    }
    public int getTotal(){
        return this.total;
    }

    public class Biz
    {
        private int channelId;

        private String channelName;

        private String cname;

        private String content;

        private String creationTime;

        private int creatorId;

        private String dataDate;

        private int hit;

        private int id;

        private String img;

        private String img1;

        private String img2;

        private String img3;

        private String img4;

        private int isCommend;

        private String keys;

        private String numbers;

        private String path;

        private String realTime;

        private String relevance;

        private String remark;

        private int sort;

        private int state;

        private String template;

        private String title;

        private String type;

        private String uc;

        private List<String> ucs;

        private String uimg;

        private int updateId;

        private String updateTime;

        private String url;

        public void setChannelId(int channelId){
            this.channelId = channelId;
        }
        public int getChannelId(){
            return this.channelId;
        }
        public void setChannelName(String channelName){
            this.channelName = channelName;
        }
        public String getChannelName(){
            return this.channelName;
        }
        public void setCname(String cname){
            this.cname = cname;
        }
        public String getCname(){
            return this.cname;
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
        public void setDataDate(String dataDate){
            this.dataDate = dataDate;
        }
        public String getDataDate(){
            return this.dataDate;
        }
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
        public void setImg1(String img1){
            this.img1 = img1;
        }
        public String getImg1(){
            return this.img1;
        }
        public void setImg2(String img2){
            this.img2 = img2;
        }
        public String getImg2(){
            return this.img2;
        }
        public void setImg3(String img3){
            this.img3 = img3;
        }
        public String getImg3(){
            return this.img3;
        }
        public void setImg4(String img4){
            this.img4 = img4;
        }
        public String getImg4(){
            return this.img4;
        }
        public void setIsCommend(int isCommend){
            this.isCommend = isCommend;
        }
        public int getIsCommend(){
            return this.isCommend;
        }
        public void setKeys(String keys){
            this.keys = keys;
        }
        public String getKeys(){
            return this.keys;
        }
        public void setNumbers(String numbers){
            this.numbers = numbers;
        }
        public String getNumbers(){
            return this.numbers;
        }
        public void setPath(String path){
            this.path = path;
        }
        public String getPath(){
            return this.path;
        }
        public void setRealTime(String realTime){
            this.realTime = realTime;
        }
        public String getRealTime(){
            return this.realTime;
        }
        public void setRelevance(String relevance){
            this.relevance = relevance;
        }
        public String getRelevance(){
            return this.relevance;
        }
        public void setRemark(String remark){
            this.remark = remark;
        }
        public String getRemark(){
            return this.remark;
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
        public void setTemplate(String template){
            this.template = template;
        }
        public String getTemplate(){
            return this.template;
        }
        public void setTitle(String title){
            this.title = title;
        }
        public String getTitle(){
            return this.title;
        }
        public void setType(String type){
            this.type = type;
        }
        public String getType(){
            return this.type;
        }
        public void setUc(String uc){
            this.uc = uc;
        }
        public String getUc(){
            return this.uc;
        }
        public void setUcs(List<String> ucs){
            this.ucs = ucs;
        }
        public List<String> getUcs(){
            return this.ucs;
        }
        public void setUimg(String uimg){
            this.uimg = uimg;
        }
        public String getUimg(){
            return this.uimg;
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
        public void setUrl(String url){
            this.url = url;
        }
        public String getUrl(){
            return this.url;
        }
    }
}

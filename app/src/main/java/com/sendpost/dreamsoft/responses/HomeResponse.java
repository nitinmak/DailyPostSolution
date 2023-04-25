package com.sendpost.dreamsoft.responses;

import com.google.gson.annotations.SerializedName;

import com.sendpost.dreamsoft.model.BusinessCardModel;
import com.sendpost.dreamsoft.model.CategoryModel;
import com.sendpost.dreamsoft.model.OfferDialogModel;
import com.sendpost.dreamsoft.model.PostsModel;
import com.sendpost.dreamsoft.model.PromocodeModel;
import com.sendpost.dreamsoft.model.SectionModel;
import com.sendpost.dreamsoft.model.SliderModel;
import com.sendpost.dreamsoft.model.SubscriptionModel;

import java.util.List;

public class HomeResponse {

    @SerializedName("code")
    public int code;

    @SerializedName("upcoming_eventcount")
    public int upcoming_eventcount;

    @SerializedName("dayscount")
    public int dayscount;

    @SerializedName("motivationalcount")
    public int motivationalcount;

    @SerializedName("leaderthoughtcount")
    public int leaderthoughtcount;

    @SerializedName("gods_daycount")
    public int gods_daycount;

    @SerializedName("relationcount")
    public int relationcount;

    @SerializedName("occasioncount")
    public int occasioncount;

    @SerializedName("festival_categorycount")
    public int festival_categorycount;

    @SerializedName("recentcount")
    public int recentcount;

    public List<TamplateModel> getBusinesscardtamplate() {
        return businesscardtamplate;
    }

    public void setBusinesscardtamplate(List<TamplateModel> businesscardtamplate) {
        this.businesscardtamplate = businesscardtamplate;
    }

    @SerializedName("businesscardtamplate")
    public List<TamplateModel> businesscardtamplate;

    public List<BusinessCardModel> getBusinesscarddigital() {
        return businesscarddigital;
    }

    public void setBusinesscarddigital(List<BusinessCardModel> businesscarddigital) {
        this.businesscarddigital = businesscarddigital;
    }

    @SerializedName("businesscarddigital")
    public List<BusinessCardModel> businesscarddigital;


    public int getUpcoming_eventcount() {
        return upcoming_eventcount;
    }

    public void setUpcoming_eventcount(int upcoming_eventcount) {
        this.upcoming_eventcount = upcoming_eventcount;
    }

    public int getDayscount() {
        return dayscount;
    }

    public void setDayscount(int dayscount) {
        this.dayscount = dayscount;
    }

    public int getMotivationalcount() {
        return motivationalcount;
    }

    public void setMotivationalcount(int motivationalcount) {
        this.motivationalcount = motivationalcount;
    }

    public int getLeaderthoughtcount() {
        return leaderthoughtcount;
    }

    public void setLeaderthoughtcount(int leaderthoughtcount) {
        this.leaderthoughtcount = leaderthoughtcount;
    }

    public int getGods_daycount() {
        return gods_daycount;
    }

    public void setGods_daycount(int gods_daycount) {
        this.gods_daycount = gods_daycount;
    }

    public int getRelationcount() {
        return relationcount;
    }

    public void setRelationcount(int relationcount) {
        this.relationcount = relationcount;
    }

    public int getOccasioncount() {
        return occasioncount;
    }

    public void setOccasioncount(int occasioncount) {
        this.occasioncount = occasioncount;
    }

    public int getFestival_categorycount() {
        return festival_categorycount;
    }

    public void setFestival_categorycount(int festival_categorycount) {
        this.festival_categorycount = festival_categorycount;
    }

    public int getRecentcount() {
        return recentcount;
    }

    public void setRecentcount(int recentcount) {
        this.recentcount = recentcount;
    }

    @SerializedName("message")
    public String message;

    @SerializedName("slider")
    public List<SliderModel> sliderdata;

    @SerializedName("section")
    public List<SectionModel> section;

    @SerializedName("upcoming_event")
    public List<CategoryModel> upcoming_event;

    @SerializedName("festival_category")
    public List<CategoryModel> festival_category;

    @SerializedName("business_category")
    public List<CategoryModel> business_category;

    @SerializedName("custom_category")
    public List<CategoryModel> custom_category;

    @SerializedName("gods_day")
    public List<CategoryModel> gods_day;

    @SerializedName("relation")
    public List<CategoryModel> relation;

    @SerializedName("amazing")
    public List<CategoryModel> amazing;

    @SerializedName("leaderthought")
    public List<CategoryModel> leaderthought;

    @SerializedName("numerology")
    public List<CategoryModel> numerology;

    @SerializedName("rashi")
    public List<CategoryModel> rashi;

    @SerializedName("occasion")
    public List<CategoryModel> occasion;

    @SerializedName("recent")
    public List<PostsModel> recent;

    @SerializedName("greeting_section")
    public List<SectionModel> greeting_section;

    @SerializedName("subscriptions")
    public List<SubscriptionModel> subscriptions;

    @SerializedName("categories")
    public List<CategoryModel> categories;

    @SerializedName("offerdialog")
    public OfferDialogModel offerdialog;

    @SerializedName("posts")
    public List<PostsModel> posts;

    @SerializedName("promocode")
    public PromocodeModel promocode;

    @SerializedName("goddevi")
    public List<CategoryModel> goddevi;

    @SerializedName("goddeva")
    public List<CategoryModel> goddeva;

    @SerializedName("msgtosoc")
    public List<CategoryModel> msgtosoc;

    @SerializedName("bestwhishes")
    public List<CategoryModel> bestwhishes;

    @SerializedName("motivational")
    public List<CategoryModel> motivational;

    @SerializedName("dailyroutin1")
    public List<CategoryModel> dailyroutin1;

    @SerializedName("dailyroutin2")
    public List<CategoryModel> dailyroutin2;

    @SerializedName("dailyroutin3")
    public List<CategoryModel> dailyroutin3;

    @SerializedName("dailyc1")
    public String dailyc1;

    @SerializedName("dailyc2")
    public String dailyc2;

    @SerializedName("dailyc3")
    public String dailyc3;

    @SerializedName("dailycgoddeva")
    public String dailycgoddeva;

    @SerializedName("dailycgoddevi")
    public String dailycgoddevi;

    @SerializedName("dailycmsgsoc")
    public String dailycmsgsoc;

    @SerializedName("dailycrashi")
    public String dailycrashi;

    @SerializedName("dailycnumro")
    public String dailycnumro;


    public String getDailyc1() {
        return dailyc1;
    }

    public void setDailyc1(String dailyc1) {
        this.dailyc1 = dailyc1;
    }

    public String getDailyc2() {
        return dailyc2;
    }

    public void setDailyc2(String dailyc2) {
        this.dailyc2 = dailyc2;
    }

    public String getDailyc3() {
        return dailyc3;
    }

    public void setDailyc3(String dailyc3) {
        this.dailyc3 = dailyc3;
    }

    public String getDailycgoddeva() {
        return dailycgoddeva;
    }

    public void setDailycgoddeva(String dailycgoddeva) {
        this.dailycgoddeva = dailycgoddeva;
    }

    public String getDailycgoddevi() {
        return dailycgoddevi;
    }

    public void setDailycgoddevi(String dailycgoddevi) {
        this.dailycgoddevi = dailycgoddevi;
    }

    public String getDailycmsgsoc() {
        return dailycmsgsoc;
    }

    public void setDailycmsgsoc(String dailycmsgsoc) {
        this.dailycmsgsoc = dailycmsgsoc;
    }

    public String getDailycrashi() {
        return dailycrashi;
    }

    public void setDailycrashi(String dailycrashi) {
        this.dailycrashi = dailycrashi;
    }

    public String getDailycnumro() {
        return dailycnumro;
    }

    public void setDailycnumro(String dailycnumro) {
        this.dailycnumro = dailycnumro;
    }

    public List<CategoryModel> getDailyroutin1() {
        return dailyroutin1;
    }

    public void setDailyroutin1(List<CategoryModel> dailyroutin1) {
        this.dailyroutin1 = dailyroutin1;
    }

    public List<CategoryModel> getDailyroutin2() {
        return dailyroutin2;
    }

    public void setDailyroutin2(List<CategoryModel> dailyroutin2) {
        this.dailyroutin2 = dailyroutin2;
    }

    public List<CategoryModel> getDailyroutin3() {
        return dailyroutin3;
    }

    public void setDailyroutin3(List<CategoryModel> dailyroutin3) {
        this.dailyroutin3 = dailyroutin3;
    }

    public List<CategoryModel> getDays() {
        return days;
    }

    public void setDays(List<CategoryModel> days) {
        this.days = days;
    }

    @SerializedName("days")
    public List<CategoryModel> days;

    public List<CategoryModel> getLeaderthought() {
        return leaderthought;
    }

    public void setLeaderthought(List<CategoryModel> leaderthought) {
        this.leaderthought = leaderthought;
    }

    public List<CategoryModel> getNumerology() {
        return numerology;
    }

    public void setNumerology(List<CategoryModel> numerology) {
        this.numerology = numerology;
    }

    public List<CategoryModel> getRashi() {
        return rashi;
    }

    public void setRashi(List<CategoryModel> rashi) {
        this.rashi = rashi;
    }


    public List<CategoryModel> getAmazing() {
        return amazing;
    }

    public void setAmazing(List<CategoryModel> amazing) {
        this.amazing = amazing;
    }

    public List<CategoryModel> getOccasion() {
        return occasion;
    }

    public void setOccasion(List<CategoryModel> occasion) {
        this.occasion = occasion;
    }

    public List<CategoryModel> getGods_day() {
        return gods_day;
    }

    public void setGods_day(List<CategoryModel> gods_day) {
        this.gods_day = gods_day;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<SliderModel> getSliderdata() {
        return sliderdata;
    }

    public void setSliderdata(List<SliderModel> sliderdata) {
        this.sliderdata = sliderdata;
    }

    public List<SectionModel> getSection() {
        return section;
    }

    public void setSection(List<SectionModel> section) {
        this.section = section;
    }

    public List<CategoryModel> getUpcoming_event() {
        return upcoming_event;
    }

    public void setUpcoming_event(List<CategoryModel> upcoming_event) {
        this.upcoming_event = upcoming_event;
    }

    public List<CategoryModel> getFestival_category() {
        return festival_category;
    }

    public void setFestival_category(List<CategoryModel> festival_category) {
        this.festival_category = festival_category;
    }

    public List<CategoryModel> getBusiness_category() {
        return business_category;
    }

    public void setBusiness_category(List<CategoryModel> business_category) {
        this.business_category = business_category;
    }

    public List<CategoryModel> getCustom_category() {
        return custom_category;
    }

    public void setCustom_category(List<CategoryModel> custom_category) {
        this.custom_category = custom_category;
    }

    public List<PostsModel> getRecent() {
        return recent;
    }

    public void setRecent(List<PostsModel> recent) {
        this.recent = recent;
    }

    public List<SectionModel> getGreeting_section() {
        return greeting_section;
    }

    public void setGreeting_section(List<SectionModel> greeting_section) {
        this.greeting_section = greeting_section;
    }

    public List<SubscriptionModel> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(List<SubscriptionModel> subscriptions) {
        this.subscriptions = subscriptions;
    }

    public List<CategoryModel> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryModel> categories) {
        this.categories = categories;
    }

    public List<PostsModel> getPosts() {
        return posts;
    }

    public void setPosts(List<PostsModel> posts) {
        this.posts = posts;
    }

    public OfferDialogModel getOfferdialog() {
        return offerdialog;
    }

    public void setOfferdialog(OfferDialogModel offerdialog) {
        this.offerdialog = offerdialog;
    }

    public PromocodeModel getPromocode() {
        return promocode;
    }

    public List<CategoryModel> getRelation() {
        return relation;
    }

    public void setRelation(List<CategoryModel> relation) {
        this.relation = relation;
    }

    public List<CategoryModel> getGoddevi() {
        return goddevi;
    }

    public void setGoddevi(List<CategoryModel> goddevi) {
        this.goddevi = goddevi;
    }

    public List<CategoryModel> getGoddeva() {
        return goddeva;
    }

    public void setGoddeva(List<CategoryModel> goddeva) {
        this.goddeva = goddeva;
    }

    public List<CategoryModel> getMsgtosoc() {
        return msgtosoc;
    }

    public void setMsgtosoc(List<CategoryModel> msgtosoc) {
        this.msgtosoc = msgtosoc;
    }

    public List<CategoryModel> getBestwhishes() {
        return bestwhishes;
    }

    public void setBestwhishes(List<CategoryModel> bestwhishes) {
        this.bestwhishes = bestwhishes;
    }

    public List<CategoryModel> getMotivational() {
        return motivational;
    }

    public void setMotivational(List<CategoryModel> motivational) {
        this.motivational = motivational;
    }

    public void setPromocode(PromocodeModel promocode) {
        this.promocode = promocode;
    }
}

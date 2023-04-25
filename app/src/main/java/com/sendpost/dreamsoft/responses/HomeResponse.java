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


    @SerializedName("dailynew1")
    public String dailynew1;

    @SerializedName("dailynew2")
    public String dailynew2;

    @SerializedName("dailynew3")
    public String dailynew3;

    @SerializedName("dailynew4")
    public String dailynew4;

    @SerializedName("dailynew5")
    public String dailynew5;

    @SerializedName("dailynew6")
    public String dailynew6;

    @SerializedName("dailynew7")
    public String dailynew7;

    @SerializedName("dailynew8")
    public String dailynew8;

    @SerializedName("dailynew9")
    public String dailynew9;

    @SerializedName("dailynew10")
    public String dailynew10;

    @SerializedName("dailynew11")
    public String dailynew11;

    @SerializedName("dailynew12")
    public String dailynew12;

    @SerializedName("dailynew13")
    public String dailynew13;

    @SerializedName("dailynew14")
    public String dailynew14;

    @SerializedName("dailynew15")
    public String dailynew15;

    @SerializedName("dailynew16")
    public String dailynew16;

    @SerializedName("dailynew17")
    public String dailynew17;

    @SerializedName("dailynew18")
    public String dailynew18;

    @SerializedName("dailynew19")
    public String dailynew19;

    @SerializedName("dailynew20")
    public String dailynew20;

    @SerializedName("dailynew21")
    public String dailynew21;

    @SerializedName("dailynew22")
    public String dailynew22;

    @SerializedName("dailynew23")
    public String dailynew23;

    @SerializedName("dailynew24")
    public String dailynew24;

    @SerializedName("dailynew25")
    public String dailynew25;

    @SerializedName("dailynew26")
    public String dailynew26;

    @SerializedName("dailynew27")
    public String dailynew27;

    @SerializedName("dailynew28")
    public String dailynew28;

    @SerializedName("dailynew29")
    public String dailynew29;

    @SerializedName("dailynew30")
    public String dailynew30;

    @SerializedName("dailynew31")
    public String dailynew31;

    @SerializedName("dailynew32")
    public String dailynew32;

    @SerializedName("dailynew33")
    public String dailynew33;

    @SerializedName("dailynew34")
    public String dailynew34;

    @SerializedName("dailynew35")
    public String dailynew35;

    @SerializedName("dailynew36")
    public String dailynew36;

    @SerializedName("dailynew37")
    public String dailynew37;



    @SerializedName("dailnewyroutin1")
    public List<CategoryModel> dailnewyroutin1;

    @SerializedName("dailnewyroutin2")
    public List<CategoryModel> dailnewyroutin2;

    @SerializedName("dailnewyroutin3")
    public List<CategoryModel> dailnewyroutin3;

    @SerializedName("dailnewyroutin4")
    public List<CategoryModel> dailnewyroutin4;

    @SerializedName("dailnewyroutin5")
    public List<CategoryModel> dailnewyroutin5;


    @SerializedName("dailnewyroutin6")
    public List<CategoryModel> dailnewyroutin6;

    @SerializedName("dailnewyroutin7")
    public List<CategoryModel> dailnewyroutin7;

    @SerializedName("dailnewyroutin8")
    public List<CategoryModel> dailnewyroutin8;

    @SerializedName("dailnewyroutin9")
    public List<CategoryModel> dailnewyroutin9;

    @SerializedName("dailnewyroutin10")
    public List<CategoryModel> dailnewyroutin10;

    @SerializedName("dailnewyroutin11")
    public List<CategoryModel> dailnewyroutin11;

    @SerializedName("dailnewyroutin12")
    public List<CategoryModel> dailnewyroutin12;

    @SerializedName("dailnewyroutin13")
    public List<CategoryModel> dailnewyroutin13;

    @SerializedName("dailnewyroutin14")
    public List<CategoryModel> dailnewyroutin14;

    @SerializedName("dailnewyroutin15")
    public List<CategoryModel> dailnewyroutin15;

    @SerializedName("dailnewyroutin16")
    public List<CategoryModel> dailnewyroutin16;

    @SerializedName("dailnewyroutin17")
    public List<CategoryModel> dailnewyroutin17;

    @SerializedName("dailnewyroutin18")
    public List<CategoryModel> dailnewyroutin18;

    @SerializedName("dailnewyroutin19")
    public List<CategoryModel> dailnewyroutin19;

    @SerializedName("dailnewyroutin20")
    public List<CategoryModel> dailnewyroutin20;

    @SerializedName("dailnewyroutin21")
    public List<CategoryModel> dailnewyroutin21;

    @SerializedName("dailnewyroutin22")
    public List<CategoryModel> dailnewyroutin22;

    @SerializedName("dailnewyroutin23")
    public List<CategoryModel> dailnewyroutin23;

    @SerializedName("dailnewyroutin24")
    public List<CategoryModel> dailnewyroutin24;

    @SerializedName("dailnewyroutin25")
    public List<CategoryModel> dailnewyroutin25;

    @SerializedName("dailnewyroutin26")
    public List<CategoryModel> dailnewyroutin26;

    @SerializedName("dailnewyroutin27")
    public List<CategoryModel> dailnewyroutin27;

    @SerializedName("dailnewyroutin28")
    public List<CategoryModel> dailnewyroutin28;

    @SerializedName("dailnewyroutin29")
    public List<CategoryModel> dailnewyroutin29;

    @SerializedName("dailnewyroutin30")
    public List<CategoryModel> dailnewyroutin30;

    @SerializedName("dailnewyroutin31")
    public List<CategoryModel> dailnewyroutin31;

    @SerializedName("dailnewyroutin32")
    public List<CategoryModel> dailnewyroutin32;

    @SerializedName("dailnewyroutin33")
    public List<CategoryModel> dailnewyroutin33;

    @SerializedName("dailnewyroutin34")
    public List<CategoryModel> dailnewyroutin34;

    @SerializedName("dailnewyroutin35")
    public List<CategoryModel> dailnewyroutin35;

    @SerializedName("dailnewyroutin36")
    public List<CategoryModel> dailnewyroutin36;

    @SerializedName("dailnewyroutin37")
    public List<CategoryModel> dailnewyroutin37;


    public String getDailynew1() {
        return dailynew1;
    }

    public void setDailynew1(String dailynew1) {
        this.dailynew1 = dailynew1;
    }

    public String getDailynew2() {
        return dailynew2;
    }

    public void setDailynew2(String dailynew2) {
        this.dailynew2 = dailynew2;
    }

    public String getDailynew3() {
        return dailynew3;
    }

    public void setDailynew3(String dailynew3) {
        this.dailynew3 = dailynew3;
    }

    public String getDailynew4() {
        return dailynew4;
    }

    public void setDailynew4(String dailynew4) {
        this.dailynew4 = dailynew4;
    }

    public String getDailynew5() {
        return dailynew5;
    }

    public void setDailynew5(String dailynew5) {
        this.dailynew5 = dailynew5;
    }

    public String getDailynew6() {
        return dailynew6;
    }

    public void setDailynew6(String dailynew6) {
        this.dailynew6 = dailynew6;
    }

    public String getDailynew7() {
        return dailynew7;
    }

    public void setDailynew7(String dailynew7) {
        this.dailynew7 = dailynew7;
    }

    public String getDailynew8() {
        return dailynew8;
    }

    public void setDailynew8(String dailynew8) {
        this.dailynew8 = dailynew8;
    }

    public String getDailynew9() {
        return dailynew9;
    }

    public void setDailynew9(String dailynew9) {
        this.dailynew9 = dailynew9;
    }

    public String getDailynew10() {
        return dailynew10;
    }

    public void setDailynew10(String dailynew10) {
        this.dailynew10 = dailynew10;
    }

    public String getDailynew11() {
        return dailynew11;
    }

    public void setDailynew11(String dailynew11) {
        this.dailynew11 = dailynew11;
    }

    public String getDailynew12() {
        return dailynew12;
    }

    public void setDailynew12(String dailynew12) {
        this.dailynew12 = dailynew12;
    }

    public String getDailynew13() {
        return dailynew13;
    }

    public void setDailynew13(String dailynew13) {
        this.dailynew13 = dailynew13;
    }

    public String getDailynew14() {
        return dailynew14;
    }

    public void setDailynew14(String dailynew14) {
        this.dailynew14 = dailynew14;
    }

    public String getDailynew15() {
        return dailynew15;
    }

    public void setDailynew15(String dailynew15) {
        this.dailynew15 = dailynew15;
    }

    public String getDailynew16() {
        return dailynew16;
    }

    public void setDailynew16(String dailynew16) {
        this.dailynew16 = dailynew16;
    }

    public String getDailynew17() {
        return dailynew17;
    }

    public void setDailynew17(String dailynew17) {
        this.dailynew17 = dailynew17;
    }

    public String getDailynew18() {
        return dailynew18;
    }

    public void setDailynew18(String dailynew18) {
        this.dailynew18 = dailynew18;
    }

    public String getDailynew19() {
        return dailynew19;
    }

    public void setDailynew19(String dailynew19) {
        this.dailynew19 = dailynew19;
    }

    public String getDailynew20() {
        return dailynew20;
    }

    public void setDailynew20(String dailynew20) {
        this.dailynew20 = dailynew20;
    }

    public String getDailynew21() {
        return dailynew21;
    }

    public void setDailynew21(String dailynew21) {
        this.dailynew21 = dailynew21;
    }

    public String getDailynew22() {
        return dailynew22;
    }

    public void setDailynew22(String dailynew22) {
        this.dailynew22 = dailynew22;
    }

    public String getDailynew23() {
        return dailynew23;
    }

    public void setDailynew23(String dailynew23) {
        this.dailynew23 = dailynew23;
    }

    public String getDailynew24() {
        return dailynew24;
    }

    public void setDailynew24(String dailynew24) {
        this.dailynew24 = dailynew24;
    }

    public String getDailynew25() {
        return dailynew25;
    }

    public void setDailynew25(String dailynew25) {
        this.dailynew25 = dailynew25;
    }

    public String getDailynew26() {
        return dailynew26;
    }

    public void setDailynew26(String dailynew26) {
        this.dailynew26 = dailynew26;
    }

    public String getDailynew27() {
        return dailynew27;
    }

    public void setDailynew27(String dailynew27) {
        this.dailynew27 = dailynew27;
    }

    public String getDailynew28() {
        return dailynew28;
    }

    public void setDailynew28(String dailynew28) {
        this.dailynew28 = dailynew28;
    }

    public String getDailynew29() {
        return dailynew29;
    }

    public void setDailynew29(String dailynew29) {
        this.dailynew29 = dailynew29;
    }

    public String getDailynew30() {
        return dailynew30;
    }

    public void setDailynew30(String dailynew30) {
        this.dailynew30 = dailynew30;
    }

    public String getDailynew31() {
        return dailynew31;
    }

    public void setDailynew31(String dailynew31) {
        this.dailynew31 = dailynew31;
    }

    public String getDailynew32() {
        return dailynew32;
    }

    public void setDailynew32(String dailynew32) {
        this.dailynew32 = dailynew32;
    }

    public String getDailynew33() {
        return dailynew33;
    }

    public void setDailynew33(String dailynew33) {
        this.dailynew33 = dailynew33;
    }

    public String getDailynew34() {
        return dailynew34;
    }

    public void setDailynew34(String dailynew34) {
        this.dailynew34 = dailynew34;
    }

    public String getDailynew35() {
        return dailynew35;
    }

    public void setDailynew35(String dailynew35) {
        this.dailynew35 = dailynew35;
    }

    public String getDailynew36() {
        return dailynew36;
    }

    public void setDailynew36(String dailynew36) {
        this.dailynew36 = dailynew36;
    }

    public String getDailynew37() {
        return dailynew37;
    }

    public void setDailynew37(String dailynew37) {
        this.dailynew37 = dailynew37;
    }

    public List<CategoryModel> getDailnewyroutin1() {
        return dailnewyroutin1;
    }

    public void setDailnewyroutin1(List<CategoryModel> dailnewyroutin1) {
        this.dailnewyroutin1 = dailnewyroutin1;
    }

    public List<CategoryModel> getDailnewyroutin2() {
        return dailnewyroutin2;
    }

    public void setDailnewyroutin2(List<CategoryModel> dailnewyroutin2) {
        this.dailnewyroutin2 = dailnewyroutin2;
    }

    public List<CategoryModel> getDailnewyroutin3() {
        return dailnewyroutin3;
    }

    public void setDailnewyroutin3(List<CategoryModel> dailnewyroutin3) {
        this.dailnewyroutin3 = dailnewyroutin3;
    }

    public List<CategoryModel> getDailnewyroutin4() {
        return dailnewyroutin4;
    }

    public void setDailnewyroutin4(List<CategoryModel> dailnewyroutin4) {
        this.dailnewyroutin4 = dailnewyroutin4;
    }

    public List<CategoryModel> getDailnewyroutin5() {
        return dailnewyroutin5;
    }

    public void setDailnewyroutin5(List<CategoryModel> dailnewyroutin5) {
        this.dailnewyroutin5 = dailnewyroutin5;
    }

    public List<CategoryModel> getDailnewyroutin6() {
        return dailnewyroutin6;
    }

    public void setDailnewyroutin6(List<CategoryModel> dailnewyroutin6) {
        this.dailnewyroutin6 = dailnewyroutin6;
    }

    public List<CategoryModel> getDailnewyroutin7() {
        return dailnewyroutin7;
    }

    public void setDailnewyroutin7(List<CategoryModel> dailnewyroutin7) {
        this.dailnewyroutin7 = dailnewyroutin7;
    }

    public List<CategoryModel> getDailnewyroutin8() {
        return dailnewyroutin8;
    }

    public void setDailnewyroutin8(List<CategoryModel> dailnewyroutin8) {
        this.dailnewyroutin8 = dailnewyroutin8;
    }

    public List<CategoryModel> getDailnewyroutin9() {
        return dailnewyroutin9;
    }

    public void setDailnewyroutin9(List<CategoryModel> dailnewyroutin9) {
        this.dailnewyroutin9 = dailnewyroutin9;
    }

    public List<CategoryModel> getDailnewyroutin10() {
        return dailnewyroutin10;
    }

    public void setDailnewyroutin10(List<CategoryModel> dailnewyroutin10) {
        this.dailnewyroutin10 = dailnewyroutin10;
    }

    public List<CategoryModel> getDailnewyroutin11() {
        return dailnewyroutin11;
    }

    public void setDailnewyroutin11(List<CategoryModel> dailnewyroutin11) {
        this.dailnewyroutin11 = dailnewyroutin11;
    }

    public List<CategoryModel> getDailnewyroutin12() {
        return dailnewyroutin12;
    }

    public void setDailnewyroutin12(List<CategoryModel> dailnewyroutin12) {
        this.dailnewyroutin12 = dailnewyroutin12;
    }

    public List<CategoryModel> getDailnewyroutin13() {
        return dailnewyroutin13;
    }

    public void setDailnewyroutin13(List<CategoryModel> dailnewyroutin13) {
        this.dailnewyroutin13 = dailnewyroutin13;
    }

    public List<CategoryModel> getDailnewyroutin14() {
        return dailnewyroutin14;
    }

    public void setDailnewyroutin14(List<CategoryModel> dailnewyroutin14) {
        this.dailnewyroutin14 = dailnewyroutin14;
    }

    public List<CategoryModel> getDailnewyroutin15() {
        return dailnewyroutin15;
    }

    public void setDailnewyroutin15(List<CategoryModel> dailnewyroutin15) {
        this.dailnewyroutin15 = dailnewyroutin15;
    }

    public List<CategoryModel> getDailnewyroutin16() {
        return dailnewyroutin16;
    }

    public void setDailnewyroutin16(List<CategoryModel> dailnewyroutin16) {
        this.dailnewyroutin16 = dailnewyroutin16;
    }

    public List<CategoryModel> getDailnewyroutin17() {
        return dailnewyroutin17;
    }

    public void setDailnewyroutin17(List<CategoryModel> dailnewyroutin17) {
        this.dailnewyroutin17 = dailnewyroutin17;
    }

    public List<CategoryModel> getDailnewyroutin18() {
        return dailnewyroutin18;
    }

    public void setDailnewyroutin18(List<CategoryModel> dailnewyroutin18) {
        this.dailnewyroutin18 = dailnewyroutin18;
    }

    public List<CategoryModel> getDailnewyroutin19() {
        return dailnewyroutin19;
    }

    public void setDailnewyroutin19(List<CategoryModel> dailnewyroutin19) {
        this.dailnewyroutin19 = dailnewyroutin19;
    }

    public List<CategoryModel> getDailnewyroutin20() {
        return dailnewyroutin20;
    }

    public void setDailnewyroutin20(List<CategoryModel> dailnewyroutin20) {
        this.dailnewyroutin20 = dailnewyroutin20;
    }

    public List<CategoryModel> getDailnewyroutin21() {
        return dailnewyroutin21;
    }

    public void setDailnewyroutin21(List<CategoryModel> dailnewyroutin21) {
        this.dailnewyroutin21 = dailnewyroutin21;
    }

    public List<CategoryModel> getDailnewyroutin22() {
        return dailnewyroutin22;
    }

    public void setDailnewyroutin22(List<CategoryModel> dailnewyroutin22) {
        this.dailnewyroutin22 = dailnewyroutin22;
    }

    public List<CategoryModel> getDailnewyroutin23() {
        return dailnewyroutin23;
    }

    public void setDailnewyroutin23(List<CategoryModel> dailnewyroutin23) {
        this.dailnewyroutin23 = dailnewyroutin23;
    }

    public List<CategoryModel> getDailnewyroutin24() {
        return dailnewyroutin24;
    }

    public void setDailnewyroutin24(List<CategoryModel> dailnewyroutin24) {
        this.dailnewyroutin24 = dailnewyroutin24;
    }

    public List<CategoryModel> getDailnewyroutin25() {
        return dailnewyroutin25;
    }

    public void setDailnewyroutin25(List<CategoryModel> dailnewyroutin25) {
        this.dailnewyroutin25 = dailnewyroutin25;
    }

    public List<CategoryModel> getDailnewyroutin26() {
        return dailnewyroutin26;
    }

    public void setDailnewyroutin26(List<CategoryModel> dailnewyroutin26) {
        this.dailnewyroutin26 = dailnewyroutin26;
    }

    public List<CategoryModel> getDailnewyroutin27() {
        return dailnewyroutin27;
    }

    public void setDailnewyroutin27(List<CategoryModel> dailnewyroutin27) {
        this.dailnewyroutin27 = dailnewyroutin27;
    }

    public List<CategoryModel> getDailnewyroutin28() {
        return dailnewyroutin28;
    }

    public void setDailnewyroutin28(List<CategoryModel> dailnewyroutin28) {
        this.dailnewyroutin28 = dailnewyroutin28;
    }

    public List<CategoryModel> getDailnewyroutin29() {
        return dailnewyroutin29;
    }

    public void setDailnewyroutin29(List<CategoryModel> dailnewyroutin29) {
        this.dailnewyroutin29 = dailnewyroutin29;
    }

    public List<CategoryModel> getDailnewyroutin30() {
        return dailnewyroutin30;
    }

    public void setDailnewyroutin30(List<CategoryModel> dailnewyroutin30) {
        this.dailnewyroutin30 = dailnewyroutin30;
    }

    public List<CategoryModel> getDailnewyroutin31() {
        return dailnewyroutin31;
    }

    public void setDailnewyroutin31(List<CategoryModel> dailnewyroutin31) {
        this.dailnewyroutin31 = dailnewyroutin31;
    }

    public List<CategoryModel> getDailnewyroutin32() {
        return dailnewyroutin32;
    }

    public void setDailnewyroutin32(List<CategoryModel> dailnewyroutin32) {
        this.dailnewyroutin32 = dailnewyroutin32;
    }

    public List<CategoryModel> getDailnewyroutin33() {
        return dailnewyroutin33;
    }

    public void setDailnewyroutin33(List<CategoryModel> dailnewyroutin33) {
        this.dailnewyroutin33 = dailnewyroutin33;
    }

    public List<CategoryModel> getDailnewyroutin34() {
        return dailnewyroutin34;
    }

    public void setDailnewyroutin34(List<CategoryModel> dailnewyroutin34) {
        this.dailnewyroutin34 = dailnewyroutin34;
    }

    public List<CategoryModel> getDailnewyroutin35() {
        return dailnewyroutin35;
    }

    public void setDailnewyroutin35(List<CategoryModel> dailnewyroutin35) {
        this.dailnewyroutin35 = dailnewyroutin35;
    }

    public List<CategoryModel> getDailnewyroutin36() {
        return dailnewyroutin36;
    }

    public void setDailnewyroutin36(List<CategoryModel> dailnewyroutin36) {
        this.dailnewyroutin36 = dailnewyroutin36;
    }

    public List<CategoryModel> getDailnewyroutin37() {
        return dailnewyroutin37;
    }

    public void setDailnewyroutin37(List<CategoryModel> dailnewyroutin37) {
        this.dailnewyroutin37 = dailnewyroutin37;
    }



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

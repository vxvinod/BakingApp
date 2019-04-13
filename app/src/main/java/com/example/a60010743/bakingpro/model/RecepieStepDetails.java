package com.example.a60010743.bakingpro.model;

import android.os.Parcel;
import android.os.Parcelable;

public class RecepieStepDetails implements Parcelable {

    private String shortDesc;
    private String desc;
    private String videoUrl;
    private String thumbnailUrl;

    public RecepieStepDetails(String shortDesc, String desc, String videoUrl, String thumbnailUrl) {
        this.shortDesc = shortDesc;
        this.desc = desc;
        this.videoUrl = videoUrl;
        this.thumbnailUrl = thumbnailUrl;
    }

    public RecepieStepDetails(Parcel source) {
        shortDesc = source.readString();
        desc = source.readString();
        videoUrl = source.readString();
        thumbnailUrl = source.readString();
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(shortDesc);
        dest.writeString(desc);
        dest.writeString(videoUrl);
        dest.writeString(thumbnailUrl);
    }

    public final static Parcelable.Creator<RecepieStepDetails> CREATOR = new Parcelable.Creator<RecepieStepDetails>(){

        @Override
        public RecepieStepDetails createFromParcel(Parcel source) {
            return new RecepieStepDetails(source);
        }

        @Override
        public RecepieStepDetails[] newArray(int size) {
            return new RecepieStepDetails[size];
        }
    };
}

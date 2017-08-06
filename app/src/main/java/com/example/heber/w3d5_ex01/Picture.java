
package com.example.heber.w3d5_ex01;

import android.graphics.Bitmap;

import java.util.HashMap;
import java.util.Map;

public class Picture {

    private String large;
    private String medium;
    private String thumbnail;
    private Bitmap bmImage;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getLarge() {
        return large;
    }

    public void setLarge(String large) {
        this.large = large;
    }

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Bitmap getBmImage() {
        return bmImage;
    }

    public void setBmImage(Bitmap bmImage) {
        this.bmImage = bmImage;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}

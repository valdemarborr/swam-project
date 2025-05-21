package model;

import jakarta.persistence.*;



@Entity
public class DigitalProduct extends Product {
    private String downloadUrl;
    private double sizeInMb;

    public String getDownloadUrl() {
        return downloadUrl;
    }
    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public double getSizeInMb() {
        return sizeInMb;
    }
    public void setSizeInMb(double sizeInMb) {
        this.sizeInMb = sizeInMb;
    }
}
package com.cecom.alchol.model;


public class CardViewItemDTO {
    public int imageView;
    public int manageButton;
    public String title;
    public String subtitle;

    public CardViewItemDTO(int imageView, String title, String subtitle) {
        this.imageView = imageView;
        this.title = title;
        this.subtitle = subtitle;
    }
    public CardViewItemDTO(String title, String subtitle) {
        this.title = title;
        this.subtitle = subtitle;
    }
}
package com.example.formapp.utils.form.bean;

import java.util.List;

public class FormImageBean extends FormBaseBean {
    private Boolean canEdit;
    private List<ImageData> imageList;

    public static class ImageData {
        private String imageUrl;
        private String imageName;

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getImageName() {
            return imageName;
        }

        public void setImageName(String imageName) {
            this.imageName = imageName;
        }
    }

    public Boolean getCanEdit() {
        return canEdit;
    }

    public void setCanEdit(Boolean canEdit) {
        this.canEdit = canEdit;
    }

    public List<ImageData> getImageList() {
        return imageList;
    }

    public void setImageList(List<ImageData> imageList) {
        this.imageList = imageList;
    }
}

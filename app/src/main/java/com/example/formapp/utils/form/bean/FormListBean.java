package com.example.formapp.utils.form.bean;

public class FormListBean {
    private String type;
    private String itemJson;

    public FormListBean(String type, String itemJson) {
        this.type = type;
        this.itemJson = itemJson;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getItemJson() {
        return itemJson;
    }

    public void setItemJson(String itemJson) {
        this.itemJson = itemJson;
    }
}

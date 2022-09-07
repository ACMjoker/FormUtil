package com.example.formapp.utils.form.bean;

public class ObservableBean {
    private int position;
    private String type;
    private String key;
    private Object value;

    public ObservableBean(int position, String type, String key, Object value) {
        this.position = position;
        this.type = type;
        this.key = key;
        this.value = value;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}

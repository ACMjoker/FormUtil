package com.example.formapp.utils.form.bean;

import com.example.formapp.utils.form.SelectView;

public class FormDateBean extends FormBaseBean {
    private Boolean canClick;
    private SelectView.SelectDateType selectType;
    private String dateType;

    public SelectView.SelectDateType getSelectType() {
        return selectType;
    }

    public void setSelectType(SelectView.SelectDateType selectType) {
        this.selectType = selectType;
    }

    public String getDateType() {
        return dateType;
    }

    public void setDateType(String dateType) {
        this.dateType = dateType;
    }

    public Boolean getCanClick() {
        return canClick;
    }

    public void setCanClick(Boolean canClick) {
        this.canClick = canClick;
    }
}

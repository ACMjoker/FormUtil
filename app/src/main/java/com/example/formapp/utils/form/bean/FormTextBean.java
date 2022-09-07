package com.example.formapp.utils.form.bean;

public class FormTextBean extends FormBaseBean {
    private Boolean canEdit;
    private Boolean canClick;

    public Boolean getCanEdit() {
        return canEdit;
    }

    public void setCanEdit(Boolean canEdit) {
        this.canEdit = canEdit;
    }

    public Boolean getCanClick() {
        return canClick;
    }

    public void setCanClick(Boolean canClick) {
        this.canClick = canClick;
    }
}

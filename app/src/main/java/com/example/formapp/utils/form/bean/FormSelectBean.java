package com.example.formapp.utils.form.bean;

import java.util.List;

public class FormSelectBean extends FormBaseBean {
    private List<OptionBean> options;
    private Boolean canClick;
    private Integer defaultSelect;

    public static class OptionBean {
        private String id;
        private String value;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public Integer getDefaultSelect() {
        return defaultSelect;
    }

    public void setDefaultSelect(Integer defaultSelect) {
        this.defaultSelect = defaultSelect;
    }

    public Boolean getCanClick() {
        return canClick;
    }

    public void setCanClick(Boolean canClick) {
        this.canClick = canClick;
    }

    public List<OptionBean> getOptions() {
        return options;
    }

    public void setOptions(List<OptionBean> options) {
        this.options = options;
    }
}

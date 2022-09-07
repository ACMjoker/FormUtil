package com.example.formapp.utils.form;

import android.content.Context;
import android.graphics.Color;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.example.formapp.utils.StringUtil;
import com.example.formapp.utils.form.bean.FormSelectBean;
import com.example.formapp.utils.form.bean.ObservableBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;

/**
 * 选择器组件
 *
 * @author GBL
 */
public class SelectView {
    public enum SelectDateType {
        /**
         * 精确到年
         */
        YEAR,
        /**
         * 精确到月
         */
        MONTH,
        /**
         * 精确到日
         */
        DAY,
        /**
         * 精确到时
         */
        HOUR,
        /**
         * 精确到分
         */
        MINUTE,
        /**
         * 精确到秒
         */
        SECOND
    }

    /**
     * 时间选择器
     */
    public static void showTimePickerView(String type, String key, Context context, String title, Observer observer, int position, SelectDateType selectType, String dataType) {
        boolean[] timeType = new boolean[]{true, true, true, true, true, false};
        if (selectType != null) {
            switch (selectType) {
                case YEAR:
                    timeType = new boolean[]{true, false, false, false, false, false};
                    break;
                case MONTH:
                    timeType = new boolean[]{true, true, false, false, false, false};
                    break;
                case DAY:
                    timeType = new boolean[]{true, true, true, false, false, false};
                    break;
                case HOUR:
                    timeType = new boolean[]{true, true, true, true, false, false};
                    break;
                case MINUTE:
                    timeType = new boolean[]{true, true, true, true, true, false};
                    break;
                case SECOND:
                    timeType = new boolean[]{true, true, true, true, true, true};
                    break;
                default:
                    break;
            }
        }
        TimePickerView tpView = new TimePickerBuilder(context, (date, v) -> {
            SimpleDateFormat sdf;
            if (StringUtil.isEmpty(dataType)) {
                sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
            } else {
                sdf = new SimpleDateFormat(dataType, Locale.CHINA);
            }
            ObservableBean observableBean = new ObservableBean(position, type, key, sdf.format(date));
            Observable.create(emitter -> {
                emitter.onNext(observableBean);
            }).subscribe(observer);
        })
                //确定按钮文字
                .setSubmitText("确定")
                .setType(timeType)
                .setLabel("年", "月", "日", "时", "分", "秒")
                //取消按钮文字
                .setCancelText("取消")
                //标题
                .setTitleText(title)
                //标题文字大小
                .setTitleSize(20)
                //标题文字颜色
                .setTitleColor(Color.BLACK)
                .build();
        tpView.show();
    }

    public static void showOptionPickerView(String type, String key, Context context, String title, List<FormSelectBean.OptionBean> optionList, Observer observer, int position) {
        List<String> textList = new ArrayList<>();
        for (int i = 0; i < optionList.size(); i++) {
            textList.add(optionList.get(i).getValue());
        }
        OptionsPickerView pvView = new OptionsPickerBuilder(context, (options1, options2, options3, v) -> {
            ObservableBean observableBean = new ObservableBean(position, type, key, optionList.get(options1));
            Observable.create(emitter -> {
                emitter.onNext(observableBean);
            }).subscribe(observer);
        })      //确定按钮文字
                .setSubmitText("确定")
                //取消按钮文字
                .setCancelText("取消")
                //标题
                .setTitleText(title)
                //标题文字大小
                .setTitleSize(20)
                //标题文字颜色
                .setTitleColor(Color.BLACK)
                //切换时是否还原，设置默认选中第一项。
                .isRestoreItem(true)
                .build();
        pvView.setPicker(textList);
        pvView.show();
    }
}

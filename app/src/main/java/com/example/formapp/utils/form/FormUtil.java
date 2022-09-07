package com.example.formapp.utils.form;

import android.content.Context;
import android.util.Log;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.formapp.utils.GsonUtil;
import com.example.formapp.utils.StringUtil;
import com.example.formapp.utils.form.adapter.FormListAdapter;
import com.example.formapp.utils.form.bean.FormDateBean;
import com.example.formapp.utils.form.bean.FormImageBean;
import com.example.formapp.utils.form.bean.FormListBean;
import com.example.formapp.utils.form.bean.FormSelectBean;
import com.example.formapp.utils.form.bean.FormTextBean;
import com.example.formapp.utils.form.bean.ObservableBean;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

public class FormUtil {
    private LinearLayout mLayout;
    private Context mContext;
    private Map<String, Object> dataMap = new HashMap<>();
    private List<FormListBean> formList = new ArrayList<>();
    private static final String TAG = "FormUtil";
    private Observer observer;
    private FormListAdapter adapter;

    public FormUtil(String json, LinearLayout layout, Context context) {
        this.mLayout = layout;
        this.mContext = context;
        createObserver();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                String type = jsonArray.getJSONObject(i).getString("type");
                String key = jsonArray.getJSONObject(i).getString("key");
                String itemJson = jsonArray.getJSONObject(i).toString();
                formList.add(new FormListBean(type, itemJson));
                if (!StringUtil.isEmpty(type)) {
                    switch (type) {
                        case "text":
                        case "edit":
                        case "date":
                            String value = jsonArray.getJSONObject(i).getString("value");
                            dataMap.put(key, value);
                            break;
                        case "select":
                            FormSelectBean selectBean = GsonUtil.fromJson(itemJson, FormSelectBean.class);
                            if (selectBean.getDefaultSelect() != null) {
                                dataMap.put(key, selectBean.getOptions().get(selectBean.getDefaultSelect()));
                            }
                            break;
                        case "image":
                            FormImageBean imageBean = GsonUtil.fromJson(itemJson, FormImageBean.class);
                            if (imageBean.getImageList() != null && imageBean.getImageList().size() > 0) {
                                dataMap.put(key, imageBean.getImageList());
                            }
                            break;
                        default:
                            break;
                    }
                }

            }
            initView();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化控件
     */
    private void initView() {
        adapter = new FormListAdapter(formList, mContext, observer);
        RecyclerView recyclerView = new RecyclerView(mContext);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(adapter);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mLayout.addView(recyclerView, layoutParams);
    }

    /**
     * 创建观察者
     */
    private void createObserver() {
        observer = new Observer() {

            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(Object o) {
                ObservableBean observableBean = (ObservableBean) o;
                if (!StringUtil.isEmpty(observableBean.getType())) {
                    switch (observableBean.getType()) {
                        case "select":
                            FormSelectBean.OptionBean optionBean = (FormSelectBean.OptionBean) observableBean.getValue();
                            dataMap.put(observableBean.getKey(), optionBean);
                            Log.e(TAG, "Position:" + observableBean.getPosition() + ",Key:" + observableBean.getKey() + ",Value:" + optionBean.getValue());
                            FormSelectBean selectBean = GsonUtil.fromJson(formList.get(observableBean.getPosition()).getItemJson(), FormSelectBean.class);
                            selectBean.setValue(optionBean.getValue());
                            formList.get(observableBean.getPosition()).setItemJson(GsonUtil.toJson(selectBean));
                            adapter.notifyItemChanged(observableBean.getPosition());
                            break;
                        case "text":
                        case "edit":
                            dataMap.put(observableBean.getKey(), observableBean.getValue());
                            FormTextBean textBean = GsonUtil.fromJson(formList.get(observableBean.getPosition()).getItemJson(), FormTextBean.class);
                            textBean.setValue(observableBean.getValue().toString());
                            formList.get(observableBean.getPosition()).setItemJson(GsonUtil.toJson(textBean));
                            adapter.notifyItemChanged(observableBean.getPosition());
                            break;
                        case "date":
                            dataMap.put(observableBean.getKey(), observableBean.getValue());
                            FormDateBean dateBean = GsonUtil.fromJson(formList.get(observableBean.getPosition()).getItemJson(), FormDateBean.class);
                            dateBean.setValue(observableBean.getValue().toString());
                            formList.get(observableBean.getPosition()).setItemJson(GsonUtil.toJson(dateBean));
                            adapter.notifyItemChanged(observableBean.getPosition());
                            break;
                        case "image":

                            break;
                        default:
                            break;
                    }
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
    }

    public Map<String, Object> getData() {
        return dataMap;
    }
}

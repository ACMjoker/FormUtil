package com.example.formapp.utils.form.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.formapp.R;
import com.example.formapp.utils.GsonUtil;
import com.example.formapp.utils.form.SelectView;
import com.example.formapp.utils.form.bean.FormDateBean;
import com.example.formapp.utils.form.bean.FormImageBean;
import com.example.formapp.utils.form.bean.FormListBean;
import com.example.formapp.utils.form.bean.FormSelectBean;
import com.example.formapp.utils.form.bean.FormTextBean;
import com.example.formapp.utils.form.bean.ObservableBean;
import com.example.formapp.view.SuperTextView;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;

public class FormListAdapter extends RecyclerView.Adapter {
    private List<FormListBean> mFormList;
    private Context mContext;
    private Observer mObserver;
    private LayoutInflater inflater;
    private static final int TEXT_TYPE = 0;
    private static final int IMAGE_TYPE = 1;
    private static final int SELECT_TYPE = 3;
    private static final int DATE_TYPE = 4;

    public FormListAdapter(List<FormListBean> formList, Context context, Observer observer) {
        this.mContext = context;
        this.mFormList = formList;
        this.mObserver = observer;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        switch (viewType) {
            case TEXT_TYPE:
            case SELECT_TYPE:
            case DATE_TYPE:
                holder = new TextHolder(inflater.inflate(R.layout.item_text_form_list, null, false));
                break;
            case IMAGE_TYPE:
                holder = new ImageListHolder(inflater.inflate(R.layout.item_image_form_list, null, false));
                break;
            default:
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        FormListBean formListBean = mFormList.get(position);
        int viewType = getItemViewType(position);
        switch (viewType) {
            case TEXT_TYPE:
                TextHolder textHolder = (TextHolder) holder;
                FormTextBean itemTextBean = GsonUtil.fromJson(formListBean.getItemJson(), FormTextBean.class);
                textHolder.superTextView.setTitle(itemTextBean.getTitle());
                textHolder.superTextView.setContent(itemTextBean.getValue());
                if (itemTextBean.getCanEdit() == null) {
                    if ("edit".equals(formListBean.getType())) {
                        textHolder.superTextView.setCanEdit(true);
                    } else {
                        textHolder.superTextView.setCanEdit(false);
                    }
                } else {
                    textHolder.superTextView.setCanEdit(itemTextBean.getCanEdit());
                }
                if (itemTextBean.getCanClick() != null && itemTextBean.getCanClick()) {
                    if ("text".equals(formListBean.getType())) {
                        textHolder.itemView.setOnClickListener(view -> {
                            Toast.makeText(mContext, itemTextBean.getTitle(), Toast.LENGTH_SHORT).show();
                        });
                    }
                }
                textHolder.superTextView.setTextChangeListener(editable -> {
                    int index = holder.getAbsoluteAdapterPosition();
                    ObservableBean observableBean = new ObservableBean(index, itemTextBean.getType(), itemTextBean.getKey(), editable.toString());
                    Observable.create(emitter -> {
                        emitter.onNext(observableBean);
                    }).subscribe(mObserver);
                });
                break;
            case IMAGE_TYPE:
                ImageListHolder imageListHolder = (ImageListHolder) holder;
                FormImageBean formImageBean = GsonUtil.fromJson(formListBean.getItemJson(), FormImageBean.class);
                imageListHolder.itemImageTitle.setTitle(formImageBean.getTitle());
                imageListHolder.itemImageTitle.setLine(false);
                imageListHolder.itemImageTitle.setCanEdit(false);
                imageListHolder.itemImageList.setLayoutManager(new GridLayoutManager(mContext, 3));
                imageListHolder.itemImageList.setAdapter(new ImageListAdapter(formImageBean.getImageList(), mContext));
                break;
            case SELECT_TYPE:
                TextHolder selectAdapter = (TextHolder) holder;
                FormSelectBean itemSelectBean = GsonUtil.fromJson(formListBean.getItemJson(), FormSelectBean.class);
                selectAdapter.superTextView.setTitle(itemSelectBean.getTitle());
                selectAdapter.superTextView.setContent(itemSelectBean.getValue());
                selectAdapter.superTextView.setCanEdit(false);
                if (itemSelectBean.getCanClick() == null || itemSelectBean.getCanClick()) {
                    selectAdapter.itemView.setOnClickListener(view -> {
                        int index = holder.getAbsoluteAdapterPosition();
                        SelectView.showOptionPickerView(itemSelectBean.getType(), itemSelectBean.getKey(), mContext, itemSelectBean.getTitle(), itemSelectBean.getOptions(), mObserver, index);
                    });
                }
                break;
            case DATE_TYPE:
                TextHolder dateAdapter = (TextHolder) holder;
                FormDateBean itemDateBean = GsonUtil.fromJson(formListBean.getItemJson(), FormDateBean.class);
                dateAdapter.superTextView.setTitle(itemDateBean.getTitle());
                dateAdapter.superTextView.setContent(itemDateBean.getValue());
                dateAdapter.superTextView.setCanEdit(false);
                if (itemDateBean.getCanClick() == null || itemDateBean.getCanClick()) {
                    dateAdapter.itemView.setOnClickListener(view -> {
                        int index = holder.getAbsoluteAdapterPosition();
                        SelectView.showTimePickerView(itemDateBean.getType(), itemDateBean.getKey(), mContext, itemDateBean.getTitle(), mObserver, index, itemDateBean.getSelectType(), itemDateBean.getDateType());
                    });
                }
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        FormListBean formListBean = mFormList.get(position);
        if ("text".equals(formListBean.getType()) || "edit".equals(formListBean.getType())) {
            return TEXT_TYPE;
        } else if ("image".equals(formListBean.getType())) {
            return IMAGE_TYPE;
        } else if ("select".equals(formListBean.getType())) {
            return SELECT_TYPE;
        } else if ("date".equals(formListBean.getType())) {
            return DATE_TYPE;
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return mFormList.size();
    }

    static class TextHolder extends RecyclerView.ViewHolder {
        private SuperTextView superTextView;

        public TextHolder(@NonNull View itemView) {
            super(itemView);
            superTextView = (SuperTextView) itemView.findViewById(R.id.item_stv);
        }
    }

    static class ImageListHolder extends RecyclerView.ViewHolder {
        private SuperTextView itemImageTitle;
        private RecyclerView itemImageList;

        public ImageListHolder(@NonNull View itemView) {
            super(itemView);
            itemImageTitle = itemView.findViewById(R.id.item_image_title);
            itemImageList = itemView.findViewById(R.id.item_image_list);
        }
    }
}

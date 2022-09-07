package com.example.formapp.view;

import static com.example.formapp.utils.CommonUtil.dip2px;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.text.Editable;
import android.text.Html;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.IntDef;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.example.formapp.R;
import com.example.formapp.utils.StringUtil;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 自定义输入控件,用法同普通控件
 *
 * @author tian
 */
public class SuperTextView extends LinearLayout {
    private ConstraintLayout viewSuperText;
    private TextView tvTitle;
    private EditText edInfo;
    private View viewLine;
    private ImageView imageRight;
    /**
     * 最大可输入字符数
     */
    int maxLength;
    /**
     * 右侧箭头是否显示
     */
    boolean showImageRight;
    /**
     * 是否拦截子项点击事件 默认不拦截
     */
    public boolean mIsIntercept = false;
    public EditTextChangeListener listener;
    public EditUpperLimitListener upperLimitListener;

    public SuperTextView(Context context) {
        this(context, null);
    }

    public SuperTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SuperTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.view_super_text, this, true);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        viewSuperText = findViewById(R.id.view_supertext);

        tvTitle = new TextView(context);
        tvTitle.setId(View.generateViewId());
        tvTitle.setGravity(Gravity.CENTER_VERTICAL);
        tvTitle.setTextColor(Color.parseColor("#99000000"));
        tvTitle.getPaint().setTextSize(getResources().getDimension(R.dimen.sp_14));

        edInfo = new EditText(context);
        edInfo.setId(View.generateViewId());
        edInfo.setBackground(null);
        edInfo.setPadding(0, 0, 0, 0);
        edInfo.getPaint().setTextSize(getResources().getDimension(R.dimen.sp_14));
        edInfo.setGravity(Gravity.CENTER_VERTICAL);
        edInfo.setHintTextColor(Color.parseColor("#40000000"));

        viewLine = new View(context);
        viewLine.setId(View.generateViewId());
        viewLine.setBackgroundColor(Color.parseColor("#d8d8d8"));
        ConstraintLayout.LayoutParams layoutParamsView = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) getResources().getDimension(R.dimen.dp_1));
        layoutParamsView.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
        layoutParamsView.setMarginStart((int) getResources().getDimension(R.dimen.dp_17));
        viewLine.setLayoutParams(layoutParamsView);
        viewSuperText.addView(viewLine);

        imageRight = new ImageView(context);
        imageRight.setId(View.generateViewId());
        imageRight.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        imageRight.setImageResource(R.mipmap.ic_right_right);
        imageRight.setVisibility(GONE);
        ConstraintLayout.LayoutParams layoutParamsImageView = new ConstraintLayout.LayoutParams((int) getResources().getDimension(R.dimen.dp_14), (int) getResources().getDimension(R.dimen.dp_14));
        layoutParamsImageView.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
        layoutParamsImageView.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        layoutParamsImageView.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
        layoutParamsImageView.setMarginEnd((int) getResources().getDimension(R.dimen.dp_16));
        imageRight.setLayoutParams(layoutParamsImageView);
        viewSuperText.addView(imageRight);

        //获取xml中的数据
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.SuperTextView,
                defStyleAttr, 0);
        String title = array.getString(R.styleable.SuperTextView_sp_title);
        String info = array.getString(R.styleable.SuperTextView_sp_info);
        int infoColor = array.getColor(R.styleable.SuperTextView_sp_infoColor, Color.parseColor("#D9000000"));
        String hint = array.getString(R.styleable.SuperTextView_sp_hint);
        boolean canEdit = array.getBoolean(R.styleable.SuperTextView_sp_canEdit, true);
        maxLength = array.getInt(R.styleable.SuperTextView_sp_maxLength, 0);
        int maxLines = array.getInt(R.styleable.SuperTextView_sp_maxLines, 0);
        int inputType = array.getInt(R.styleable.SuperTextView_sp_inputType, -1);
        boolean showLine = array.getBoolean(R.styleable.SuperTextView_sp_showLine, true);
        int lineColor = array.getColor(R.styleable.SuperTextView_sp_lineColor, Color.parseColor("#d8d8d8"));
        String digits = array.getString(R.styleable.SuperTextView_sp_digits);
        int titleWidth = array.getDimensionPixelSize(R.styleable.SuperTextView_sp_titleWidth, 0);
        boolean required = array.getBoolean(R.styleable.SuperTextView_sp_required, false);
        int titleColor = array.getColor(R.styleable.SuperTextView_sp_titleColor, Color.parseColor("#99000000"));
        int titleSize = array.getDimensionPixelOffset(R.styleable.SuperTextView_sp_titleSize, 0);
        int infoSize = array.getDimensionPixelOffset(R.styleable.SuperTextView_sp_infoSize, 0);
        int titleStyle = array.getInt(R.styleable.SuperTextView_sp_titleStyle, -1);
        int infoStyle = array.getInt(R.styleable.SuperTextView_sp_infoStyle, -1);
        int gravity = array.getInt(R.styleable.SuperTextView_sp_gravity, 1);
        int paddingEnd = array.getDimensionPixelSize(R.styleable.SuperTextView_sp_paddingEnd, 0);
        int paddingStart = array.getDimensionPixelSize(R.styleable.SuperTextView_sp_paddingStart, 0);
        int titleMarginStart = array.getDimensionPixelSize(R.styleable.SuperTextView_sp_titleMarginStart, 0);
        showImageRight = array.getBoolean(R.styleable.SuperTextView_sp_imageRight, false);
        int minHeight = array.getDimensionPixelSize(R.styleable.SuperTextView_sp_minHeight, 0);
        //用完记得销毁
        array.recycle();
        //初始化标题
        ConstraintLayout.LayoutParams layoutParams2 = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams2.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
        layoutParams2.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
        layoutParams2.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        if (titleMarginStart > 0) {
            layoutParams2.setMarginStart(titleMarginStart);
        } else {
            layoutParams2.setMarginStart((int) getResources().getDimension(R.dimen.dp_20));
        }
        tvTitle.setLayoutParams(layoutParams2);
        viewSuperText.addView(tvTitle);
        //初始化内容
        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
        layoutParams.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
        layoutParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        layoutParams.startToEnd = tvTitle.getId();
        layoutParams.setMarginStart((int) getResources().getDimension(R.dimen.dp_12));
        layoutParams.setMarginEnd((int) getResources().getDimension(R.dimen.dp_40));
        edInfo.setLayoutParams(layoutParams);
        viewSuperText.addView(edInfo);
        //设置控件最小高度
        if (minHeight > 0) {
            viewSuperText.setMinHeight(minHeight);
        }
        //设置标题宽度
        if (titleWidth > 0) {
            tvTitle.setWidth(titleWidth);
        }
        //设置内容框边距
        setInfoStartAndEndPadding(paddingStart, paddingEnd);
        //设置标题字体大小
        setTitleSize(titleSize, TextSizeType.PX);
        //设置内容字体大小
        setInfoSize(infoSize, TextSizeType.PX);
        // 设置标题字体格式
        setTitleStyle(titleStyle);
        // 设置内容字体格式
        setInfoStyle(infoStyle);
        //设置标题
        if (!StringUtil.isEmpty(title)) {
            setTitle(title);
        }
        //是否显示必填星星
        setRequired(required);
        //设置提示信息
        if (!StringUtil.isEmpty(hint)) {
            setInfoHint(hint);
        }
        //设置能否输入
        setCanEdit(canEdit);
        //设置是否拦截子控件的点击事件,避免子控件拦截点击事件
        mIsIntercept = !canEdit;
        //设置输入的类型
        if (inputType != -1) {
            setInfoInputType(inputType);
        }
        //设置底部横线颜色
        setLineColor(lineColor);
        //设置是否显示底部横线
        setLine(showLine);
        //设置是右侧箭头是否显示
        setImageRight(showImageRight);
        //设置能否输入哪些字符
        if (!StringUtil.isEmpty(digits)) {
            setInfoDigits(digits);
        }
        //设置标题颜色
        setTitleColor(titleColor);
        //设置内容颜色
        setInfoColor(infoColor);
        //设置最大输入行数
        setMaxLines(maxLines);
        //设置内容
        if (!StringUtil.isEmpty(info)) {
            setContent(info);
        }
        //设置对齐方式
        setInfoGravity(gravity);
        //初始化监听
        initListener();
    }

    private void initListener() {
        //为输入框添加监听
        edInfo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (maxLength > 0 && s != null && s.length() > maxLength) {
                    s.delete(maxLength, s.length());
                    if (upperLimitListener != null) {
                        upperLimitListener.onUpperLimit(maxLength);
                    }
                }

                if (listener != null) {
                    listener.onTextChangeAfter(s);
                }
            }
        });
    }

    /**
     * 是否拦截子控件点击事件的方法
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //true就是拦截  false  就是不拦截，拦截的意思是事件不会继续往下分发，如果当前View，处理这个点击事件,则事件到此终止，如果不处理这次事件，则事件会继续往上传递，不会往下分发了
        return mIsIntercept;
    }

    /**
     * 设置内容信息
     */
    public void setContent(String info) {
        edInfo.setText(info);
    }

    /**
     * 获取输入的信息
     */
    public String getContent() {
        return edInfo.getText().toString();
    }

    /**
     * 信息是否为空,便于做非空判断时使用
     */
    public boolean isContentEmpty() {
        return TextUtils.isEmpty(edInfo.getText().toString());
    }

    /**
     * 输入监听
     */
    public void setTextChangeListener(EditTextChangeListener listener) {
        this.listener = listener;
    }

    /**
     * 文字变化监听的监听器,只监听文字写完的监听
     */
    public interface EditTextChangeListener {
        /**
         * 监听文字变化
         */
        void onTextChangeAfter(Editable editable);
    }

    /**
     * 输入上限监听
     */
    public void setEditUpperLimitListener(EditUpperLimitListener upperLimitListener) {
        this.upperLimitListener = upperLimitListener;
    }

    /**
     * 监听文字输入是否达到上限
     */
    public interface EditUpperLimitListener {
        /**
         * 监听文字输入是否达到上限
         */
        void onUpperLimit(int maxLength);
    }

    /**
     * 设置标题的方法
     */
    public void setTitle(String info) {
        tvTitle.setText(Html.fromHtml(info));
    }

    /**
     * 是否显示必填星星
     */
    public void setRequired(boolean required) {
        if (required) {
            String strMsg = tvTitle.getText() + "<font color=\"#EB2537\"> *</font>";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                tvTitle.setText(Html.fromHtml(strMsg, Html.FROM_HTML_MODE_LEGACY));
            } else {
                tvTitle.setText(Html.fromHtml(strMsg));
            }
        }
    }

    /**
     * 设置是否可编辑
     */
    public void setCanEdit(boolean isEdit) {
        //设置能否输入
        edInfo.setEnabled(isEdit);
        //设置是否拦截子控件的点击事件,避免子控件拦截点击事件
        mIsIntercept = !isEdit;
    }

    /**
     * 获取有多少行数据
     */
    public int getLines() {
        return edInfo.getLineCount();
    }

    /**
     * 设置输入限制（限制特殊字符、字符个数等）
     */
    public void setFilters(InputFilter[] etFilters) {
        edInfo.setFilters(etFilters);
    }

    /**
     * 设置标题宽度
     *
     * @param titleWidth 标题宽度(dp)
     */
    public void setTitleWidth(int titleWidth) {
        tvTitle.setWidth(dip2px(titleWidth));
    }

    /**
     * 设置内容框开始内边距
     *
     * @param spPaddingStart 开始内边距
     */
    public void setInfoPaddingStart(int spPaddingStart) {
        setInfoStartAndEndPadding(spPaddingStart, -1);
    }

    /**
     * 设置内容框结束内边距
     *
     * @param spPaddingEnd 结束内边距
     */
    public void setInfoPaddingEnd(int spPaddingEnd) {
        setInfoStartAndEndPadding(-1, spPaddingEnd);
    }

    /**
     * 设置内容框边距
     *
     * @param paddingStart 开始内边距
     * @param paddingEnd   结束内边距
     */
    public void setInfoStartAndEndPadding(int paddingStart, int paddingEnd) {
        if (paddingEnd >= 0 || paddingStart >= 0) {
            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(viewSuperText);
            if (!showImageRight && paddingEnd > 0) {
                constraintSet.setMargin(edInfo.getId(), ConstraintSet.END, paddingEnd);
                constraintSet.applyTo(viewSuperText);
            }
            if (paddingStart > 0) {
                constraintSet.setMargin(edInfo.getId(), ConstraintSet.START, paddingStart);
                constraintSet.applyTo(viewSuperText);
            }
        }
    }

    /**
     * 长度单位
     */
    @Retention(RetentionPolicy.RUNTIME)
    @IntDef({TextSizeType.PX, TextSizeType.DP, TextSizeType.SP})
    public @interface TextSizeType {
        int PX = TypedValue.COMPLEX_UNIT_PX;
        int DP = TypedValue.COMPLEX_UNIT_DIP;
        int SP = TypedValue.COMPLEX_UNIT_SP;
    }

    /**
     * 设置标题字体大小
     *
     * @param titleSize 字体大小
     * @param type      单位
     */
    public void setTitleSize(int titleSize, int type) {
        if (titleSize > 0) {
            tvTitle.setTextSize(type, titleSize);
        }
    }

    /**
     * 设置内容字体大小
     *
     * @param infoSize 字体大小
     * @param type     单位
     */
    public void setInfoSize(int infoSize, int type) {
        if (infoSize > 0) {
            edInfo.setTextSize(type, infoSize);
        }
    }

    /**
     * 字体类型
     */
    @Retention(RetentionPolicy.RUNTIME)
    @IntDef({TextStyleType.BOLD, TextStyleType.ITALIC, TextStyleType.NORMAL})
    public @interface TextStyleType {
        int BOLD = 1;
        int ITALIC = 2;
        int NORMAL = 3;
    }

    /**
     * 设置标题字体格式
     */
    public void setTitleStyle(int titleStyle) {
        switch (titleStyle) {
            case TextStyleType.BOLD:
                tvTitle.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                break;
            case TextStyleType.ITALIC:
                tvTitle.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC));
                break;
            case TextStyleType.NORMAL:
                tvTitle.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                break;
            default:
                break;
        }
    }

    /**
     * 设置内容字体格式
     */
    public void setInfoStyle(int infoStyle) {
        switch (infoStyle) {
            case TextStyleType.BOLD:
                edInfo.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                break;
            case TextStyleType.ITALIC:
                edInfo.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC));
                break;
            case TextStyleType.NORMAL:
                edInfo.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                break;
            default:
                break;
        }
    }

    /**
     * 设置提示信息
     */
    public void setInfoHint(String hint) {
        edInfo.setHint(hint);
    }

    /**
     * 输入类型
     */
    @Retention(RetentionPolicy.RUNTIME)
    @IntDef({InfoInputType.NUMBER, InfoInputType.NUMBER_DECIMAL, InfoInputType.NUMBER_SIGNED})
    public @interface InfoInputType {
        /**
         * 纯数字
         */
        int NUMBER = 1;
        /**
         * 带小数点类型
         */
        int NUMBER_DECIMAL = 2;
        /**
         * 有符号数字格式
         */
        int NUMBER_SIGNED = 3;
    }

    /**
     * 设置输入的类型
     */
    public void setInfoInputType(int inputType) {
        switch (inputType) {
            case InfoInputType.NUMBER:
                //纯数字 number
                edInfo.setInputType(InputType.TYPE_CLASS_NUMBER);
                break;
            case InfoInputType.NUMBER_DECIMAL:
                //带小数点类型 numberDecimal
                edInfo.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                break;
            case InfoInputType.NUMBER_SIGNED:
                //有符号数字格式 numberSigned
                edInfo.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);
                break;
            default:
                break;
        }
    }

    /**
     * 设置底部横线颜色
     */
    public void setLineColor(int lineColor) {
        viewLine.setBackgroundColor(lineColor);
    }

    /**
     * 设置是否显示底部横线
     */
    public void setLine(boolean showLine) {
        if (showLine) {
            viewLine.setVisibility(VISIBLE);
        } else {
            viewLine.setVisibility(GONE);
        }
    }

    /**
     * 设置是右侧箭头是否显示
     */
    public void setImageRight(boolean showImageRight) {
        if (showImageRight) {
            imageRight.setVisibility(VISIBLE);
        } else {
            imageRight.setVisibility(GONE);
        }
    }

    /**
     * 设置能否输入哪些字符
     */
    public void setInfoDigits(String digits) {
        if (!TextUtils.isEmpty(digits)) {
            edInfo.setKeyListener(DigitsKeyListener.getInstance(digits));
        }
    }

    /**
     * 设置标题颜色
     */
    public void setTitleColor(int titleColor) {
        tvTitle.setTextColor(titleColor);
    }

    /**
     * 设置内容颜色
     */
    public void setInfoColor(int color) {
        edInfo.setTextColor(color);
    }

    /**
     * 设置最大输入个数
     */
    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    /**
     * 设置最大输入行数
     */
    public void setMaxLines(int maxLines) {
        if (maxLines > 0) {
            edInfo.setKeyListener(null);
            edInfo.setEllipsize(TextUtils.TruncateAt.END);
            edInfo.setMaxLines(maxLines);
        }
    }

    /**
     * 对齐类型
     */
    @Retention(RetentionPolicy.RUNTIME)
    @IntDef({GravityType.SE_START, GravityType.SE_CENTER, GravityType.SE_END})
    public @interface GravityType {
        int SE_START = 1;
        int SE_CENTER = 2;
        int SE_END = 3;
    }

    /**
     * 设置对齐方式
     */
    public void setInfoGravity(int gravityType) {
        switch (gravityType) {
            case GravityType.SE_START:
                edInfo.setGravity(Gravity.START);
                break;
            case GravityType.SE_CENTER:
                edInfo.setGravity(Gravity.CENTER);
                break;
            case GravityType.SE_END:
                edInfo.setGravity(Gravity.END);
                break;
            default:
                break;
        }
    }

    /**
     * 设置控件最小高度
     */
    public void setMinHeight(int minHeight) {
        viewSuperText.setMinHeight(dip2px(minHeight));
    }
}

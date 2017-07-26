package com.dzq.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.dzq.viewindicator.R;

/**
 * 指示器
 * Created by dingzuoqiang on 2017/7/26.
 * 530858106@qq.com
 */

public class ViewIndicator extends LinearLayout {

    private Drawable unSelectedDrawable;
    private Drawable selectedDrawable;
    private int itemCount;
    private int selectedIndicatorColor = 0xffff0000;
    private int unSelectedIndicatorColor = 0x88888888;
    private Shape indicatorShape = Shape.oval;
    private int selectedIndicatorHeight = 6;
    private int selectedIndicatorWidth = 6;
    private int unSelectedIndicatorHeight = 6;
    private int unSelectedIndicatorWidth = 6;

    private int indicatorSpace = 3;

    private int cornerRadii = 0;//拐角半径
    private OnItemClickListener onItemClickListener;

    private enum Shape {
        rect, oval
    }

    public ViewIndicator(Context context) {
        super(context);
        init(null, 0);
    }

    public ViewIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public ViewIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }

    private void init(AttributeSet attrs, int defStyle) {

        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.ViewIndicator, defStyle, 0);
        selectedIndicatorColor = array.getColor(R.styleable.ViewIndicator_selectedColor, selectedIndicatorColor);
        unSelectedIndicatorColor = array.getColor(R.styleable.ViewIndicator_unSelectedColor, unSelectedIndicatorColor);
        int shape = array.getInt(R.styleable.ViewIndicator_indicatorShape, Shape.oval.ordinal());
        for (Shape shape1 : Shape.values()) {
            if (shape1.ordinal() == shape) {
                indicatorShape = shape1;
                break;
            }
        }
        selectedIndicatorHeight = (int) array.getDimension(R.styleable.ViewIndicator_selectedHeight, selectedIndicatorHeight);
        selectedIndicatorWidth = (int) array.getDimension(R.styleable.ViewIndicator_selectedWidth, selectedIndicatorWidth);
        unSelectedIndicatorHeight = (int) array.getDimension(R.styleable.ViewIndicator_unSelectedHeight, unSelectedIndicatorHeight);
        unSelectedIndicatorWidth = (int) array.getDimension(R.styleable.ViewIndicator_unSelectedWidth, unSelectedIndicatorWidth);

        indicatorSpace = (int) array.getDimension(R.styleable.ViewIndicator_indicatorSpace, indicatorSpace);
        cornerRadii = (int) array.getDimension(R.styleable.ViewIndicator_cornerRadii, 0);
        array.recycle();

        //绘制未选中状态图形
        LayerDrawable unSelectedLayerDrawable;
        LayerDrawable selectedLayerDrawable;
        GradientDrawable unSelectedGradientDrawable;
        unSelectedGradientDrawable = new GradientDrawable();

        //绘制选中状态图形
        GradientDrawable selectedGradientDrawable;
        selectedGradientDrawable = new GradientDrawable();
        switch (indicatorShape) {
            case rect:
                unSelectedGradientDrawable.setShape(GradientDrawable.RECTANGLE);
                selectedGradientDrawable.setShape(GradientDrawable.RECTANGLE);
                setCornerRadii(unSelectedGradientDrawable, cornerRadii, cornerRadii, cornerRadii, cornerRadii);
                setCornerRadii(selectedGradientDrawable, cornerRadii, cornerRadii, cornerRadii, cornerRadii);
                break;
            case oval:
                unSelectedGradientDrawable.setShape(GradientDrawable.OVAL);
                selectedGradientDrawable.setShape(GradientDrawable.OVAL);
                break;
        }
        unSelectedGradientDrawable.setColor(unSelectedIndicatorColor);
        unSelectedGradientDrawable.setSize(unSelectedIndicatorWidth, unSelectedIndicatorHeight);
        unSelectedLayerDrawable = new LayerDrawable(new Drawable[]{unSelectedGradientDrawable});
        unSelectedDrawable = unSelectedLayerDrawable;

        selectedGradientDrawable.setColor(selectedIndicatorColor);
        selectedGradientDrawable.setSize(selectedIndicatorWidth, selectedIndicatorHeight);
        selectedLayerDrawable = new LayerDrawable(new Drawable[]{selectedGradientDrawable});
        selectedDrawable = selectedLayerDrawable;
    }

    private void setCornerRadii(GradientDrawable drawable,
                                float r0, float r1, float r2, float r3) {
        drawable.setCornerRadii(new float[]{r0, r0, r1, r1,
                r2, r2, r3, r3});
    }

    public void init(int totalNum) {
        itemCount = totalNum;
        removeAllViewsInLayout();
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);
        ViewGroup.LayoutParams layoutParams;
        //初始化指示器，并添加到指示器容器布局
        for (int i = 0; i < itemCount; i++) {
            ImageView indicator = new ImageView(getContext());
            layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            indicator.setLayoutParams(layoutParams);
            if (i != itemCount - 1)
                indicator.setPadding(0, 0, indicatorSpace, 0);
            indicator.setImageDrawable(unSelectedDrawable);
            addView(indicator);
            indicator.setTag(i);
            indicator.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = (int) v.getTag();
                    if (onItemClickListener != null)
                        onItemClickListener.onnItemClick(position);
                }
            });
        }
        switchIndicator(0);
    }

    /**
     * 切换指示器状态
     *
     * @param currentPosition 当前位置
     */
    public void switchIndicator(int currentPosition) {
        for (int i = 0; i < this.getChildCount(); i++) {
            ((ImageView) this.getChildAt(i)).setImageDrawable(i == currentPosition ? selectedDrawable : unSelectedDrawable);
        }
    }

    public interface OnItemClickListener {

        public void onnItemClick(int position);

    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}

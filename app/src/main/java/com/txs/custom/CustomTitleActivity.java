package com.txs.custom;

import android.content.Context;

import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.txs.R;
import com.txs.base.TopClickEventInterface;

import butterknife.ButterKnife;

public class CustomTitleActivity extends LinearLayout implements TopClickEventInterface {
    /*@BindView(R.id.iv_left_back)*/
    ImageView iv_left_back;
    // @BindView(R.id.iv_right_search)
    ImageView iv_right_search;
    // @BindView(R.id.iv_icon_order)
    ImageView iv_icon_order;
    // @BindView(R.id.txt_title)
    TextView txt_title;

    public CustomTitleActivity(Context context) {
        super(context);
    }

    public CustomTitleActivity(Context context, AttributeSet attrs) {
        super(context, attrs);
        ButterKnife.bind(this);
        initView(context, attrs);

    }

    public CustomTitleActivity(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    String title;
    int visible;
    int mReference;
    int searchId;
    int leftSrc;

    public void initView(final Context context, AttributeSet attributeSet) {
        LayoutInflater.from(context).inflate(R.layout.activity_custom_title, this);
        iv_left_back = (ImageView) findViewById(R.id.iv_left_back);
        iv_right_search = (ImageView) findViewById(R.id.iv_right_search);
        iv_icon_order = (ImageView) findViewById(R.id.iv_icon_order);
        txt_title = (TextView) findViewById(R.id.txt_title);

        if (attributeSet != null) {
            TypedArray ta = context.obtainStyledAttributes(attributeSet, R.styleable.CustomTitleActivity);
            title = ta.getString(R.styleable.CustomTitleActivity_title);
            visible = ta.getInt(R.styleable.CustomTitleActivity_Visibility, 0);
            mReference = ta.getResourceId(R.styleable.CustomTitleActivity_orderSrc, 0);
            searchId = ta.getResourceId(R.styleable.CustomTitleActivity_rightSearch, 0);
            leftSrc =ta.getResourceId(R.styleable.CustomTitleActivity_leftSrc,0);
            txt_title.setText(title);
            iv_icon_order.setImageResource(mReference);
            iv_right_search.setImageResource(searchId);
            iv_left_back.setImageResource(leftSrc);
        }

       /* if (visible==0){
            visible=VISIBLE;
            iv_icon_order.setVisibility(visible);
        }else if (visible==4){
            visible=INVISIBLE;
            iv_icon_order.setVisibility(visible);
        }else if(visible==8){
            visible=GONE;
            iv_icon_order.setVisibility(visible);
        }*/


       /* switch (visible){
            case 0:
                visible=VISIBLE;
                iv_icon_order.setVisibility(visible);
                break;

            case 4:
                visible=INVISIBLE;
                iv_icon_order.setVisibility(visible);
                break;

            case 8:
                visible=GONE;
                iv_icon_order.setVisibility(visible);
                break;

            default:

               break;`

        }*/


    }


    @Override
    public void setBackListener(OnClickListener clickListener) {
        iv_left_back.setOnClickListener(clickListener);
    }

    @Override
    public void setSearchListener(OnClickListener clickListener) {
        iv_right_search.setOnClickListener(clickListener);
    }

    @Override
    public void setOrderListener(OnClickListener clickListener) {
        iv_icon_order.setOnClickListener(clickListener);
    }
}

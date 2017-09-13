package com.txs.base;

import android.view.View;

/**
 * Created by lkpassword on 2017/7/7.
 */

public interface TopClickEventInterface {
        public void setBackListener(View.OnClickListener clickListener);
        public void setSearchListener(View.OnClickListener clickListener);
        public void setOrderListener(View.OnClickListener clickListener);
}

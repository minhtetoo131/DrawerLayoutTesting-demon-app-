package com.annonymouss.sexeducation.components.rvset;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by min on 3/28/2018.
 */

public class LabelShowHideRecyclerView extends SmartRecyclerView {
    private View mTitleView;
    public LabelShowHideRecyclerView(Context context) {
        super(context);
        init(context, null);
    }

    public LabelShowHideRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public LabelShowHideRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }
    public void setTitleView(View view){
        mTitleView = view ;
        checkIfEmpty();
    }

    @Override
    protected void checkIfEmpty() {
        super.checkIfEmpty();
        if (mTitleView != null && getAdapter() != null) {
            final boolean isEmpty = getAdapter().getItemCount() == 0;
            mTitleView.setVisibility(isEmpty ? GONE : VISIBLE);
        }
    }
}

package com.annonymouss.sexeducation.viewholders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.annonymouss.sexeducation.R;
import com.annonymouss.sexeducation.data.vos.AnatomyVO;

import butterknife.BindView;

public class AnatomyViewHolder extends BaseViewHolder<AnatomyVO> {
    @BindView(R.id.iv_anatomy)ImageView ivAvatomy;
    @BindView(R.id.tv_anatomy_title)TextView tvAnatomyTitle;


    public AnatomyViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setData(AnatomyVO data) {

    }

    @Override
    public void onClick(View view) {

    }
}

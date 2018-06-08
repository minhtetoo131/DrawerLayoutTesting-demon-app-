package com.annonymouss.sexeducation.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.annonymouss.sexeducation.R;
import com.annonymouss.sexeducation.data.vos.AnatomyVO;
import com.annonymouss.sexeducation.viewholders.AnatomyViewHolder;

public class AnatomyAdapter extends BaseRecyclerAdapter<AnatomyViewHolder,AnatomyVO> {

    public AnatomyAdapter(Context context) {
        super(context);
    }

    @Override
    public AnatomyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.item_view_anatomy,parent,false);
        return new AnatomyViewHolder(itemView);
    }
}

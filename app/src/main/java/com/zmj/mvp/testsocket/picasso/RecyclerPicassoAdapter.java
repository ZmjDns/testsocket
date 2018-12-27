package com.zmj.mvp.testsocket.picasso;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zmj.mvp.testsocket.R;

import java.util.List;

/**
 * @author Zmj
 * @date 2018/12/27
 */
public class RecyclerPicassoAdapter extends RecyclerView.Adapter<RecyclerPicassoAdapter.PicHilder> {

    private Context mContext;
    private List<String> urlLists;
    private LayoutInflater mInflater;

    public RecyclerPicassoAdapter(Context mContext, List<String> urlLists) {
        this.mContext = mContext;
        this.urlLists = urlLists;
        this.mInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public PicHilder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.recycler_picasso_item,viewGroup,false);
        PicHilder picHilder = new PicHilder(view);

        return picHilder;
    }

    @Override
    public void onBindViewHolder(@NonNull PicHilder picHilder, int position) {
        Picasso
                .get()
                .load(urlLists.get(position))
//                .fit()
                .into(picHilder.iv_picItem);
    }

    @Override
    public int getItemCount() {
        return urlLists.size();
    }

    class PicHilder extends RecyclerView.ViewHolder {
        TextView tv_picDesc;
        ImageView iv_picItem;
        public PicHilder(@NonNull View itemView) {
            super(itemView);
            tv_picDesc = itemView.findViewById(R.id.tv_picDesc);
            iv_picItem = itemView.findViewById(R.id.iv_picItem);
        }
    }
}

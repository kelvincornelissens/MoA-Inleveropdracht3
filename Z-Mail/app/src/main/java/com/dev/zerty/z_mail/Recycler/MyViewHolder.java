package com.dev.zerty.z_mail.Recycler;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dev.zerty.z_mail.R;

public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnClickListener {

    //All have to be public to be accessable for the MyRecyclerAdapter class.
    ImageView imgView;
    TextView tName;
    TextView tStudentNumber;
    TextView tClassname;
    ItemClickListener itemClickListener;
    ItemClickListener itemLongClickListener;
    RelativeLayout container;

    public MyViewHolder(View itemView) {
        super(itemView);

        //Init controls
        imgView = (ImageView) itemView.findViewById(R.id.imageView);
        tName = (TextView) itemView.findViewById(R.id.tName);
        tStudentNumber = (TextView) itemView.findViewById(R.id.tStudentNumber);
        tClassname = (TextView) itemView.findViewById(R.id.tClassname);
        container = (RelativeLayout) itemView.findViewById(R.id.cardViewID);

        //These clicklisteners are used in the MyRecyclerAdapter class, because I can only pass the position from there
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    public void onClickListener(ItemClickListener ic) {
        this.itemClickListener = ic;
    }
    public void onLongClickListener(ItemClickListener ic) {
        this.itemLongClickListener = ic;
    }

    @Override
    public void onClick(View v) {
        this.itemClickListener.onItemClick(v, getLayoutPosition());
    }

    @Override
    public boolean onLongClick(View v) {
        this.itemLongClickListener.onItemClick(v, getLayoutPosition());
        return true;
    }
}

package com.example.itransit;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class StopCardAdapter extends RecyclerView.Adapter<StopCardAdapter.StopViewHolder>{

    private ArrayList<StopCardItem> mStopList;
    private ArrayList<StopCardItem> mStopFullList;
    private OnItemClickListener mListener;

    public static class StopViewHolder extends RecyclerView.ViewHolder{

        //VARIABLE
        public ImageView mImageView;
        public TextView mTextView;

        public StopViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            mImageView=itemView.findViewById(R.id.imageViewBus1);
            mTextView=itemView.findViewById(R.id.textViewBusName);
            //Onclick Method on Stop
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null){
                        int position=getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }

                    }
                }
            });
        }
    }

    public StopCardAdapter(ArrayList<StopCardItem> stopList){
        mStopList=stopList;
        mStopFullList=new ArrayList<>(stopList);
    }

    @NonNull
    @Override
    public StopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.stop_card,parent,false);
        StopViewHolder svh=new StopViewHolder(v,mListener);
        return svh;
    }

    @Override
    public void onBindViewHolder(@NonNull StopViewHolder holder, int position) {
        StopCardItem currentItem=mStopList.get(position);
        holder.mImageView.setImageResource(currentItem.getImageResource());
        holder.mTextView.setText(currentItem.getText());
    }

    @Override
    public int getItemCount() {
        return mStopList.size();
    }


    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        mListener=listener;
    }
}

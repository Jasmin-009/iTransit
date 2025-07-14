package com.example.itransit;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BusCardAdapter extends RecyclerView.Adapter<BusCardAdapter.StopViewHolder> {

    private ArrayList<BusCardItem> mStopList;
    private StopCardAdapter.OnItemClickListener mListener;


    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public void setOnItemClickListener(StopCardAdapter.OnItemClickListener listener){
        mListener=listener;
    }

    public static class StopViewHolder extends RecyclerView.ViewHolder{
        public ImageView mImageView;
        public TextView mTextView,mChangeBus,mPrice;

        public StopViewHolder(@NonNull View itemView, final StopCardAdapter.OnItemClickListener listener) {
            super(itemView);
            mImageView=itemView.findViewById(R.id.imageViewBus1);
            mTextView=itemView.findViewById(R.id.textViewBusName1);
            mChangeBus=itemView.findViewById(R.id.textViewChangeBus);
            mPrice=itemView.findViewById(R.id.textViewPrice);
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

    public BusCardAdapter(ArrayList<BusCardItem> stopList){
        mStopList=stopList;
    }

    @NonNull
    @Override
    public StopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_bus_card,parent,false);
        BusCardAdapter.StopViewHolder svh= new StopViewHolder(v,mListener);
        return svh;
    }

    @Override
    public void onBindViewHolder(@NonNull StopViewHolder holder, int position) {
        BusCardItem currentItem=mStopList.get(position);
        holder.mImageView.setImageResource(currentItem.getImageResource());
        holder.mTextView.setText(currentItem.getText());
        holder.mChangeBus.setText(currentItem.getChangeStation());
        holder.mPrice.setText(currentItem.getPrice());
    }

    @Override
    public int getItemCount() {
        return mStopList.size();
    }
}

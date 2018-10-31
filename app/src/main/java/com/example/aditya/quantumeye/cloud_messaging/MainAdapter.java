package com.example.aditya.quantumeye.cloud_messaging;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.aditya.quantumeye.R;
import com.example.aditya.quantumeye.api.NotificationAlert;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder>{

    private Context mContext;
    private ArrayList<NotificationAlert> mAlerts;


    public MainAdapter(Context context, ArrayList<NotificationAlert> alerts){
        mAlerts = alerts;
        mContext = context;
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.main_list_item, viewGroup, false);

        return new MainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder mainViewHolder, int position) {
        mainViewHolder.Bind(mAlerts.get(position));
    }

    @Override
    public int getItemCount() {
        return mAlerts.size();
    }

    public class MainViewHolder extends RecyclerView.ViewHolder{

        private ImageView mItemImage;
        private TextView mMessage;

        public MainViewHolder(View view){
            super(view);
            mItemImage = (ImageView) view.findViewById(R.id.list_item_imageView);
            mMessage = (TextView) view.findViewById(R.id.list_item_textView);
        }

        public void Bind(final NotificationAlert notificationAlert) {
            mMessage.setText(notificationAlert.getMessage());
            /*Picasso.get()
                    .load(notificationAlert.getImageUrl())
                    .centerCrop()
                    .error(R.drawable.illegal_error_image)
                    .into(mItemImage); */
            Glide.with(mContext)
                    .load(notificationAlert.getImageUrl())
                    .into(mItemImage);
        }
    }




}
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
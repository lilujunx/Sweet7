package com.example.ticket.ui.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.ticket.R;
import com.example.ticket.entity.passenger.PassengerEntity;

import java.util.List;

public class ItemLvMinePassengerActivityAdapter extends BaseAdapter {

    private List<PassengerEntity.PassengerBean> mEntities;
    private View.OnClickListener mOnClickListener;
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public ItemLvMinePassengerActivityAdapter(Context context, List<PassengerEntity.PassengerBean> entities) {
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.mEntities = entities;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    @Override
    public int getCount() {
        return mEntities.size();
    }

    @Override
    public PassengerEntity.PassengerBean getItem(int position) {
        return mEntities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_lv_mine_passenger_activity, parent, false);
            convertView.setTag(new ViewHolder(convertView));
        }
        initializeViews((PassengerEntity.PassengerBean) getItem(position), (ViewHolder) convertView.getTag(), position);
        return convertView;
    }

    private void initializeViews(PassengerEntity.PassengerBean entity, ViewHolder holder, int position) {
        //TODO implement
        holder.tvNameItemLvMinePassengerActivity.setText(entity.getPName());
        holder.tvIdNumItemLvMinePassengerActivity.setText(entity.getPIdNum());
        holder.btDeleteItemLvMinePassengerActivity.setTag(position);

        if(mOnClickListener!=null){
            holder.btDeleteItemLvMinePassengerActivity.setOnClickListener(mOnClickListener);
        }
    }

    protected class ViewHolder {
        private TextView tvNameItemLvMinePassengerActivity;
        private TextView tvIdNumItemLvMinePassengerActivity;
        private Button btDeleteItemLvMinePassengerActivity;

        public ViewHolder(View view) {
            tvNameItemLvMinePassengerActivity = (TextView) view.findViewById(R.id.tv_name_item_lv_mine_passenger_activity);
            tvIdNumItemLvMinePassengerActivity = (TextView) view.findViewById(R.id.tv_idNum_item_lv_mine_passenger_activity);
            btDeleteItemLvMinePassengerActivity = (Button) view.findViewById(R.id.bt_delete_item_lv_mine_passenger_activity);
        }
    }
}

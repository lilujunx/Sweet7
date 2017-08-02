package com.example.ticket.ui.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ticket.R;
import com.example.ticket.entity.passenger.PassengerEntity;

import java.util.List;

public class ItemLvPassengerTicketActivityAdapter extends BaseAdapter {

    private List<PassengerEntity.PassengerBean> mEntities;

    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public ItemLvPassengerTicketActivityAdapter(Context context, List<PassengerEntity.PassengerBean> entities) {
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.mEntities = entities;
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
            convertView = mLayoutInflater.inflate(R.layout.item_lv_passenger_ticket_activity, parent, false);
            convertView.setTag(new ViewHolder(convertView));
        }
        initializeViews((PassengerEntity.PassengerBean) getItem(position), (ViewHolder) convertView.getTag(), position);
        return convertView;
    }

    private void initializeViews(PassengerEntity.PassengerBean entity, ViewHolder holder, int position) {
        //TODO implement
        holder.tvNameItemLvPassengerTicketActivity.setText(entity.getPName());
        holder.tvIdNumItemLvPassengerTicketActivity.setText(entity.getPIdNum());
    }

    protected class ViewHolder {
        private TextView tvNameItemLvPassengerTicketActivity;
        private TextView tvIdNumItemLvPassengerTicketActivity;

        public ViewHolder(View view) {
            tvNameItemLvPassengerTicketActivity = (TextView) view.findViewById(R.id.tv_name_item_lv_passenger_ticket_activity);
            tvIdNumItemLvPassengerTicketActivity = (TextView) view.findViewById(R.id.tv_idNum_item_lv_passenger_ticket_activity);
        }
    }
}

package com.example.ticket.ui.adapter;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.ticket.R;
import com.example.ticket.entity.passenger.PassengerEntity;

import java.util.List;

public class ItemLvPassengerActivityAdapter extends BaseAdapter{

    private List<PassengerEntity.PassengerBean> mEntities;

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private int mPosition;
    private View.OnClickListener mOnClickListener;

    public ItemLvPassengerActivityAdapter(Context context, List<PassengerEntity.PassengerBean> entities) {
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.mEntities = entities;
    }

    public void setEntities(List<PassengerEntity.PassengerBean> entities) {
        mEntities = entities;
        notifyDataSetChanged();
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
            convertView = mLayoutInflater.inflate(R.layout.item_lv_passenger_activity, parent, false);
            convertView.setTag(new ViewHolder(convertView));
        }
        initializeViews((PassengerEntity.PassengerBean) getItem(position), (ViewHolder) convertView.getTag(), position);
        return convertView;
    }

    private void initializeViews(PassengerEntity.PassengerBean entity, ViewHolder holder, int position) {
        //TODO implement
        holder.tvNameItemLvPassengerActivity.setText(entity.getPName());
        holder.tvIdNumItemLvPassengerActivity.setText(entity.getPIdNum());
        holder.cbItemLvPassengerActivity.setTag(entity);
        holder.cbItemLvPassengerActivity.setTag(position);

        holder.btDeleteItemLvPassengerActivity.setTag(position);
        if(mOnClickListener!=null) {
            holder.btDeleteItemLvPassengerActivity.setOnClickListener(mOnClickListener);
            holder.cbItemLvPassengerActivity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = (int) v.getTag();
                    CheckBox cb = (CheckBox) v;
                    PassengerEntity.PassengerBean passengerEntity = mEntities.get(position);
                    passengerEntity.setChecked(cb.isChecked());
                }
            });
        }
        holder.cbItemLvPassengerActivity.setChecked(entity.isChecked());
    }




    protected class ViewHolder {
        private CheckBox cbItemLvPassengerActivity;
        private TextView tvNameItemLvPassengerActivity;
        private TextView tvIdNumItemLvPassengerActivity;
        private Button btDeleteItemLvPassengerActivity;

        public ViewHolder(View view) {
            cbItemLvPassengerActivity = (CheckBox) view.findViewById(R.id.cb_item_lv_passenger_activity);
            tvNameItemLvPassengerActivity = (TextView) view.findViewById(R.id.tv_name_item_lv_passenger_activity);
            tvIdNumItemLvPassengerActivity = (TextView) view.findViewById(R.id.tv_idNum_item_lv_passenger_activity);
            btDeleteItemLvPassengerActivity = (Button) view.findViewById(R.id.bt_delete_item_lv_passenger_activity);
        }
    }
}

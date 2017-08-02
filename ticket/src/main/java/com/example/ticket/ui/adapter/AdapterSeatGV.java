package com.example.ticket.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.example.ticket.R;
import com.example.ticket.entity.passenger.PassengerEntity;

/**
 * Created by Administrator on 2017/2/11.
 */

public class AdapterSeatGV extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private CompoundButton.OnCheckedChangeListener mOnCheckedChangeListener;

    public AdapterSeatGV(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }


    public void setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
        mOnCheckedChangeListener = onCheckedChangeListener;
    }

    @Override
    public int getCount() {
        return 36;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_gv_seat, parent, false);
            viewHolder=new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        viewHolder= (ViewHolder) convertView.getTag();

        if(position%6==2){
            viewHolder.mCheckBox.setBackgroundColor(Color.WHITE);

        }else{
            if(mOnCheckedChangeListener!=null) {
                viewHolder.mCheckBox.setTag(position);
                viewHolder.mCheckBox.setOnCheckedChangeListener(mOnCheckedChangeListener);
            }

        }
        return convertView;
    }


    private class ViewHolder {
        private CheckBox mCheckBox;

        public ViewHolder(View view) {
            mCheckBox = (CheckBox) view.findViewById(R.id.cb_seat_gv);
        }
    }
}

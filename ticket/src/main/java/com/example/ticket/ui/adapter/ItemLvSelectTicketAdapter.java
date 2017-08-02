package com.example.ticket.ui.adapter;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.ticket.R;
import com.example.ticket.entity.ticket.TicketEntity;

import java.util.List;

public class ItemLvSelectTicketAdapter extends BaseAdapter {

    private List<TicketEntity.TicketsBean> mEntities;
    private View.OnClickListener mOnClickListener;
    private Context mContext;
    private LayoutInflater mLayoutInflater;


    public ItemLvSelectTicketAdapter(Context context, List<TicketEntity.TicketsBean> entities) {
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.mEntities = entities;
    }

    public void setEntities(List<TicketEntity.TicketsBean> entities) {
        mEntities = entities;
        notifyDataSetChanged();
    }

    public void addEntities(List<TicketEntity.TicketsBean> entities){
        mEntities.addAll(entities);
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
    public TicketEntity.TicketsBean getItem(int position) {
        return mEntities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_lv_select_ticket, parent, false);
            convertView.setTag(new ViewHolder(convertView));
        }
        initializeViews((TicketEntity.TicketsBean) getItem(position), (ViewHolder) convertView.getTag(), position);
        return convertView;
    }

    private void initializeViews(TicketEntity.TicketsBean entity, ViewHolder holder, int position) {
        //TODO implement
        holder.tvSurplusnumItemLvSelectTicket.setTextColor(Color.BLACK);
        holder.btReserveItemLvSelectTicket.setText("预定");
        holder.btReserveItemLvSelectTicket.setEnabled(true);

        holder.tvTimeItemLvSelectTicket.setText(entity.getTime());
        holder.tvTotalnumItemLvSelectTicket.setText(entity.getTotalNum()+"");
        holder.tvPriceItemLvSelectTicket.setText("￥"+entity.getPrice()+"");
        holder.tvSurplusnumItemLvSelectTicket.setText(entity.getSurplus()+"");
        holder.tvTypeItemLvSelectTicket.setText(entity.getType());
        holder.btReserveItemLvSelectTicket.setTag(entity);

        if(mOnClickListener!=null){
            holder.btReserveItemLvSelectTicket.setOnClickListener(mOnClickListener);
        }
        if(entity.getSurplus()==0){
            holder.tvSurplusnumItemLvSelectTicket.setTextColor(Color.RED);
            holder.btReserveItemLvSelectTicket.setText("没票");
            holder.btReserveItemLvSelectTicket.setEnabled(false);
        }


    }

    protected class ViewHolder {
        private TextView tvTimeItemLvSelectTicket;
        private TextView tvTotalnumItemLvSelectTicket;
        private TextView tvSurplusnumItemLvSelectTicket;
        private TextView tvPriceItemLvSelectTicket;
        private TextView tvTypeItemLvSelectTicket;
        private Button btReserveItemLvSelectTicket;

        public ViewHolder(View view) {
            tvTimeItemLvSelectTicket = (TextView) view.findViewById(R.id.tv_time_item_lv_select_ticket);
            tvTotalnumItemLvSelectTicket = (TextView) view.findViewById(R.id.tv_totalnum_item_lv_select_ticket);
            tvSurplusnumItemLvSelectTicket = (TextView) view.findViewById(R.id.tv_surplusnum_item_lv_select_ticket);
            tvPriceItemLvSelectTicket = (TextView) view.findViewById(R.id.tv_price_item_lv_select_ticket);
            tvTypeItemLvSelectTicket = (TextView) view.findViewById(R.id.tv_type_item_lv_select_ticket);
            btReserveItemLvSelectTicket = (Button) view.findViewById(R.id.bt_reserve_item_lv_select_ticket);
        }
    }
}

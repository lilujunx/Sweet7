package com.example.ticket.ui.adapter;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.ticket.R;
import com.example.ticket.db.DBConfig;
import com.example.ticket.db.ticket.TicketRecord;
import com.example.ticket.db.ticket.TicketRecord;
import com.example.ticket.ui.activity.PayZFBActivity;



import java.util.List;

public class ItemLvRecordFragmentAdapter extends BaseAdapter {

    private List<TicketRecord> mEntities;
    private View.OnClickListener mOnClickListener;
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public ItemLvRecordFragmentAdapter(Context context, List<TicketRecord> entities) {
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.mEntities = entities;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    public void setEntities(List<TicketRecord> entities) {

        mEntities = entities;
        notifyDataSetChanged();

    }

    @Override
    public int getCount() {
        return mEntities.size();
    }

    @Override
    public TicketRecord getItem(int position) {
        return mEntities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_lv_record_fragment, parent, false);
            convertView.setTag(new ViewHolder(convertView));
        }
        initializeViews((TicketRecord) getItem(position), (ViewHolder) convertView.getTag(), position);
        return convertView;
    }

    private void initializeViews(TicketRecord entity, ViewHolder holder, int position) {
        //TODO implement
        holder.tvTimeItemLvRecordFragment.setText(entity.getDate()+"      "+entity.getTime()+"  出发");
        holder.tvButTimeLvRecordFragment.setText(entity.getBuyTime());
        holder.tvTypeItemLvSelectTicket.setText(entity.getType());
        holder.btPayItemLvRecordFragment.setText(entity.getState());
        holder.btPayItemLvRecordFragment.setTag(entity);
        holder.tvEndTypeItemLvSelectTicket.setText(entity.getEnd());
        holder.tvPriceItemLvSelectTicket.setText("￥"+entity.getPrice());
        holder.btDeleteItemLvRecordFragment.setTag(entity);
        if(mOnClickListener!=null) {
            holder.btDeleteItemLvRecordFragment.setOnClickListener(mOnClickListener);
        }
        if(holder.btPayItemLvRecordFragment.getText().equals("未支付")){
            holder.btPayItemLvRecordFragment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TicketRecord ticketRecord= (TicketRecord) v.getTag();
                    Intent intent=new Intent(mContext, PayZFBActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("id",ticketRecord.getId());
                    intent.putExtra("id",bundle);
                    mContext.startActivity(intent);
                }
            });
        }
    }

    protected class ViewHolder {
        private TextView tvTimeItemLvRecordFragment;
        private Button btPayItemLvRecordFragment;
        private TextView tvTypeItemLvSelectTicket;
        private TextView tvItemLvRecordFragment;
        private TextView tvEndTypeItemLvSelectTicket;
        private TextView tvButTimeLvRecordFragment;
        private TextView tvPriceItemLvSelectTicket;
        private Button btDeleteItemLvRecordFragment;
        public ViewHolder(View view) {
            tvTimeItemLvRecordFragment = (TextView) view.findViewById(R.id.tv_time_item_lv_record_fragment);
            btPayItemLvRecordFragment = (Button) view.findViewById(R.id.bt_pay_item_lv_record_fragment);
            tvTypeItemLvSelectTicket = (TextView) view.findViewById(R.id.tv_type_item_lv_select_ticket);
            tvItemLvRecordFragment = (TextView) view.findViewById(R.id.tv_item_lv_record_fragment);
            tvEndTypeItemLvSelectTicket = (TextView) view.findViewById(R.id.tv_end_type_item_lv_select_ticket);
            tvButTimeLvRecordFragment = (TextView) view.findViewById(R.id.tv_butTime_lv_record_fragment);
            tvPriceItemLvSelectTicket = (TextView) view.findViewById(R.id.tv_price_item_lv_select_ticket);
            btDeleteItemLvRecordFragment= (Button) view.findViewById(R.id.bt_delete_item_lv_record_fragment);
        }
    }
}

package com.example.sqlplease;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter2 extends RecyclerView.Adapter<CustomAdapter2.ViewHolder> {

    private ArrayList<ResultItem> mResultItems;
    private Context mContext;
    private DBHelper mDBHelper;

    public CustomAdapter2(ArrayList<ResultItem> mResultItems, Context mContext) {
        this.mResultItems = mResultItems;
        this.mContext = mContext;
        mDBHelper = new DBHelper(mContext);
    }

    @NonNull
    @Override
    public CustomAdapter2.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View holder = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new CustomAdapter2.ViewHolder(holder);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.tv_name.setText(mResultItems.get(position).getName());
        holder.tv_number.setText(mResultItems.get(position).getNumber());
        holder.tv_coupon1.setText(mResultItems.get(position).getCoupon1());
        holder.tv_coupon2.setText(mResultItems.get(position).getCoupon2());

    }


    @Override
    public int getItemCount() {
        return mResultItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_name;
        private TextView tv_number;
        private TextView tv_coupon1;
        private TextView tv_coupon2;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);

            tv_name = itemView.findViewById(R.id.tv_name);
            tv_number = itemView.findViewById(R.id.tv_number);
            tv_coupon1 = itemView.findViewById(R.id.tv_coupon1);
            tv_coupon2 = itemView.findViewById(R.id.tv_coupon2);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int curPos = getAdapterPosition();
                    ResultItem resultItem = mResultItems.get(curPos);

                    String name = resultItem.getName();
                    String number = resultItem.getNumber();
                    String coupon1 = resultItem.getCoupon1();
                    String coupon2 = resultItem.getCoupon2();

                    Intent intent = new Intent(mContext, Coupon_dialog.class);

                    intent.putExtra("name", name);
                    intent.putExtra("number", number);
                    intent.putExtra("coupon1", coupon1);
                    intent.putExtra("coupon2", coupon2);

                    mContext.startActivity(intent);
                }
            });
        }
    }

    // 액티비티에서 호출되는 함수, 현재 어댑터에 새로운 아이템을 전달받아 추가하는 기능.
    public void addItem(ResultItem _Item) {
        mResultItems.add(0, _Item);
        notifyItemInserted(0);
    }
}

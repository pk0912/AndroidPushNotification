package com.keystone.demo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by sky on 22-Oct-15.
 */
public class CustomAdapterRecycler extends RecyclerView.Adapter<CustomAdapterRecycler.MyViewHolder> {
    private LayoutInflater inflater;
    Context context;
    List<EmpVO> list;

    public CustomAdapterRecycler(Context context,List<EmpVO> list)
    {
        this.context=context;
        inflater=LayoutInflater.from(context);
        this.list=list;

    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,int viewType)
    {
        View view =inflater.inflate(R.layout.customrow,parent,false);
        MyViewHolder holder=new MyViewHolder(view);
        return holder;

    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        EmpVO empVO=list.get(position);
        holder.empid.setText(empVO.getEmp_id());
        holder.empname.setText(empVO.getEmp_name());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
        TextView empid;
        TextView empname;


        public MyViewHolder(View itemView) {
            super(itemView);
            empid = (TextView) itemView.findViewById(R.id.emp_id);
            empname=(TextView) itemView.findViewById(R.id.emp_name);

        }

        @Override
        public void onClick(View v) {

        }
    }

}

package com.example.beta.lot;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.beta.R;
import com.example.beta.rest.model.ApiResquestUserAuth;
import com.example.beta.rest.model.LotEntity;

import java.util.List;

public class LotAdapter  extends RecyclerView.Adapter<LotAdapter.DataViewHolder> {
    private List<LotEntity> dateList;
    public  LotAdapter(List<LotEntity> dateList){
        this.dateList=dateList;
    }
    @NonNull
    @Override
    public LotAdapter.DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lot_item_data, parent, false);
        return new DataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LotAdapter.DataViewHolder holder, int position) {
     LotEntity data = dateList.get(position);
    holder.nomLot.setText(data.getNomLot());
    holder.dateDebut.setText(data.getDateDebut());
holder.dateFin.setText(data.getDateFin());
    }

    @Override
    public int getItemCount() {
        return dateList.size();
    }
public  static  class DataViewHolder extends RecyclerView.ViewHolder{
        TextView nomLot;
        TextView dateDebut;
        TextView dateFin;

    public DataViewHolder(@NonNull View itemView) {
        super(itemView);
        nomLot=itemView.findViewById(R.id.nomLot);
        dateDebut=itemView.findViewById(R.id.dateDebut);
        dateFin=itemView.findViewById(R.id.dateFin);
    }
}
}

package pe.gob.inei.generadorinei.adapters;


import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import pe.gob.inei.generadorinei.R;
import pe.gob.inei.generadorinei.model.CamposMarco;
import pe.gob.inei.generadorinei.model.ItemMarco;


/**
 * Created by user on 9/08/2017.
 */

public class MarcoAdapter extends RecyclerView.Adapter<MarcoAdapter.ItemMarcoHolder> {
    ArrayList<ItemMarco> items;
    CamposMarco camposMarco;
    OnItemClickListener onItemClickListener;


    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public MarcoAdapter(CamposMarco camposMarco, OnItemClickListener onItemClickListener) {
        this.camposMarco = camposMarco;
        this.onItemClickListener = onItemClickListener;
    }

    public void setMarcos(ArrayList<ItemMarco> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public ItemMarcoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_marco, parent, false);
        ItemMarcoHolder viewHolder = new ItemMarcoHolder(view,camposMarco);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ItemMarcoHolder holder, final int position) {
        //TODO: traer campos segun la cantidad de campos a mostrar
        ItemMarco itemMarco = items.get(position);
        holder.tvCampo1.setText(itemMarco.getCampo1());
        holder.tvCampo2.setText(itemMarco.getCampo2());
        holder.tvCampo3.setText(itemMarco.getCampo3());
        holder.tvCampo4.setText(itemMarco.getCampo4());
        holder.tvCampo5.setText(itemMarco.getCampo5());
        holder.tvCampo6.setText(itemMarco.getCampo6());
        holder.tvCampo7.setText(itemMarco.getCampo7());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(view, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ItemMarcoHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView tvCampo1;
        TextView tvCampo2;
        TextView tvCampo3;
        TextView tvCampo4;
        TextView tvCampo5;
        TextView tvCampo6;
        TextView tvCampo7;

        public ItemMarcoHolder(View itemView, CamposMarco camposMarco) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cvItemMarco);
            tvCampo1 = itemView.findViewById(R.id.tvItemMarcoCampo1);
            tvCampo2 = itemView.findViewById(R.id.tvItemMarcoCampo2);
            tvCampo3 = itemView.findViewById(R.id.tvItemMarcoCampo3);
            tvCampo4 = itemView.findViewById(R.id.tvItemMarcoCampo4);
            tvCampo5 = itemView.findViewById(R.id.tvItemMarcoCampo5);
            tvCampo6 = itemView.findViewById(R.id.tvItemMarcoCampo6);
            tvCampo7 = itemView.findViewById(R.id.tvItemMarcoCampo7);

            configurarVistaCampo(camposMarco,tvCampo1);
            configurarVistaCampo(camposMarco,tvCampo2);
            configurarVistaCampo(camposMarco,tvCampo3);
            configurarVistaCampo(camposMarco,tvCampo4);
            configurarVistaCampo(camposMarco,tvCampo5);
            configurarVistaCampo(camposMarco,tvCampo6);
            configurarVistaCampo(camposMarco,tvCampo7);
        }

        private void configurarVistaCampo(CamposMarco camposMarco, TextView textView) {
            if (camposMarco.getVariable1().equals("")) textView.setVisibility(View.GONE);
            else setPesoLayout(textView,Integer.parseInt(camposMarco.getPeso1()));
        }

        public void setPesoLayout(View view, int peso){
            LinearLayout.LayoutParams params =
                    new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, peso*1.0f);
            view.setLayoutParams(params);
        }
    }
}

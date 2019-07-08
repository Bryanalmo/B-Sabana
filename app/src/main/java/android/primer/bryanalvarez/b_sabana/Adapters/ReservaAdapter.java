package android.primer.bryanalvarez.b_sabana.Adapters;

import android.content.Context;
import android.primer.bryanalvarez.b_sabana.Models.Evento;
import android.primer.bryanalvarez.b_sabana.Models.Reserva;
import android.primer.bryanalvarez.b_sabana.R;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

/**
 * Created by nayar on 22/05/2018.
 */

public class ReservaAdapter extends RecyclerView.Adapter<ReservaAdapter.ViewHolder> {

    private Context context;
    private List<Reserva> reservas;
    private int layout;
    private OnItemClickListener itemClickListener;
    private OnButtonClickListener btnClickListener;

    public ReservaAdapter(Context context, List<Reserva> reservas, int layout, OnItemClickListener itemClickListener, OnButtonClickListener btnClickListener) {
        this.context = context;
        this.reservas = reservas;
        this.layout = layout;
        this.itemClickListener = itemClickListener;
        this.btnClickListener = btnClickListener;
    }

    @Override
    public ReservaAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        context = parent.getContext();
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ReservaAdapter.ViewHolder holder, int position) {
        holder.bind(reservas.get(position),itemClickListener,btnClickListener);
    }

    @Override
    public int getItemCount() {
        return reservas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewNombreMaterial;
        public TextView textViewDescripcionMaterial;
        public TextView textViewHoraReserva;
        public TextView textViewFechaReserva;
        public Button buttonDelete;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewNombreMaterial = (TextView) itemView.findViewById(R.id.textViewNombreMaterial);
            textViewDescripcionMaterial = (TextView) itemView.findViewById(R.id.textViewMaterialDescripcion);
            textViewHoraReserva = (TextView) itemView.findViewById(R.id.textViewHora);
            textViewFechaReserva = (TextView) itemView.findViewById(R.id.textViewFecha);
            buttonDelete = (Button) itemView.findViewById(R.id.buttonDeleteReserva);
        }

        public void bind(final Reserva reserva, final OnItemClickListener itemListener, final OnButtonClickListener btnListener) {

            textViewNombreMaterial.setText(reserva.getMaterial());
            textViewDescripcionMaterial.setText(reserva.getDescripcionMaterial());
            textViewHoraReserva.setText(reserva.getHora());
            textViewFechaReserva.setText(reserva.getFecha());

            buttonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btnListener.onButtonClick(reserva, getAdapterPosition());
                }
            });
        }
    }


    public interface OnItemClickListener {
        void onItemClick(Reserva reserva, int position);
    }

    public interface OnButtonClickListener {
        void onButtonClick(Reserva reserva, int position);
    }
}




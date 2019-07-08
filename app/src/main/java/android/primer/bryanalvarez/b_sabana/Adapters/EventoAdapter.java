package android.primer.bryanalvarez.b_sabana.Adapters;

import android.content.Context;
import android.os.AsyncTask;
import android.primer.bryanalvarez.b_sabana.Models.Evento;
import android.primer.bryanalvarez.b_sabana.R;
import android.primer.bryanalvarez.b_sabana.Utils.Util;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.List;

/**
 * Created by nayar on 21/04/2018.
 */

public class EventoAdapter extends RecyclerView.Adapter<EventoAdapter.ViewHolder> {

    private Context context;
    private List<Evento> eventos;
    private int layout;
    private OnItemClickListener itemClickListener;
    private OnButtonCallClickListener btnCallClickListener;

    public EventoAdapter(List<Evento> eventos, int layout, OnItemClickListener itemClickListener, OnButtonCallClickListener btnCallClickListener) {
        this.eventos = eventos;
        this.layout = layout;
        this.itemClickListener = itemClickListener;
        this.btnCallClickListener = btnCallClickListener;
    }

    public void setEventos(List<Evento> eventos) {
        this.eventos = eventos;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        context = parent.getContext();
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(eventos.get(position),itemClickListener, btnCallClickListener);
    }

    @Override
    public int getItemCount() {
        return eventos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageViewTipo;
        public TextView textViewPeronasRequeridas;
        public TextView textViewNombreUsuario;
        public TextView textViewEventoDescripcion;
        public TextView textViewCelular;
        public Button btnLlamar;

        public ViewHolder(View itemView) {
            super(itemView);
            imageViewTipo = (ImageView) itemView.findViewById(R.id.imageViewTipo);
            textViewPeronasRequeridas = (TextView) itemView.findViewById(R.id.textViewPeronasRequeridas);
            textViewNombreUsuario= (TextView) itemView.findViewById(R.id.textViewNombreUsuario);
            textViewEventoDescripcion = (TextView) itemView.findViewById(R.id.textViewEventoDescripcion);
            textViewCelular= (TextView) itemView.findViewById(R.id.textViewCelular);
            btnLlamar = (Button) itemView.findViewById(R.id.buttonLlamar);


        }

        public void bind(final Evento evento, final OnItemClickListener itemListener, final OnButtonCallClickListener btnCallListener) {

            textViewPeronasRequeridas.setText(evento.getPersonasSolicitadas()+"");
            textViewNombreUsuario.setText(evento.getUsuario());
            textViewEventoDescripcion.setText(evento.getDescripcion());
            textViewCelular.setText(evento.getCelularUsuario());
            if(evento.getCategoria()==1){
                imageViewTipo.setImageResource(R.drawable.deportes2);
            }else{
                imageViewTipo.setImageResource(R.drawable.cultura2);
            }

            btnLlamar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btnCallListener.onButtonClick(evento, getAdapterPosition());
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Evento evento, int position);
    }
    public interface OnButtonCallClickListener {
        void onButtonClick(Evento evento, int position);
    }
}

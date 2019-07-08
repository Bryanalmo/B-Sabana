package android.primer.bryanalvarez.b_sabana.Adapters;

import android.content.Context;
import android.primer.bryanalvarez.b_sabana.Models.Noticia;
import android.primer.bryanalvarez.b_sabana.R;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by nayar on 22/05/2018.
 */

public class NoticiasAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<Noticia> list;

    public NoticiasAdapter(Context context, int layout, List<Noticia> list) {
        this.context = context;
        this.layout = layout;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Noticia getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        final Noticia noticiaActual = getItem(position);
        if(convertView == null){

            convertView = LayoutInflater.from(context).inflate(layout,null);
            holder = new ViewHolder();
            holder.titulo = (TextView) convertView.findViewById(R.id.tituloNoticia);
            holder.descripcion = (TextView) convertView.findViewById(R.id.descripcionNoticia);
            holder.categoria = (ImageView) convertView.findViewById(R.id.imageViewCategoria);
            convertView.setTag(holder);


        }else{holder = (ViewHolder) convertView.getTag();}


        holder.titulo.setText(noticiaActual.getTitulo());
        holder.descripcion.setText(noticiaActual.getDescripcion());
        if(noticiaActual.getCategoria() == 1){
            holder.categoria.setImageResource(R.drawable.deporte);
        }else if(noticiaActual.getCategoria() == 2){
            holder.categoria.setImageResource(R.drawable.arte);
        }

        return convertView;
    }

    static class ViewHolder {
        private TextView titulo;
        private TextView descripcion;
        private ImageView categoria;
    }
}

package android.primer.bryanalvarez.b_sabana.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.primer.bryanalvarez.b_sabana.Activities.DepartamentoActivity;
import android.primer.bryanalvarez.b_sabana.Activities.MisReservasActivity;
import android.primer.bryanalvarez.b_sabana.Activities.NoticiasActivity;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.primer.bryanalvarez.b_sabana.R;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class BienestarFragment extends Fragment {

    private ImageView imageViewDeporte;
    private ImageView imageViewCultura;
    private ImageView imageViewReservas;
    private LinearLayout linearLayoutNoticias;

    public BienestarFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bienestar, container, false);

        imageViewDeporte = (ImageView) view.findViewById(R.id.imageViewDeporte);
        imageViewCultura = (ImageView) view.findViewById(R.id.imageViewCultura);
        imageViewReservas= (ImageView) view.findViewById(R.id.imageViewReserva);
        linearLayoutNoticias = (LinearLayout) view.findViewById(R.id.news);

        imageViewDeporte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext().getApplicationContext(), DepartamentoActivity.class);
                intent.putExtra("id",1);
                startActivity(intent);
            }
        });

        imageViewCultura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext().getApplicationContext(), DepartamentoActivity.class);
                intent.putExtra("id",2);
                startActivity(intent);
            }
        });

        imageViewReservas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext().getApplicationContext(), MisReservasActivity.class);
                startActivity(intent);
            }
        });

        linearLayoutNoticias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext().getApplicationContext(),NoticiasActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

}

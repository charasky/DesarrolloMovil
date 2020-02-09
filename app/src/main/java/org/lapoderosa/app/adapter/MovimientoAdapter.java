package org.lapoderosa.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lapoderosa.app.R;

import org.lapoderosa.app.model.Movimiento;

import java.util.List;

public class MovimientoAdapter extends RecyclerView.Adapter<MovimientoAdapter.MovimientoViewHolder>{
    private Context context;
    private List<Movimiento> movimientoList;

    public MovimientoAdapter(Context context, List<Movimiento> movimientoList){
        this.context = context;
        this.movimientoList = movimientoList;
    }

    @NonNull
    @Override
    public MovimientoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.rv_movimiento_row, viewGroup, false);

        return new MovimientoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovimientoViewHolder holder, int position) {
        //todo completar
    }

    @Override
    public int getItemCount() {
        return movimientoList.size();
    }

    public class MovimientoViewHolder extends RecyclerView.ViewHolder {
        TextView rvUsuario, rvFecha, rvQueHizo, rvHora, rvUsuarioAceptado;

        public MovimientoViewHolder(@NonNull View itemView) {
            super(itemView);

            rvUsuario = itemView.findViewById(R.id.rvUsuario);
            rvFecha = itemView.findViewById(R.id.rvFecha);
            rvQueHizo = itemView.findViewById(R.id.rvQueHizo);
            rvHora = itemView.findViewById(R.id.rvHora);
            rvUsuarioAceptado = itemView.findViewById(R.id.rvUsuarioAceptado);
        }
    }
}

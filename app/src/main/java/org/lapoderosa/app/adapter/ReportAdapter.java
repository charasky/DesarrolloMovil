package org.lapoderosa.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lapoderosa.app.R;

import org.lapoderosa.app.normal.ReportVisualizacion;
import org.lapoderosa.app.model.Report;

import java.util.ArrayList;
import java.util.List;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ReportViewHolder> {
    private Context context;
    private List<Report> reportList;

    public ReportAdapter(Context context, List<Report> reportList) {
        this.context = context;
        this.reportList = reportList;
    }

    @NonNull
    @Override
    public ReportViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.rv_report_row, viewGroup, false);
        return new ReportViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ReportViewHolder reportViewHolder, int i) {
        reportViewHolder.tvLugar.setText(reportList.get(i).getPais());
        reportViewHolder.tvProvincia.setText(reportList.get(i).getProvincia());
        reportViewHolder.tvName.setText(reportList.get(i).getFullName());
        reportViewHolder.tvDate.setText(reportList.get(i).getFecha());
        reportViewHolder.tvHour.setText(reportList.get(i).getHora());
    }

    @Override
    public int getItemCount() {
        return reportList.size();
    }

    public class ReportViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvLugar, tvProvincia, tvName, tvDate, tvHour;

        public ReportViewHolder(@NonNull View itemView) {
            super(itemView);

            tvLugar = itemView.findViewById(R.id.tvLugar);
            tvProvincia = itemView.findViewById(R.id.tvCiudad);
            tvName = itemView.findViewById(R.id.tvName);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvHour = itemView.findViewById(R.id.tvHour);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Report report = reportList.get(position);

            Intent intent = new Intent(context, ReportVisualizacion.class);
            intent.putExtra("name", report.getFullName());
            intent.putExtra("fecha", report.getFecha());
            intent.putExtra("hora", report.getHora());
            intent.putExtra("provincia", report.getProvincia());
            intent.putExtra("pais", report.getPais());
            intent.putExtra("id", report.getIdReporte());

            context.startActivity(intent);
        }
    }

    public void filtrar(ArrayList<Report> filtroUsuarios) {
        this.reportList = filtroUsuarios;
        notifyDataSetChanged();
    }
}

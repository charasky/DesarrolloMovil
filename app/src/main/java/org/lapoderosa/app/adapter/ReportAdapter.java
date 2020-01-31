package org.lapoderosa.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lapoderosa.app.R;

import org.lapoderosa.app.util.Report;

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
        reportViewHolder.tvCiudad.setText(reportList.get(i).getCiudad());
        reportViewHolder.tvName.setText(reportList.get(i).getFullName());
        reportViewHolder.tvDate.setText(reportList.get(i).getFecha());
    }

    @Override
    public int getItemCount() {
        return reportList.size();
    }

    public class ReportViewHolder extends RecyclerView.ViewHolder {

        TextView tvLugar, tvCiudad, tvName, tvDate;

        public ReportViewHolder(@NonNull View itemView) {
            super(itemView);

            tvLugar = itemView.findViewById(R.id.tvLugar);
            tvCiudad = itemView.findViewById(R.id.tvCiudad);
            tvName = itemView.findViewById(R.id.tvName);
            tvDate = itemView.findViewById(R.id.tvDate);
        }
    }

    public void filtrar(ArrayList<Report> filtroUsuarios) {
        this.reportList = filtroUsuarios;
        notifyDataSetChanged();
    }
}

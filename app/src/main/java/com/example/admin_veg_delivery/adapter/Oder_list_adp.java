package com.example.admin_veg_delivery.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.admin_veg_delivery.R;
import com.example.admin_veg_delivery.model.Oders_details;
import java.util.List;


public class Oder_list_adp extends RecyclerView.Adapter<Oder_list_adp.Myholder>{
    Context context;
    List<Oders_details> lits;

    public Oder_list_adp(Context context, List<Oders_details> lits) {
        this.context = context;
        this.lits = lits;
    }

    @NonNull
    @Override
    public Myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.oder_list_view, parent, false);

        return new Myholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Myholder holder, int position) {
        holder.user_no.setText(lits.get(position).getUser_no());
        holder.user_add.setText(lits.get(position).getUser_add());
        holder.oder_time.setText(lits.get(position).getOder_time());
        holder.oders.setText(lits.get(position).getOders());




    }

    @Override
    public int getItemCount() {
        return lits.size();
    }

    class Myholder extends RecyclerView.ViewHolder  {
        TextView user_no,user_add,oder_time,oders;

        public Myholder(@NonNull View itemView) {
            super(itemView);
            user_no=itemView.findViewById(R.id.user_no);
            user_add=itemView.findViewById(R.id.user_add);
            oder_time=itemView.findViewById(R.id.oder_time);
            oders=itemView.findViewById(R.id.oders);


        }


    }


}

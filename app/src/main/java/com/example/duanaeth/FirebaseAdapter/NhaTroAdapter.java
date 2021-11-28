package com.example.duanaeth.FirebaseAdapter;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanaeth.ArrayAdapter.NhaTro;
import com.example.duanaeth.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class NhaTroAdapter extends RecyclerView.Adapter<NhaTroAdapter.ViewHolder> {

    private String linkdatabase;
    private Context context;
    private DatabaseReference reference;
    public  List<NhaTro> list;
    private int layout;
    private SimpleDateFormat dfm = new SimpleDateFormat("dd/MM/yyyy");
    private NumberFormat fm = new DecimalFormat("#,###");
    boolean isDark = false;

    public NhaTroAdapter() {
    }

    public void setData(List<NhaTro> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public NhaTroAdapter(Context context, ArrayList<NhaTro> list, Boolean isDark) {
        this.context = context;
        this.list = list;
        this.isDark = isDark;
    }


    public NhaTroAdapter(Context context, ArrayList<NhaTro> list) {
        this.context = context;
        this.list = list;

    }
    public NhaTroAdapter(Context context, int layout, ArrayList<NhaTro> list) {
        this.context = context;
        this.list = list;
        this.layout=layout;

    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tenmenu, diachi, giatien;
        private ImageView hinhmenu;
        RelativeLayout relativeLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tenmenu = itemView.findViewById(R.id.tenmenu);
            diachi = itemView.findViewById(R.id.diachi);
            giatien = itemView.findViewById(R.id.giatien);
            hinhmenu = itemView.findViewById(R.id.hinhmenu);

        }


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
//        linkdatabase = context.getString(R.string.linkreutime);
        final NhaTro nhaTro = list.get(position);
        if (nhaTro == null) {
            return;
        }
        String linkHanhDB = nhaTro.getAvatar();
        Picasso.get().load(linkHanhDB).into(holder.hinhmenu);
        holder.tenmenu.setText(nhaTro.getTenPhong());
        holder.giatien.setText(""+fm.format(Integer.parseInt(nhaTro.getGiaPhong()))+"đ ");
        holder.diachi.setText(""+nhaTro.getTenDuong()+" - "+nhaTro.getPhuongXa()+" - "+nhaTro.getQuanHuyen()+" - "+nhaTro.getTinhThanh());


    }


    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }


}
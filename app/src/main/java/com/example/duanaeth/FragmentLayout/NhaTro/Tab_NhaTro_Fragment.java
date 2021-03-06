package com.example.duanaeth.FragmentLayout.NhaTro;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import com.example.duanaeth.ArrayAdapter.NhaTro;
import com.example.duanaeth.FirebaseAdapter.NhaTroAdapter;
import com.example.duanaeth.LayoutChucNang.ThemNhaTro;
import com.example.duanaeth.R;
import com.example.duanaeth.SplashScreen.IntroActivity;
import com.example.duanaeth.TrangChu;
import com.github.clans.fab.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tab_NhaTro_Fragment extends Fragment {
    View view;
    private RecyclerView rcv;
    private String linkdatabase;
    private DatabaseReference reference;
    private ArrayList<NhaTro> listNhaTro = new ArrayList<>();
    private NhaTroAdapter nhaTroAdapter;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_nha_tro_tab, container, false);
        // findViewById
        linkdatabase = getResources().getString(R.string.link_RealTime_Database);
        rcv = view.findViewById(R.id.rcv_NhaTro);

        //get data phong tr???
        getLisviewDatabasefirebase("");


        nhaTroAdapter = new NhaTroAdapter();
        nhaTroAdapter = new NhaTroAdapter(getActivity(), R.layout.itemsanpham, listNhaTro);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcv.setLayoutManager(linearLayoutManager);
        listNhaTro = new ArrayList<>();
        nhaTroAdapter.setData(listNhaTro);
        rcv.setAdapter(nhaTroAdapter);



        return view;
    }

    private void getLisviewDatabasefirebase(String key) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            return;
        }
        reference = FirebaseDatabase.getInstance(linkdatabase).getReference("danhSachTro");
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Map<String, String> map = (Map<String, String>) snapshot.getValue();
                NhaTro nhaTro = new NhaTro(map.get("idPhong"), map.get("avatar"), map.get("tenPhong"), map.get("giaPhong"),map.get("tenDuong"),map.get("phuongXa"),map.get("quanHuyen") ,map.get("tinhThanh"));
                if (nhaTro != null) {
                    listNhaTro.add(nhaTro);
                    nhaTroAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onChildChanged(@NonNull   DataSnapshot snapshot, @Nullable   String previousChildName) {
                Map<String, String> map = (Map<String, String>) snapshot.getValue();
                NhaTro nhaTro = new NhaTro(map.get("idPhong"),map.get("avatar"), map.get("tenPhong"), map.get("giaPhong"),map.get("tenDuong"),map.get("phuongXa"),map.get("quanHuyen") ,map.get("tinhThanh"));

                if (nhaTro == null || listNhaTro == null || listNhaTro.isEmpty()) {
                    return;
                }
                for (int i = 0; i < listNhaTro.size(); i++) {
                    if (nhaTro.getIdPhong() == listNhaTro.get(i).getIdPhong()) {
                        listNhaTro.set(i, nhaTro);
                        break;
                    }
                }
                nhaTroAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull  DataSnapshot snapshot) {
                Map<String, String> map = (Map<String, String>) snapshot.getValue();
                NhaTro nhaTro = new NhaTro(map.get("idPhong"),map.get("avatar"), map.get("tenPhong"), map.get("giaPhong"),map.get("tenDuong"),map.get("phuongXa"),map.get("quanHuyen") ,map.get("tinhThanh"));
                if (nhaTro == null || listNhaTro == null || listNhaTro.isEmpty()) {
                    return;
                }
                for (int i = 0; i < listNhaTro.size(); i++) {
                    if (nhaTro.getIdPhong() == listNhaTro.get(i).getIdPhong()) {
                        listNhaTro.remove(listNhaTro.get(i));
                        break;
                    }
                }
                nhaTroAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull   DataSnapshot snapshot, @Nullable   String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
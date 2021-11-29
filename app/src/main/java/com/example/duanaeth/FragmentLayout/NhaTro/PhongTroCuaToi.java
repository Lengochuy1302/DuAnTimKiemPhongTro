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

import com.example.duanaeth.ArrayAdapter.NhaTro;
import com.example.duanaeth.FirebaseAdapter.NhaTroAdapter;
import com.example.duanaeth.FirebaseAdapter.NhaTroCuaToiAdapter;
import com.example.duanaeth.LayoutChucNang.ThemNhaTro;
import com.example.duanaeth.R;
import com.github.clans.fab.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Map;

public class PhongTroCuaToi extends Fragment {
    View view;
    FloatingActionButton btnThem;
    private RecyclerView rcv;
    private String linkdatabase;
    private DatabaseReference reference;
    private ArrayList<NhaTro> listNhaTro = new ArrayList<>();
    private NhaTroCuaToiAdapter nhaTroAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_phong_tro_cua_toi, container, false);
        // findViewById
        linkdatabase = getResources().getString(R.string.link_RealTime_Database);
        btnThem = view.findViewById(R.id.addBtn);
        rcv = view.findViewById(R.id.rcv_NhaTro);

        //get data phong trọ
        getLisviewDatabasefirebase("");


        nhaTroAdapter = new NhaTroCuaToiAdapter();
        nhaTroAdapter = new NhaTroCuaToiAdapter(getActivity(), R.layout.itemsanpham, listNhaTro);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcv.setLayoutManager(linearLayoutManager);
        listNhaTro = new ArrayList<>();
        nhaTroAdapter.setData(listNhaTro);
        rcv.setAdapter(nhaTroAdapter);

        // event click
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ThemNhaTro.class));
            }
        });
        return view;
    }

    private void getLisviewDatabasefirebase(String key) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            return;
        }
        reference = FirebaseDatabase.getInstance(linkdatabase).getReference("users").child(user.getUid()).child("baiDangCuaToi");
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
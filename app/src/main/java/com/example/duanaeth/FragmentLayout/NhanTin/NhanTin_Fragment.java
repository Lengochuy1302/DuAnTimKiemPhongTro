package com.example.duanaeth.FragmentLayout.NhanTin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import com.example.duanaeth.R;
import com.github.clans.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NhanTin_Fragment extends Fragment {
    View view;
    GridView rcv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_nhan_tin_, container, false);

        rcv = view.findViewById(R.id.danhsachtinnhan);


        List<HashMap<String,Object>> dsmenu = new ArrayList<HashMap<String,Object>>();
        HashMap<String, Object> itemmenu = new HashMap<>();

        itemmenu.put("ten", "Gia Lâm");
        itemmenu.put("hinh", R.drawable.hinh3);
        itemmenu.put("giatien", "16:45");
        itemmenu.put("diachi", "Xin chào!!");
        dsmenu.add(itemmenu);

        itemmenu= new HashMap<String, Object>();
        itemmenu.put("ten", "Lê Thùy Linh");
        itemmenu.put("hinh", R.drawable.gai5);
        itemmenu.put("giatien", "10:22");
        itemmenu.put("diachi", "Giá phòng bao nhiêu?");
        dsmenu.add(itemmenu);

        itemmenu= new HashMap<String, Object>();
        itemmenu.put("ten", "Phong Nhi");
        itemmenu.put("hinh", R.drawable.hinh1);
        itemmenu.put("giatien", "15:18");
        itemmenu.put("diachi", "Còn phòng không bạn?");
        dsmenu.add(itemmenu);

        itemmenu= new HashMap<String, Object>();
        itemmenu.put("ten", "Nguyễn Văn Hoàng");
        itemmenu.put("hinh", R.drawable.trai1);
        itemmenu.put("giatien", "08:55");
        itemmenu.put("diachi", "Phòng mấy người vậy?");
        dsmenu.add(itemmenu);


        String[]from = {"ten", "hinh", "giatien", "diachi"};
        int[]to ={R.id.tenmenu, R.id.hinhmenu, R.id.giatien, R.id.diachi};
        SimpleAdapter myadapter = new SimpleAdapter(getContext(), dsmenu, R.layout.item_chat, from, to);
        rcv.setAdapter(myadapter);

        return view;
    }
}
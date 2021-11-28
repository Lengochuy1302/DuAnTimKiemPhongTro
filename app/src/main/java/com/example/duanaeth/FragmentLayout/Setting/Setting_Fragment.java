package com.example.duanaeth.FragmentLayout.Setting;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.duanaeth.FragmentLayout.NhaTro.PhongTroCuaToi;
import com.example.duanaeth.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

public class Setting_Fragment extends Fragment {
    View view;
    ImageView avatar;
    TextView tennguoidung, gmailtext, baidang;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_setting_, container, false);
         gmailtext = (TextView) view.findViewById(R.id.gmailnguoidung);
         tennguoidung = (TextView) view.findViewById(R.id.tennguoidung);
         avatar = (ImageView) view.findViewById(R.id.avatar);
        baidang = (TextView) view.findViewById(R.id.baidang);


        //event
        baidang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });


        setprofile();
        return view;
    }

    //hàm set profile
    public void setprofile() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            return;
        }

        String name = user.getDisplayName();
        String email = user.getEmail();
        Uri photoUrl = user.getPhotoUrl();

        tennguoidung.setText(name);
        gmailtext.setText(email);
        Picasso.get().load(photoUrl).into(avatar);
    }
}
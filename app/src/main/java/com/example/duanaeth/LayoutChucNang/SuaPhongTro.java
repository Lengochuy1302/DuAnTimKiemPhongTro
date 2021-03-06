package com.example.duanaeth.LayoutChucNang;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.duanaeth.ArrayAdapter.NhaTro;
import com.example.duanaeth.ArrayAdapter.PhotoAlbum;
import com.example.duanaeth.ArrayAdapter.TienIch;
import com.example.duanaeth.FirebaseAdapter.ImgAdapter;
import com.example.duanaeth.FirebaseAdapter.NhaTroCuaToiAdapter;
import com.example.duanaeth.FirebaseAdapter.PhotoAdapter;
import com.example.duanaeth.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import cn.pedant.SweetAlert.SweetAlertDialog;
import gun0912.tedbottompicker.TedBottomPicker;
import gun0912.tedbottompicker.TedBottomSheetDialogFragment;

public class SuaPhongTro extends AppCompatActivity {
    private static final int MAX_LENGTH = 10 ;
    public String linkRealTime, linkStorage;
    private static final int RESULT_SPEECH = 1;
    public ImageView btnImg;
    private TextView btnClean, btnHuy, btnSuaPhongTro, btnNoi;
    private EditText edtSdtNhaTro, edtNoiDung, edtTenDuong, edtTienNuoc, edtTienDien, edtGiaPhong, edtDienTich, edtSoNguoi, edtTenPhong;
    private AutoCompleteTextView edtPhuongXa, edtQuanHuyen, edtTinhThanh, edtLoaiPhong, edtTinhTrangPhong;
    private ToggleButton btginternet, btgWc, btgThuCung, btgNhaBep, btgTivi, btgMayNuocNong, btgMayLanh, btgTuLanh, btgTuDo, btgMayGiat, btgGac, btgAnNinh;
    private RecyclerView rcvPhoto;
    private PhotoAdapter photoAdapter;
    private ImgAdapter imgAdapter;
    private DatabaseReference reference;
    private static final int PICK_IMG = 1;
    private List<PhotoAlbum> ImageList = new ArrayList<>();
    private ArrayList<PhotoAdapter> listPhoto = new ArrayList<>();
    private TienIch tienIch = new TienIch();
    private TienIch tienIch1 = new TienIch();
    private TienIch tienIch2 = new TienIch();
    private TienIch tienIch3 = new TienIch();
    private TienIch tienIch4 = new TienIch();
    private TienIch tienIch5 = new TienIch();
    private TienIch tienIch6 = new TienIch();
    private TienIch tienIch7 = new TienIch();
    private TienIch tienIch8 = new TienIch();
    private TienIch tienIch9 = new TienIch();
    private TienIch tienIch10 = new TienIch();
    private TienIch tienIch11 = new TienIch();
    private List<TienIch> dsTienIch = new ArrayList<>();
    private String photoAlbum1 = "";
    private List<PhotoAlbum> dsAlbum = new ArrayList<>();
    private int uploads = 0;
    private int i = 0;
    private ArrayAdapter<String> arrayAdapter;
    private String loaiPhong = "";
    private String tinhthanh = "";
    private String quanhuyen = "";
    private String phuongxa = "";
    private String tinhtrang = "";

    private String arrloaiphong[] = {"Ph??ng cho thu??", "Nh?? nguy??n c??n", "K?? t??c x?? / Homestay", "C??n h???"};
    private String arrTinhTrang[] = {"C??n Ph??ng", "H???t Ph??ng"};

    private String arrTinhThanh[] = {"H?? N???i", "????k L??k", "H??? Ch?? Minh"};
    private String arrquanhuyenhanoi[] = {"Ba ????nh", "B???c T??? Li??m", "C???u Gi???y", "?????ng ??a", "Ho??ng Mai", "Long Bi??n", "Nam T??? Li??m",};
    private String arrquanhuyendaklak[] = {"Bu??n Ma Thu???t", "Bu??n ????n", "C?? M'gar", "Ea H'leo", "Ea Kar"};
    private String arrquanhuyenhochiminh[] = {"Qu???n 1", "Qu???n 4", "Qu???n T??n B??nh", "Qu???n G?? V???p"};

    private String arrbadinh[]={"C???ng V???", "??i???n Bi??n", "?????i C???n", "Gi???ng V??", "Kim M??", "Li???u Giai","Ng???c H??", "Ng???c Kh??nh", "Nguy???n Trung Tr???c", "Ph??c X??"};
    private String arrbactuliem[]={"?????c Th???ng", "????ng Ng???c", "Th???y Ph????ng", "Li??n M???c", "Th?????ng C??t", "T??y T???u", "Minh Khai", "Ph?? Di???n", "Ph??c Di???n", "Xu??n ?????nh"};
    private String arrcaugiay[]={"D???ch V???ng", "D???ch V???ng H???u", "Mai D???ch", "Ngh??a ????", "Ngh??a T??n", "Quan Hoa", "Trung H??a", "Y??n H??a"};
    private String arrdongda[]={"C??t Linh", "H??ng B???t", "Kh??m Thi??n", "Kh????ng Th?????ng", "Kim Li??n", "L??ng H???", "L??ng Th?????ng", "Nam ?????ng", "Nguy???n Tr??i", "?? Ch??? D???a", "Ph????ng Li??n"};
    private String arrhoangmai[]={"Mai H??ng", "Qu???nh D???"," Qu???nh Ph????ng", "Qu???nh Thi???n", "Qu???nh Xu??n", "Qu???nh Li??n", "Qu???nh L???c"};
    private String arrlongbien[]={"B??? ?????", "C??? Kh???i", "?????c Giang", "Gia Th???y", "Giang Bi??n", "Long Bi??n", "Ng???c L??m", "Ng???c Th???y", "Ph??c ?????ng"};
    private String arrnamtuliem[]={"C???u Di???n", "M??? ????nh 1", "M??? ????nh 2", "Ph?? ????", "M??? Tr??", "Trung V??n", "?????i M???", "T??y M???"};

    private String arrbuonmathuot[]={"Ea Tam", "Kh??nh Xu??n", "T??n An", "T??n H??a", "T??n L???p", "T??n L???i", "T??n Th??nh", "T??n Ti???n", "Th???ng L???i", "Th??nh C??ng"};
    private String arrbuondon[]={"Kr??ng Na", "Ea Huar", "Ea Wer"};
    private String arrcumga[]={"C?? Dli?? M'n??ng", "C?? M'gar", "C?? Su??", "Cu??r ????ng", "Ea Dr??ng", "Ea H'??ing", "Ea Ki???t", "Ea Kpam"};
    private String arreahleo[]={"Ea Nam", "Ea Khal", "Ea Hiao"};
    private String arreakar[]={"Ea ??ar", "Ea Pal", "Ea ??", "C?? Ni"};

    private String arrquan1[]={"Ph?????ng 1", "Ph?????ng 2", "Ph?????ng 3", "Ph?????ng 4", "Ph?????ng 5", "Ph?????ng 6", "Ph?????ng 7"};
    private String arrquan4[]={"Ph?????ng 1", "Ph?????ng 2", "Ph?????ng 3", "Ph?????ng 4", "Ph?????ng 6", "Ph?????ng 9", "Ph?????ng 10"};
    private String arrtanbinh[]={"Ph?????ng 1", "Ph?????ng 2", "Ph?????ng 3", "Ph?????ng 4", "Ph?????ng 5", "Ph?????ng 6", "Ph?????ng 7"};
    private String arrvogap[]={"Ph?????ng 1", "Ph?????ng 2", "Ph?????ng 3", "Ph?????ng 4", "Ph?????ng 5", "Ph?????ng 6", "Ph?????ng 7"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_phong_tro);

        //??nh x???
        anhXa();


        //???n toolBar
        getSupportActionBar().hide();

        //set ???nh v??o rcv
        setImgRcv();

        //c??c event click
        eventClick();

        //h??m set data update
        setDataUpdate();

        //set ?????a ch???
        setAutoComplete();

        //onclickToggleButton
        onclickToggleButton();

        //set sdt
        setSdt();


    }

    //h??m set data s???a
    public void setDataUpdate() {
        Intent intent = getIntent();
        String value1 = intent.getStringExtra("IDPHONGTRO");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            return;
        }

        reference = FirebaseDatabase.getInstance(linkRealTime).getReference("users").child(user.getUid()).child("baiDangCuaToi").child(value1);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String tenPhong = dataSnapshot.child("tenPhong").getValue(String.class);
                String sucChua = dataSnapshot.child("sucChua").getValue(String.class);
                String dienTich = dataSnapshot.child("dienTich").getValue(String.class);
                String giaPhong = dataSnapshot.child("giaPhong").getValue(String.class);
                String tienDien = dataSnapshot.child("tienDien").getValue(String.class);
                String tienNuoc = dataSnapshot.child("tienNuoc").getValue(String.class);
                String tenDuong = dataSnapshot.child("tenDuong").getValue(String.class);
                String moTa = dataSnapshot.child("moTaPhong").getValue(String.class);

                //loai phong
                String loaiPhong = dataSnapshot.child("loaiPhong").getValue(String.class);
                int vitriloaiphong = 0;
                if (loaiPhong.equals("Ph??ng cho thu??")) {
                    vitriloaiphong = 0;
                } else if (loaiPhong.equals("Nh?? nguy??n c??n")) {
                    vitriloaiphong = 1;
                } else  if (loaiPhong.equals("K?? t??c x?? / Homestay")){
                    vitriloaiphong = 2;
                } else  if (loaiPhong.equals("C??n h???")){
                    vitriloaiphong = 3;
                }

                arrayAdapter = new ArrayAdapter<String>(SuaPhongTro.this, android.R.layout.simple_list_item_1, arrloaiphong);
                edtLoaiPhong.setText(loaiPhong);
                edtLoaiPhong.setAdapter(arrayAdapter);
                loaiPhong = String.valueOf(arrayAdapter.getItem(vitriloaiphong));

                //tinh thanh
                String tinhThanh = dataSnapshot.child("tinhThanh").getValue(String.class);
                int vitritinhthanh = 0;
                if (tinhThanh.equals("H?? N???i")) {
                    vitritinhthanh = 0;
                } else if (tinhThanh.equals("????k L??k")) {
                    vitritinhthanh = 1;
                } else  if (tinhThanh.equals("H??? Ch?? Minh")){
                    vitritinhthanh = 2;
                }

                arrayAdapter = new ArrayAdapter<String>(SuaPhongTro.this, android.R.layout.simple_list_item_1, arrTinhThanh);
                edtTinhThanh.setText(tinhThanh);
                edtTinhThanh.setAdapter(arrayAdapter);
                tinhthanh = String.valueOf(arrayAdapter.getItem(vitriloaiphong));

                //quan huyen
                String quanHuyen = dataSnapshot.child("quanHuyen").getValue(String.class);
                if (tinhThanh.equals("H?? N???i")) {
                    int vitrihanoi = 0;
                    if (quanHuyen.equals("Ba ????nh")) {
                        vitrihanoi = 0;
                    } else if (quanHuyen.equals("B???c T??? Li??m")) {
                        vitrihanoi = 1;
                    } else  if (quanHuyen.equals("C???u Gi???y")){
                        vitrihanoi = 2;
                    } else  if (quanHuyen.equals("?????ng ??a")){
                        vitrihanoi = 3;
                    } else  if (quanHuyen.equals("Ho??ng Mai")){
                        vitrihanoi = 4;
                    } else  if (quanHuyen.equals("Long Bi??n")){
                        vitrihanoi = 5;
                    } else  if (quanHuyen.equals("Nam T??? Li??m")){
                        vitrihanoi = 6;
                    }

                    arrayAdapter = new ArrayAdapter<String>(SuaPhongTro.this, android.R.layout.simple_list_item_1, arrquanhuyenhanoi);
                    edtQuanHuyen.setText(quanHuyen);
                    edtQuanHuyen.setAdapter(arrayAdapter);
                    quanhuyen = String.valueOf(arrayAdapter.getItem(vitrihanoi));
                } else if (tinhThanh.equals("????k L??k")) {
                    int vitridaklak = 0;
                    if (quanHuyen.equals("Bu??n Ma Thu???t")) {
                        vitridaklak = 0;
                    } else if (quanHuyen.equals("Bu??n ????n")) {
                        vitridaklak = 1;
                    } else  if (quanHuyen.equals("C?? M'gar")){
                        vitridaklak = 2;
                    } else  if (quanHuyen.equals("Ea H'leo")){
                        vitridaklak = 3;
                    } else  if (quanHuyen.equals("Ea Kar")){
                        vitridaklak = 4;
                    }

                    arrayAdapter = new ArrayAdapter<String>(SuaPhongTro.this, android.R.layout.simple_list_item_1, arrquanhuyendaklak);
                    edtQuanHuyen.setText(quanHuyen);
                    edtQuanHuyen.setAdapter(arrayAdapter);
                    quanhuyen = String.valueOf(arrayAdapter.getItem(vitridaklak));
                } else if (tinhThanh.equals("H??? Ch?? Minh")) {
                    int vitrihanoi = 0;
                    if (quanHuyen.equals("Ba ????nh")) {
                        vitrihanoi = 0;
                    } else if (quanHuyen.equals("Qu???n 1")) {
                        vitrihanoi = 1;
                    } else  if (quanHuyen.equals("Qu???n 4")){
                        vitrihanoi = 2;
                    } else  if (quanHuyen.equals("Qu???n T??n B??nh")){
                        vitrihanoi = 3;
                    } else  if (quanHuyen.equals("Qu???n G?? V???p")){
                        vitrihanoi = 4;
                    }

                    arrayAdapter = new ArrayAdapter<String>(SuaPhongTro.this, android.R.layout.simple_list_item_1, arrquanhuyenhochiminh);
                    edtQuanHuyen.setText(quanHuyen);
                    edtQuanHuyen.setAdapter(arrayAdapter);
                    quanhuyen = String.valueOf(arrayAdapter.getItem(vitrihanoi));
                }

                //phuong xa
                String phuongXa = dataSnapshot.child("phuongXa").getValue(String.class);
                if (quanHuyen.equals("Ba ????nh")) {
                   int vitri = 0;
                    if (phuongXa.equals("C???ng v???nh")) {
                         vitri = 0;
                    } else  if (phuongXa.equals("??i???n Bi??n")){
                         vitri = 1;
                    } else  if (phuongXa.equals("?????i V???n")){
                         vitri = 2;
                    } else  if (phuongXa.equals("Gi???ng V??")){
                         vitri = 3;
                    } else  if (phuongXa.equals("Kim M??")){
                         vitri = 4;
                    } else  if (phuongXa.equals("Li???u Giai")){
                         vitri = 5;
                    } else if (phuongXa.equals("Ng???c H??")) {
                         vitri = 6;
                    } else if (phuongXa.equals("Ng???c Kh??nh")) {
                         vitri = 7;
                    } else  if (phuongXa.equals("Nguy???n Trung Tr???c")){
                         vitri = 8;
                    } else  if (phuongXa.equals("Ph??c X??")){
                         vitri = 9;
                    }
                    arrayAdapter = new ArrayAdapter<String>(SuaPhongTro.this, android.R.layout.simple_list_item_1, arrbadinh);
                    edtPhuongXa.setText(phuongXa);
                    edtPhuongXa.setAdapter(arrayAdapter);
                    phuongxa = String.valueOf(arrayAdapter.getItem(vitri));

                } else if (quanHuyen.equals("B???c T??? Li??m")) {
                    int vitri = 0;
                    if (phuongXa.equals("?????c Th???ng")) {
                        vitri = 0;
                    } else  if (phuongXa.equals("????ng Ng???c")){
                        vitri = 1;
                    } else  if (phuongXa.equals("Th???y Ph????ng")){
                        vitri = 2;
                    } else  if (phuongXa.equals("Li??n M???c")){
                        vitri = 3;
                    } else  if (phuongXa.equals("Th?????ng C??t")){
                        vitri = 4;
                    } else  if (phuongXa.equals("T??y L???u")){
                        vitri = 5;
                    } else if (phuongXa.equals("Minh Khai")) {
                        vitri = 6;
                    } else if (phuongXa.equals("Ph?? Di???n")) {
                        vitri = 7;
                    } else  if (phuongXa.equals("Ph??c Di???n")){
                        vitri = 8;
                    } else  if (phuongXa.equals("Xu??n ?????nh")){
                        vitri = 9;
                    }
                    arrayAdapter = new ArrayAdapter<String>(SuaPhongTro.this, android.R.layout.simple_list_item_1, arrbactuliem);
                    edtPhuongXa.setText(phuongXa);
                    edtPhuongXa.setAdapter(arrayAdapter);
                    phuongxa = String.valueOf(arrayAdapter.getItem(vitri));
                } else  if (quanHuyen.equals("C???u Gi???y")){
                    int vitri = 0;
                    if (phuongXa.equals("D???ch V???ng")) {
                        vitri = 0;
                    } else  if (phuongXa.equals("D???ch V???ng H???u")){
                        vitri = 1;
                    } else  if (phuongXa.equals("Mai D???ch")){
                        vitri = 2;
                    } else  if (phuongXa.equals("Ngh??a ????")){
                        vitri = 3;
                    } else  if (phuongXa.equals("Ngh??a T??n")){
                        vitri = 4;
                    } else  if (phuongXa.equals("Quan Hoa")){
                        vitri = 5;
                    } else if (phuongXa.equals("Trung H??a")) {
                        vitri = 6;
                    } else if (phuongXa.equals("Y??n H??a")) {
                        vitri = 7;
                    }
                    arrayAdapter = new ArrayAdapter<String>(SuaPhongTro.this, android.R.layout.simple_list_item_1, arrcaugiay);
                    edtPhuongXa.setText(phuongXa);
                    edtPhuongXa.setAdapter(arrayAdapter);
                    phuongxa = String.valueOf(arrayAdapter.getItem(vitri));
                } else  if (quanHuyen.equals("?????ng ??a")){
                    int vitri = 0;
                    if (phuongXa.equals("C??t Linh")) {
                        vitri = 0;
                    } else  if (phuongXa.equals("H??ng B???t")){
                        vitri = 1;
                    } else  if (phuongXa.equals("Kh??m Thi??n")){
                        vitri = 2;
                    } else  if (phuongXa.equals("Kh????ng Th?????ng")){
                        vitri = 3;
                    } else  if (phuongXa.equals("Kim Li??n")){
                        vitri = 4;
                    } else  if (phuongXa.equals("L??ng H???")){
                        vitri = 5;
                    } else if (phuongXa.equals("L??ng Th?????ng")) {
                        vitri = 6;
                    } else if (phuongXa.equals("Nam ?????ng")) {
                        vitri = 7;
                    } else  if (phuongXa.equals("Nguy???n Tr??i")){
                        vitri = 8;
                    } else  if (phuongXa.equals("?? Ch??? D???a")){
                        vitri = 9;
                    } else  if (phuongXa.equals("Ph????ng Li??n")){
                        vitri = 10;
                    }
                    arrayAdapter = new ArrayAdapter<String>(SuaPhongTro.this, android.R.layout.simple_list_item_1, arrdongda);
                    edtPhuongXa.setText(phuongXa);
                    edtPhuongXa.setAdapter(arrayAdapter);
                    phuongxa = String.valueOf(arrayAdapter.getItem(vitri));
                } else  if (quanHuyen.equals("Ho??ng Mai")){
                    int vitri = 0;
                    if (phuongXa.equals("Mai H??ng")) {
                        vitri = 0;
                    } else  if (phuongXa.equals("Qu???nh D???")){
                        vitri = 1;
                    } else  if (phuongXa.equals("Qu???nh Ph????ng")){
                        vitri = 2;
                    } else  if (phuongXa.equals("Qu???nh Thi???n")){
                        vitri = 3;
                    } else  if (phuongXa.equals("Qu???nh Xu??n")){
                        vitri = 4;
                    } else  if (phuongXa.equals("Qu???nh Li??n")){
                        vitri = 5;
                    } else if (phuongXa.equals("Qu???nh L???c")) {
                        vitri = 6;
                    }
                    arrayAdapter = new ArrayAdapter<String>(SuaPhongTro.this, android.R.layout.simple_list_item_1, arrhoangmai);
                    edtPhuongXa.setText(phuongXa);
                    edtPhuongXa.setAdapter(arrayAdapter);
                    phuongxa = String.valueOf(arrayAdapter.getItem(vitri));
                } else  if (quanHuyen.equals("Long Bi??n")){
                    int vitri = 0;
                    if (phuongXa.equals("B??? ?????")) {
                        vitri = 0;
                    } else  if (phuongXa.equals("C??? Kh???i")){
                        vitri = 1;
                    } else  if (phuongXa.equals("?????c Giang")){
                        vitri = 2;
                    } else  if (phuongXa.equals("Gia Th???y")){
                        vitri = 3;
                    } else  if (phuongXa.equals("Giang Bi??n")){
                        vitri = 4;
                    } else  if (phuongXa.equals("Long Bi??n")){
                        vitri = 5;
                    } else if (phuongXa.equals("Ng???c L??m")) {
                        vitri = 6;
                    } else if (phuongXa.equals("Ng???c Th???y")) {
                        vitri = 7;
                    } else  if (phuongXa.equals("Ph??c ?????ng")){
                        vitri = 8;
                    }
                    arrayAdapter = new ArrayAdapter<String>(SuaPhongTro.this, android.R.layout.simple_list_item_1, arrlongbien);
                    edtPhuongXa.setText(phuongXa);
                    edtPhuongXa.setAdapter(arrayAdapter);
                    phuongxa = String.valueOf(arrayAdapter.getItem(vitri));
                } else  if (quanHuyen.equals("Nam T??? Li??m")){
                    int vitri = 0;
                    if (phuongXa.equals("C???u Di???n")) {
                        vitri = 0;
                    } else  if (phuongXa.equals("M??? ????nh 1")){
                        vitri = 1;
                    } else  if (phuongXa.equals("M??? ????nh 2")){
                        vitri = 2;
                    } else  if (phuongXa.equals("Ph?? ????")){
                        vitri = 3;
                    } else  if (phuongXa.equals("M??? Tr??")){
                        vitri = 4;
                    } else  if (phuongXa.equals("Trung V??n")){
                        vitri = 5;
                    } else if (phuongXa.equals("?????i M???")) {
                        vitri = 6;
                    } else if (phuongXa.equals("T??y M???")) {
                        vitri = 7;
                    }
                    arrayAdapter = new ArrayAdapter<String>(SuaPhongTro.this, android.R.layout.simple_list_item_1, arrnamtuliem);
                    edtPhuongXa.setText(phuongXa);
                    edtPhuongXa.setAdapter(arrayAdapter);
                    phuongxa = String.valueOf(arrayAdapter.getItem(vitri));
                } else if (quanHuyen.equals("Bu??n Ma Thu???t")) {
                    int vitri = 0;
                    if (phuongXa.equals("Ea Tam")) {
                        vitri = 0;
                    } else  if (phuongXa.equals("Kh??nh Xu??n")){
                        vitri = 1;
                    } else  if (phuongXa.equals("T??n An")){
                        vitri = 2;
                    } else  if (phuongXa.equals("T??n H??a")){
                        vitri = 3;
                    } else  if (phuongXa.equals("T??n L???p")){
                        vitri = 4;
                    } else  if (phuongXa.equals("T??n L???i")){
                        vitri = 5;
                    } else if (phuongXa.equals("T??n Th??nh")) {
                        vitri = 6;
                    } else if (phuongXa.equals("T??n Ti???n")) {
                        vitri = 7;
                    } else  if (phuongXa.equals("Th???ng L???i")){
                        vitri = 8;
                    } else  if (phuongXa.equals("Th??nh C??ng")){
                        vitri = 9;
                    }
                    arrayAdapter = new ArrayAdapter<String>(SuaPhongTro.this, android.R.layout.simple_list_item_1, arrbuonmathuot);
                    edtPhuongXa.setText(phuongXa);
                    edtPhuongXa.setAdapter(arrayAdapter);
                    phuongxa = String.valueOf(arrayAdapter.getItem(vitri));
                } else if (quanHuyen.equals("Bu??n ????n")) {
                    int vitri = 0;
                    if (phuongXa.equals("Kr??ng Na")) {
                        vitri = 0;
                    } else  if (phuongXa.equals("Ea Huar")){
                        vitri = 1;
                    } else  if (phuongXa.equals("Ea Wer")){
                        vitri = 2;
                    }
                    arrayAdapter = new ArrayAdapter<String>(SuaPhongTro.this, android.R.layout.simple_list_item_1, arrbuondon);
                    edtPhuongXa.setText(phuongXa);
                    edtPhuongXa.setAdapter(arrayAdapter);
                    phuongxa = String.valueOf(arrayAdapter.getItem(vitri));
                } else  if (quanHuyen.equals("C?? M'gar")){
                    int vitri = 0;
                    if (phuongXa.equals("C?? Dli?? M'n??ng")) {
                        vitri = 0;
                    } else  if (phuongXa.equals("C?? M'gar")){
                        vitri = 1;
                    } else  if (phuongXa.equals("C?? Su??")){
                        vitri = 2;
                    } else  if (phuongXa.equals("Cu??r ????ng")){
                        vitri = 3;
                    } else  if (phuongXa.equals("Ea Dr??ng")){
                        vitri = 4;
                    } else  if (phuongXa.equals("Ea H'??ing")){
                        vitri = 5;
                    } else if (phuongXa.equals("Ea Ki???t")) {
                        vitri = 6;
                    } else if (phuongXa.equals("Ea Kpam")) {
                        vitri = 7;
                    }
                    arrayAdapter = new ArrayAdapter<String>(SuaPhongTro.this, android.R.layout.simple_list_item_1, arrcumga);
                    edtPhuongXa.setText(phuongXa);
                    edtPhuongXa.setAdapter(arrayAdapter);
                    phuongxa = String.valueOf(arrayAdapter.getItem(vitri));
                } else  if (quanHuyen.equals("Ea H'leo")){
                    int vitri = 0;
                    if (phuongXa.equals("Ea Nam")) {
                        vitri = 0;
                    } else  if (phuongXa.equals("Ea Khal")){
                        vitri = 1;
                    } else  if (phuongXa.equals("Ea Hiao")){
                        vitri = 2;
                    }
                    arrayAdapter = new ArrayAdapter<String>(SuaPhongTro.this, android.R.layout.simple_list_item_1, arreahleo);
                    edtPhuongXa.setText(phuongXa);
                    edtPhuongXa.setAdapter(arrayAdapter);
                    phuongxa = String.valueOf(arrayAdapter.getItem(vitri));
                } else  if (quanHuyen.equals("Ea Kar")){
                    int vitri = 0;
                    if (phuongXa.equals("Ea ??ar")) {
                        vitri = 0;
                    } else  if (phuongXa.equals("Ea Pal")){
                        vitri = 1;
                    } else  if (phuongXa.equals("Ea ??")){
                        vitri = 2;
                    } else  if (phuongXa.equals("C?? Ni")){
                        vitri = 3;
                    }
                    arrayAdapter = new ArrayAdapter<String>(SuaPhongTro.this, android.R.layout.simple_list_item_1, arreakar);
                    edtPhuongXa.setText(phuongXa);
                    edtPhuongXa.setAdapter(arrayAdapter);
                    phuongxa = String.valueOf(arrayAdapter.getItem(vitri));
                } else  if (quanHuyen.equals("Qu???n 1")) {
                    int vitri = 0;
                    if (phuongXa.equals("Ph?????ng 1")) {
                        vitri = 0;
                    } else  if (phuongXa.equals("Ph?????ng 2")){
                        vitri = 1;
                    } else  if (phuongXa.equals("Ph?????ng 3")){
                        vitri = 2;
                    } else  if (phuongXa.equals("Ph?????ng 4")){
                        vitri = 3;
                    } else  if (phuongXa.equals("Ph?????ng 5")){
                        vitri = 4;
                    } else  if (phuongXa.equals("Ph?????ng 6")){
                        vitri = 5;
                    } else if (phuongXa.equals("Ph?????ng 7")) {
                        vitri = 6;
                    }
                    arrayAdapter = new ArrayAdapter<String>(SuaPhongTro.this, android.R.layout.simple_list_item_1, arrquan1);
                    edtPhuongXa.setText(phuongXa);
                    edtPhuongXa.setAdapter(arrayAdapter);
                    phuongxa = String.valueOf(arrayAdapter.getItem(vitri));
                } else  if (quanHuyen.equals("Qu???n 4")){
                    int vitri = 0;
                    if (phuongXa.equals("Ph?????ng 1")) {
                        vitri = 0;
                    } else  if (phuongXa.equals("Ph?????ng 2")){
                        vitri = 1;
                    } else  if (phuongXa.equals("Ph?????ng 3")){
                        vitri = 2;
                    } else  if (phuongXa.equals("Ph?????ng 4")){
                        vitri = 3;
                    } else  if (phuongXa.equals("Ph?????ng 6")){
                        vitri = 4;
                    } else  if (phuongXa.equals("Ph?????ng 9")){
                        vitri = 5;
                    } else if (phuongXa.equals("Ph?????ng 10")) {
                        vitri = 6;
                    }
                    arrayAdapter = new ArrayAdapter<String>(SuaPhongTro.this, android.R.layout.simple_list_item_1, arrquan4);
                    edtPhuongXa.setText(phuongXa);
                    edtPhuongXa.setAdapter(arrayAdapter);
                    phuongxa = String.valueOf(arrayAdapter.getItem(vitri));
                } else  if (quanHuyen.equals("Qu???n T??n B??nh")){
                    int vitri = 0;
                    if (phuongXa.equals("Ph?????ng 1")) {
                        vitri = 0;
                    } else  if (phuongXa.equals("Ph?????ng 2")){
                        vitri = 1;
                    } else  if (phuongXa.equals("Ph?????ng 3")){
                        vitri = 2;
                    } else  if (phuongXa.equals("Ph?????ng 4")){
                        vitri = 3;
                    } else  if (phuongXa.equals("Ph?????ng 5")){
                        vitri = 4;
                    } else  if (phuongXa.equals("Ph?????ng 6")){
                        vitri = 5;
                    } else if (phuongXa.equals("Ph?????ng 7")) {
                        vitri = 6;
                    }
                    arrayAdapter = new ArrayAdapter<String>(SuaPhongTro.this, android.R.layout.simple_list_item_1, arrtanbinh);
                    edtPhuongXa.setText(phuongXa);
                    edtPhuongXa.setAdapter(arrayAdapter);
                    phuongxa = String.valueOf(arrayAdapter.getItem(vitri));
                } else  if (quanHuyen.equals("Qu???n G?? V???p")){
                    int vitri = 0;
                    if (phuongXa.equals("Ph?????ng 1")) {
                        vitri = 0;
                    } else  if (phuongXa.equals("Ph?????ng 2")){
                        vitri = 1;
                    } else  if (phuongXa.equals("Ph?????ng 3")){
                        vitri = 2;
                    } else  if (phuongXa.equals("Ph?????ng 4")){
                        vitri = 3;
                    } else  if (phuongXa.equals("Ph?????ng 5")){
                        vitri = 4;
                    } else  if (phuongXa.equals("Ph?????ng 6")){
                        vitri = 5;
                    } else if (phuongXa.equals("Ph?????ng 7")) {
                        vitri = 6;
                    }
                    arrayAdapter = new ArrayAdapter<String>(SuaPhongTro.this, android.R.layout.simple_list_item_1, arrvogap);
                    edtPhuongXa.setText(phuongXa);
                    edtPhuongXa.setAdapter(arrayAdapter);
                    phuongxa = String.valueOf(arrayAdapter.getItem(vitri));
                }

                //tinh trang
                String tinhTrang = dataSnapshot.child("tinhTrangPhong").getValue(String.class);
                int vitritinhtrang = 0;
                if (tinhTrang.equals("C??n Ph??ng")) {
                    vitritinhtrang = 0;
                } else if (tinhTrang.equals("H???t Ph??ng")) {
                    vitritinhtrang = 1;
                }

                arrayAdapter = new ArrayAdapter<String>(SuaPhongTro.this, android.R.layout.simple_list_item_1, arrTinhTrang);
                edtTinhTrangPhong.setText(tinhTrang);
                edtTinhTrangPhong.setAdapter(arrayAdapter);
                tinhtrang = String.valueOf(arrayAdapter.getItem(vitritinhtrang));

//                photoAdapter = new PhotoAdapter(SuaPhongTro.this);

//
//
//                    reference = FirebaseDatabase.getInstance(linkRealTime).getReference("users").child(user.getUid()).child("baiDangCuaToi").child(value1).child("anhPhong").child("0");
//                    reference.addChildEventListener(new ChildEventListener() {
//                        @Override
//                        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                            PhotoAlbum photoAlbum = snapshot.getValue(PhotoAlbum.class);
//                            if (photoAlbum != null) {
//                                ImageList.add(photoAlbum);
//                                imgAdapter.notifyDataSetChanged();
//                            }
//
//                        }
//
//                        @Override
//                        public void onChildChanged(@NonNull   DataSnapshot snapshot, @Nullable   String previousChildName) {
//
//                        }
//
//                        @Override
//                        public void onChildRemoved(@NonNull  DataSnapshot snapshot) {
//
//                        }
//
//                        @Override
//                        public void onChildMoved(@NonNull   DataSnapshot snapshot, @Nullable   String previousChildName) {
//
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//
//                        }
//                    });
//
//                imgAdapter = new ImgAdapter();
//                imgAdapter = new ImgAdapter(SuaPhongTro.this, R.layout.itemsanpham, ImageList);
//                GridLayoutManager gridLayoutManager = new GridLayoutManager(SuaPhongTro.this, 3);
//                rcvPhoto.setLayoutManager(gridLayoutManager);
//                ImageList = new ArrayList<>();
//                imgAdapter.setDataPhoto(ImageList);
//                rcvPhoto.setAdapter(imgAdapter);






                edtTenPhong.setText(tenPhong);
                edtSoNguoi.setText(sucChua);
                edtDienTich.setText(dienTich);
                edtGiaPhong.setText(giaPhong);
                edtTienDien.setText(tienDien);
                edtTienNuoc.setText(tienNuoc);
                edtTenDuong.setText(tenDuong);
                edtNoiDung.setText(moTa);


            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    //h??m set s??t
    public void setSdt() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            return;
        }

        reference = FirebaseDatabase.getInstance(linkRealTime).getReference("users").child(user.getUid()).child("Profile");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String phoneFromDB = dataSnapshot.child("numberphone").getValue(String.class);
                edtSdtNhaTro.setText(phoneFromDB);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    //h??m set d??? li???u cho AutoCompleteText
    public void setAutoComplete() {

        //lo???i ph??ng
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrloaiphong);
        edtLoaiPhong.setAdapter(arrayAdapter);
        edtLoaiPhong.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                loaiPhong = parent.getItemAtPosition(position).toString();
            }
        });

        //t??nh tr???ng ph??ng
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrTinhTrang);
        edtTinhTrangPhong.setAdapter(arrayAdapter);
        edtTinhTrangPhong.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tinhtrang = parent.getItemAtPosition(position).toString();
            }
        });

        //t???nh th??nh
        arrayAdapter = new ArrayAdapter<String>(SuaPhongTro.this, android.R.layout.simple_list_item_1, arrTinhThanh);
        edtTinhThanh.setAdapter(arrayAdapter);
        edtTinhThanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtQuanHuyen.setText("");
                edtPhuongXa.setText("");
                edtTinhThanh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        tinhthanh = parent.getItemAtPosition(position).toString();

                        //qu???n huy???n
                        if (tinhthanh.equals("H?? N???i")) {
                            arrayAdapter = new ArrayAdapter<String>(SuaPhongTro.this, android.R.layout.simple_list_item_1, arrquanhuyenhanoi);
                            edtQuanHuyen.setAdapter(arrayAdapter);
                            edtQuanHuyen.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    quanhuyen = parent.getItemAtPosition(position).toString();

                                    //ph?????ng x??
                                    if (quanhuyen.equals("Ba ????nh")) {
                                        arrayAdapter = new ArrayAdapter<String>(SuaPhongTro.this, android.R.layout.simple_list_item_1, arrbadinh);
                                        edtPhuongXa.setAdapter(arrayAdapter);
                                        edtPhuongXa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                phuongxa = parent.getItemAtPosition(position).toString();
                                            }
                                        });
                                    } else   if (quanhuyen.equals("B???c T??? Li??m")) {
                                        arrayAdapter = new ArrayAdapter<String>(SuaPhongTro.this, android.R.layout.simple_list_item_1, arrbactuliem);
                                        edtPhuongXa.setAdapter(arrayAdapter);
                                        edtPhuongXa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                phuongxa = parent.getItemAtPosition(position).toString();
                                            }
                                        });
                                    } else   if (quanhuyen.equals("C???u Gi???y")) {
                                        arrayAdapter = new ArrayAdapter<String>(SuaPhongTro.this, android.R.layout.simple_list_item_1, arrcaugiay);
                                        edtPhuongXa.setAdapter(arrayAdapter);
                                        edtPhuongXa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                phuongxa = parent.getItemAtPosition(position).toString();
                                            }
                                        });
                                    } else   if (quanhuyen.equals("?????ng ??a")) {
                                        arrayAdapter = new ArrayAdapter<String>(SuaPhongTro.this, android.R.layout.simple_list_item_1, arrdongda);
                                        edtPhuongXa.setAdapter(arrayAdapter);
                                        edtPhuongXa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                phuongxa = parent.getItemAtPosition(position).toString();
                                            }
                                        });
                                    } else   if (quanhuyen.equals("Ho??ng Mai")) {
                                        arrayAdapter = new ArrayAdapter<String>(SuaPhongTro.this, android.R.layout.simple_list_item_1, arrhoangmai);
                                        edtPhuongXa.setAdapter(arrayAdapter);
                                        edtPhuongXa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                phuongxa = parent.getItemAtPosition(position).toString();
                                            }
                                        });
                                    } else   if (quanhuyen.equals("Long Bi??n")) {
                                        arrayAdapter = new ArrayAdapter<String>(SuaPhongTro.this, android.R.layout.simple_list_item_1, arrlongbien);
                                        edtPhuongXa.setAdapter(arrayAdapter);
                                        edtPhuongXa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                phuongxa = parent.getItemAtPosition(position).toString();
                                            }
                                        });
                                    } else   if (quanhuyen.equals("Nam T??? Li??m")) {
                                        arrayAdapter = new ArrayAdapter<String>(SuaPhongTro.this, android.R.layout.simple_list_item_1, arrnamtuliem);
                                        edtPhuongXa.setAdapter(arrayAdapter);
                                        edtPhuongXa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                phuongxa = parent.getItemAtPosition(position).toString();
                                            }
                                        });
                                    }
                                }
                            });
                        } else   if (tinhthanh.equals("????k L??k")) {
                            arrayAdapter = new ArrayAdapter<String>(SuaPhongTro.this, android.R.layout.simple_list_item_1, arrquanhuyendaklak);
                            edtQuanHuyen.setAdapter(arrayAdapter);
                            edtQuanHuyen.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    quanhuyen = parent.getItemAtPosition(position).toString();

                                    //ph?????ng x??
                                    if (quanhuyen.equals("Bu??n Ma Thu???t")) {
                                        arrayAdapter = new ArrayAdapter<String>(SuaPhongTro.this, android.R.layout.simple_list_item_1, arrbuonmathuot);
                                        edtPhuongXa.setAdapter(arrayAdapter);
                                        edtPhuongXa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                phuongxa = parent.getItemAtPosition(position).toString();
                                            }
                                        });
                                    } else   if (quanhuyen.equals("Bu??n ????n")) {
                                        arrayAdapter = new ArrayAdapter<String>(SuaPhongTro.this, android.R.layout.simple_list_item_1, arrbuondon);
                                        edtPhuongXa.setAdapter(arrayAdapter);
                                        edtPhuongXa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                phuongxa = parent.getItemAtPosition(position).toString();
                                            }
                                        });
                                    } else   if (quanhuyen.equals("Ea Kar")) {
                                        arrayAdapter = new ArrayAdapter<String>(SuaPhongTro.this, android.R.layout.simple_list_item_1, arreakar);
                                        edtPhuongXa.setAdapter(arrayAdapter);
                                        edtPhuongXa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                phuongxa = parent.getItemAtPosition(position).toString();
                                            }
                                        });
                                    } else   if (quanhuyen.equals("Ea H'leo")) {
                                        arrayAdapter = new ArrayAdapter<String>(SuaPhongTro.this, android.R.layout.simple_list_item_1, arreahleo);
                                        edtPhuongXa.setAdapter(arrayAdapter);
                                        edtPhuongXa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                phuongxa = parent.getItemAtPosition(position).toString();
                                            }
                                        });
                                    } else   if (quanhuyen.equals("C?? M'gar")) {
                                        arrayAdapter = new ArrayAdapter<String>(SuaPhongTro.this, android.R.layout.simple_list_item_1, arrcumga);
                                        edtPhuongXa.setAdapter(arrayAdapter);
                                        edtPhuongXa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                phuongxa = parent.getItemAtPosition(position).toString();
                                            }
                                        });
                                    }
                                }
                            });
                        } else   if (tinhthanh.equals("H??? Ch?? Minh")) {
                            arrayAdapter = new ArrayAdapter<String>(SuaPhongTro.this, android.R.layout.simple_list_item_1, arrquanhuyenhochiminh);
                            edtQuanHuyen.setAdapter(arrayAdapter);
                            edtQuanHuyen.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    quanhuyen = parent.getItemAtPosition(position).toString();

                                    //ph?????ng x??
                                    if (quanhuyen.equals("Qu???n 1")) {
                                        arrayAdapter = new ArrayAdapter<String>(SuaPhongTro.this, android.R.layout.simple_list_item_1, arrquan1);
                                        edtPhuongXa.setAdapter(arrayAdapter);
                                        edtPhuongXa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                phuongxa = parent.getItemAtPosition(position).toString();
                                            }
                                        });
                                    } else   if (quanhuyen.equals("Qu???n 4")) {
                                        arrayAdapter = new ArrayAdapter<String>(SuaPhongTro.this, android.R.layout.simple_list_item_1, arrquan4);
                                        edtPhuongXa.setAdapter(arrayAdapter);
                                        edtPhuongXa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                phuongxa = parent.getItemAtPosition(position).toString();
                                            }
                                        });
                                    } else   if (quanhuyen.equals("Qu???n T??n B??nh")) {
                                        arrayAdapter = new ArrayAdapter<String>(SuaPhongTro.this, android.R.layout.simple_list_item_1, arrtanbinh);
                                        edtPhuongXa.setAdapter(arrayAdapter);
                                        edtPhuongXa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                phuongxa = parent.getItemAtPosition(position).toString();
                                            }
                                        });
                                    } else   if (quanhuyen.equals("Qu???n G?? G???p")) {
                                        arrayAdapter = new ArrayAdapter<String>(SuaPhongTro.this, android.R.layout.simple_list_item_1, arrvogap);
                                        edtPhuongXa.setAdapter(arrayAdapter);
                                        edtPhuongXa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                phuongxa = parent.getItemAtPosition(position).toString();
                                            }
                                        });
                                    }
                                }
                            });
                        }
                    }
                });
            }
        });
        edtQuanHuyen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tinhthanh.equals("")) {
                    new SweetAlertDialog(SuaPhongTro.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Oops...")
                            .setContentText("Kh??ng th??? ch???n qu???n huy???n khi ch??a ch???n t???nh th??nh!")
                            .show();
                    return;
                }

                edtPhuongXa.setText("");
                //ph?????ng x??
                if (quanhuyen.equals("Ba ????nh")) {
                    arrayAdapter = new ArrayAdapter<String>(SuaPhongTro.this, android.R.layout.simple_list_item_1, arrbadinh);
                    edtPhuongXa.setAdapter(arrayAdapter);
                    edtPhuongXa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            phuongxa = parent.getItemAtPosition(position).toString();
                        }
                    });
                } else   if (quanhuyen.equals("B???c T??? Li??m")) {
                    arrayAdapter = new ArrayAdapter<String>(SuaPhongTro.this, android.R.layout.simple_list_item_1, arrbactuliem);
                    edtPhuongXa.setAdapter(arrayAdapter);
                    edtPhuongXa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            phuongxa = parent.getItemAtPosition(position).toString();
                        }
                    });
                } else   if (quanhuyen.equals("C???u Gi???y")) {
                    arrayAdapter = new ArrayAdapter<String>(SuaPhongTro.this, android.R.layout.simple_list_item_1, arrcaugiay);
                    edtPhuongXa.setAdapter(arrayAdapter);
                    edtPhuongXa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            phuongxa = parent.getItemAtPosition(position).toString();
                        }
                    });
                } else   if (quanhuyen.equals("?????ng ??a")) {
                    arrayAdapter = new ArrayAdapter<String>(SuaPhongTro.this, android.R.layout.simple_list_item_1, arrdongda);
                    edtPhuongXa.setAdapter(arrayAdapter);
                    edtPhuongXa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            phuongxa = parent.getItemAtPosition(position).toString();
                        }
                    });
                } else   if (quanhuyen.equals("Ho??ng Mai")) {
                    arrayAdapter = new ArrayAdapter<String>(SuaPhongTro.this, android.R.layout.simple_list_item_1, arrhoangmai);
                    edtPhuongXa.setAdapter(arrayAdapter);
                    edtPhuongXa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            phuongxa = parent.getItemAtPosition(position).toString();
                        }
                    });
                } else   if (quanhuyen.equals("Long Bi??n")) {
                    arrayAdapter = new ArrayAdapter<String>(SuaPhongTro.this, android.R.layout.simple_list_item_1, arrlongbien);
                    edtPhuongXa.setAdapter(arrayAdapter);
                    edtPhuongXa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            phuongxa = parent.getItemAtPosition(position).toString();
                        }
                    });
                } else   if (quanhuyen.equals("Nam T??? Li??m")) {
                    arrayAdapter = new ArrayAdapter<String>(SuaPhongTro.this, android.R.layout.simple_list_item_1, arrnamtuliem);
                    edtPhuongXa.setAdapter(arrayAdapter);
                    edtPhuongXa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            phuongxa = parent.getItemAtPosition(position).toString();
                        }
                    });
                }

                //ph?????ng x??
                if (quanhuyen.equals("Bu??n Ma Thu???t")) {
                    arrayAdapter = new ArrayAdapter<String>(SuaPhongTro.this, android.R.layout.simple_list_item_1, arrbuonmathuot);
                    edtPhuongXa.setAdapter(arrayAdapter);
                    edtPhuongXa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            phuongxa = parent.getItemAtPosition(position).toString();
                        }
                    });
                } else   if (quanhuyen.equals("Bu??n ????n")) {
                    arrayAdapter = new ArrayAdapter<String>(SuaPhongTro.this, android.R.layout.simple_list_item_1, arrbuondon);
                    edtPhuongXa.setAdapter(arrayAdapter);
                    edtPhuongXa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            phuongxa = parent.getItemAtPosition(position).toString();
                        }
                    });
                } else   if (quanhuyen.equals("Ea Kar")) {
                    arrayAdapter = new ArrayAdapter<String>(SuaPhongTro.this, android.R.layout.simple_list_item_1, arreakar);
                    edtPhuongXa.setAdapter(arrayAdapter);
                    edtPhuongXa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            phuongxa = parent.getItemAtPosition(position).toString();
                        }
                    });
                } else   if (quanhuyen.equals("Ea H'leo")) {
                    arrayAdapter = new ArrayAdapter<String>(SuaPhongTro.this, android.R.layout.simple_list_item_1, arreahleo);
                    edtPhuongXa.setAdapter(arrayAdapter);
                    edtPhuongXa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            phuongxa = parent.getItemAtPosition(position).toString();
                        }
                    });
                } else   if (quanhuyen.equals("C?? M'gar")) {
                    arrayAdapter = new ArrayAdapter<String>(SuaPhongTro.this, android.R.layout.simple_list_item_1, arrcumga);
                    edtPhuongXa.setAdapter(arrayAdapter);
                    edtPhuongXa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            phuongxa = parent.getItemAtPosition(position).toString();
                        }
                    });
                }

                //ph?????ng x??
                if (quanhuyen.equals("Qu???n 1")) {
                    arrayAdapter = new ArrayAdapter<String>(SuaPhongTro.this, android.R.layout.simple_list_item_1, arrquan1);
                    edtPhuongXa.setAdapter(arrayAdapter);
                    edtPhuongXa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            phuongxa = parent.getItemAtPosition(position).toString();
                        }
                    });
                } else   if (quanhuyen.equals("Qu???n 4")) {
                    arrayAdapter = new ArrayAdapter<String>(SuaPhongTro.this, android.R.layout.simple_list_item_1, arrquan4);
                    edtPhuongXa.setAdapter(arrayAdapter);
                    edtPhuongXa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            phuongxa = parent.getItemAtPosition(position).toString();
                        }
                    });
                } else   if (quanhuyen.equals("Qu???n T??n B??nh")) {
                    arrayAdapter = new ArrayAdapter<String>(SuaPhongTro.this, android.R.layout.simple_list_item_1, arrtanbinh);
                    edtPhuongXa.setAdapter(arrayAdapter);
                    edtPhuongXa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            phuongxa = parent.getItemAtPosition(position).toString();
                        }
                    });
                } else   if (quanhuyen.equals("Qu???n G?? G???p")) {
                    arrayAdapter = new ArrayAdapter<String>(SuaPhongTro.this, android.R.layout.simple_list_item_1, arrvogap);
                    edtPhuongXa.setAdapter(arrayAdapter);
                    edtPhuongXa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            phuongxa = parent.getItemAtPosition(position).toString();
                        }
                    });
                }
            }
        });

        edtPhuongXa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tinhthanh.equals("")) {
                    new SweetAlertDialog(SuaPhongTro.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Oops...")
                            .setContentText("Kh??ng th??? ch???n ph?????ng x?? khi ch??a ch???n t???nh th??nh v?? qu???n huy???n!")
                            .show();
                    return;
                } else    if (quanhuyen.equals("")) {
                    new SweetAlertDialog(SuaPhongTro.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Oops...")
                            .setContentText("Kh??ng th??? ch???n ph?????ng x?? khi ch??a ch???n qu???n huy???n!")
                            .show();
                    return;
                }
            }
        });

    }

    // h??m c??c event click
    public void eventClick() {
        // click ch???n ???nh
        btnImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pemission();
            }
        });

        //click x??a ???nh
        btnClean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photoAdapter.setDataPhoto(null);
                btnImg.setVisibility(View.VISIBLE);
                btnClean.setVisibility(View.GONE);
            }
        });

        //click h???y

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                new SweetAlertDialog(SuaPhongTro.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("B???n mu???n tho??t?")
                        .setContentText("L??u ??: Sau khi b???n tho??t m???i d??? li???u b???n v???a nh???p s??? b??? x??a! B???n ch???c ch???n ch????")
                        .setCancelText("H???y")
                        .setConfirmText("?????ng ??")
                        .showCancelButton(true)
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.cancel();
                            }
                        })
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                finish();
                            }
                        })
                        .show();




            }
        });

        //click th??m ph??ng tr???
        btnSuaPhongTro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSuaPhongTro();
            }
        });

        //click n??i
        btnNoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSpeechToText();
            }
        });

    }

    //h??m l???y ???nh
    private void SendLink(String url) {
        Calendar calendar = Calendar.getInstance();
        PhotoAlbum photoAlbum = new PhotoAlbum();
        photoAlbum.setId(""+ dsAlbum.size());
        photoAlbum.setLinkanh(url);
        dsAlbum.add(photoAlbum);
    }

    private void pemission() {

        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                onClickImg();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(SuaPhongTro.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }


        };
        TedPermission.with(SuaPhongTro.this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .check();


    }

    private void onClickImg() {

        TedBottomPicker.with(SuaPhongTro.this)
                .setPeekHeight(1600)
                .showTitle(true)
                .setTitle("Ch???n ???nh nh?? tr???")
                .setCompleteButtonText("Xong")
                .setEmptySelectionText("Ch??a ch???n ???nh n??o")
                .showMultiImage(new TedBottomSheetDialogFragment.OnMultiImageSelectedListener() {
                    @Override
                    public void onImagesSelected(List<Uri> uriList) {
                        // here is selected image uri list
                        if (uriList != null && !uriList.isEmpty()) {
                            btnClean.setVisibility(View.VISIBLE);
                            photoAdapter.setDataPhoto(uriList);
                            final StorageReference ImageFolder =  FirebaseStorage.getInstance(linkStorage).getReference();
                            for (uploads=0; uploads < uriList.size(); uploads++) {
                                final StorageReference imagename = ImageFolder.child(""+  UUID.randomUUID() +".png");

                                imagename.putFile(uriList.get(uploads)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        imagename.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
                                                String url = String.valueOf(uri);
                                                photoAlbum1 = String.valueOf(uri);
                                                SendLink(url);
                                            }
                                        });

                                    }
                                });


                            }

                        }
                    }
                });



    }

    //h??m set ???nh v??o rcv
    public void setImgRcv() {
        photoAdapter = new PhotoAdapter(SuaPhongTro.this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        rcvPhoto.setLayoutManager(gridLayoutManager);
        rcvPhoto.setAdapter(photoAdapter);
    }

    //h??m th??m nh?? tr???
    public void setSuaPhongTro() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            return;
        }

        String tenPhong = edtTenPhong.getText().toString().trim();
        String sucChua = edtSoNguoi.getText().toString().trim();
        String dienTinh = edtDienTich.getText().toString().trim();
        String loaiPhong = edtLoaiPhong.getText().toString().trim();
        String giaPhong = edtGiaPhong.getText().toString().trim();
        String tienDien = edtTienDien.getText().toString().trim();
        String tienNuoc = edtTienNuoc.getText().toString().trim();
        String tinhThanh = edtTinhThanh.getText().toString().trim();
        String quanHuyen = edtQuanHuyen.getText().toString().trim();
        String phuongXa = edtPhuongXa.getText().toString().trim();
        String soNha = edtTenDuong.getText().toString().trim();
        String tinhTrang = edtTinhTrangPhong.getText().toString().trim();
        String noiDung = edtNoiDung.getText().toString().trim();
        String soDT = edtSdtNhaTro.getText().toString().trim();


        Boolean checkError = true;
        if (photoAdapter.getItemCount() == 0) {
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Oops...")
                    .setContentText("B???n ch??a th??m h??nh m??!")
                    .show();
            checkError = false;
        } else if (photoAdapter.getItemCount() < 4) {
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Oops...")
                    .setContentText("S??? h??nh t???i thi???u l?? 4 t???m!")
                    .show();
            checkError = false;
        }

        if (tenPhong.isEmpty()) {
            edtTenPhong.setError("Kh??ng ????? tr???ng!");
            checkError = false;
        }

        if (sucChua.isEmpty()) {
            edtSoNguoi.setError("Kh??ng ????? tr???ng!");
            checkError = false;
        }

        if (dienTinh.isEmpty()) {
            edtDienTich.setError("Kh??ng ????? tr???ng!");
            checkError = false;
        }

        if (loaiPhong.isEmpty()) {
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Oops...")
                    .setContentText("Ch??a ch???n lo???i ph??ng!")
                    .show();
            checkError = false;
        }

        if (giaPhong.isEmpty()) {
            edtGiaPhong.setError("Kh??ng ????? tr???ng!");
            checkError = false;
        }

        if (tienDien.isEmpty()) {
            edtTienDien.setError("Kh??ng ????? tr???ng!");
            checkError = false;
        }

        if (tienNuoc.isEmpty()) {
            edtTienNuoc.setError("Kh??ng ????? tr???ng!");
            checkError = false;
        }

        if (tinhThanh.isEmpty()) {
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Oops...")
                    .setContentText("Vui l??ng ch???n t???nh th??nh!")
                    .show();
            checkError = false;
        }

        if (quanHuyen.isEmpty()) {
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Oops...")
                    .setContentText("Vui l??ng ch???n qu???n huy???n!")
                    .show();
            checkError = false;
        }

        if (phuongXa.isEmpty()) {
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Oops...")
                    .setContentText("Vui l??ng ch???n ph?????ng x??!")
                    .show();
            checkError = false;
        }

        if (soNha.isEmpty()) {
            edtTenDuong.setError("Kh??ng ????? tr???ng!");
            checkError = false;
        }

        if (tinhTrang.isEmpty()) {
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Oops...")
                    .setContentText("Vui l??ng ch???n t??nh tr???ng ph??ng!")
                    .show();
            checkError = false;
        }

        if (noiDung.length() < 50) {
            edtNoiDung.setError("Ph???i m?? t??? ph??ng ??t nh???t 50 k?? t???");
            checkError = false;
        }

        if (checkError == true) {

            Calendar calendar = Calendar.getInstance();
            NhaTro nhaTro = new NhaTro();

            nhaTro.setIdPhong("" + calendar.getTimeInMillis());
            nhaTro.setTenPhong(tenPhong);
            nhaTro.setSucChua(sucChua);
            nhaTro.setDienTich(dienTinh);
            nhaTro.setLoaiPhong(loaiPhong);
            nhaTro.setGiaPhong(giaPhong);
            nhaTro.setTienDien(tienDien);
            nhaTro.setTienNuoc(tienNuoc);
            nhaTro.setTinhThanh(tinhThanh);
            nhaTro.setQuanHuyen(quanHuyen);
            nhaTro.setPhuongXa(phuongXa);
            nhaTro.setTenDuong(soNha);
            nhaTro.setTinhTrangPhong(tinhTrang);
            nhaTro.setMoTaPhong(noiDung);
            nhaTro.setSoDTPhong(soDT);

            // bai dawng tong quan
            reference = FirebaseDatabase.getInstance(linkRealTime).getReference("danhSachTro");
            reference.child("" + calendar.getTimeInMillis()).setValue(nhaTro);
            reference = FirebaseDatabase.getInstance(linkRealTime).getReference("danhSachTro").child("" + calendar.getTimeInMillis());
            reference.child("anhPhong").setValue(dsAlbum);
            reference = FirebaseDatabase.getInstance(linkRealTime).getReference("danhSachTro");
            reference.child("" + calendar.getTimeInMillis()).child("avatar").setValue(photoAlbum1);
            reference = FirebaseDatabase.getInstance(linkRealTime).getReference("danhSachTro").child("" + calendar.getTimeInMillis());
            reference.child("tienIchPhong").setValue(dsTienIch);

            //theem voo baif ddawng cua toi
            reference = FirebaseDatabase.getInstance(linkRealTime).getReference("users").child(user.getUid()).child("baiDangCuaToi");
            reference.child("" + calendar.getTimeInMillis()).setValue(nhaTro);
            reference = FirebaseDatabase.getInstance(linkRealTime).getReference("users").child(user.getUid()).child("baiDangCuaToi").child("" + calendar.getTimeInMillis());
            reference.child("anhPhong").setValue(dsAlbum);
            reference = FirebaseDatabase.getInstance(linkRealTime).getReference("users").child(user.getUid()).child("baiDangCuaToi");
            reference.child("" + calendar.getTimeInMillis()).child("avatar").setValue(photoAlbum1);
            reference = FirebaseDatabase.getInstance(linkRealTime).getReference("users").child(user.getUid()).child("baiDangCuaToi").child("" + calendar.getTimeInMillis());
            reference.child("tienIchPhong").setValue(dsTienIch);

        }
    }


    //h??m event Toggle Buttons
    public void onclickToggleButton() {
        //Internet
        btginternet.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tienIch.setId(""+ dsTienIch.size());
                    tienIch.setTen("Internet");
                    dsTienIch.add(tienIch);
                } else {
                    for (int i = 0; i <= dsTienIch.size(); i++){
                        if (tienIch.getTen().equals("Internet")) {
                            dsTienIch.remove(i);
                            return;
                        }
                    }
                }
            }
        });

        //Wc
        btgWc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tienIch1.setId(""+ dsTienIch.size());
                    tienIch1.setTen("WC Ri??ng");
                    dsTienIch.add(tienIch1);
                } else {
                    for (int i = 0; i <= dsTienIch.size(); i++){
                        if (tienIch1.getTen().equals("WC Ri??ng")) {
                            dsTienIch.remove(i);
                            return;
                        }
                    }
                }
            }
        });

        //m??y l???nh
        btgMayLanh.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tienIch2.setId(""+ dsTienIch.size());
                    tienIch2.setTen("M??y L???nh");
                    dsTienIch.add(tienIch2);
                } else {
                    for (int i = 0; i <= dsTienIch.size(); i++){
                        if (tienIch2.getTen().equals("M??y L???nh")) {
                            dsTienIch.remove(i);
                            return;
                        }
                    }
                }
            }
        });

        //an ninh
        btgAnNinh.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tienIch3.setId(""+ dsTienIch.size());
                    tienIch3.setTen("An Ninh");
                    dsTienIch.add(tienIch3);
                } else {
                    for (int i = 0; i <= dsTienIch.size(); i++){
                        if (tienIch3.getTen().equals("An Ninh")) {
                            dsTienIch.remove(i);
                            return;
                        }
                    }
                }
            }
        });

        //m??y n?????c n??ng
        btgMayNuocNong.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tienIch4.setId(""+ dsTienIch.size());
                    tienIch4.setTen("M??y N?????c N??ng");
                    dsTienIch.add(tienIch4);
                } else {
                    for (int i = 0; i <= dsTienIch.size(); i++){
                        if (tienIch4.getTen().equals("M??y N?????c N??ng")) {
                            dsTienIch.remove(i);
                            return;
                        }
                    }
                }
            }
        });

        //g??c
        btgGac.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tienIch5.setId(""+ dsTienIch.size());
                    tienIch5.setTen("G??c L???ng");
                    dsTienIch.add(tienIch5);
                } else {
                    for (int i = 0; i <= dsTienIch.size(); i++){
                        if (tienIch5.getTen().equals("G??c L???ng")) {
                            dsTienIch.remove(i);
                            return;
                        }
                    }
                }
            }
        });

        //tivi
        btgTivi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tienIch6.setId(""+ dsTienIch.size());
                    tienIch6.setTen("Tivi");
                    dsTienIch.add(tienIch6);
                } else {
                    for (int i = 0; i <= dsTienIch.size(); i++){
                        if (tienIch6.getTen().equals("Tivi")) {
                            dsTienIch.remove(i);
                            return;
                        }
                    }
                }
            }
        });

        //m??y gi???t
        btgMayGiat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tienIch7.setId(""+ dsTienIch.size());
                    tienIch7.setTen("M??y Gi???t");
                    dsTienIch.add(tienIch7);
                } else {
                    for (int i = 0; i <= dsTienIch.size(); i++){
                        if (tienIch7.getTen().equals("M??y Gi???t")) {
                            dsTienIch.remove(i);
                            return;
                        }
                    }
                }
            }
        });

        //nh?? b???p
        btgNhaBep.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tienIch8.setId(""+ dsTienIch.size());
                    tienIch8.setTen("Nh?? B???p");
                    dsTienIch.add(tienIch8);
                } else {
                    for (int i = 0; i <= dsTienIch.size(); i++){
                        if (tienIch8.getTen().equals("Nh?? B???p")) {
                            dsTienIch.remove(i);
                            return;
                        }
                    }
                }
            }
        });


        //t??? ?????
        btgTuDo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tienIch9.setId(""+ dsTienIch.size());
                    tienIch9.setTen("T??? ?????");
                    dsTienIch.add(tienIch9);
                } else {
                    for (int i = 0; i <= dsTienIch.size(); i++){
                        if (tienIch9.getTen().equals("T??? ?????")) {
                            dsTienIch.remove(i);
                            return;
                        }
                    }
                }
            }
        });


        //th?? c??ng
        btgThuCung.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tienIch10.setId(""+ dsTienIch.size());
                    tienIch10.setTen("Th?? C??ng");
                    dsTienIch.add(tienIch10);
                } else {
                    for (int i = 0; i <= dsTienIch.size(); i++){
                        if (tienIch10.getTen().equals("Th?? C??ng")) {
                            dsTienIch.remove(i);
                            return;
                        }
                    }
                }
            }
        });

        //t??? l???nh
        btgTuLanh.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tienIch11.setId(""+ dsTienIch.size());
                    tienIch11.setTen("T??? L???nh");
                    dsTienIch.add(tienIch11);
                } else {
                    for (int i = 0; i <= dsTienIch.size(); i++){
                        if (tienIch11.getTen().equals("T??? L???nh")) {
                            dsTienIch.remove(i);
                            return;
                        }
                    }
                }
            }
        });
    }

    //h??m onclick Speech to text
    public void onClickSpeechToText() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "");
        try {
            startActivityForResult(intent, RESULT_SPEECH);
        }catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case RESULT_SPEECH:
                if (resultCode == RESULT_OK && data != null){
                    ArrayList<String> text =data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    edtNoiDung.setText(text.get(0));
                }
                break;
        }
    }

    //??nh x???
    public void  anhXa() {
        //g???i ???????ng d???n firebase
        linkRealTime = getResources().getString(R.string.link_RealTime_Database);
        linkStorage = getResources().getString(R.string.link_Storage);
        //findViewById
        rcvPhoto = findViewById(R.id.rcv_img);
        //btn
        btnImg = findViewById(R.id.btnSelectImg);
        btnClean = findViewById(R.id.btnCleanImg);
        btnNoi = findViewById(R.id.btnNoi);
        btnHuy = findViewById(R.id.btnHuy);
        btnSuaPhongTro = findViewById(R.id.btnThemNhaTro);
        //edt
        edtSdtNhaTro = findViewById(R.id.edtSdtNhaTro);
        edtNoiDung = findViewById(R.id.edtNoiDung);
        edtPhuongXa = findViewById(R.id.edtPhuongXa);
        edtQuanHuyen = findViewById(R.id.edtQuanHuyen);
        edtTinhThanh = findViewById(R.id.edtTinhThanh);
        edtTienNuoc = findViewById(R.id.edtTienNuoc);
        edtTienDien = findViewById(R.id.edtTienDien);
        edtGiaPhong = findViewById(R.id.edtGiaPhong);
        edtLoaiPhong = findViewById(R.id.edtLoaiPhong);
        edtSoNguoi = findViewById(R.id.edtSoNguoi);
        edtDienTich = findViewById(R.id.edtDienTich);
        edtTenPhong = findViewById(R.id.edtTenPhong);
        edtTinhTrangPhong = findViewById(R.id.edtTinhTrangPhong);
        edtTenDuong = findViewById(R.id.edtTenDuong);
        //btg
        btginternet = findViewById(R.id.btginternet);
        btgWc = findViewById(R.id.btgWc);
        btgMayLanh = findViewById(R.id.btgMayLanh);
        btgMayNuocNong = findViewById(R.id.btgMayNuocNong);
        btgMayGiat = findViewById(R.id.btgMayGiat);
        btgGac = findViewById(R.id.btgGac);
        btgNhaBep = findViewById(R.id.btgNhaBep);
        btgTivi = findViewById(R.id.btgTivi);
        btgAnNinh = findViewById(R.id.btgAnNinh);
        btgTuDo = findViewById(R.id.btgTuDo);
        btgThuCung = findViewById(R.id.btgThuCung);
        btgTuLanh = findViewById(R.id.btgTuLanh);
    }
}
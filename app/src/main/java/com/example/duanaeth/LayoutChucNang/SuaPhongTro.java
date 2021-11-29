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

    private String arrloaiphong[] = {"Phòng cho thuê", "Nhà nguyên căn", "Ký túc xá / Homestay", "Căn hộ"};
    private String arrTinhTrang[] = {"Còn Phòng", "Hết Phòng"};

    private String arrTinhThanh[] = {"Hà Nội", "Đăk Lăk", "Hồ Chí Minh"};
    private String arrquanhuyenhanoi[] = {"Ba Đình", "Bắc Từ Liêm", "Cầu Giấy", "Đống Đa", "Hoàng Mai", "Long Biên", "Nam Từ Liêm",};
    private String arrquanhuyendaklak[] = {"Buôn Ma Thuột", "Buôn Đôn", "Cư M'gar", "Ea H'leo", "Ea Kar"};
    private String arrquanhuyenhochiminh[] = {"Quận 1", "Quận 4", "Quận Tân Bình", "Quận Gò Vấp"};

    private String arrbadinh[]={"Cống Vị", "Điện Biên", "Đội Cấn", "Giảng Võ", "Kim Mã", "Liễu Giai","Ngọc Hà", "Ngọc Khánh", "Nguyễn Trung Trực", "Phúc Xá"};
    private String arrbactuliem[]={"Đức Thắng", "Đông Ngạc", "Thụy Phương", "Liên Mạc", "Thượng Cát", "Tây Tựu", "Minh Khai", "Phú Diễn", "Phúc Diễn", "Xuân Đỉnh"};
    private String arrcaugiay[]={"Dịch Vọng", "Dịch Vọng Hậu", "Mai Dịch", "Nghĩa Đô", "Nghĩa Tân", "Quan Hoa", "Trung Hòa", "Yên Hòa"};
    private String arrdongda[]={"Cát Linh", "Hàng Bột", "Khâm Thiên", "Khương Thượng", "Kim Liên", "Láng Hạ", "Láng Thượng", "Nam Đồng", "Nguyễn Trãi", "Ô Chợ Dừa", "Phương Liên"};
    private String arrhoangmai[]={"Mai Hùng", "Quỳnh Dị"," Quỳnh Phương", "Quỳnh Thiện", "Quỳnh Xuân", "Quỳnh Liên", "Quỳnh Lộc"};
    private String arrlongbien[]={"Bồ Đề", "Cự Khối", "Đức Giang", "Gia Thụy", "Giang Biên", "Long Biên", "Ngọc Lâm", "Ngọc Thụy", "Phúc Đồng"};
    private String arrnamtuliem[]={"Cầu Diễn", "Mỹ Đình 1", "Mỹ Đình 2", "Phú Đô", "Mễ Trì", "Trung Văn", "Đại Mỗ", "Tây Mỗ"};

    private String arrbuonmathuot[]={"Ea Tam", "Khánh Xuân", "Tân An", "Tân Hòa", "Tân Lập", "Tân Lợi", "Tân Thành", "Tân Tiến", "Thắng Lợi", "Thành Công"};
    private String arrbuondon[]={"Krông Na", "Ea Huar", "Ea Wer"};
    private String arrcumga[]={"Cư Dliê M'nông", "Cư M'gar", "Cư Suê", "Cuôr Đăng", "Ea Drơng", "Ea H'đing", "Ea Kiết", "Ea Kpam"};
    private String arreahleo[]={"Ea Nam", "Ea Khal", "Ea Hiao"};
    private String arreakar[]={"Ea Đar", "Ea Pal", "Ea Ô", "Cư Ni"};

    private String arrquan1[]={"Phường 1", "Phường 2", "Phường 3", "Phường 4", "Phường 5", "Phường 6", "Phường 7"};
    private String arrquan4[]={"Phường 1", "Phường 2", "Phường 3", "Phường 4", "Phường 6", "Phường 9", "Phường 10"};
    private String arrtanbinh[]={"Phường 1", "Phường 2", "Phường 3", "Phường 4", "Phường 5", "Phường 6", "Phường 7"};
    private String arrvogap[]={"Phường 1", "Phường 2", "Phường 3", "Phường 4", "Phường 5", "Phường 6", "Phường 7"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_phong_tro);

        //ánh xạ
        anhXa();


        //ẩn toolBar
        getSupportActionBar().hide();

        //set ảnh vào rcv
        setImgRcv();

        //các event click
        eventClick();

        //hàm set data update
        setDataUpdate();

        //set địa chỉ
        setAutoComplete();

        //onclickToggleButton
        onclickToggleButton();

        //set sdt
        setSdt();


    }

    //hàm set data sửa
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
                if (loaiPhong.equals("Phòng cho thuê")) {
                    vitriloaiphong = 0;
                } else if (loaiPhong.equals("Nhà nguyên căn")) {
                    vitriloaiphong = 1;
                } else  if (loaiPhong.equals("Ký túc xá / Homestay")){
                    vitriloaiphong = 2;
                } else  if (loaiPhong.equals("Căn hộ")){
                    vitriloaiphong = 3;
                }

                arrayAdapter = new ArrayAdapter<String>(SuaPhongTro.this, android.R.layout.simple_list_item_1, arrloaiphong);
                edtLoaiPhong.setText(loaiPhong);
                edtLoaiPhong.setAdapter(arrayAdapter);
                loaiPhong = String.valueOf(arrayAdapter.getItem(vitriloaiphong));

                //tinh thanh
                String tinhThanh = dataSnapshot.child("tinhThanh").getValue(String.class);
                int vitritinhthanh = 0;
                if (tinhThanh.equals("Hà Nội")) {
                    vitritinhthanh = 0;
                } else if (tinhThanh.equals("Đăk Lăk")) {
                    vitritinhthanh = 1;
                } else  if (tinhThanh.equals("Hồ Chí Minh")){
                    vitritinhthanh = 2;
                }

                arrayAdapter = new ArrayAdapter<String>(SuaPhongTro.this, android.R.layout.simple_list_item_1, arrTinhThanh);
                edtTinhThanh.setText(tinhThanh);
                edtTinhThanh.setAdapter(arrayAdapter);
                tinhthanh = String.valueOf(arrayAdapter.getItem(vitriloaiphong));

                //quan huyen
                String quanHuyen = dataSnapshot.child("quanHuyen").getValue(String.class);
                if (tinhThanh.equals("Hà Nội")) {
                    int vitrihanoi = 0;
                    if (quanHuyen.equals("Ba Đình")) {
                        vitrihanoi = 0;
                    } else if (quanHuyen.equals("Bắc Từ Liêm")) {
                        vitrihanoi = 1;
                    } else  if (quanHuyen.equals("Cầu Giấy")){
                        vitrihanoi = 2;
                    } else  if (quanHuyen.equals("Đống Đa")){
                        vitrihanoi = 3;
                    } else  if (quanHuyen.equals("Hoàng Mai")){
                        vitrihanoi = 4;
                    } else  if (quanHuyen.equals("Long Biên")){
                        vitrihanoi = 5;
                    } else  if (quanHuyen.equals("Nam Từ Liêm")){
                        vitrihanoi = 6;
                    }

                    arrayAdapter = new ArrayAdapter<String>(SuaPhongTro.this, android.R.layout.simple_list_item_1, arrquanhuyenhanoi);
                    edtQuanHuyen.setText(quanHuyen);
                    edtQuanHuyen.setAdapter(arrayAdapter);
                    quanhuyen = String.valueOf(arrayAdapter.getItem(vitrihanoi));
                } else if (tinhThanh.equals("Đăk Lăk")) {
                    int vitridaklak = 0;
                    if (quanHuyen.equals("Buôn Ma Thuột")) {
                        vitridaklak = 0;
                    } else if (quanHuyen.equals("Buôn Đôn")) {
                        vitridaklak = 1;
                    } else  if (quanHuyen.equals("Cư M'gar")){
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
                } else if (tinhThanh.equals("Hồ Chí Minh")) {
                    int vitrihanoi = 0;
                    if (quanHuyen.equals("Ba Đình")) {
                        vitrihanoi = 0;
                    } else if (quanHuyen.equals("Quận 1")) {
                        vitrihanoi = 1;
                    } else  if (quanHuyen.equals("Quận 4")){
                        vitrihanoi = 2;
                    } else  if (quanHuyen.equals("Quận Tân Bình")){
                        vitrihanoi = 3;
                    } else  if (quanHuyen.equals("Quận Gò Vấp")){
                        vitrihanoi = 4;
                    }

                    arrayAdapter = new ArrayAdapter<String>(SuaPhongTro.this, android.R.layout.simple_list_item_1, arrquanhuyenhochiminh);
                    edtQuanHuyen.setText(quanHuyen);
                    edtQuanHuyen.setAdapter(arrayAdapter);
                    quanhuyen = String.valueOf(arrayAdapter.getItem(vitrihanoi));
                }

                //phuong xa
                String phuongXa = dataSnapshot.child("phuongXa").getValue(String.class);
                if (quanHuyen.equals("Ba Đình")) {
                   int vitri = 0;
                    if (phuongXa.equals("Cống vịnh")) {
                         vitri = 0;
                    } else  if (phuongXa.equals("Điện Biên")){
                         vitri = 1;
                    } else  if (phuongXa.equals("Đội Vấn")){
                         vitri = 2;
                    } else  if (phuongXa.equals("Giảng Võ")){
                         vitri = 3;
                    } else  if (phuongXa.equals("Kim Mã")){
                         vitri = 4;
                    } else  if (phuongXa.equals("Liễu Giai")){
                         vitri = 5;
                    } else if (phuongXa.equals("Ngọc Hà")) {
                         vitri = 6;
                    } else if (phuongXa.equals("Ngọc Khánh")) {
                         vitri = 7;
                    } else  if (phuongXa.equals("Nguyễn Trung Trực")){
                         vitri = 8;
                    } else  if (phuongXa.equals("Phúc Xá")){
                         vitri = 9;
                    }
                    arrayAdapter = new ArrayAdapter<String>(SuaPhongTro.this, android.R.layout.simple_list_item_1, arrbadinh);
                    edtPhuongXa.setText(phuongXa);
                    edtPhuongXa.setAdapter(arrayAdapter);
                    phuongxa = String.valueOf(arrayAdapter.getItem(vitri));

                } else if (quanHuyen.equals("Bắc Từ Liêm")) {
                    int vitri = 0;
                    if (phuongXa.equals("Đức Thắng")) {
                        vitri = 0;
                    } else  if (phuongXa.equals("Đông Ngạc")){
                        vitri = 1;
                    } else  if (phuongXa.equals("Thụy Phương")){
                        vitri = 2;
                    } else  if (phuongXa.equals("Liên Mạc")){
                        vitri = 3;
                    } else  if (phuongXa.equals("Thượng Cát")){
                        vitri = 4;
                    } else  if (phuongXa.equals("Tây Lựu")){
                        vitri = 5;
                    } else if (phuongXa.equals("Minh Khai")) {
                        vitri = 6;
                    } else if (phuongXa.equals("Phú Diễn")) {
                        vitri = 7;
                    } else  if (phuongXa.equals("Phúc Diễn")){
                        vitri = 8;
                    } else  if (phuongXa.equals("Xuân Đỉnh")){
                        vitri = 9;
                    }
                    arrayAdapter = new ArrayAdapter<String>(SuaPhongTro.this, android.R.layout.simple_list_item_1, arrbactuliem);
                    edtPhuongXa.setText(phuongXa);
                    edtPhuongXa.setAdapter(arrayAdapter);
                    phuongxa = String.valueOf(arrayAdapter.getItem(vitri));
                } else  if (quanHuyen.equals("Cầu Giấy")){
                    int vitri = 0;
                    if (phuongXa.equals("Dịch Vọng")) {
                        vitri = 0;
                    } else  if (phuongXa.equals("Dịch Vọng Hậu")){
                        vitri = 1;
                    } else  if (phuongXa.equals("Mai Dịch")){
                        vitri = 2;
                    } else  if (phuongXa.equals("Nghĩa Đô")){
                        vitri = 3;
                    } else  if (phuongXa.equals("Nghĩa Tân")){
                        vitri = 4;
                    } else  if (phuongXa.equals("Quan Hoa")){
                        vitri = 5;
                    } else if (phuongXa.equals("Trung Hòa")) {
                        vitri = 6;
                    } else if (phuongXa.equals("Yên Hòa")) {
                        vitri = 7;
                    }
                    arrayAdapter = new ArrayAdapter<String>(SuaPhongTro.this, android.R.layout.simple_list_item_1, arrcaugiay);
                    edtPhuongXa.setText(phuongXa);
                    edtPhuongXa.setAdapter(arrayAdapter);
                    phuongxa = String.valueOf(arrayAdapter.getItem(vitri));
                } else  if (quanHuyen.equals("Đống Đa")){
                    int vitri = 0;
                    if (phuongXa.equals("Cát Linh")) {
                        vitri = 0;
                    } else  if (phuongXa.equals("Hàng Bột")){
                        vitri = 1;
                    } else  if (phuongXa.equals("Khâm Thiên")){
                        vitri = 2;
                    } else  if (phuongXa.equals("Khương Thượng")){
                        vitri = 3;
                    } else  if (phuongXa.equals("Kim Liên")){
                        vitri = 4;
                    } else  if (phuongXa.equals("Láng Hạ")){
                        vitri = 5;
                    } else if (phuongXa.equals("Láng Thượng")) {
                        vitri = 6;
                    } else if (phuongXa.equals("Nam Đồng")) {
                        vitri = 7;
                    } else  if (phuongXa.equals("Nguyễn Trãi")){
                        vitri = 8;
                    } else  if (phuongXa.equals("Ô Chợ Dừa")){
                        vitri = 9;
                    } else  if (phuongXa.equals("Phương Liên")){
                        vitri = 10;
                    }
                    arrayAdapter = new ArrayAdapter<String>(SuaPhongTro.this, android.R.layout.simple_list_item_1, arrdongda);
                    edtPhuongXa.setText(phuongXa);
                    edtPhuongXa.setAdapter(arrayAdapter);
                    phuongxa = String.valueOf(arrayAdapter.getItem(vitri));
                } else  if (quanHuyen.equals("Hoàng Mai")){
                    int vitri = 0;
                    if (phuongXa.equals("Mai Hùng")) {
                        vitri = 0;
                    } else  if (phuongXa.equals("Quỳnh Dị")){
                        vitri = 1;
                    } else  if (phuongXa.equals("Quỳnh Phương")){
                        vitri = 2;
                    } else  if (phuongXa.equals("Quỳnh Thiện")){
                        vitri = 3;
                    } else  if (phuongXa.equals("Quỳnh Xuân")){
                        vitri = 4;
                    } else  if (phuongXa.equals("Quỳnh Liên")){
                        vitri = 5;
                    } else if (phuongXa.equals("Quỳnh Lộc")) {
                        vitri = 6;
                    }
                    arrayAdapter = new ArrayAdapter<String>(SuaPhongTro.this, android.R.layout.simple_list_item_1, arrhoangmai);
                    edtPhuongXa.setText(phuongXa);
                    edtPhuongXa.setAdapter(arrayAdapter);
                    phuongxa = String.valueOf(arrayAdapter.getItem(vitri));
                } else  if (quanHuyen.equals("Long Biên")){
                    int vitri = 0;
                    if (phuongXa.equals("Bồ Đề")) {
                        vitri = 0;
                    } else  if (phuongXa.equals("Cự Khối")){
                        vitri = 1;
                    } else  if (phuongXa.equals("Đức Giang")){
                        vitri = 2;
                    } else  if (phuongXa.equals("Gia Thụy")){
                        vitri = 3;
                    } else  if (phuongXa.equals("Giang Biên")){
                        vitri = 4;
                    } else  if (phuongXa.equals("Long Biên")){
                        vitri = 5;
                    } else if (phuongXa.equals("Ngọc Lâm")) {
                        vitri = 6;
                    } else if (phuongXa.equals("Ngọc Thụy")) {
                        vitri = 7;
                    } else  if (phuongXa.equals("Phúc Đồng")){
                        vitri = 8;
                    }
                    arrayAdapter = new ArrayAdapter<String>(SuaPhongTro.this, android.R.layout.simple_list_item_1, arrlongbien);
                    edtPhuongXa.setText(phuongXa);
                    edtPhuongXa.setAdapter(arrayAdapter);
                    phuongxa = String.valueOf(arrayAdapter.getItem(vitri));
                } else  if (quanHuyen.equals("Nam Từ Liêm")){
                    int vitri = 0;
                    if (phuongXa.equals("Cầu Diễn")) {
                        vitri = 0;
                    } else  if (phuongXa.equals("Mỹ Đình 1")){
                        vitri = 1;
                    } else  if (phuongXa.equals("Mỹ Đình 2")){
                        vitri = 2;
                    } else  if (phuongXa.equals("Phú Đô")){
                        vitri = 3;
                    } else  if (phuongXa.equals("Mễ Trì")){
                        vitri = 4;
                    } else  if (phuongXa.equals("Trung Văn")){
                        vitri = 5;
                    } else if (phuongXa.equals("Đại Mỗ")) {
                        vitri = 6;
                    } else if (phuongXa.equals("Tây Mỗ")) {
                        vitri = 7;
                    }
                    arrayAdapter = new ArrayAdapter<String>(SuaPhongTro.this, android.R.layout.simple_list_item_1, arrnamtuliem);
                    edtPhuongXa.setText(phuongXa);
                    edtPhuongXa.setAdapter(arrayAdapter);
                    phuongxa = String.valueOf(arrayAdapter.getItem(vitri));
                } else if (quanHuyen.equals("Buôn Ma Thuột")) {
                    int vitri = 0;
                    if (phuongXa.equals("Ea Tam")) {
                        vitri = 0;
                    } else  if (phuongXa.equals("Khánh Xuân")){
                        vitri = 1;
                    } else  if (phuongXa.equals("Tân An")){
                        vitri = 2;
                    } else  if (phuongXa.equals("Tân Hòa")){
                        vitri = 3;
                    } else  if (phuongXa.equals("Tân Lập")){
                        vitri = 4;
                    } else  if (phuongXa.equals("Tân Lợi")){
                        vitri = 5;
                    } else if (phuongXa.equals("Tân Thành")) {
                        vitri = 6;
                    } else if (phuongXa.equals("Tân Tiến")) {
                        vitri = 7;
                    } else  if (phuongXa.equals("Thắng Lợi")){
                        vitri = 8;
                    } else  if (phuongXa.equals("Thành Công")){
                        vitri = 9;
                    }
                    arrayAdapter = new ArrayAdapter<String>(SuaPhongTro.this, android.R.layout.simple_list_item_1, arrbuonmathuot);
                    edtPhuongXa.setText(phuongXa);
                    edtPhuongXa.setAdapter(arrayAdapter);
                    phuongxa = String.valueOf(arrayAdapter.getItem(vitri));
                } else if (quanHuyen.equals("Buôn Đôn")) {
                    int vitri = 0;
                    if (phuongXa.equals("Krông Na")) {
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
                } else  if (quanHuyen.equals("Cư M'gar")){
                    int vitri = 0;
                    if (phuongXa.equals("Cư Dliê M'nông")) {
                        vitri = 0;
                    } else  if (phuongXa.equals("Cư M'gar")){
                        vitri = 1;
                    } else  if (phuongXa.equals("Cư Suê")){
                        vitri = 2;
                    } else  if (phuongXa.equals("Cuôr Đăng")){
                        vitri = 3;
                    } else  if (phuongXa.equals("Ea Drơng")){
                        vitri = 4;
                    } else  if (phuongXa.equals("Ea H'đing")){
                        vitri = 5;
                    } else if (phuongXa.equals("Ea Kiết")) {
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
                    if (phuongXa.equals("Ea Đar")) {
                        vitri = 0;
                    } else  if (phuongXa.equals("Ea Pal")){
                        vitri = 1;
                    } else  if (phuongXa.equals("Ea Ô")){
                        vitri = 2;
                    } else  if (phuongXa.equals("Cư Ni")){
                        vitri = 3;
                    }
                    arrayAdapter = new ArrayAdapter<String>(SuaPhongTro.this, android.R.layout.simple_list_item_1, arreakar);
                    edtPhuongXa.setText(phuongXa);
                    edtPhuongXa.setAdapter(arrayAdapter);
                    phuongxa = String.valueOf(arrayAdapter.getItem(vitri));
                } else  if (quanHuyen.equals("Quận 1")) {
                    int vitri = 0;
                    if (phuongXa.equals("Phường 1")) {
                        vitri = 0;
                    } else  if (phuongXa.equals("Phường 2")){
                        vitri = 1;
                    } else  if (phuongXa.equals("Phường 3")){
                        vitri = 2;
                    } else  if (phuongXa.equals("Phường 4")){
                        vitri = 3;
                    } else  if (phuongXa.equals("Phường 5")){
                        vitri = 4;
                    } else  if (phuongXa.equals("Phường 6")){
                        vitri = 5;
                    } else if (phuongXa.equals("Phường 7")) {
                        vitri = 6;
                    }
                    arrayAdapter = new ArrayAdapter<String>(SuaPhongTro.this, android.R.layout.simple_list_item_1, arrquan1);
                    edtPhuongXa.setText(phuongXa);
                    edtPhuongXa.setAdapter(arrayAdapter);
                    phuongxa = String.valueOf(arrayAdapter.getItem(vitri));
                } else  if (quanHuyen.equals("Quận 4")){
                    int vitri = 0;
                    if (phuongXa.equals("Phường 1")) {
                        vitri = 0;
                    } else  if (phuongXa.equals("Phường 2")){
                        vitri = 1;
                    } else  if (phuongXa.equals("Phường 3")){
                        vitri = 2;
                    } else  if (phuongXa.equals("Phường 4")){
                        vitri = 3;
                    } else  if (phuongXa.equals("Phường 6")){
                        vitri = 4;
                    } else  if (phuongXa.equals("Phường 9")){
                        vitri = 5;
                    } else if (phuongXa.equals("Phường 10")) {
                        vitri = 6;
                    }
                    arrayAdapter = new ArrayAdapter<String>(SuaPhongTro.this, android.R.layout.simple_list_item_1, arrquan4);
                    edtPhuongXa.setText(phuongXa);
                    edtPhuongXa.setAdapter(arrayAdapter);
                    phuongxa = String.valueOf(arrayAdapter.getItem(vitri));
                } else  if (quanHuyen.equals("Quận Tân Bình")){
                    int vitri = 0;
                    if (phuongXa.equals("Phường 1")) {
                        vitri = 0;
                    } else  if (phuongXa.equals("Phường 2")){
                        vitri = 1;
                    } else  if (phuongXa.equals("Phường 3")){
                        vitri = 2;
                    } else  if (phuongXa.equals("Phường 4")){
                        vitri = 3;
                    } else  if (phuongXa.equals("Phường 5")){
                        vitri = 4;
                    } else  if (phuongXa.equals("Phường 6")){
                        vitri = 5;
                    } else if (phuongXa.equals("Phường 7")) {
                        vitri = 6;
                    }
                    arrayAdapter = new ArrayAdapter<String>(SuaPhongTro.this, android.R.layout.simple_list_item_1, arrtanbinh);
                    edtPhuongXa.setText(phuongXa);
                    edtPhuongXa.setAdapter(arrayAdapter);
                    phuongxa = String.valueOf(arrayAdapter.getItem(vitri));
                } else  if (quanHuyen.equals("Quận Gò Vấp")){
                    int vitri = 0;
                    if (phuongXa.equals("Phường 1")) {
                        vitri = 0;
                    } else  if (phuongXa.equals("Phường 2")){
                        vitri = 1;
                    } else  if (phuongXa.equals("Phường 3")){
                        vitri = 2;
                    } else  if (phuongXa.equals("Phường 4")){
                        vitri = 3;
                    } else  if (phuongXa.equals("Phường 5")){
                        vitri = 4;
                    } else  if (phuongXa.equals("Phường 6")){
                        vitri = 5;
                    } else if (phuongXa.equals("Phường 7")) {
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
                if (tinhTrang.equals("Còn Phòng")) {
                    vitritinhtrang = 0;
                } else if (tinhTrang.equals("Hết Phòng")) {
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



    //hàm set sđt
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

    //hàm set dữ liệu cho AutoCompleteText
    public void setAutoComplete() {

        //loại phòng
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrloaiphong);
        edtLoaiPhong.setAdapter(arrayAdapter);
        edtLoaiPhong.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                loaiPhong = parent.getItemAtPosition(position).toString();
            }
        });

        //tình trạng phòng
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrTinhTrang);
        edtTinhTrangPhong.setAdapter(arrayAdapter);
        edtTinhTrangPhong.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tinhtrang = parent.getItemAtPosition(position).toString();
            }
        });

        //tỉnh thành
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

                        //quận huyện
                        if (tinhthanh.equals("Hà Nội")) {
                            arrayAdapter = new ArrayAdapter<String>(SuaPhongTro.this, android.R.layout.simple_list_item_1, arrquanhuyenhanoi);
                            edtQuanHuyen.setAdapter(arrayAdapter);
                            edtQuanHuyen.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    quanhuyen = parent.getItemAtPosition(position).toString();

                                    //phường xã
                                    if (quanhuyen.equals("Ba Đình")) {
                                        arrayAdapter = new ArrayAdapter<String>(SuaPhongTro.this, android.R.layout.simple_list_item_1, arrbadinh);
                                        edtPhuongXa.setAdapter(arrayAdapter);
                                        edtPhuongXa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                phuongxa = parent.getItemAtPosition(position).toString();
                                            }
                                        });
                                    } else   if (quanhuyen.equals("Bắc Từ Liêm")) {
                                        arrayAdapter = new ArrayAdapter<String>(SuaPhongTro.this, android.R.layout.simple_list_item_1, arrbactuliem);
                                        edtPhuongXa.setAdapter(arrayAdapter);
                                        edtPhuongXa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                phuongxa = parent.getItemAtPosition(position).toString();
                                            }
                                        });
                                    } else   if (quanhuyen.equals("Cầu Giấy")) {
                                        arrayAdapter = new ArrayAdapter<String>(SuaPhongTro.this, android.R.layout.simple_list_item_1, arrcaugiay);
                                        edtPhuongXa.setAdapter(arrayAdapter);
                                        edtPhuongXa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                phuongxa = parent.getItemAtPosition(position).toString();
                                            }
                                        });
                                    } else   if (quanhuyen.equals("Đống Đa")) {
                                        arrayAdapter = new ArrayAdapter<String>(SuaPhongTro.this, android.R.layout.simple_list_item_1, arrdongda);
                                        edtPhuongXa.setAdapter(arrayAdapter);
                                        edtPhuongXa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                phuongxa = parent.getItemAtPosition(position).toString();
                                            }
                                        });
                                    } else   if (quanhuyen.equals("Hoàng Mai")) {
                                        arrayAdapter = new ArrayAdapter<String>(SuaPhongTro.this, android.R.layout.simple_list_item_1, arrhoangmai);
                                        edtPhuongXa.setAdapter(arrayAdapter);
                                        edtPhuongXa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                phuongxa = parent.getItemAtPosition(position).toString();
                                            }
                                        });
                                    } else   if (quanhuyen.equals("Long Biên")) {
                                        arrayAdapter = new ArrayAdapter<String>(SuaPhongTro.this, android.R.layout.simple_list_item_1, arrlongbien);
                                        edtPhuongXa.setAdapter(arrayAdapter);
                                        edtPhuongXa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                phuongxa = parent.getItemAtPosition(position).toString();
                                            }
                                        });
                                    } else   if (quanhuyen.equals("Nam Từ Liêm")) {
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
                        } else   if (tinhthanh.equals("Đăk Lăk")) {
                            arrayAdapter = new ArrayAdapter<String>(SuaPhongTro.this, android.R.layout.simple_list_item_1, arrquanhuyendaklak);
                            edtQuanHuyen.setAdapter(arrayAdapter);
                            edtQuanHuyen.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    quanhuyen = parent.getItemAtPosition(position).toString();

                                    //phường xã
                                    if (quanhuyen.equals("Buôn Ma Thuột")) {
                                        arrayAdapter = new ArrayAdapter<String>(SuaPhongTro.this, android.R.layout.simple_list_item_1, arrbuonmathuot);
                                        edtPhuongXa.setAdapter(arrayAdapter);
                                        edtPhuongXa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                phuongxa = parent.getItemAtPosition(position).toString();
                                            }
                                        });
                                    } else   if (quanhuyen.equals("Buôn Đôn")) {
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
                                    } else   if (quanhuyen.equals("Cư M'gar")) {
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
                        } else   if (tinhthanh.equals("Hồ Chí Minh")) {
                            arrayAdapter = new ArrayAdapter<String>(SuaPhongTro.this, android.R.layout.simple_list_item_1, arrquanhuyenhochiminh);
                            edtQuanHuyen.setAdapter(arrayAdapter);
                            edtQuanHuyen.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    quanhuyen = parent.getItemAtPosition(position).toString();

                                    //phường xã
                                    if (quanhuyen.equals("Quận 1")) {
                                        arrayAdapter = new ArrayAdapter<String>(SuaPhongTro.this, android.R.layout.simple_list_item_1, arrquan1);
                                        edtPhuongXa.setAdapter(arrayAdapter);
                                        edtPhuongXa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                phuongxa = parent.getItemAtPosition(position).toString();
                                            }
                                        });
                                    } else   if (quanhuyen.equals("Quận 4")) {
                                        arrayAdapter = new ArrayAdapter<String>(SuaPhongTro.this, android.R.layout.simple_list_item_1, arrquan4);
                                        edtPhuongXa.setAdapter(arrayAdapter);
                                        edtPhuongXa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                phuongxa = parent.getItemAtPosition(position).toString();
                                            }
                                        });
                                    } else   if (quanhuyen.equals("Quận Tân Bình")) {
                                        arrayAdapter = new ArrayAdapter<String>(SuaPhongTro.this, android.R.layout.simple_list_item_1, arrtanbinh);
                                        edtPhuongXa.setAdapter(arrayAdapter);
                                        edtPhuongXa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                phuongxa = parent.getItemAtPosition(position).toString();
                                            }
                                        });
                                    } else   if (quanhuyen.equals("Quận Gò Gấp")) {
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
                            .setContentText("Không thể chọn quận huyện khi chưa chọn tỉnh thành!")
                            .show();
                    return;
                }

                edtPhuongXa.setText("");
                //phường xã
                if (quanhuyen.equals("Ba Đình")) {
                    arrayAdapter = new ArrayAdapter<String>(SuaPhongTro.this, android.R.layout.simple_list_item_1, arrbadinh);
                    edtPhuongXa.setAdapter(arrayAdapter);
                    edtPhuongXa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            phuongxa = parent.getItemAtPosition(position).toString();
                        }
                    });
                } else   if (quanhuyen.equals("Bắc Từ Liêm")) {
                    arrayAdapter = new ArrayAdapter<String>(SuaPhongTro.this, android.R.layout.simple_list_item_1, arrbactuliem);
                    edtPhuongXa.setAdapter(arrayAdapter);
                    edtPhuongXa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            phuongxa = parent.getItemAtPosition(position).toString();
                        }
                    });
                } else   if (quanhuyen.equals("Cầu Giấy")) {
                    arrayAdapter = new ArrayAdapter<String>(SuaPhongTro.this, android.R.layout.simple_list_item_1, arrcaugiay);
                    edtPhuongXa.setAdapter(arrayAdapter);
                    edtPhuongXa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            phuongxa = parent.getItemAtPosition(position).toString();
                        }
                    });
                } else   if (quanhuyen.equals("Đống Đa")) {
                    arrayAdapter = new ArrayAdapter<String>(SuaPhongTro.this, android.R.layout.simple_list_item_1, arrdongda);
                    edtPhuongXa.setAdapter(arrayAdapter);
                    edtPhuongXa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            phuongxa = parent.getItemAtPosition(position).toString();
                        }
                    });
                } else   if (quanhuyen.equals("Hoàng Mai")) {
                    arrayAdapter = new ArrayAdapter<String>(SuaPhongTro.this, android.R.layout.simple_list_item_1, arrhoangmai);
                    edtPhuongXa.setAdapter(arrayAdapter);
                    edtPhuongXa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            phuongxa = parent.getItemAtPosition(position).toString();
                        }
                    });
                } else   if (quanhuyen.equals("Long Biên")) {
                    arrayAdapter = new ArrayAdapter<String>(SuaPhongTro.this, android.R.layout.simple_list_item_1, arrlongbien);
                    edtPhuongXa.setAdapter(arrayAdapter);
                    edtPhuongXa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            phuongxa = parent.getItemAtPosition(position).toString();
                        }
                    });
                } else   if (quanhuyen.equals("Nam Từ Liêm")) {
                    arrayAdapter = new ArrayAdapter<String>(SuaPhongTro.this, android.R.layout.simple_list_item_1, arrnamtuliem);
                    edtPhuongXa.setAdapter(arrayAdapter);
                    edtPhuongXa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            phuongxa = parent.getItemAtPosition(position).toString();
                        }
                    });
                }

                //phường xã
                if (quanhuyen.equals("Buôn Ma Thuột")) {
                    arrayAdapter = new ArrayAdapter<String>(SuaPhongTro.this, android.R.layout.simple_list_item_1, arrbuonmathuot);
                    edtPhuongXa.setAdapter(arrayAdapter);
                    edtPhuongXa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            phuongxa = parent.getItemAtPosition(position).toString();
                        }
                    });
                } else   if (quanhuyen.equals("Buôn Đôn")) {
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
                } else   if (quanhuyen.equals("Cư M'gar")) {
                    arrayAdapter = new ArrayAdapter<String>(SuaPhongTro.this, android.R.layout.simple_list_item_1, arrcumga);
                    edtPhuongXa.setAdapter(arrayAdapter);
                    edtPhuongXa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            phuongxa = parent.getItemAtPosition(position).toString();
                        }
                    });
                }

                //phường xã
                if (quanhuyen.equals("Quận 1")) {
                    arrayAdapter = new ArrayAdapter<String>(SuaPhongTro.this, android.R.layout.simple_list_item_1, arrquan1);
                    edtPhuongXa.setAdapter(arrayAdapter);
                    edtPhuongXa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            phuongxa = parent.getItemAtPosition(position).toString();
                        }
                    });
                } else   if (quanhuyen.equals("Quận 4")) {
                    arrayAdapter = new ArrayAdapter<String>(SuaPhongTro.this, android.R.layout.simple_list_item_1, arrquan4);
                    edtPhuongXa.setAdapter(arrayAdapter);
                    edtPhuongXa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            phuongxa = parent.getItemAtPosition(position).toString();
                        }
                    });
                } else   if (quanhuyen.equals("Quận Tân Bình")) {
                    arrayAdapter = new ArrayAdapter<String>(SuaPhongTro.this, android.R.layout.simple_list_item_1, arrtanbinh);
                    edtPhuongXa.setAdapter(arrayAdapter);
                    edtPhuongXa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            phuongxa = parent.getItemAtPosition(position).toString();
                        }
                    });
                } else   if (quanhuyen.equals("Quận Gò Gấp")) {
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
                            .setContentText("Không thể chọn phường xã khi chưa chọn tỉnh thành và quận huyện!")
                            .show();
                    return;
                } else    if (quanhuyen.equals("")) {
                    new SweetAlertDialog(SuaPhongTro.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Oops...")
                            .setContentText("Không thể chọn phường xã khi chưa chọn quận huyện!")
                            .show();
                    return;
                }
            }
        });

    }

    // hàm các event click
    public void eventClick() {
        // click chọn ảnh
        btnImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pemission();
            }
        });

        //click xóa ảnh
        btnClean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photoAdapter.setDataPhoto(null);
                btnImg.setVisibility(View.VISIBLE);
                btnClean.setVisibility(View.GONE);
            }
        });

        //click hủy

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                new SweetAlertDialog(SuaPhongTro.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Bạn muốn thoát?")
                        .setContentText("Lưu ý: Sau khi bạn thoát mọi dữ liệu bạn vừa nhập sẽ bị xóa! Bạn chắc chắn chứ?")
                        .setCancelText("Hủy")
                        .setConfirmText("Đồng ý")
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

        //click thêm phòng trọ
        btnSuaPhongTro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSuaPhongTro();
            }
        });

        //click nói
        btnNoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSpeechToText();
            }
        });

    }

    //hàm lấy ảnh
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
                .setTitle("Chọn ảnh nhà trọ")
                .setCompleteButtonText("Xong")
                .setEmptySelectionText("Chưa chọn ảnh nào")
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

    //hàm set ảnh vào rcv
    public void setImgRcv() {
        photoAdapter = new PhotoAdapter(SuaPhongTro.this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        rcvPhoto.setLayoutManager(gridLayoutManager);
        rcvPhoto.setAdapter(photoAdapter);
    }

    //hàm thêm nhà trọ
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
                    .setContentText("Bạn chưa thêm hình mà!")
                    .show();
            checkError = false;
        } else if (photoAdapter.getItemCount() < 4) {
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Oops...")
                    .setContentText("Số hình tối thiểu là 4 tấm!")
                    .show();
            checkError = false;
        }

        if (tenPhong.isEmpty()) {
            edtTenPhong.setError("Không để trống!");
            checkError = false;
        }

        if (sucChua.isEmpty()) {
            edtSoNguoi.setError("Không để trống!");
            checkError = false;
        }

        if (dienTinh.isEmpty()) {
            edtDienTich.setError("Không để trống!");
            checkError = false;
        }

        if (loaiPhong.isEmpty()) {
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Oops...")
                    .setContentText("Chưa chọn loại phòng!")
                    .show();
            checkError = false;
        }

        if (giaPhong.isEmpty()) {
            edtGiaPhong.setError("Không để trống!");
            checkError = false;
        }

        if (tienDien.isEmpty()) {
            edtTienDien.setError("Không để trống!");
            checkError = false;
        }

        if (tienNuoc.isEmpty()) {
            edtTienNuoc.setError("Không để trống!");
            checkError = false;
        }

        if (tinhThanh.isEmpty()) {
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Oops...")
                    .setContentText("Vui lòng chọn tỉnh thành!")
                    .show();
            checkError = false;
        }

        if (quanHuyen.isEmpty()) {
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Oops...")
                    .setContentText("Vui lòng chọn quận huyện!")
                    .show();
            checkError = false;
        }

        if (phuongXa.isEmpty()) {
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Oops...")
                    .setContentText("Vui lòng chọn phường xã!")
                    .show();
            checkError = false;
        }

        if (soNha.isEmpty()) {
            edtTenDuong.setError("Không để trống!");
            checkError = false;
        }

        if (tinhTrang.isEmpty()) {
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Oops...")
                    .setContentText("Vui lòng chọn tình trạng phòng!")
                    .show();
            checkError = false;
        }

        if (noiDung.length() < 50) {
            edtNoiDung.setError("Phải mô tả phòng ít nhất 50 ký tự");
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


    //hàm event Toggle Buttons
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
                    tienIch1.setTen("WC Riêng");
                    dsTienIch.add(tienIch1);
                } else {
                    for (int i = 0; i <= dsTienIch.size(); i++){
                        if (tienIch1.getTen().equals("WC Riêng")) {
                            dsTienIch.remove(i);
                            return;
                        }
                    }
                }
            }
        });

        //máy lạnh
        btgMayLanh.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tienIch2.setId(""+ dsTienIch.size());
                    tienIch2.setTen("Máy Lạnh");
                    dsTienIch.add(tienIch2);
                } else {
                    for (int i = 0; i <= dsTienIch.size(); i++){
                        if (tienIch2.getTen().equals("Máy Lạnh")) {
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

        //máy nước nóng
        btgMayNuocNong.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tienIch4.setId(""+ dsTienIch.size());
                    tienIch4.setTen("Máy Nước Nóng");
                    dsTienIch.add(tienIch4);
                } else {
                    for (int i = 0; i <= dsTienIch.size(); i++){
                        if (tienIch4.getTen().equals("Máy Nước Nóng")) {
                            dsTienIch.remove(i);
                            return;
                        }
                    }
                }
            }
        });

        //gác
        btgGac.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tienIch5.setId(""+ dsTienIch.size());
                    tienIch5.setTen("Gác Lửng");
                    dsTienIch.add(tienIch5);
                } else {
                    for (int i = 0; i <= dsTienIch.size(); i++){
                        if (tienIch5.getTen().equals("Gác Lửng")) {
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

        //máy giặt
        btgMayGiat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tienIch7.setId(""+ dsTienIch.size());
                    tienIch7.setTen("Máy Giặt");
                    dsTienIch.add(tienIch7);
                } else {
                    for (int i = 0; i <= dsTienIch.size(); i++){
                        if (tienIch7.getTen().equals("Máy Giặt")) {
                            dsTienIch.remove(i);
                            return;
                        }
                    }
                }
            }
        });

        //nhà bếp
        btgNhaBep.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tienIch8.setId(""+ dsTienIch.size());
                    tienIch8.setTen("Nhà Bếp");
                    dsTienIch.add(tienIch8);
                } else {
                    for (int i = 0; i <= dsTienIch.size(); i++){
                        if (tienIch8.getTen().equals("Nhà Bếp")) {
                            dsTienIch.remove(i);
                            return;
                        }
                    }
                }
            }
        });


        //tủ đồ
        btgTuDo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tienIch9.setId(""+ dsTienIch.size());
                    tienIch9.setTen("Tủ Đồ");
                    dsTienIch.add(tienIch9);
                } else {
                    for (int i = 0; i <= dsTienIch.size(); i++){
                        if (tienIch9.getTen().equals("Tủ Đồ")) {
                            dsTienIch.remove(i);
                            return;
                        }
                    }
                }
            }
        });


        //thú cưng
        btgThuCung.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tienIch10.setId(""+ dsTienIch.size());
                    tienIch10.setTen("Thú Cưng");
                    dsTienIch.add(tienIch10);
                } else {
                    for (int i = 0; i <= dsTienIch.size(); i++){
                        if (tienIch10.getTen().equals("Thú Cưng")) {
                            dsTienIch.remove(i);
                            return;
                        }
                    }
                }
            }
        });

        //tủ lạnh
        btgTuLanh.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tienIch11.setId(""+ dsTienIch.size());
                    tienIch11.setTen("Tủ Lạnh");
                    dsTienIch.add(tienIch11);
                } else {
                    for (int i = 0; i <= dsTienIch.size(); i++){
                        if (tienIch11.getTen().equals("Tủ Lạnh")) {
                            dsTienIch.remove(i);
                            return;
                        }
                    }
                }
            }
        });
    }

    //hàm onclick Speech to text
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

    //ánh xạ
    public void  anhXa() {
        //gọi đường dẫn firebase
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
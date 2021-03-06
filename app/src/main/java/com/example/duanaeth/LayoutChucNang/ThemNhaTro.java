package com.example.duanaeth.LayoutChucNang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.speech.RecognizerIntent;
import android.util.Log;
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
import com.example.duanaeth.FirebaseAdapter.PhotoAdapter;
import com.example.duanaeth.R;
import com.example.duanaeth.TrangChu;
import com.example.duanaeth.UpdateProfile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import cn.pedant.SweetAlert.SweetAlertDialog;
import gun0912.tedbottompicker.TedBottomPicker;
import gun0912.tedbottompicker.TedBottomSheetDialogFragment;

public class ThemNhaTro extends AppCompatActivity {
    private static final int MAX_LENGTH = 10 ;
    public String linkRealTime, linkStorage;
    private static final int RESULT_SPEECH = 1;
    public ImageView btnImg;
    private TextView btnClean, btnHuy, btnThemNhaTro, btnNoi;
    private EditText edtSdtNhaTro, edtNoiDung, edtTenDuong, edtTienNuoc, edtTienDien, edtGiaPhong, edtDienTich, edtSoNguoi, edtTenPhong;
    private AutoCompleteTextView edtPhuongXa, edtQuanHuyen, edtTinhThanh, edtLoaiPhong, edtTinhTrangPhong;
    private ToggleButton btginternet, btgWc, btgThuCung, btgNhaBep, btgTivi, btgMayNuocNong, btgMayLanh, btgTuLanh, btgTuDo, btgMayGiat, btgGac, btgAnNinh;
    private RecyclerView rcvPhoto;
    private PhotoAdapter photoAdapter;
    private DatabaseReference reference;
    private static final int PICK_IMG = 1;
    private ArrayList<Uri> ImageList = new ArrayList<Uri>();
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
        setContentView(R.layout.activity_them_nha_tro);

        //??nh x???
        anhXa();

        //???n toolBar
        getSupportActionBar().hide();

        //set ???nh v??o rcv
        setImgRcv();

        //c??c event click
        eventClick();

        //set ?????a ch???
        setAutoComplete();

        //onclickToggleButton
        onclickToggleButton();

        //set sdt
        setSdt();


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
        arrayAdapter = new ArrayAdapter<String>(ThemNhaTro.this, android.R.layout.simple_list_item_1, arrTinhThanh);
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
                            arrayAdapter = new ArrayAdapter<String>(ThemNhaTro.this, android.R.layout.simple_list_item_1, arrquanhuyenhanoi);
                            edtQuanHuyen.setAdapter(arrayAdapter);
                            edtQuanHuyen.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    quanhuyen = parent.getItemAtPosition(position).toString();

                                    //ph?????ng x??
                                    if (quanhuyen.equals("Ba ????nh")) {
                                        arrayAdapter = new ArrayAdapter<String>(ThemNhaTro.this, android.R.layout.simple_list_item_1, arrbadinh);
                                        edtPhuongXa.setAdapter(arrayAdapter);
                                        edtPhuongXa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                phuongxa = parent.getItemAtPosition(position).toString();
                                            }
                                        });
                                    } else   if (quanhuyen.equals("B???c T??? Li??m")) {
                                        arrayAdapter = new ArrayAdapter<String>(ThemNhaTro.this, android.R.layout.simple_list_item_1, arrbactuliem);
                                        edtPhuongXa.setAdapter(arrayAdapter);
                                        edtPhuongXa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                phuongxa = parent.getItemAtPosition(position).toString();
                                            }
                                        });
                                    } else   if (quanhuyen.equals("C???u Gi???y")) {
                                        arrayAdapter = new ArrayAdapter<String>(ThemNhaTro.this, android.R.layout.simple_list_item_1, arrcaugiay);
                                        edtPhuongXa.setAdapter(arrayAdapter);
                                        edtPhuongXa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                phuongxa = parent.getItemAtPosition(position).toString();
                                            }
                                        });
                                    } else   if (quanhuyen.equals("?????ng ??a")) {
                                        arrayAdapter = new ArrayAdapter<String>(ThemNhaTro.this, android.R.layout.simple_list_item_1, arrdongda);
                                        edtPhuongXa.setAdapter(arrayAdapter);
                                        edtPhuongXa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                phuongxa = parent.getItemAtPosition(position).toString();
                                            }
                                        });
                                    } else   if (quanhuyen.equals("Ho??ng Mai")) {
                                        arrayAdapter = new ArrayAdapter<String>(ThemNhaTro.this, android.R.layout.simple_list_item_1, arrhoangmai);
                                        edtPhuongXa.setAdapter(arrayAdapter);
                                        edtPhuongXa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                phuongxa = parent.getItemAtPosition(position).toString();
                                            }
                                        });
                                    } else   if (quanhuyen.equals("Long Bi??n")) {
                                        arrayAdapter = new ArrayAdapter<String>(ThemNhaTro.this, android.R.layout.simple_list_item_1, arrlongbien);
                                        edtPhuongXa.setAdapter(arrayAdapter);
                                        edtPhuongXa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                phuongxa = parent.getItemAtPosition(position).toString();
                                            }
                                        });
                                    } else   if (quanhuyen.equals("Nam T??? Li??m")) {
                                        arrayAdapter = new ArrayAdapter<String>(ThemNhaTro.this, android.R.layout.simple_list_item_1, arrnamtuliem);
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
                            arrayAdapter = new ArrayAdapter<String>(ThemNhaTro.this, android.R.layout.simple_list_item_1, arrquanhuyendaklak);
                            edtQuanHuyen.setAdapter(arrayAdapter);
                            edtQuanHuyen.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    quanhuyen = parent.getItemAtPosition(position).toString();

                                    //ph?????ng x??
                                    if (quanhuyen.equals("Bu??n Ma Thu???t")) {
                                        arrayAdapter = new ArrayAdapter<String>(ThemNhaTro.this, android.R.layout.simple_list_item_1, arrbuonmathuot);
                                        edtPhuongXa.setAdapter(arrayAdapter);
                                        edtPhuongXa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                phuongxa = parent.getItemAtPosition(position).toString();
                                            }
                                        });
                                    } else   if (quanhuyen.equals("Bu??n ????n")) {
                                        arrayAdapter = new ArrayAdapter<String>(ThemNhaTro.this, android.R.layout.simple_list_item_1, arrbuondon);
                                        edtPhuongXa.setAdapter(arrayAdapter);
                                        edtPhuongXa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                phuongxa = parent.getItemAtPosition(position).toString();
                                            }
                                        });
                                    } else   if (quanhuyen.equals("Ea Kar")) {
                                        arrayAdapter = new ArrayAdapter<String>(ThemNhaTro.this, android.R.layout.simple_list_item_1, arreakar);
                                        edtPhuongXa.setAdapter(arrayAdapter);
                                        edtPhuongXa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                phuongxa = parent.getItemAtPosition(position).toString();
                                            }
                                        });
                                    } else   if (quanhuyen.equals("Ea H'leo")) {
                                        arrayAdapter = new ArrayAdapter<String>(ThemNhaTro.this, android.R.layout.simple_list_item_1, arreahleo);
                                        edtPhuongXa.setAdapter(arrayAdapter);
                                        edtPhuongXa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                phuongxa = parent.getItemAtPosition(position).toString();
                                            }
                                        });
                                    } else   if (quanhuyen.equals("C?? M'gar")) {
                                        arrayAdapter = new ArrayAdapter<String>(ThemNhaTro.this, android.R.layout.simple_list_item_1, arrcumga);
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
                            arrayAdapter = new ArrayAdapter<String>(ThemNhaTro.this, android.R.layout.simple_list_item_1, arrquanhuyenhochiminh);
                            edtQuanHuyen.setAdapter(arrayAdapter);
                            edtQuanHuyen.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    quanhuyen = parent.getItemAtPosition(position).toString();

                                    //ph?????ng x??
                                    if (quanhuyen.equals("Qu???n 1")) {
                                        arrayAdapter = new ArrayAdapter<String>(ThemNhaTro.this, android.R.layout.simple_list_item_1, arrquan1);
                                        edtPhuongXa.setAdapter(arrayAdapter);
                                        edtPhuongXa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                phuongxa = parent.getItemAtPosition(position).toString();
                                            }
                                        });
                                    } else   if (quanhuyen.equals("Qu???n 4")) {
                                        arrayAdapter = new ArrayAdapter<String>(ThemNhaTro.this, android.R.layout.simple_list_item_1, arrquan4);
                                        edtPhuongXa.setAdapter(arrayAdapter);
                                        edtPhuongXa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                phuongxa = parent.getItemAtPosition(position).toString();
                                            }
                                        });
                                    } else   if (quanhuyen.equals("Qu???n T??n B??nh")) {
                                        arrayAdapter = new ArrayAdapter<String>(ThemNhaTro.this, android.R.layout.simple_list_item_1, arrtanbinh);
                                        edtPhuongXa.setAdapter(arrayAdapter);
                                        edtPhuongXa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                phuongxa = parent.getItemAtPosition(position).toString();
                                            }
                                        });
                                    } else   if (quanhuyen.equals("Qu???n G?? G???p")) {
                                        arrayAdapter = new ArrayAdapter<String>(ThemNhaTro.this, android.R.layout.simple_list_item_1, arrvogap);
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
                    new SweetAlertDialog(ThemNhaTro.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Oops...")
                            .setContentText("Kh??ng th??? ch???n qu???n huy???n khi ch??a ch???n t???nh th??nh!")
                            .show();
                    return;
                }

                    edtPhuongXa.setText("");
                //ph?????ng x??
                if (quanhuyen.equals("Ba ????nh")) {
                    arrayAdapter = new ArrayAdapter<String>(ThemNhaTro.this, android.R.layout.simple_list_item_1, arrbadinh);
                    edtPhuongXa.setAdapter(arrayAdapter);
                    edtPhuongXa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            phuongxa = parent.getItemAtPosition(position).toString();
                        }
                    });
                } else   if (quanhuyen.equals("B???c T??? Li??m")) {
                    arrayAdapter = new ArrayAdapter<String>(ThemNhaTro.this, android.R.layout.simple_list_item_1, arrbactuliem);
                    edtPhuongXa.setAdapter(arrayAdapter);
                    edtPhuongXa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            phuongxa = parent.getItemAtPosition(position).toString();
                        }
                    });
                } else   if (quanhuyen.equals("C???u Gi???y")) {
                    arrayAdapter = new ArrayAdapter<String>(ThemNhaTro.this, android.R.layout.simple_list_item_1, arrcaugiay);
                    edtPhuongXa.setAdapter(arrayAdapter);
                    edtPhuongXa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            phuongxa = parent.getItemAtPosition(position).toString();
                        }
                    });
                } else   if (quanhuyen.equals("?????ng ??a")) {
                    arrayAdapter = new ArrayAdapter<String>(ThemNhaTro.this, android.R.layout.simple_list_item_1, arrdongda);
                    edtPhuongXa.setAdapter(arrayAdapter);
                    edtPhuongXa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            phuongxa = parent.getItemAtPosition(position).toString();
                        }
                    });
                } else   if (quanhuyen.equals("Ho??ng Mai")) {
                    arrayAdapter = new ArrayAdapter<String>(ThemNhaTro.this, android.R.layout.simple_list_item_1, arrhoangmai);
                    edtPhuongXa.setAdapter(arrayAdapter);
                    edtPhuongXa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            phuongxa = parent.getItemAtPosition(position).toString();
                        }
                    });
                } else   if (quanhuyen.equals("Long Bi??n")) {
                    arrayAdapter = new ArrayAdapter<String>(ThemNhaTro.this, android.R.layout.simple_list_item_1, arrlongbien);
                    edtPhuongXa.setAdapter(arrayAdapter);
                    edtPhuongXa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            phuongxa = parent.getItemAtPosition(position).toString();
                        }
                    });
                } else   if (quanhuyen.equals("Nam T??? Li??m")) {
                    arrayAdapter = new ArrayAdapter<String>(ThemNhaTro.this, android.R.layout.simple_list_item_1, arrnamtuliem);
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
                    arrayAdapter = new ArrayAdapter<String>(ThemNhaTro.this, android.R.layout.simple_list_item_1, arrbuonmathuot);
                    edtPhuongXa.setAdapter(arrayAdapter);
                    edtPhuongXa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            phuongxa = parent.getItemAtPosition(position).toString();
                        }
                    });
                } else   if (quanhuyen.equals("Bu??n ????n")) {
                    arrayAdapter = new ArrayAdapter<String>(ThemNhaTro.this, android.R.layout.simple_list_item_1, arrbuondon);
                    edtPhuongXa.setAdapter(arrayAdapter);
                    edtPhuongXa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            phuongxa = parent.getItemAtPosition(position).toString();
                        }
                    });
                } else   if (quanhuyen.equals("Ea Kar")) {
                    arrayAdapter = new ArrayAdapter<String>(ThemNhaTro.this, android.R.layout.simple_list_item_1, arreakar);
                    edtPhuongXa.setAdapter(arrayAdapter);
                    edtPhuongXa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            phuongxa = parent.getItemAtPosition(position).toString();
                        }
                    });
                } else   if (quanhuyen.equals("Ea H'leo")) {
                    arrayAdapter = new ArrayAdapter<String>(ThemNhaTro.this, android.R.layout.simple_list_item_1, arreahleo);
                    edtPhuongXa.setAdapter(arrayAdapter);
                    edtPhuongXa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            phuongxa = parent.getItemAtPosition(position).toString();
                        }
                    });
                } else   if (quanhuyen.equals("C?? M'gar")) {
                    arrayAdapter = new ArrayAdapter<String>(ThemNhaTro.this, android.R.layout.simple_list_item_1, arrcumga);
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
                    arrayAdapter = new ArrayAdapter<String>(ThemNhaTro.this, android.R.layout.simple_list_item_1, arrquan1);
                    edtPhuongXa.setAdapter(arrayAdapter);
                    edtPhuongXa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            phuongxa = parent.getItemAtPosition(position).toString();
                        }
                    });
                } else   if (quanhuyen.equals("Qu???n 4")) {
                    arrayAdapter = new ArrayAdapter<String>(ThemNhaTro.this, android.R.layout.simple_list_item_1, arrquan4);
                    edtPhuongXa.setAdapter(arrayAdapter);
                    edtPhuongXa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            phuongxa = parent.getItemAtPosition(position).toString();
                        }
                    });
                } else   if (quanhuyen.equals("Qu???n T??n B??nh")) {
                    arrayAdapter = new ArrayAdapter<String>(ThemNhaTro.this, android.R.layout.simple_list_item_1, arrtanbinh);
                    edtPhuongXa.setAdapter(arrayAdapter);
                    edtPhuongXa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            phuongxa = parent.getItemAtPosition(position).toString();
                        }
                    });
                } else   if (quanhuyen.equals("Qu???n G?? G???p")) {
                    arrayAdapter = new ArrayAdapter<String>(ThemNhaTro.this, android.R.layout.simple_list_item_1, arrvogap);
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
                    new SweetAlertDialog(ThemNhaTro.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Oops...")
                            .setContentText("Kh??ng th??? ch???n ph?????ng x?? khi ch??a ch???n t???nh th??nh v?? qu???n huy???n!")
                            .show();
                    return;
                } else    if (quanhuyen.equals("")) {
                    new SweetAlertDialog(ThemNhaTro.this, SweetAlertDialog.ERROR_TYPE)
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


                new SweetAlertDialog(ThemNhaTro.this, SweetAlertDialog.WARNING_TYPE)
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
                                Intent introIntent = new Intent(ThemNhaTro.this, TrangChu.class);
                                startActivity(introIntent);
                                finishAffinity();
                            }
                        })
                        .show();




            }
        });

        //click th??m ph??ng tr???
        btnThemNhaTro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setThemNhaTro();
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
                Toast.makeText(ThemNhaTro.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }


        };
        TedPermission.with(ThemNhaTro.this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .check();


    }

    private void onClickImg() {

        TedBottomPicker.with(ThemNhaTro.this)
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
        photoAdapter = new PhotoAdapter(ThemNhaTro.this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        rcvPhoto.setLayoutManager(gridLayoutManager);
        rcvPhoto.setAdapter(photoAdapter);
    }

    //h??m th??m nh?? tr???
    public void setThemNhaTro() {
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

            SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("??ang th??m ph??ng tr???...!");
            pDialog.setCancelable(false);
            pDialog.show();

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(ThemNhaTro.this, TrangChu.class));
                    finishAffinity();
                }
            },1000);

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
        btnThemNhaTro = findViewById(R.id.btnThemNhaTro);
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
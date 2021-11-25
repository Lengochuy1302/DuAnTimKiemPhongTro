package com.example.duanaeth.LayoutChucNang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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

import com.example.duanaeth.ArrayAdapter.PhotoAlbum;
import com.example.duanaeth.ArrayAdapter.TienIch;
import com.example.duanaeth.FirebaseAdapter.PhotoAdapter;
import com.example.duanaeth.R;
import com.example.duanaeth.TrangChu;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
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
    public String linkRealTime;
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
    private TienIch tienIch = new TienIch();
    private List<TienIch> dsTienIch = new ArrayList<>();
    private int uploads = 0;
    private DatabaseReference databaseReference;
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
        setContentView(R.layout.activity_them_nha_tro);

        //ánh xạ
        anhXa();

        //ẩn toolBar
        getSupportActionBar().hide();

        //set ảnh vào rcv
        setImgRcv();

        //các event click
        eventClick();

        //set địa chỉ
        setAutoComplete();

        //onclickToggleButton
        onclickToggleButton();


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
                        //quận huyện
                        if (tinhthanh.equals("Hà Nội")) {
                            arrayAdapter = new ArrayAdapter<String>(ThemNhaTro.this, android.R.layout.simple_list_item_1, arrquanhuyenhanoi);
                            edtQuanHuyen.setAdapter(arrayAdapter);
                            edtQuanHuyen.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    quanhuyen = parent.getItemAtPosition(position).toString();

                                    //phường xã
                                    if (quanhuyen.equals("Ba Đình")) {
                                        arrayAdapter = new ArrayAdapter<String>(ThemNhaTro.this, android.R.layout.simple_list_item_1, arrbadinh);
                                        edtPhuongXa.setAdapter(arrayAdapter);
                                        edtPhuongXa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                phuongxa = parent.getItemAtPosition(position).toString();
                                            }
                                        });
                                    } else   if (quanhuyen.equals("Bắc Từ Liêm")) {
                                        arrayAdapter = new ArrayAdapter<String>(ThemNhaTro.this, android.R.layout.simple_list_item_1, arrbactuliem);
                                        edtPhuongXa.setAdapter(arrayAdapter);
                                        edtPhuongXa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                phuongxa = parent.getItemAtPosition(position).toString();
                                            }
                                        });
                                    } else   if (quanhuyen.equals("Cầu Giấy")) {
                                        arrayAdapter = new ArrayAdapter<String>(ThemNhaTro.this, android.R.layout.simple_list_item_1, arrcaugiay);
                                        edtPhuongXa.setAdapter(arrayAdapter);
                                        edtPhuongXa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                phuongxa = parent.getItemAtPosition(position).toString();
                                            }
                                        });
                                    } else   if (quanhuyen.equals("Đống Đa")) {
                                        arrayAdapter = new ArrayAdapter<String>(ThemNhaTro.this, android.R.layout.simple_list_item_1, arrdongda);
                                        edtPhuongXa.setAdapter(arrayAdapter);
                                        edtPhuongXa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                phuongxa = parent.getItemAtPosition(position).toString();
                                            }
                                        });
                                    } else   if (quanhuyen.equals("Hoàng Mai")) {
                                        arrayAdapter = new ArrayAdapter<String>(ThemNhaTro.this, android.R.layout.simple_list_item_1, arrhoangmai);
                                        edtPhuongXa.setAdapter(arrayAdapter);
                                        edtPhuongXa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                phuongxa = parent.getItemAtPosition(position).toString();
                                            }
                                        });
                                    } else   if (quanhuyen.equals("Long Biên")) {
                                        arrayAdapter = new ArrayAdapter<String>(ThemNhaTro.this, android.R.layout.simple_list_item_1, arrlongbien);
                                        edtPhuongXa.setAdapter(arrayAdapter);
                                        edtPhuongXa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                phuongxa = parent.getItemAtPosition(position).toString();
                                            }
                                        });
                                    } else   if (quanhuyen.equals("Nam Từ Liêm")) {
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
                        } else   if (tinhthanh.equals("Đăk Lăk")) {
                            arrayAdapter = new ArrayAdapter<String>(ThemNhaTro.this, android.R.layout.simple_list_item_1, arrquanhuyendaklak);
                            edtQuanHuyen.setAdapter(arrayAdapter);
                            edtQuanHuyen.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    quanhuyen = parent.getItemAtPosition(position).toString();

                                    //phường xã
                                    if (quanhuyen.equals("Buôn Ma Thuột")) {
                                        arrayAdapter = new ArrayAdapter<String>(ThemNhaTro.this, android.R.layout.simple_list_item_1, arrbuonmathuot);
                                        edtPhuongXa.setAdapter(arrayAdapter);
                                        edtPhuongXa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                phuongxa = parent.getItemAtPosition(position).toString();
                                            }
                                        });
                                    } else   if (quanhuyen.equals("Buôn Đôn")) {
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
                                    } else   if (quanhuyen.equals("Cư M'gar")) {
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
                        } else   if (tinhthanh.equals("Hồ Chí Minh")) {
                            arrayAdapter = new ArrayAdapter<String>(ThemNhaTro.this, android.R.layout.simple_list_item_1, arrquanhuyenhochiminh);
                            edtQuanHuyen.setAdapter(arrayAdapter);
                            edtQuanHuyen.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    quanhuyen = parent.getItemAtPosition(position).toString();

                                    //phường xã
                                    if (quanhuyen.equals("Quận 1")) {
                                        arrayAdapter = new ArrayAdapter<String>(ThemNhaTro.this, android.R.layout.simple_list_item_1, arrquan1);
                                        edtPhuongXa.setAdapter(arrayAdapter);
                                        edtPhuongXa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                phuongxa = parent.getItemAtPosition(position).toString();
                                            }
                                        });
                                    } else   if (quanhuyen.equals("Quận 4")) {
                                        arrayAdapter = new ArrayAdapter<String>(ThemNhaTro.this, android.R.layout.simple_list_item_1, arrquan4);
                                        edtPhuongXa.setAdapter(arrayAdapter);
                                        edtPhuongXa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                phuongxa = parent.getItemAtPosition(position).toString();
                                            }
                                        });
                                    } else   if (quanhuyen.equals("Quận Tân Bình")) {
                                        arrayAdapter = new ArrayAdapter<String>(ThemNhaTro.this, android.R.layout.simple_list_item_1, arrtanbinh);
                                        edtPhuongXa.setAdapter(arrayAdapter);
                                        edtPhuongXa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                phuongxa = parent.getItemAtPosition(position).toString();
                                            }
                                        });
                                    } else   if (quanhuyen.equals("Quận Gò Gấp")) {
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
                    edtPhuongXa.setText("");
                //phường xã
                if (quanhuyen.equals("Ba Đình")) {
                    arrayAdapter = new ArrayAdapter<String>(ThemNhaTro.this, android.R.layout.simple_list_item_1, arrbadinh);
                    edtPhuongXa.setAdapter(arrayAdapter);
                    edtPhuongXa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            phuongxa = parent.getItemAtPosition(position).toString();
                        }
                    });
                } else   if (quanhuyen.equals("Bắc Từ Liêm")) {
                    arrayAdapter = new ArrayAdapter<String>(ThemNhaTro.this, android.R.layout.simple_list_item_1, arrbactuliem);
                    edtPhuongXa.setAdapter(arrayAdapter);
                    edtPhuongXa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            phuongxa = parent.getItemAtPosition(position).toString();
                        }
                    });
                } else   if (quanhuyen.equals("Cầu Giấy")) {
                    arrayAdapter = new ArrayAdapter<String>(ThemNhaTro.this, android.R.layout.simple_list_item_1, arrcaugiay);
                    edtPhuongXa.setAdapter(arrayAdapter);
                    edtPhuongXa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            phuongxa = parent.getItemAtPosition(position).toString();
                        }
                    });
                } else   if (quanhuyen.equals("Đống Đa")) {
                    arrayAdapter = new ArrayAdapter<String>(ThemNhaTro.this, android.R.layout.simple_list_item_1, arrdongda);
                    edtPhuongXa.setAdapter(arrayAdapter);
                    edtPhuongXa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            phuongxa = parent.getItemAtPosition(position).toString();
                        }
                    });
                } else   if (quanhuyen.equals("Hoàng Mai")) {
                    arrayAdapter = new ArrayAdapter<String>(ThemNhaTro.this, android.R.layout.simple_list_item_1, arrhoangmai);
                    edtPhuongXa.setAdapter(arrayAdapter);
                    edtPhuongXa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            phuongxa = parent.getItemAtPosition(position).toString();
                        }
                    });
                } else   if (quanhuyen.equals("Long Biên")) {
                    arrayAdapter = new ArrayAdapter<String>(ThemNhaTro.this, android.R.layout.simple_list_item_1, arrlongbien);
                    edtPhuongXa.setAdapter(arrayAdapter);
                    edtPhuongXa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            phuongxa = parent.getItemAtPosition(position).toString();
                        }
                    });
                } else   if (quanhuyen.equals("Nam Từ Liêm")) {
                    arrayAdapter = new ArrayAdapter<String>(ThemNhaTro.this, android.R.layout.simple_list_item_1, arrnamtuliem);
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
                    arrayAdapter = new ArrayAdapter<String>(ThemNhaTro.this, android.R.layout.simple_list_item_1, arrbuonmathuot);
                    edtPhuongXa.setAdapter(arrayAdapter);
                    edtPhuongXa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            phuongxa = parent.getItemAtPosition(position).toString();
                        }
                    });
                } else   if (quanhuyen.equals("Buôn Đôn")) {
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
                } else   if (quanhuyen.equals("Cư M'gar")) {
                    arrayAdapter = new ArrayAdapter<String>(ThemNhaTro.this, android.R.layout.simple_list_item_1, arrcumga);
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
                    arrayAdapter = new ArrayAdapter<String>(ThemNhaTro.this, android.R.layout.simple_list_item_1, arrquan1);
                    edtPhuongXa.setAdapter(arrayAdapter);
                    edtPhuongXa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            phuongxa = parent.getItemAtPosition(position).toString();
                        }
                    });
                } else   if (quanhuyen.equals("Quận 4")) {
                    arrayAdapter = new ArrayAdapter<String>(ThemNhaTro.this, android.R.layout.simple_list_item_1, arrquan4);
                    edtPhuongXa.setAdapter(arrayAdapter);
                    edtPhuongXa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            phuongxa = parent.getItemAtPosition(position).toString();
                        }
                    });
                } else   if (quanhuyen.equals("Quận Tân Bình")) {
                    arrayAdapter = new ArrayAdapter<String>(ThemNhaTro.this, android.R.layout.simple_list_item_1, arrtanbinh);
                    edtPhuongXa.setAdapter(arrayAdapter);
                    edtPhuongXa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            phuongxa = parent.getItemAtPosition(position).toString();
                        }
                    });
                } else   if (quanhuyen.equals("Quận Gò Gấp")) {
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


                new SweetAlertDialog(ThemNhaTro.this, SweetAlertDialog.WARNING_TYPE)
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
                                Intent introIntent = new Intent(ThemNhaTro.this, TrangChu.class);
                                startActivity(introIntent);
                                finishAffinity();
                            }
                        })
                        .show();




            }
        });

        //click thêm phòng trọ
        btnThemNhaTro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setThemNhaTro();
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
        photoAlbum.setId(""+ calendar.getTimeInMillis());
        photoAlbum.setLinkanh(url);
        databaseReference.child(""+  calendar.getTimeInMillis()).setValue(photoAlbum).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        });


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
                .setTitle("Chọn ảnh nhà trọ")
                .setCompleteButtonText("Xong")
                .setEmptySelectionText("Chưa chọn ảnh nào")
                .showMultiImage(new TedBottomSheetDialogFragment.OnMultiImageSelectedListener() {
                    @Override
                    public void onImagesSelected(List<Uri> uriList) {
                        // here is selected image uri list
                        if (uriList != null && !uriList.isEmpty()) {
//                            btnImg.setVisibility(View.GONE);
                            btnClean.setVisibility(View.VISIBLE);
                            photoAdapter.setDataPhoto(uriList);
                            final StorageReference ImageFolder =  FirebaseStorage.getInstance("gs://thodf-8e9db.appspot.com").getReference();
                            for (uploads=0; uploads < uriList.size(); uploads++) {
                                Uri Image  = uriList.get(uploads);

                                Calendar calendar = Calendar.getInstance();
                                final StorageReference imagename = ImageFolder.child(""+  UUID.randomUUID() +".png");

                                imagename.putFile(uriList.get(uploads)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        imagename.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {

                                                String url = String.valueOf(uri);
//                                                SendLink(url);
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
        photoAdapter = new PhotoAdapter(ThemNhaTro.this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        rcvPhoto.setLayoutManager(gridLayoutManager);
        rcvPhoto.setAdapter(photoAdapter);
    }

    //hàm thêm nhà trọ
    public void setThemNhaTro() {

    }

    //hàm event Toggle Buttons
    public void onclickToggleButton() {
        //Internet
        btginternet.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tienIch = new TienIch();
                    tienIch.setId(""+ dsTienIch.size());
                    tienIch.setTen("Internet");
                    dsTienIch.add(tienIch);
                    Log.d("aaaa", "trước khi xóa: "+ dsTienIch);
                    Toast.makeText(ThemNhaTro.this, "Đã bật"+ tienIch.getTen(),Toast.LENGTH_SHORT).show();
                } else {
                    for (int i = 0 ; i < dsTienIch.size(); i++) {
                        if (tienIch.getTen().equals("Internet")) {
                            tienIch.getTen();
//                            dsTienIch.remove(i);
                            Log.d("aaaa", "sau khi xóa: "+ dsTienIch);
                            Toast.makeText(ThemNhaTro.this, "Đã tắt"+ tienIch.getTen(),Toast.LENGTH_SHORT).show();
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
                    tienIch = new TienIch();
                    tienIch.setId(""+ dsTienIch.size());
                    tienIch.setTen("WC Riêng");
                    dsTienIch.add(tienIch);
                    Log.d("aaaa", "trước khi xóa: "+ dsTienIch);
                    Toast.makeText(ThemNhaTro.this, "Đã bật"+ tienIch.getTen(),Toast.LENGTH_SHORT).show();
                } else {
                    for (int i = 0 ; i < dsTienIch.size(); i++) {
                        if (tienIch.getTen().equals("WC Riêng")) {
                            tienIch.getTen();
//                            dsTienIch.remove(i);
                            Log.d("aaaa", "sau khi xóa: "+ dsTienIch);
                            Toast.makeText(ThemNhaTro.this, "Đã tắt"+ tienIch.getTen(),Toast.LENGTH_SHORT).show();
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
                    tienIch = new TienIch();
                    tienIch.setId(""+ dsTienIch.size());
                    tienIch.setTen("Máy Lạnh");
                    dsTienIch.add(tienIch);
                    Log.d("aaaa", "trước khi xóa: "+ dsTienIch);
                    Toast.makeText(ThemNhaTro.this, "Đã bật"+ tienIch.getTen(),Toast.LENGTH_SHORT).show();
                } else {
                    for (int i = 0 ; i < dsTienIch.size(); i++) {
                        if (tienIch.getTen().equals("Máy Lạnh")) {
                            tienIch.getTen();
//                            dsTienIch.remove(i);
                            Log.d("aaaa", "sau khi xóa: "+ dsTienIch);
                            Toast.makeText(ThemNhaTro.this, "Đã tắt"+ tienIch.getTen(),Toast.LENGTH_SHORT).show();
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
                    tienIch = new TienIch();
                    tienIch.setId(""+ dsTienIch.size());
                    tienIch.setTen("An Ninh");
                    dsTienIch.add(tienIch);
                    Log.d("aaaa", "trước khi xóa: "+ dsTienIch);
                    Toast.makeText(ThemNhaTro.this, "Đã bật"+ tienIch.getTen(),Toast.LENGTH_SHORT).show();
                } else {
                    for (int i = 0 ; i < dsTienIch.size(); i++) {
                        if (tienIch.getTen().equals("An Ninh")) {
                            tienIch.getTen();
//                            dsTienIch.remove(i);
                            Log.d("aaaa", "sau khi xóa: "+ dsTienIch);
                            Toast.makeText(ThemNhaTro.this, "Đã tắt"+ tienIch.getTen(),Toast.LENGTH_SHORT).show();
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
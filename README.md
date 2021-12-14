TRƯỜNG CAO ĐẲNG THỰC HÀNH FPT POLYTECHNIC
----------

 

DỰ ÁN 1
ĐỀ TÀI: APP TÌM KIẾM PHÒNG TRỌ
CHUYÊN NGÀNH: LẬP TRÌNH MÁY TÍNH – THIẾT BỊ DI ĐỘNG


                                    Giáo viên hướng dẫn: 	Phạm Văn Bình
 	Sinh viên:   	Nguyễn Trường Giang – PK01855
			Lê Ngọc Huy – PK01810
			Nguyễn Duy Tài– PK01978 
			Hoàng Tuấn Đạt – PK01816					      
 	Lớp:	PT163LT






Buôn Ma Thuột, tháng 12 năm 2021
Mục Lục
I.	GIỚI THIỆU TỔNG QUAN	4
1.	Giới thiệu đề tài	4
2.	Phạm vi đề tài	5
3.	Tiêu chí chức năng của đề tài	5
3.1.	Chức năng dành cho người dùng	5
3.2.	Chức năng dành cho Admin	5
II.	NGÔN NGỮ VÀ CÔNG CỤ SỬ DỤNG	6
1.	Ngôn ngữ	6
1.1.	Java	6
2.	Công cụ khác	6
2.1.	Android Studio	6
2.2.	Adobe XD	7
2.3.	Adobe Photoshop	7
2.4.	Cơ sở dữ liệu FireBase	8
III.	PHÂN TÍCH VÀ THIẾT KẾ HỆ THỐNG	8
1.	Sơ đồ thiết kế hệ thống	9
2.	Biểu đồ phân rã chức năng	9
2.1.	Biểu đồ phân rã chức năng của Admin trên App	9
2.2.	Biểu đồ phân rã chức năng của Người dùng trên App	9
3.	Use case	10
3.1.	UC01: Use case Admin	10
3.2.	UC02: Use case Người dùng	11
3.3.	UC08: Quản lý thống kê của Admin trên App	11
3.4.	UC15: Đăng nhập của người dùng trên App	12
3.5.	UC17: Tìm kiếm phòng trọ của người dùng trên App	12
3.6.	UC18: Quản lý cá nhân của của người dùng trên App	12
3.7.	UC21: Đánh giá, bình luận và báo cáo của người dùng trên App	13
3.8.	UC22: Quản lý sản phẩm yêu thích của người dùng trên App	13
4.	Cơ sở dữ liệu	14
4.1.	Chi tiết các thực thể	14
4.2.	Sơ đồ ERD	16
4.3.	Mối liên hệ giữa các thực thể	17
5.	Phác thảo giao diện	20
5.1.	Sơ đồ luồng giao diện	20
5.2.	Giao diện App	20
6.	Test case	31
6.1.	Test case của người dùng	31
6.2.	Test case của Admin	39
7.	Kế hoạch test và phân chia công việc	41
IV.	TÀI LIỆU THAM KHẢO	43
V.	ĐÁNH GIÁ DỰ ÁN	43
1.	Ưu điểm	43
2.	Nhược điểm	44
3.	Cơ hội	44
4.	Thách thức	44
5.	Định hướng phát triển ứng dụng	44
VI.	LỜI CẢM ƠN	44





NHẬN XÉT
(Của giảng viên hướng dẫn)
..........................................................................................................................................
..........................................................................................................................................
..........................................................................................................................................
..........................................................................................................................................
..........................................................................................................................................
..........................................................................................................................................
..........................................................................................................................................
..........................................................................................................................................
..........................................................................................................................................
..........................................................................................................................................
..........................................................................................................................................
..........................................................................................................................................
..........................................................................................................................................
Buôn Ma Thuột, ngày…..tháng…..năm 20…
Giảng viên hướng dẫn



LỜI NÓI ĐẦU
Ngày nay, với sự phát triển mạnh mẽ của công nghệ thông tin và những ứng dụng của nó trong đời sống. Điện thoại không còn là một thứ phương tiện lạ lẫm đối với mọi người mà nó dần trở thành một công cụ thông dụng và hữu ích của chúng ta, giúp chúng ta tìm kiếm thông tin nhanh chóng. Việc tìm phòng trọ thực sự là quá trình khó khăn và mất nhiều thời gian đối với những bạn người mới lên thành phố sinh sống. Không những khó khăn trong việc tìm kiếm mà việc chọn được một nhà trọ tốt, an toàn cùng chi phí hợp lý cũng làm nhiều người đau đầu.

Vì thế, chúng em đã cùng nhau thiết kế “App Tìm kiếm phòng trọ” để giải quyết vấn đề tìm kiếm phòng trọ một cách nhanh chóng.
Cùng với sự chỉ bảo tận tình của thầy Phạm Văn Bình nhóm em đã hoàn thành App này. Trong quá trình phân tích thiết kế hệ thống không thể tránh khỏi những sai sót mong thầy cô và các bạn đóng góp ý kiến để App được hoàn thiện hơn.

                                                                           Em xin chân thành cảm ơn!


 

I.	GIỚI THIỆU TỔNG QUAN
1.	Giới thiệu đề tài 
Việc tìm kiếm phòng trọ từ trước đến nay là một nhu cầu thiết yếu đối với tất cả mọi người đang sinh sống và làm việc xa nhà. Không phải lúc nào cũng tìm được một phòng trọ phù hợp với mình, đặc biệt là một phòng trọ tốt, an toàn và giá cả hợp lí với mình. Nắm bắt được nhu cầu thiết yếu này, sau một khoảng thời gian nghiên cứu và tìm hiểu thị trường nhóm em đã đưa ra quyết định làm dự án 1 là: “App tìm kiếm phòng trọ ”.
2.	Phạm vi đề tài
 App tìm kiếm phòng trọ phù hợp với mọi đối tượng như sinh viên, người làm việc xa nhà có nhu cầu tìm kiếm phòng trọ và chủ phòng trọ muốn cho thuê phòng trọ . 
3.	Tiêu chí chức năng của đề tài 
3.1.	Chức năng dành cho người dùng
	Giao diện dễ sử dụng và tính thẩm mỹ cao.
	Cho phép khách hàng đăng nhập bằng tài khoản: Google, Email và đảm bảo bảo mật thông tin.
	 Xem và thay đổi các thông tin về tài khoản (Tên , Ngày sinh . . .)
	Thêm, xóa và cập nhật thông tin phòng trọ 
	Xem thông tin chi tiết về phòng trọ: số tiền hàng háng, điện nước, vật tư, hình ảnh...
	Tìm kiếm phòng trọ theo khu vực.
	Đánh giá về phòng trọ
	Liên hệ với chủ trọ.
	Tìm ở ghép, thông tin người ở ghép,  liên hệ với người ở ghép, thêm người ở ghép.
	Thêm phòng trọ vào mục yêu thích.
	Báo cáo phòng trọ.
	Bảo vệ thông tin cá nhân của chủ trọ.
	Xác minh SĐT,  Gmail.
3.2.	Chức năng dành cho Admin
Ngoài các yêu cầu giống như của khách hàng, thì hệ thống phải đảm bảo những yêu cầu sau của ban quản trị:
	Thống kê được số lượng phòng trọ đã đăng.
	Thống kê được số chủ trọ .
	Thống kê được số lượt xem.
	Xác minh, duyệt, kiểm tra và xóa phòng trọ.
II.	NGÔN NGỮ VÀ CÔNG CỤ SỬ DỤNG
1.	Ngôn ngữ
1.1.	Java
Java là một ngôn ngữ lập trình và nền tảng tính toán được phân phối lần đầu tiên bởi Sun Microsystems vào năm 1995. Rất nhiều ứng dụng, trang web đều được viết bằng Java. Java nhanh, bảo mật và đáng tin cậy. 
 
2.	Công cụ khác
2.1.	Android Studio
 
Android Studio là một phầm mềm bao gồm các bộ công cụ khác nhau dùng để phát triển ứng dụng chạy trên thiết bị sử dụng hệ điều hành Android như các loại điện thoại smartphone, các tablet... Android Studio được đóng gói với một bộ code editor, debugger, các công cụ performance tool và một hệ thống build/deploy (trong đó có trình giả lập simulator để giả lập môi trường của thiết bị điện thoại hoặc tablet trên máy tính) cho phép các lập trình viên có thể nhanh chóng phát triển các ứng dụng từ đơn giản tới phức tạp.
2.2.	 Adobe XD
Adobe XD” chính là viết tắt của cụm từ “Adobe Experience Design” – đây là một phần mềm rất hữu ích trong việc thiết kế cũng như tạo nguyên mẫu cho nhiều ứng dụng khác nhau. Các bạn có thể hiểu một cách đơn giản nhất đây là một công cụ hỗ trợ rất đắc lực khi thiết kế UX.
 
Adobe XD sở hữu rất nhiều tính năng vượt trội, tiện ích thông minh giúp hỗ trợ tối đa cho việc thiết kế UX được làm việc nhanh chóng, hiệu quả hơn . Ngoài ra, cách sử dụng phần mềm này cũng tương đối dễ dàng, gần giống như một số phần mềm Adobe khác  điển hình như photoshop, illustrator,...  Theo đánh giá của người dùng thì giao diện chính của phần mềm Adobe XD được thiết kế khá đơn giản, thoáng và quen thuộc, gần gũi với những người thường dùng các công cụ line, rectangle, ellipse,... sẵn có ở thanh công cụ ở phía bên trái của giao diện. Bên cạnh đó, người dùng cũng có thể tìm thấy các công cụ khác như text hay pen, cho phép bạn có thể tự do vẽ các hình dạng tùy theo ý thích
2.3.	Adobe Photoshop
Adobe Photoshop (thường được gọi là Photoshop) là một phần mềm chỉnh sửa đồ họa được phát triển và phát hành bởi hãng Adobe Systems ra đời vào năm 1988 trên hệ máy Macintosh. Photoshop được đánh giá là phần mềm dẫn đầu thị trường về sửa ảnh bitmap và được coi là chuẩn cho các ngành liên quan tới chỉnh sửa ảnh. Từ phiên bản Photoshop 7.0 ra đời năm 2002, Photoshop đã làm lên một cuộc cách mạng về ảnh bitmap. Phiên bản mới nhất hiện nay là Adobe Photoshop CC.
 
Ngoài khả năng chính là chỉnh sửa ảnh cho các ấn phẩm, Photoshop còn được sử dụng trong các hoạt động như thiết kế trang web, vẽ các loại tranh (matte painting và nhiều thể loại khác), vẽ texture cho các chương trình 3D... gần như là mọi hoạt động liên quan đến ảnh bitmap.  Adobe Photoshop có khả năng tương thích với hầu hết các chương trình đồ họa khác của Adobe như Adobe Illustrator, Adobe Premiere, After After Effects và Adobe Encore.
2.4.	Cơ sở dữ liệu FireBase
Firebase là một nền tảng phát triển ứng dụng di động và web. Họ cung cấp rất nhiều công cụ và dịch vụ để phát triển ứng dụng chất lượng, rút ngắn thời gian phát triển và phát triển cơ sở người dùng mà không cần quan tâm đến hạ tầng phần cứng.
 
Firebase là sự kết hợp giữa nền tảng cloud với hệ thống máy chủ cực kì mạnh mẽ của Google. Firebase cung cấp cho chúng ta những API đơn giản, mạnh mẽ và đa nền tảng trong việc quản lý, sử dụng database.
III.	PHÂN TÍCH VÀ THIẾT KẾ HỆ THỐNG
1.	Sơ đồ thiết kế hệ thống 
 
2.	Biểu đồ phân rã chức năng
2.1.	Biểu đồ phân rã chức năng của Admin trên App
 
2.2.	Biểu đồ phân rã chức năng của Người dùng trên App
 
3.	Use case 
3.1.	UC01: Use case Admin
 
3.2.	UC02: Use case Người dùng
 
3.3.	UC03: Quản lý thống kê của Admin trên App
 


3.4.	UC04: Đăng nhập của người dùng trên App
 
3.5.	UC05: Tìm kiếm phòng trọ của người dùng trên App
 
3.6.	UC06: Quản lý cá nhân của của người dùng trên App
 
3.7.	UC07: Đánh giá, bình luận và báo cáo của người dùng trên App
 
3.8.	UC08: Quản lý sản phẩm yêu thích của người dùng trên App
 
4.	Cơ sở dữ liệu
4.1.	Chi tiết các thực thể 
Tên thực thể	Chi tiết thực thể
Phòng	ID phòng
ID ảnh phòng
Tên phòng
ID giá phòng
Địa chỉ
Kiểm duyệt
Đánh giá
Tọa độ
Số lượng người
Loại phòng
Lượt xem
Diện tích
Thời gian
Tình trạng phòng
ID users
ID tiện ích phòng
Users (tài khoản)	ID users
Avatar
Email
Giới tính
Loại người dùng
Số điện thoại
mật khẩu
RoomComment(Đánh giá phòng)	ID đánh giá
Nội dung
Lượt thích
Số sao
Thời gian
Tiêu đề
ID users
Comment (bình luận)	ID bình luận
Thời gian
RoomPrice (Giá phòng)	ID giá phòng
ID chi tiết giá phòng
RoomPriceType (Chi tiết giá phòng)	ID chi tiết giá phòng
Hình ảnh
Tên
Giá tiền

ReportRoom(Báo cáo phòng)	ID Report
nội dung
tiêu đề
thời gian
ID users
ID phòng
FavoriteRoom (Phòng ưa thích)	ID phòng
Id users
thời gian
RoomImages (Ảnh phòng)	ID ảnh phỏng
Album ảnh
RoomCompressionImages (Avatar phòng)	ID avatar phòng
Hình ảnh
FindRoom (Tìm phòng )	ID tìm phòng
Tiện ích
Giới tính
Địa chỉ
Giá max
Giá min
ID users
Thời gian
LocationRoom (vị trí phòng)	ID vị trí phòng
Khu vực
RoomTypes (Loại phòng)	ID loại phòng
Tên loại phòng
RoomConvenients ( Tiện ích phòng)	ID tiện ích phòng
ID tiện ích
Convenients (Tiện ích)	ID Tiện ích
Tên
Hình ảnh
RoomViews (Lượt xem phòng)	ID users
Thời gian
ID phòng
4.2.	Sơ đồ ERD
 
4.3.	Mối liên hệ giữa các thực thể
Người dùng – Phòng trọ
Mối quan hệ giữa thực thể người dùng và thực thể phòng trọ là mối quan hệ một – nhiều. Tức là 1 người dùng có thể thuê hoặc đăng được nhiều phòng trọ.
 
Phòng trọ - Tiện ích 
Mối quan hệ giữa thực thể phòng trọ và thực thể tiện ích là mối quan hệ một – nhiều. Tức là 1 phòng trọ sẽ có nhiều tiện ích.
 

Phòng trọ - đánh giá, bình luận
Mối quan hệ giữa phòng trọ và đánh giá, bình luận là mối quan hệ một – nhiều. Tức là 1 phòng trọ sẽ có nhiều đánh giá và bình luận. 
Phòng trọ - chi phí
Mối quan hệ giữa phòng trọ và chi phí là mối quan hệ một – nhiều. Tức là 1 phòng trọ sẽ có nhiều chi phí như: tiền điện, tiền nước….. 
Phòng trọ - Loại Phòng trọ
Mối quan hệ giữa phòng trọ và loại phòng trọ là mối quan hệ một – nhiều. Tức là 1 phòng trọ sẽ có nhiều loại phòng như : nhà nguyên căn, chung cư…. 
Người dùng -  Phòng ưa thích
Mối quan hệ giữa người dùng và phòng trọ yêu thích là mối quan hệ nhiều – nhiều. Tức là 1 người dùng sẽ có thể thích nhiều phòng trọ và 1 phòng trọ có thể có nhiều người thích 
Phòng trọ - Địa chỉ
Mối quan hệ giữa phòng trọ và địa chỉ là mối quan hệ một – một . Tức là 1 phòng trọ chỉ có 1 địa chỉ 
Phòng trọ - hình ảnh 
Mối quan hệ giữa phòng trọ và hình ảnh là mối quan hệ một – nhiều . Tức là 1 phòng trọ có thể có nhiều hình ảnh. 
Giá phòng – chi tiết giá phòng
Mối quan hệ giữa giá phòng và chi tiết giá phòng là mối quan hệ một – nhiều . Tức là 1 giá phòng có nhiều chi tiết giá phòng. 
Người dùng – báo cáo phòng trọ
Mối quan hệ giữa người dùng và báo cáo phòng trọ là mối quan hệ nhiều– nhiều . Tức là 1 người dùng có thể báo cáo nhiều phòng trọ và 1 phòng trọ có thể bị nhiều người báo cáo. 
5.	Phác thảo giao diện
5.1.	Sơ đồ luồng giao diện
5.1.1.	App 
 








5.2.	Giao diện App
Màn hình chào

 






Đăng nhập
 


Đăng kí 
 

Trang chủ
              
STT	Tên	Mô tả
1	Tìm kiếm 	Giúp người dùng tìm kiếm phòng trọ theo khu vực 
2	Đề xuất phòng trọ	Đề xuất những khu vực có nhiều phòng trọ
3	Trang chủ	Hiển thị danh sách phòng trọ 
4	Tìm ở ghép 	Hiển thị danh sách người ở ghép
5	Bản đồ	Hiện thị vị trí phòng trọ trên bản đồ 
6	Cá nhân	Hiển thị các danh mục cá nhân


Màn hình ở ghép
Hiển thị danh sách người ở ghép
 



Màn hình bản đồ
Hiển thị địa chỉ phòng trọ trên bản đồ
 
Màn hình cá nhân
 
STT	Tên	Mô tả
1	Cập nhật thông tin cá nhân	Cho phép thay đổi thông tin cá nhân, đổi mật khẩu.
2	Phòng của tôi	Cho phép truy cập và hiển thị các phòng trọ đã đăng..
3	Phòng trọ ưa thích	Cho phép truy cập và hiển thị các phòng trọ đã thích.
4	Tìm ở ghép của tôi	Cho phép truy cập và hiển thị các ở ghép đã đăng.
5	Góp ý phát triển 	Cho phép đánh giá và góp ý về ứng dụng.
6	Phiên bản	Hiển thị phiên bản hiện tại của ứng dụng.
7	Đăng xuất	Đăng xuất khỏi tài khoản và trở về màn hình đăng nhập.


Màn hình đăng phòng trọ
              
                          
             
Màn hình chi tiết phòng trọ
Hiển thị chi tiết thông tin phòng trọ.
 

Màn hình Bình luận và đánh giá 
Hiển thị bình luận và đánh giá về phòng trọ
 
STT	Tên	Mô tả
1	Thêm phòng trọ vào yêu thích	Thêm phòng trọ vào danh sách yêu thích
2	Báo cáo	Báo cáo phòng trọ cho admin


6.	Test case 
6.1.	Test case của người dùng
Đăng nhập, đăng kí, đăng xuất

ID	Title	Pre- condition	Description	Expected output	Actual output	Status
1	Kiểm tra kết nối 	Nếu người dùng chưa kết nối vào internet	Hiển thị một màn hình “no internet”	Trang chủ	Hiển thị một màn hình “no internet”	OK
2	Đối với người dùng lần đầu vào  app.	Khi bấm vào app.	Một chuỗi màn hình OnBroading Screen xuất hiện giới thiệu về app. 	Cá nhân	Một chuỗi 4 màn hình chào sẽ xuất hiện giới thiệu sơ qua về app, người dùng có bấm next hoặc skip để đến màn hình đăng nhập	OK
3	Với những người dùng đã đăng nhập vào app.	Khi mở app	Sẽ bỏ qua màn hình giới thiệu chức năng và chạy vào màn hình trang chủ.	Cá nhân	Chạy thẳng vào màn hinh trang chủ.	OK
4	Đăng ký tài khoản bằng email	Bấm vào nút đăng ký bằng Email	Chạy qua màn hình đăng ký bằng Email.	Cá nhân	Chạy qua màn hình đăng ký bằng Email.	OK

		Không điền thông tin đây đủ, đúng định dạng vào form	Sẽ báo lỗi :“ Không được bỏ trống hoặc sai định dạng” 	Cá nhân	Sẽ báo lỗi :“ Không được bỏ trống hoặc sai định dạng” 	OK
5	Đăng nhập bằng tài khoản Google	Bấm vào nút “đăng nhập bằng Google”	Một popup sẽ hiện lên để lựa chọn tài khoản Google để đăng nhập.	Cá nhân	Một popup sẽ hiện lên để lựa chọn tài khoản Google để đăng nhập. Nếu người dùng đăng nhập lần đầu, thì sẽ bắt xác minh SĐT qua OTP và chuyển sang màn hình trang chủ.	OK
6	Đăng xuất	Bấm đăng xuất	Tài khoản thoát khỏi ứng dụng và quay trở về màn hình đăng nhập.	Cá nhân	Tài khoản thoát khỏi ứng dụng và quay trở về màn hình đăng nhập.	OK


Màn hình trang chủ
ID	Title	Pre- condition	Description	Expected output	Actual output	Status
1	Kiểm tra kết nối 	Nếu người dùng chưa kết nối vào internet	Hiển thị một màn hình “no internet”	Trang chủ	Hiển thị một màn hình “no internet”	OK
2	Tìm kiếm	Bấm vào thanh tìm kiếm hoặc nút tìm kiếm	Hiển thị màn hình tìm kiếm và lịch sử tìm kiếm	Trang chủ	Hiển thị màn hình tìm kiếm và lịch sử tìm kiếm sau khi tìm kiếm có thể lọc theo: giá, loại phòng…	OK
3	Gợi ý khu vực có nhiều phòng	Khi bấm vô khu vực gợi ý	Hiện thi danh sách phòng trọ nằm trong khu vực được gợi ý.	Trang chủ	Hiện thi danh sách phòng trọ nằm trong khu vực được gợi ý. Có thể lọc theo: giá, tiện ích …	OK
4	Danh sách phòng trọ	Bấm vào một phòng trọ.	Hiển thị chi tiết phòng trọ đó	Trang chủ	Hiển thị chi tiết phòng trọ đó.	OK
5	Chi tiết phòng trọ	Bấm vào hình trái tim.	Thêm phòng trọ vào danh sách ưa thích.	Trang chủ	Trái tim chuyển màu đỏ, một thông báo hiện lên: “Đã thêm vào danh sách ưa thích”.	OK
		Bấm vào nút “báo cáo”	Một popup hiện lên để ghi nôi dung báo cáo cho admin	Trang chủ	Một popup hiện lên để ghi nôi dung báo cáo cho admin	OK
		Bấm vào nút “Liên hệ”	Sẽ chuyển sang ứng dụng gọi điện.	Trang chủ	Sẽ chuyển sang ứng dụng gọi điện và tự động điền số điện thoại của chủ trọ.	OK
		Bấm vào nút “Check Map”	Sẽ hiển thị ví trí phòng trọ trên bản đồ.	Trang chủ.	Sẽ hiển thị ví trí phòng trọ trên bản đồ và có nút chỉ đường để mở google map.	OK
		Bấm vào nút “Viết bình luận”	Hiển thị màn hình đánh giá và bình luận.	Trang chủ	Hiển thị màn hình đánh giá và bình luận, có thể xem lại bình luận của mình và người khác.	OK

Màn hình ở ghép
ID	Title	Pre- condition	Description	Expected output	Actual output	Status
1	Danh sách người tìm ở ghép	Khi bấm vào biểu tưởng ở ghép ở bottom navition 	Chuyển sang màn hình hiển thi danh sách người tìm ở ghép	Ở ghép	Chuyển sang màn hình hiển thi danh sách người tìm ở ghép	OK
2	Thông tin người tim ở ghép	Bấm vào một người xác định	Chuyển sang màn hình chi tiết thông tin của người tìm ở ghép.	Ở ghép	Chuyển sang màn hình chi tiết thông tin của người tìm ở ghép và yêu cầu ở ghép.	OK
		Bấm vào nút liên hệ	Chuyển sang ứng dụng gọi điện.	Ở ghép	Chuyển sang ứng dụng gọi điện và tự động điền SĐT.	OK

Bản đồ
ID	Title	Pre- condition	Description	Expected output	Actual output	Status
1	Hiển thị bản đồ theo vị trí hiện tại của người dùng	Khi bấm vào biểu tưởng bản đồ ở bottom navition 	Chuyển sang màn hình có hiển thị bản đồ	Bản đồ	Chuyển sang màn hình có hiển thị bản đồ.	OK
2	Hiện thi nhà trọ theo vị trí trên bản đồ	Khi người dùng tìm kiếm ở một vị trí xác định	Hiển thị những icon cho biết ở vị trí đó có nhà trọ cho thuê.	Bản đồ	Hiển thị những icon cho biết ở vị trí đó có nhà trọ cho thuê.	OK
		Bấm vào một màn nhà trọ trên bản đồ	Một dialog sẽ xuất hiện và hiển thị chi tiết thông tin của phòng đó.	Bản đồ	Một dialog sẽ xuất hiện và hiển thị chi tiết thông tin của phòng đó.	OK
		Bấm vào ô tìm kiếm	Chuyển sang màn hình tìm kiếm.	Bản đồ	Chuyển sang màn hình tìm kiếm	OK
		Bấm vào thông tin chi tiết của nhà trọ	Chuyển sang màn hình chi tiết của phòng trọ.	Bản đồ	Chuyển sang màn hình chi tiết của phòng trọ.	OK

Màn hình tài khoản
ID	Title	Pre- condition	Description	Expected output	Actual output	Status
1	Cập nhật thông tin cá nhân	Bấm vào phần “Cập nhập thông tin cá nhân”	Chuyển đến màn hình chi tiết thông tin cá nhân	Tài khoản	Chuyển đến màn hình chi tiết thông tin cá nhân	OK

2		Bấm vào icon bút chì	Hiển thị ra thông tin người dùng và nút đổi mật khẩu	Tài khoản	Hiển thị ra thông tin người dùng và nút đổi mật khẩu	OK
3		Nếu chưa điền đủ thông tin để đổi mật khẩu mà bấm nút “LƯU MẬT KHẨU”	Một thông báo yêu cầu người dùng điền đủ thông tin yêu cầu	Tài khoản	Một thông báo yêu cầu người dùng điền đủ thông tin yêu cầu	OK
4		Nếu điền không đúng thông tin để đổi mật khẩu	Một thông báo xuất hiện yêu cầu người dùng nhập đúng thông tin yêu cầu	Tài khoản	Một thông báo xuất hiện yêu cầu người dùng nhập đúng thông tin yêu cầu	OK
5		Nếu điền đúng và đầy đủ thông tin	Một thông báo sẽ hiện lên rằng mật khẩu đã được thay đổi	Tài khoản	Một thông báo sẽ hiện lên rằng mật khẩu đã được thay đổi	OK
6		Bấm vào dấu tích	Tắt phần đổi mật khẩu và xác nhận thay đổi thông tin cá nhân	Tài khoản	Tắt phần đổi mật khẩu và xác nhận thay đổi thông tin cá nhân	OK
7	Phòng của tôi	Bấm vào ô phòng của tôi	Chuyển sang màn hình hiển thị danh sách chi tiết phòng của tôi	Tài khoản	Chuyển sang màn hình hiển thị danh sách chi tiết phòng của tôi	OK
8		Bấm vô icon thêm	Chuyển sang màn hình thêm phòng trọ	Tài khoản	Chuyển sang màn hình thêm phòng trọ	OK
9		Bấm vào nút xác nhận ở màn hình vị trí khi chưa điền đủ thông tin	Một thông báo hiện lên yêu cầu người dùng điền đầy đủ thông tin	Tài khoản	Một thông báo hiện lên yêu cầu người dùng điền đầy đủ thông tin	OK
10		Bấm vào nút xác nhận ở màn hình vị trí khi đã điền đủ thông tin	Chuyển sang màn hình thông tin	Tài Khoản	Chuyển sang màn hình thông tin	OK
11		Bấm vào nút xác nhận ở màn hình thông tin  khi chưa  điền đủ thông tin	Một thông báo hiện lên yêu cầu người dùng điền đầy đủ thông tin	Tài khoản	Một thông báo hiện lên yêu cầu người dùng điền đầy đủ thông tin	OK
12		Bấm vào nút xác nhận ở màn hình thông tin  khi đã điền đủ và chính xác thông tin	Chuyển sang màn hình tiện ích	Tài khoản	Chuyển sang màn hình tiện ích	OK
13		Bấm vào nút xác nhận ở màn hình tiện ích khi chưa chọn ảnh và tiện ích	Một thông báo một hiện lên yêu cầu người dụng thêm ảnh và chọn bằng tiện ích.	Tài khoản	Một thông báo một hiện lên yêu cầu người dụng thêm ảnh và chọn bằng tiện ích.	OK
14		Bấm vào nút xác nhận ở màn hình tiện ích khi đã chọn chọn ảnh và tiện ích	Chuyển sang màn hình xác nhận	Tài khoản	Chuyển sang màn hình xác nhận	OK
15		Bấm vào nút đăng phòng khi chưa điền đủ thông tin 	Một thông báo yêu cầu người dùng điền đầy đủ thông tin 	Tài khoản 	Một thông báo yêu cầu người dùng điền đầy đủ thông tin	OK
16		Bấm vào nút đăng phòng và điền đầy đủ thông tin	Trở về màn hình phòng trọ chi tiết	Tài khoản 	Trở về màn hình phòng trọ chi tiết	OK
17		Nếu phòng trọ chưa được duyệt	Danh sách phòng trọ sẽ hiển thị là :” phòng trọ đang chờ duyệt”	Tài khoản	Danh sách phòng trọ sẽ hiển thị là :” phòng trọ đang chờ duyệt”	OK
18		Nếu phòng trọ đã được duyệt	Danh sách phòng trọ sẽ hiển thị các chức năng: sửa, xóa, số lượt xem phòng, bình luận, thay đổi trạng thái phòng	Tài khoản	Danh sách phòng trọ sẽ hiển thị các chức năng: sửa, xóa, số lượt xem phòng, bình luận, thay đổi trạng thái phòng	OK
19		Nếu phòng trọ đã sửa và chưa được duyệt	Danh sách phòng trọ sẽ hiển thị là :” phòng trọ đang chờ duyệt”	Tài khoản	Danh sách phòng trọ sẽ hiển thị là :” phòng trọ đang chờ duyệt”	OK
20		Nếu phòng trọ đã sửa và được duyệt	Danh sách phòng trọ sẽ hiển thị các chức năng: sửa, xóa, số lượt xem phòng, bình luận, thay đổi trạng thái phòng	Tài khoản	Danh sách phòng trọ sẽ hiển thị các chức năng: sửa, xóa, số lượt xem phòng, bình luận, thay đổi trạng thái phòng	OK
21	Tìm ở ghép của tôi 	Bấm vào mục tìm ở ghép của tôi 	Chuyển sang màn hình thông tin chi tiết ở ghép	Tài khoản	Chuyển sang màn hình thông tin chi tiết ở ghép	OK
22		Bấm vào icon thêm ở ghép	Chuyển sang màn hình thêm ở ghép	Tài khoản	Chuyển sang màn hình thêm ở ghép	OK
23		Bấm vào nút ”Đăng” trong màn hình thêm ở ghép	Trở về màn hình thông tin ở ghép và hiển thị danh sách ở ghép đã đăng	Tài khoản	Trở về màn hình thông tin ở ghép và hiển thị danh sách ở ghép đã đăng	OK
24		Bấm vào nút “Xóa Bài”	Xóa bài được chọn và làm mới lại trang tìm ở ghép của tôi	Tài khoản 	Xóa bài được chọn và làm mới lại trang tìm ở ghép của tôi	OK
25	Góp ý và phát triển	Bấm vào mục “Góp ý và phát triển”	Chuyển sang màn hình góp ý và phát triển	Tài khoản	Chuyển sang màn hình góp ý và phát triển, có thể đánh giá và góp ý.	OK
		Bấm nút gửi ở màn hình “góp ý và phát triển”	Đánh giá và góp ý sẽ gửi sang tài khoản admin và hiện thông báo cảm ơn.	Tài khoản	Đánh giá và góp ý sẽ gửi sang tài khoản admin và hiện thông báo cảm ơn.	OK
26	Phiên bản	Bấm vào mục “Phiên bản”	Hiển thị phiên bản đang sử dụng.	Tài khoản	Hiển thị phiên bản đang sử dụng.	OK

6.2.	Test case của Admin
Admin
ID	Title	Pre- condition	Description	Expected output	Actual output	Status
1	Đăng nhập vào tài khoản admin	Bấm nút đăng nhập	Chuyển sang màn hình quản lí của admin	Admin	Chuyển sang màn hình quản lí của admin	OK
2	Đăng xuất	Bấm đăng xuất	Tài khoản thoát khỏi ứng dụng và quay trở về màn hình đăng nhập.	Admin	Tài khoản thoát khỏi ứng dụng và quay trở về màn hình đăng nhập.	OK
3	Quản lí phòng đã đăng	Bấm vào mục phòng đã đăng	Hiển thị danh sách tất cả phòng trọ	Admin	Hiển thị danh sách tất cả phòng và có thể xem chi tiết từng phòng	OK
4	Quản lí chủ phòng	Bấm vào mục chủ phòng	Hiển thị danh sách chủ phòng và số lượng phòng đã đăng	Admin	Hiển thị danh sách chủ phòng, số lượng phòng đã đăng và chi tiết từng phòng	OK
5	Quản lí đơn báo cáo	Bấm vào mục đơn báo cáo	Hiển thị danh sách các phòng trọ bị báo cáo và lí do 	Admin	Hiển thị danh sách các phòng trọ bị báo cáo và lí do	OK
6		Bấm phòng trọ bị báo cáo 	Hiển thị chi tiết thông tin của phòng trọ bị báo cáo và có thể xóa phòng trọ	Admin	Hiển thị chi tiết thông tin của phòng trọ bị báo cáo và có thể xóa phòng trọ	OK
7	Duyệt phòng	Bấm vào mục phòng chờ duyệt	Chuyển sang màn hình hiển thị danh sách phòng trọ chờ duyệt	Admin	Chuyển sang màn hình hiển thị danh sách phòng trọ chờ duyệt	OK
8 		Bấm vào nút “Duyệt”	Một popup hiện lên để xác nhận duyệt phòng	Admin	Một popup hiện lên để xác nhận duyệt phòng	OK

7.	Kế hoạch test và phân chia công việc 
Kế hoạch làm app (từ ngày 4/11/2021 đến ngày 9/12/2021)
STT
	Ngày Thực hiện	Công việc	Thành viên thực hiện
1	
4/11/2021 - 7/11/2021	Tìm hiểu chức năng, và cách làm ứng dụng, phân tích yêu cầu của người dùng. Lên kế hoạch triển khai app
	

Tất cả thảnh viên
2	8/11/2021 -  11/11/2021	Phân tích database, Tạo cơ sở dữ liệu	
Tất cả thành viên
3	
12/11/2021 – 13/11/2021	Thiết kế giao diện màn hình chao, đăng nhập, đăng kí, quên mật khẩu	Huy(màn hình chào)
Giang(Đăng nhập)
Đạt(Đăng kí)
Tài(Quên mật khẩu)
4	
12/11/2021 – 13/11/2021	
Làm chức năng đăng nhập, đăng kí bằng email, google, quên mật khẩu	
Đăng nhập ,đăng kí bằng google (Huy & Đạt)
Đăng kí bằng email(Giang)
Quên mật khẩu(Tài)
5	14/11/2021 – 17/11/2021	
Thiết kế giao diện trang chủ, ở ghép, bản đồ, trang cá nhân	Trang chủ ( Đạt )
Ở ghép (Giang)
Bản đồ (Huy)
Trang cá nhân(Tài)

6	18/11/2021 – 20/11/2021	Thiết kế giao diện thêm phòng trọ	Màn hình vị trí(Huy)
Màn hình thông tin(Đạt)
Màn hình tiện ích và xác nhận ( Giang & Tài)
7	21/11/2021 – 25/11/ 2021 	Làm chức năng thêm, sửa, xóa phòng trọ	Huy
8	21/11/2021 – 25/11/2021	Làm chức năng admin duyệt phòng trọ, giao diện hiển thị chi tiết phòng trọ	Duyệt phòng trọ(Đạt)
Giao diện hiển thị chi tiết phòng trọ (Giang & Tài)
9	26/11/2021 – 31/11/2021	Chức năng tìm kiếm và lọc,
Phòng trọ của tôi,
Phòng trọ yêu thích,
Tìm ở ghép	Tìm kiếm (Giang & Huy  )
Phòng trọ của tôi (Huy)
Yêu thích phòng (Đạt )
Tìm ở ghép (Tài )
10	1/12/2021 – 3/12/2021	Chức năng đánh giá, bình luận. Báo cáo phòng trọ. Cập nhật thông tin cá nhân. Đổi mật khẩu.	Đánh giá, bình luận (Giang)
Báo cáo phòng (Tài)
Cập nhật thông tin (Huy)
Đổi mật khẩu (Đạt)
11	4/12/2021 – 7/12/2021	Chức năng check map và liện hệ.
Thiết kế biểu đồ thống kê của admin.
Thống kê phòng đã đăng cho admin.
Thống kê số chủ phòng cho admin.
Duyệt đơn báo cáo cho admin.	Checkmap, liên hệ (Huy)
Thiết kế biểu đồ admin (Huy)
Thống kê phòng (Đạt)
Thống kê số chủ trọ (Giang)
Duyệt báo cáo (Tài)

12	7/12/2021 – 9/12/2021	Kiểm tra và tối ưu lại app	Tất cả thành viên

IV.	TÀI LIỆU THAM KHẢO
	Một số tài liệu tham khảo ngoài của nhóm:
•	https://stackoverflow.com/
•	https://github.com/
•	https://firebase.google.com/docs
•	https://developer.android.com/ 
•	https://developers.google.com/
•	https://square.github.io/picasso/
•	https://material.io/develop
V.	ĐÁNH GIÁ DỰ ÁN
1.	Ưu điểm
	Giúp cho người dùng tìm phòng một cách thuận tiện và nhanh chóng
	Giao diện dễ sử dụng, đẹp mắt
	Tìm kiếm nhanh chóng, chính xác
	Bảo mật cao, an toàn cho người dùng
	Tiết kiệm được thời gian và công sức
	Hạn chế được rủi ro, lừa đảo
	Giúp cho chủ trọ tiếp cận được với nhiều người thuê hơn
	Dễ dàng tìm kiếm được người ở ghép phù hợp với mình
	Giúp người dùng xác định được số lượng phòng cho thuê tại một khu vực xác định
	Giúp người tìm kiếm phòng dễ dàng tìm được phòng trọ phù hợp với yêu cầu của mình
	Người tìm trọ có thể tham khảo ý kiến của những người đã từng thuê thông qua đánh giá 
	Người dùng có thể liên hệ dễ dàng với người cho thuê trọ hoặc người ở ghép
	Người dùng có thể xác định đúng được vị trí của phòng trọ một cách nhanh chóng

2.	Nhược điểm 
	Chưa thể sử dụng trên nhiều nền tảng.
	Bản đồ chưa thể sử dụng ở nhiều khu vực
	Kho lưu trữ dữ liệu còn hạn chế
3.	Cơ hội
	Dễ dàng tiếp cận được với số lượng người dùng đông đảo.
	Tăng cường mối liên hệ giữa người dùng và người cho thuê thông qua việc giao tiếp trực tuyến.
	Giúp người dùng có nhiều lựa chọn về các loại phòng trọ, chung cư….
	Tìm phòng trực tuyến tạo động lực phát triển công nghệ thông tin góp phần vào sự chuyển dịch cơ cấu kinh tế và hội nhập.
4.	Thách thức
	Đối thủ cạnh tranh đông đảo.
	Luôn thay đổi và cập nhật.
5.	Định hướng phát triển ứng dụng
	Phát triển ứng dụng lên nhiều nền tảng như: Website, ios ,... 
	Tải ứng dụng lên cửa hàng google play, AppStore.
	Mở rộng phạm vị phòng trọ cho toàn quốc.
	Vẫn còn 1 số giao diện chưa thực sự mượt, sẽ fix bug sau này.
	Cần có chiến lược phát triển rõ ràng, đồng thời sử dụng các mạng xã hội facebook để marketing cho sản phẩm. Bên cạnh đó cũng nên tìm hiểu xem đang có đối thủ nào ở gần không để vạch ra chiến lược để cạnh tranh dễ dàng hơn.
VI.	LỜI CẢM ƠN
Được học tập tại trường Cao Đẳng Thực Hành FPT Polytechnic với một môi trường năng động, giảng viên tận tình chỉ bảo và giúp đỡ đó chính là một trải nghiệm khó quên đối với học sinh chúng em, nhờ vậy chúng em có thêm nhiều kiến thức về ngành học của mình. Chúng em thật sự biết ơn sự giúp đỡ tận tình của giảng viên và đặc biệt hơn nữa là sự giúp đỡ của thầy Phan Văn Bình, nhờ thầy đã giúp đỡ và đưa ra những ý kiến, đóng góp giúp chúng em có thể hoàn thành dự án 1 nhanh chóng.

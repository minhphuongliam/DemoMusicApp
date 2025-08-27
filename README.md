# IN-PROGRESS PROJECT
# MusicApp (Android)

## Mô tả
Ứng dụng nghe nhạc Android với các chức năng cơ bản: đăng nhập/đăng ký bằng Firebase, nghe nhạc trực tuyến, lưu bài hát offline, tìm kiếm, và quản lý thư viện cá nhân. Ứng dụng sử dụng kiến trúc MVVM, Hilt cho Dependency Injection, và Room cho lưu trữ offline.

## Tính năng chính

### Authentication
- Đăng ký / đăng nhập bằng Email & Password qua Firebase Auth.
- Lưu thông tin người dùng (tên, email, tuổi) trên Firestore và EncryptedSharedPreferences.
- Tự động đăng nhập khi mở app.

### Home & Music Player
- Hiển thị danh sách bài hát, album, nghệ sĩ từ Deezer API.
- Phát nhạc trực tuyến với ExoPlayer, hỗ trợ pause, next, previous, và phát ở background với MediaStyle notification.
- Hỗ trợ lưu bài hát offline (ảnh bìa, file nhạc) sử dụng Room + MediaStore.

### Search & Library
- Tìm kiếm bài hát, nghệ sĩ, album.
- Lưu bài hát yêu thích vào thư viện cá nhân.

### Profile
- Xem thông tin người dùng (tên, email).
- Đăng xuất và quay lại màn hình đăng nhập.

### UI / UX
- Giao diện Material Design với Bottom Navigation.
- Hỗ trợ chế độ Dark / Light.
- Tối ưu hiển thị danh sách bài hát với RecyclerView và Glide.

## Công nghệ & thư viện
- **Android:** Kotlin, MVVM, ViewBinding, Navigation Component
- **Dependency Injection:** Hilt
- **Networking:** Retrofit, Gson, OkHttp
- **Database & Storage:** Firebase Firestore, EncryptedSharedPreferences, Room, MediaStore
- **Audio:** ExoPlayer
- **Image loading:** Glide

## Kiến trúc
- **Repository pattern:** Tách rõ data layer (DTO, API, Room) và domain layer (Model).
- **ViewModel:** Quản lý state và xử lý dữ liệu từ Repository, cung cấp LiveData cho UI.
- **UI Layer:** Fragment / Activity chỉ chịu trách nhiệm hiển thị, nhận LiveData từ ViewModel.

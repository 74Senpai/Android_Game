# Android_Game

> Dự án game Android đơn giản — bạn vui, người chơi nhảy, và bug thì… ít đi (hy vọng vậy 😄)

## Mô tả

“Android_Game” là một ứng dụng game dành cho Android, được viết bằng Java, sử dụng cấu trúc chuẩn của Android Studio (Gradle, module `app`, …) và nhằm mục đích học tập & thử nghiệm.
Bạn có thể coi nó như một khung để phát triển game nhỏ, hoặc làm mẫu để học cách xây dựng game Android.

## Công nghệ sử dụng

* Java (100 % code Java)
* Android (sử dụng cấu trúc Android Studio: có folder `app`, có Gradle wrapper)
* Build hệ thống: Gradle (có `build.gradle`, `gradle.properties`, `gradlew`, `gradlew.bat`)

## Cấu trúc thư mục chính

Dưới đây là các thành phần nổi bật từ repository:

```
├───.gradle
├───.idea
├───app
│   ├───build
│   └───src
│       ├───androidTest
│       │   └───java
│       │       └───com
│       │           └───example
│       │               └───gameproject
│       ├───main
│       │   ├───java
│       │   └───res
│       └───test
├───build
└───gradle
```

Giải thích nhanh:

* `app/`: module ứng dụng chính chứa code game.
* `gradle/`: thư mục cấu hình Gradle wrapper.
* `build.gradle`, `settings.gradle`: các script cấu hình build.
* `gradle.properties`: thiết lập môi trường build.
* `gradlew`, `gradlew.bat`: wrapper để build đồng bộ giữa máy Mac/Linux/Windows.

## Cách cài đặt & chạy trên máy của bạn

1. Clone repository về máy:

   ```bash
   git clone https://github.com/74Senpai/Android_Game.git
   cd Android_Game
   ```
2. Open dự án trong Android Studio (phiên bản tương thích Android API + Java).
3. Đợi Android Studio tải và cấu hình Gradle.
4. Kết nối thiết bị Android hoặc khởi chạy AVD (Android Virtual Device).
5. Chạy ứng dụng (`Run ‘app’`) — nếu có lỗi build, kiểm tra Android SDK, Gradle version, minSdkVersion, compileSdkVersion trong `app/build.gradle`.
6. (Tùy bạn) Cải tiến: thêm màn hình chính, menu, âm thanh, hiệu ứng… đi tới cấp “pro”.

## Tính năng hiện tại và phát triển

### Phát triển đề xuất

* Thêm màn hình “Menu” (Start / Options / Exit) để bắt đầu game thay vì vào luôn gameplay.
* Tích hợp âm thanh: nhạc nền + hiệu ứng.
* Tối ưu UX: hiển thị thông tin game (score, lives, level).
* Đóng gói và phát hành (APK) nếu muốn.

## Cấu hình build & môi trường

* Java version: phù hợp với Android (Java 17 theo `app/build.gradle`).
* Android Studio version: đề nghị phiên bản mới (ví dụ Arctic Fox / Bumblebee trở lên) để tương thích Gradle.
* Gradle plugin version và Android Gradle plugin – hãy kiểm tra `build.gradle` và cập nhật nếu cần.

## Góp phần & Hỗ trợ

Nếu bạn hoặc người khác muốn đóng góp:

* Fork repository, tạo branch mới, viết tính năng hoặc sửa bug, sau đó gửi Pull Request.
* Mở Issue nếu phát hiện lỗi hoặc muốn đề xuất tính năng mới.
* Đặt tên và comment code rõ ràng để người khác dễ hiểu.


## Kết

Cảm ơn bạn đã xem dự án này! Hy vọng nó là bước khởi đầu tốt cho những game Android vui nhộn của bạn 😄

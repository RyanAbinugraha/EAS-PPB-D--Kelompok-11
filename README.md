Aplikasi MyHabit

MyHabit adalah aplikasi Android sederhana yang dibuat dengan Kotlin yang memungkinkan pengguna untuk mendaftar, masuk, dan melacak kebiasaan harian mereka. Data disimpan di Firebase Realtime Database, dan antarmuka menggunakan tema marun dengan tata letak tengah untuk pengalaman yang bersih dan fokus.

Fitur Utama
- Autentikasi pengguna melalui Firebase Authentication
- Menambah kebiasaan baru yang disimpan ke cloud
- Melihat daftar kebiasaan dalam tampilan gulir menggunakan RecyclerView
- Umpan balik real-time melalui Toast dan logcat untuk status sukses atau gagal

Tampilan Layar
1. Daftar (RegisterActivity) untuk membuat akun baru
2. Masuk (LoginActivity) untuk login dengan email dan kata sandi
3. Beranda (HomeActivity) untuk menambah dan melihat daftar kebiasaan

Struktur Proyek
```
app/
├─ src/main/
│  ├─ java/com/example/myhabit/
│  │  ├─ auth/
│  │  │  ├─ LoginActivity.kt
│  │  │  └─ RegisterActivity.kt
│  │  ├─ data/
│  │  │  └─ Habit.kt
│  │  └─ home/
│  │     ├─ HomeActivity.kt
│  │     └─ HabitAdapter.kt
│  └─ res/
│     ├─ layout/
│     │  ├─ activity_login.xml
│     │  ├─ activity_register.xml
│     │  ├─ activity_home.xml
│     │  ├─ item_habit.xml
│     │  └─ …
│     └─ values/
│        ├─ colors.xml
│        └─ themes.xml
└─ build.gradle.kts
```

Cara Menjalankan
1. Clone repositori ini
2. Buka di Android Studio
3. Tambahkan file google-services.json ke direktori app/
4. Ganti URL Realtime Database di HomeActivity.kt dengan URL proyek Anda, misalnya:
   val databaseUrl = "https://myhabit-847e9-default-rtdb.asia-southeast1.firebasedatabase.app"
5. Sync Gradle dan jalankan aplikasi di emulator atau perangkat fisik

Penjelasan Kode
RegisterActivity.kt
- Memuat layout activity_register.xml
- Memvalidasi email dan kata sandi (minimal 6 karakter)
- Memanggil FirebaseAuth.createUserWithEmailAndPassword
- Setelah berhasil, berpindah ke LoginActivity

LoginActivity.kt
- Memuat layout activity_login.xml
- Memvalidasi kredensial pengguna
- Memanggil FirebaseAuth.signInWithEmailAndPassword
- Setelah berhasil, berpindah ke HomeActivity

Habit.kt (Model Data)
data class Habit(
    val name: String = "",
    val timestamp: Long = 0L
)

HabitAdapter.kt
- RecyclerView.Adapter yang mengikat objek Habit ke layout item_habit.xml
- Menampilkan nama kebiasaan dan waktu terformat dari timestamp

HomeActivity.kt
- Memuat layout activity_home.xml dengan ViewBinding
- Memeriksa pengguna sudah login
- Menginisialisasi DatabaseReference dengan URL eksplisit
- Mengatur RecyclerView dan memanggil fungsi loadHabits untuk memuat daftar kebiasaan
- Tombol Save Habit:
  1. Validasi input tidak kosong
  2. Menyimpan Habit ke Firebase
  3. Jika berhasil: menampilkan Toast, membersihkan input, memuat ulang daftar
  4. Jika gagal: menampilkan Toast error dan mencatat exception di log
- Fungsi loadHabits membaca data sekali dan memperbarui adapter serta menampilkan jumlah kebiasaan

Tema UI
- Warna marun sebagai warna primer dan putih untuk teks
- Formulir berada di tengah secara vertikal dan horizontal untuk fokus pengguna

Lisensi
Proyek ini bersifat open source di bawah lisensi MIT.

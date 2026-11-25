# TokoBajuRafiNaKal

## Anggota Kelompok
| Nama                      | NPM            |
|---------------------------|----------------|
| Nala Ahmad Rayyan Saragih | `140810250061` | 
| Rafi Abdul Hikam          | `140810250064` | 
| Muhammad Haikal Muzhoffar | `140810250082` | 

## Deskripsi
Aplikasi kasir dan pengecekan stok aebuah toko baju yang berbasis aplikasi 

## Fungsionalitas
- Katalog: Menampilkan nama, harga, dan gambar produk
- Keranjang Belanja: Menyimpan produk yang ingin dibeli
- Kasir: Melaksanakan transaksi untuk barang yang disimpan dalam keranjang
- Penyimpanan: Menghapus dan menambah stok barang ke database.
- Beranda & Navigasi: Memisahkan akses menu utama antara **Kasir** dan **Admin** serta menyediakan menu bar navigasi antar halaman (`HomeFrame` & `NavBar`).
- Pencarian & Detail Produk: Mencari produk secara *real-time* berdasarkan nama serta melihat informasi lengkap produk beserta deskripsi dan pratinjau gambar (`ItemDetailFrame`).
- Manajemen Produk Lengkap (CRUD): Menambahkan produk baru beserta foto & deskripsi (`NewItem`), memperbarui data/gambar/stok produk (`EditItemFrame`), serta menghapus produk.
- Riwayat Transaksi: Menampilkan histori transaksi penjualan beserta rincian barang, subtotal, total belanja, nominal bayar, dan kembalian (`TransactionHistoryFrame`).
- Log Aktivitas Stok: Mencatat dan menampilkan riwayat keluar-masuk stok produk secara otomatis untuk stok awal (`INITIAL`), penjualan (`SALE`), pembaruan admin (`ADMIN`), dan penghapusan produk (`DELETED`) (`ItemLogFrame`).

## Alur

### 1. Alur Kasir (Transaksi Penjualan)
1. Membuka Aplikasi: Sistem memuat antarmuka (FlatLaf), menghubungkan ke database, dan menampilkan halaman Beranda (`HomeFrame`) untuk memilih menu **Kasir (Mulai Transaksi)** serta navigasi cepat melalui `NavBar`.
2. Katalog Produk (`ItemListFrame`): Menampilkan daftar baju beserta harga dan **jumlah stok saat ini** (`current_stock`), dilengkapi fitur pencarian nama produk, tombol **Detail** (`ItemDetailFrame`) untuk melihat foto & deskripsi produk, serta tombol akses ke **Riwayat Transaksi**.
3. Pilih & Masuk Keranjang: Pengguna memilih produk untuk dimasukkan ke keranjang (indikator jumlah item pada tombol keranjang diperbarui otomatis), lalu di halaman `CartFrame` dapat menambah/mengurangi kuantitas (`+`/`-`), menghapus item tertentu, atau mengosongkan keranjang.
4. Checkout (Pembayaran):
   - Sistem menghitung total belanja, menerima input nominal pembayaran (`paid`), memvalidasi kecukupan bayar, dan menghitung uang kembalian.
   - Menyimpan data utama ke tabel `transactions` (termasuk `total` dan `paid`).
   - Menyimpan rincian belanja ke tabel `transaction_details`.
   - Mengurangi `current_stock` di tabel `products`.
   - Mencatat aktivitas pengeluaran stok secara otomatis ke `product_logs` (dengan status `SALE`).
5. Selesai: Menampilkan ringkasan pembayaran & kembalian, mengosongkan keranjang, dan mengarahkan kembali ke katalog untuk transaksi berikutnya.

### 2. Alur Admin (Manajemen Stok)
1. Menu Kelola Stok: Pengguna (admin) memilih menu **Admin (Kelola Inventory)** dari `HomeFrame` untuk membuka halaman `InventoryFrame` yang dilengkapi fitur pencarian produk.
2. Tambah Produk Baru (`NewItem`): Admin dapat menginput nama, harga, stok awal, deskripsi, serta memilih foto produk; sistem menyimpan data ke `products` dan mencatat stok awal ke `product_logs` (`INITIAL`).
3. Pilih & Edit Barang (`EditItemFrame`): Memilih baju yang akan diperbarui informasinya atau ditambah/dikurangi stoknya (*restock*).
4. Simpan Perubahan / Hapus Barang:
   - Saat perubahan disimpan, sistem memperbarui data & `current_stock` di tabel `products`, serta mencatat selisih perubahan stok ke `product_logs` (`ADMIN`).
   - Saat produk dihapus, sistem mencatat pengurangan sisa stok ke `product_logs` (`DELETED`) dan melepas relasi `product_id` pada `transaction_details` agar histori transaksi tetap terjaga.
5. Pemantauan Log & Riwayat: Admin dapat membuka halaman **Log Stok Produk** (`ItemLogFrame`) untuk memantau histori perubahan stok dan **Riwayat Transaksi** (`TransactionHistoryFrame`) untuk melihat rekap penjualan.


## Rancangan Database

### Products
| Nama Kolom      | Tipe Data      | Keterangan                                      |
|-----------------|----------------|-------------------------------------------------|
| `product_id`    | INT (PK)       | ID unik untuk setiap baju (Auto Increment).     |
| `product_name`  | VARCHAR(100)   | Nama atau model baju.                           |
| `price`         | DECIMAL(10,2)  | Harga baju.                                     |
| `product_image` | LONGBLOB       | Menyimpan file gambar baju dalam bentuk byte.   |
| `current_stock` | INT            | Stok produk saat ini.                           |
| `product_desc`  | VARCHAR(500)   | Deskripsi atau keterangan detail produk baju.   |

### Transactions
| Nama Kolom         | Tipe Data      | Keterangan                                      |
|--------------------|----------------|-------------------------------------------------|
| `transaction_id`   | INT (PK)       | ID unik transaksi (Auto Increment).             |
| `transaction_date` | DATE           | Tanggal transaksi dilakukan.                    |
| `total`            | DECIMAL(10,2)  | Total harga keseluruhan dari transaksi tersebut.|
| `paid`             | INT            | Nominal uang yang dibayarkan oleh pembeli.      |

### Transaction_details
| Nama Kolom         | Tipe Data      | Keterangan                                      |
|--------------------|----------------|-------------------------------------------------|
| `detail_id`        | INT (PK)       | ID unik detail transaksi (Auto Increment).      |
| `transaction_id`   | INT (FK)       | Foreign Key, merujuk ke `transaction_id` di tabel `transactions`. |
| `product_id`       | INT (FK)       | Foreign Key, merujuk ke `product_id` di tabel `products`.         |
| `quantity`         | INT            | Jumlah barang (baju) yang dibeli pada item ini. |
| `subtotal`         | DECIMAL(10,2)  | Subtotal harga (`quantity` x harga produk).     |

### Product_logs
| Nama Kolom        | Tipe Data      | Keterangan                                      |
|-------------------|----------------|-------------------------------------------------|
| `log_id`          | INT (PK)       | ID unik log stok (Auto Increment).              |
| `product_id`      | INT (FK)       | Foreign Key, merujuk ke tabel `products`.       |
| `log_date`        | DATE           | Waktu terjadinya penambahan/pengurangan stok.   |
| `quantity_change` | INT            | Jumlah perubahan (`+` untuk stok masuk, `-` untuk terjual/keluar). |
| `log_type`        | ENUM           | Tipe aktivitas perubahan stok (`SALE`, `ADMIN`, `INITIAL`, `DELETED`). |

## Skema Class

### Paket: `com.mycompany.tokobajurafinakal` 
*   `TokoBajuRafiNaKal.java`: *Class* utama yang berfungsi untuk menginisialisasi tema FlatDarkLaf dan memicu tampilan awal aplikasi (`HomeFrame`).

### Paket: `com.mycompany.tokobajurafinakal.entity`
*   `Product.java`: Representasi entitas data produk baju (`id`, `productName`, `price`, `productImage`, `currentStock`, `productDesc`).
*   `ProductLog.java`: Representasi entitas catatan log perubahan stok produk.
*   `Transaction.java`: Representasi entitas transaksi penjualan.
*   `TransactionDetail.java`: Representasi entitas rincian item produk dalam suatu transaksi.

### Paket: `com.mycompany.tokobajurafinakal.model`
*   `CartItem.java`: Menyimpan item produk beserta jumlah beli dan perhitungan subtotal di dalam keranjang.
*   `CartModel.java`: *Singleton* untuk mengelola daftar belanja sementara, validasi batas stok, serta perhitungan total harga.
*   `ProductListModel.java`: *Class* model pendukung daftar produk.
*   `ProductLogModel.java`: Model tampilan gabungan data log stok dengan nama produk untuk `ItemLogFrame`.
*   `TransactionHistoryModel.java`: Model tampilan gabungan riwayat transaksi beserta rincian barang, total, nominal bayar, dan kembalian untuk `TransactionHistoryFrame`.

### Paket: `com.mycompany.tokobajurafinakal.view` 
*   `HomeFrame.java`: *Frame* beranda utama yang memisahkan navigasi awal antara mode **Kasir** dan **Admin**.
*   `NavBar.java`: Komponen menu bar navigasi untuk berpindah cepat antar halaman di dalam aplikasi.
*   `ItemListFrame.java`: *Frame* katalog kasir yang menampilkan daftar baju, stok tersedia, harga, fitur pencarian produk, tombol detail, dan aksi tambah ke keranjang.
*   `ItemDetailFrame.java`: *Frame* untuk melihat detail lengkap produk (ID, nama, harga, stok, deskripsi, dan gambar produk dari BLOB) serta menambahkannya ke keranjang.
*   `CartFrame.java`: *Frame* untuk melihat dan mengatur isi keranjang belanja (`+`, `-`, hapus item, kosongkan keranjang), menginput nominal pembayaran, dan melakukan aksi Checkout.
*   `InventoryFrame.java`: *Frame* khusus admin untuk melihat, mencari, dan menghapus produk, serta membuka menu tambah produk, edit produk, log stok, dan riwayat transaksi.
*   `NewItem.java`: *Frame* form admin untuk menambahkan produk baru beserta deskripsi dan *upload/preview* foto produk.
*   `EditItemFrame.java`: *Frame* form admin untuk mengubah informasi produk, mengganti/menghapus foto, dan memperbarui jumlah stok (*restock*).
*   `ItemLogFrame.java`: *Frame* untuk menampilkan tabel histori aktivitas perubahan stok produk (`SALE`, `ADMIN`, `INITIAL`, `DELETED`).
*   `TransactionHistoryFrame.java`: *Frame* untuk menampilkan tabel riwayat transaksi penjualan lengkap dengan nominal bayar dan kembalian.
*   `ButtonRenderer.java` & `ButtonEditor.java`: Komponen kustom *renderer* dan *editor* agar tombol aksi dapat ditampilkan dan diklik di dalam sel `JTable`.
*   `UiUtil.java`: *Class* utilitas tampilan untuk menyeragamkan gaya tombol, *font*, warna, tabel, navigasi kembali, serta format mata uang Rupiah.

### Paket: `com.mycompany.tokobajurafinakal.repository` (Logika & Pemrosesan)
*   `DatabaseUtil.java`: *Class* utilitas yang menyediakan konfigurasi dan koneksi JDBC ke database MySQL (`tokobajurafinakal`).
*   `ProductRepository.java`: Menangani operasi CRUD produk, termasuk menyimpan/mengambil gambar BLOB & deskripsi, memperbarui `current_stock`, dan mencatat histori otomatis ke `product_logs` (`INITIAL`, `ADMIN`, `DELETED`).
*   `TransactionRepository.java`: Menangani logika *checkout* secara transaksional (insert ke `transactions` dan `transaction_details`, pemotongan stok di `products`, serta pencatatan `SALE` ke `product_logs`) sekaligus pengambilan riwayat transaksi.
*   `LogRepository.java`: Menangani pengambilan daftar histori perubahan stok dari tabel `product_logs` yang digabungkan dengan tabel `products`.



# TokoBajuRafiNaKal

## Deskripsi
Aplikasi kasir dan pengecekan stok aebuah toko baju yang berbasis aplikasi 

## Fungsionalitas
- Katalog: Menampilkan nama, harga, dan gambar produk
- Keranjang Belanja: Menyimpan produk yang ingin dibeli
- Kasir: Melaksanakan transaksi untuk barang yang disimpan dalam keranjang
- Penyimpanan: Menghapus dan menambah stok barang ke database.

## Alur

### 1. Alur Kasir (Transaksi Penjualan)
1. Membuka Aplikasi: Sistem memuat antarmuka (FlatLaf) dan menghubungkan ke database.
2. Katalog Produk: Menampilkan daftar baju beserta gambar dan **jumlah stok saat ini** (`current_stock`).
3. Pilih & Masuk Keranjang: Pengguna memilih produk dan menentukan jumlah beli, lalu memasukkannya ke keranjang (halaman `CartFrame`).
4. Checkout (Pembayaran):
   - Sistem menghitung total belanja.
   - Menyimpan data utama ke tabel `transactions`.
   - Menyimpan rincian belanja ke tabel `transaction_details`.
   - Mengurangi `current_stock` di tabel `products`.
   - Mencatat aktivitas pengeluaran stok secara otomatis ke `product_logs` (dengan status `SALE`).
5. Selesai: Keranjang dikosongkan dan aplikasi siap untuk transaksi berikutnya.

### 2. Alur Admin (Manajemen Stok)
1. Menu Kelola Stok: Pengguna (admin) membuka halaman khusus manajemen barang.
2. Pilih Barang: Memilih baju yang akan ditambah stoknya (restock).
3. Input Jumlah: Memasukkan jumlah barang yang masuk dari *supplier*.
4. Simpan Perubahan: 
   - Sistem menambahkan jumlah tersebut ke `current_stock` di tabel `products`.
   - Sistem mencatat histori penambahan ini ke `product_logs`.


## Rancangan Database

### Products
| Nama Kolom      | Tipe Data      | Keterangan                                      |
|-----------------|----------------|-------------------------------------------------|
| `id`            | INT (PK)       | ID unik untuk setiap baju (Auto Increment).     |
| `product_name`  | VARCHAR(100)   | Nama atau model baju.                           |
| `price`         | DECIMAL(10,2)  | Harga baju.                                     |
| `product_image` | LONGBLOB       | Menyimpan file gambar baju dalam bentuk byte.   |
| `current_stock` | INT            | Stok produk saat ini.                           |

### Transactions
| Nama Kolom         | Tipe Data      | Keterangan                                      |
|--------------------|----------------|-------------------------------------------------|
| `id`               | INT (PK)       | ID unik transaksi (Auto Increment).             |
| `transaction_date` | DATETIME       | Tanggal dan waktu transaksi dilakukan.          |

### Transaction_details
| Nama Kolom         | Tipe Data      | Keterangan                                      |
|--------------------|----------------|-------------------------------------------------|
| `id`               | INT (PK)       | ID unik detail transaksi (Auto Increment).      |
| `transaction_id`   | INT (FK)       | Foreign Key, merujuk ke `id` di tabel `transactions`. |
| `product_id`       | INT (FK)       | Foreign Key, merujuk ke `id` di tabel `products`.     |
| `quantity`         | INT            | Jumlah barang (baju) yang dibeli pada item ini. |
| `subtotal`         | DECIMAL(10,2)  | Subtotal harga (`quantity` x harga produk).     |
| `total_amount`     | DECIMAL(10,2)  | Total harga keseluruhan dari transaksi tersebut.|

### Product_logs
| Nama Kolom        | Tipe Data      | Keterangan                                      |
|-------------------|----------------|-------------------------------------------------|
| `id`              | INT (PK)       | ID unik log stok (Auto Increment).              |
| `product_id`      | INT (FK)       | Foreign Key, merujuk ke tabel `products`.       |
| `log_date`        | DATETIME       | Waktu terjadinya penambahan/pengurangan stok.   |
| `quantity_change` | INT            | Jumlah perubahan (`+` untuk stok masuk, `-` untuk terjual/keluar). |
| `reference_id`    | INT            | (Opsional) Merujuk ke `transaction_id` jika perubahan karena penjualan. |


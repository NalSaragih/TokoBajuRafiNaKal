# TokoBajuRafiNaKal

## Deskripsi
Aplikasi kasir dan pengecekan stok aebuah toko baju yang berbasis aplikasi 

## Fungsionalitas
- Katalog: Menampilkan nama, harga, dan gambar produk
- Keranjang Belanja: Menyimpan produk yang ingin dibeli
- Kasir: Melaksanakan transaksi untuk barang yang disimpan dalam keranjang
- Penyimpanan: Menghapus dan menambah stok barang ke database.

## Alur (user)


## Rancangan Database

### Products
| Nama Kolom      | Tipe Data      | Keterangan                                      |
|-----------------|----------------|-------------------------------------------------|
| `id`            | INT (PK)       | ID unik untuk setiap baju (Auto Increment).     |
| `product_name`  | VARCHAR(100)   | Nama atau model baju.                           |
| `price`         | DECIMAL(10,2)  | Harga baju.                                     |
| `product_image` | LONGBLOB       | Menyimpan file gambar baju dalam bentuk byte.   |

### Transactions
| Nama Kolom         | Tipe Data      | Keterangan                                      |
|--------------------|----------------|-------------------------------------------------|
| `id`               | INT (PK)       | ID unik transaksi (Auto Increment).             |
| `transaction_date` | DATETIME       | Tanggal dan waktu transaksi dilakukan.          |
| `customer_name`    | VARCHAR(100)   | Nama pembeli (opsional/bisa dikosongkan).       |

### Transaction_details
| Nama Kolom         | Tipe Data      | Keterangan                                      |
|--------------------|----------------|-------------------------------------------------|
| `id`               | INT (PK)       | ID unik detail transaksi (Auto Increment).      |
| `transaction_id`   | INT (FK)       | Foreign Key, merujuk ke `id` di tabel `transactions`. |
| `product_id`       | INT (FK)       | Foreign Key, merujuk ke `id` di tabel `products`.     |
| `quantity`         | INT            | Jumlah barang (baju) yang dibeli pada item ini. |
| `subtotal`         | DECIMAL(10,2)  | Subtotal harga (`quantity` x harga produk).     |
| `total_amount`     | DECIMAL(10,2)  | Total harga keseluruhan dari transaksi tersebut.|


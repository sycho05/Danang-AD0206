package com.aqua.aquascape.Database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "barang_table")
data class Barang(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "nomor") var nomor: Int,
    @ColumnInfo(name = "jenis") var jenis: String,
    @ColumnInfo(name = "nama") var nama: String,
    @ColumnInfo(name = "deskripsi") var deskripsi: String,
    @ColumnInfo(name = "harga") var harga: String,
    @ColumnInfo(name = "gambar") var gambar: String,
    @ColumnInfo(name = "alamat") var alamat: String
): Parcelable
package com.aqua.aquascape.Database

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Dao
import com.aqua.aquascape.Database.Barang

@Dao
interface Dao{
    @Query("SELECT * from barang_table ORDER BY nama ASC")
    fun getAlphabetizedBarang(): LiveData<List<Barang>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(barang: Barang)

    @Query("UPDATE barang_table SET jenis =:strJenis, nama =:strNama, deskripsi =:strDeskripsi, harga =:strHarga, gambar =:strGambar, alamat =:strAlamat WHERE nomor =:IntNomor")
    fun update(IntNomor: Int, strJenis:String, strNama:String, strDeskripsi:String, strHarga:String, strGambar:String, strAlamat:String)

    @Query("DELETE FROM barang_table")
    suspend fun deleteAll()

    @Delete
    fun delete(barang: Barang)

    @Query("SELECT * FROM barang_table")
    fun getCursorAll(): Cursor
}
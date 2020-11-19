package com.aqua.aquascape.Database

import androidx.lifecycle.LiveData
import com.aqua.aquascape.Database.Barang
import com.aqua.aquascape.Database.Dao
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class DatabaseRepository(private val dao: Dao) {

    val allBarang: LiveData<List<Barang>> = dao.getAlphabetizedBarang ()

    suspend fun insert(listBarang: Barang) {
        dao.insert(listBarang)
    }
    fun update(listBarang: Barang) {
        doAsync {
            dao.update(listBarang.nomor,listBarang.jenis,listBarang.nama,listBarang.deskripsi,listBarang.harga,listBarang.gambar,listBarang.alamat)
            uiThread {

            }
        }
    }
    fun deleteData(listBarang: Barang) {
        doAsync {
            dao.delete(listBarang)
            uiThread {

            }
        }
    }
}
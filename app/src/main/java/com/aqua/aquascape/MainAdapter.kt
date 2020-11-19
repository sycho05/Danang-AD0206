package com.aqua.aquascape

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aqua.aquascape.Database.Barang
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class MainAdapter internal constructor(
    val context: Context
) : RecyclerView.Adapter<MainAdapter. BarangViewHolder>() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var listBarang = emptyList<Barang>()
    inner class BarangViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgPhoto: ImageView = itemView.findViewById(R.id.img_item_photo)
        val namaTextView: TextView = itemView.findViewById(R.id.tv_item_name)
        val alamatItemView: TextView = itemView.findViewById(R.id.tv_item_price)
    }
    private var onItemClickCallback: OnItemClickCallback? = null
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
    interface OnItemClickCallback {
        fun onItemClicked(data: Barang)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BarangViewHolder {
        val itemView = inflater.inflate(R.layout.activity_home_item, parent, false)
        return BarangViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: BarangViewHolder, position: Int) {
        val current = listBarang[position]
        Glide.with(holder.itemView.context)
            .load(current.gambar)
            .apply(RequestOptions().override(350, 550))
            .into(holder.imgPhoto)
        holder.namaTextView.text = current.nama
        holder.alamatItemView.text = current.harga
        holder.itemView.setOnClickListener{
            onItemClickCallback?.onItemClicked(current)
        }
    }
    internal fun setBarang(listBarang: List<Barang>) {
        this.listBarang = listBarang
        notifyDataSetChanged()
    }
    override fun getItemCount() = listBarang.size
}
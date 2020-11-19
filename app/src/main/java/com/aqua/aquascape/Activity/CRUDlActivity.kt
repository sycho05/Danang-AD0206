package com.aqua.aquascape.Activity

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.aqua.aquascape.Database.Barang
import com.aqua.aquascape.R
import com.bumptech.glide.Glide
import com.vincent.filepicker.Constant
import com.vincent.filepicker.activity.ImagePickActivity
import kotlinx.android.synthetic.main.activity_crud.*
import pub.devrel.easypermissions.EasyPermissions

class CRUDlActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_DATA = "extra_data"
        private val IMAGE_PICK_CODE = 1000;
        private val PERMISSION_CODE = 1001;
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crud)

        var extraData = intent.getParcelableExtra(EXTRA_DATA) as? Barang
        if(extraData!= null){
            edit_jenis.setText(extraData.jenis)
            edit_nama.setText(extraData.nama)
            edit_deskripsi.setText(extraData.deskripsi)
            edit_harga.setText(extraData.harga)
            Glide.with(this).load(extraData.gambar).into(imagePreview)
            edit_alamat.setText(extraData.alamat)
        }
        btnSimpan.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(edit_nama.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                var newData = Barang(
                    0,
                    edit_jenis.text.toString(),
                    edit_nama.text.toString(),
                    edit_deskripsi.text.toString(),
                    edit_harga.text.toString(),
                    imagePreview.toString(),
                    edit_alamat.text.toString()
                )
                if(extraData!= null){
                    newData = Barang(
                        extraData!!.nomor,
                        edit_jenis.text.toString(),
                        edit_nama.text.toString(),
                        edit_deskripsi.text.toString(),
                        edit_harga.text.toString(),
                        imagePreview.toString(),
                        edit_alamat.text.toString()
                    )
                }
                replyIntent.putExtra(EXTRA_DATA, newData)
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }

        btnlok.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("geo:0,0?q=Pasar+Ikan+Hias+Mina+Restu+Purwanegara+Jl.+Brigjend.+Encung%2C+Karangjambu%2C+Purwanegara%2C+Kec.+Purwokerto+Utara%2C+Kabupaten+Banyumas%2C+Jawa+Tengah+53127&oq=Jl.+Brigjend.+Encung%2C+Karangjambu%2C+Purwanegara%2C+Kec.+Purwokerto+Utara%2C+Kabupaten+Banyumas%2C+Jawa+Tengah+53127")
            }
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            }
        }

        btnPick.setOnClickListener{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED){
                    //permission denied
                    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE);
                    //show popup to request runtime permission
                    requestPermissions(permissions, PERMISSION_CODE);
                }
                else{
                    //permission already granted
                    pickImageFromGallery();
                }
            }
            else{
                //system OS is < Marshmallow
                pickImageFromGallery();
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.size >0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED){
                    pickImageFromGallery()
                }
                else{
                    //permission from popup denied
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    //handle result of picked image
    @SuppressLint("MissingSuperCall")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE){
            imagePreview.setImageURI(data?.data)
        }
    }

    private fun pickImageFromGallery() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_crud, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.getItemId()

        if (id == R.id.delete) {
            val builder = AlertDialog.Builder(this@CRUDlActivity)
            var extraData = intent.getParcelableExtra(EXTRA_DATA) as? Barang
            builder.setTitle("Delete "+extraData!!.nama)
            builder.setMessage("Are you sure to delete"+ extraData!!.nama +"?")
            builder.setPositiveButton("YES"){dialog, which ->
                var newData = Barang(
                    extraData!!.nomor,
                    edit_jenis.text.toString(),
                    edit_nama.text.toString(),
                    edit_deskripsi.text.toString(),
                    edit_harga.text.toString(),
                    imagePreview.toString(),
                    edit_alamat.text.toString()
                )
                val replyIntent = Intent()
                replyIntent.putExtra(EXTRA_DATA, newData)
                setResult(Activity.RESULT_OK, replyIntent)
                setResult(3, replyIntent)
                finish()
            }
            builder.setNegativeButton("No"){dialog,which ->
                Toast.makeText(applicationContext,"You are not sure.", Toast.LENGTH_SHORT).show()
            }
            val dialog: AlertDialog = builder.create()
            dialog.show()
            return true
        }
        return super.onOptionsItemSelected(item)
    }


}

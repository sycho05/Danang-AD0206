package com.aqua.aquascape.Activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aqua.aquascape.*
import com.aqua.aquascape.Database.Barang
import com.aqua.aquascape.Database.BarangViewModel
import com.aqua.aquascape.Fragment.HewanFragment
import com.aqua.aquascape.Fragment.HomeFragment
import com.aqua.aquascape.Fragment.TerumbuKarangFragment
import com.aqua.aquascape.Fragment.TumbuhanFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    private lateinit var barangViewModel: BarangViewModel
    private val newBarangActivityRequestCode = 1
    private val editBarangActivityRequestCode = 2
    private val deleteBarangActivityRequestCode = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = MainAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        barangViewModel = ViewModelProvider(this).get(BarangViewModel::class.java)
        barangViewModel.allBarang.observe(this, Observer { barang ->
            barang?.let { adapter.setBarang(it) }
        })

        fab.setOnClickListener {
            val intent = Intent(this@HomeActivity, CRUDlActivity::class.java)
            startActivityForResult(intent, newBarangActivityRequestCode)
        }

        adapter.setOnItemClickCallback(object :
            MainAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Barang) {
                val moveWithObjectIntent = Intent(this@HomeActivity, CRUDlActivity::class.java)
                moveWithObjectIntent.putExtra(CRUDlActivity.EXTRA_DATA, data)
                startActivityForResult(moveWithObjectIntent,editBarangActivityRequestCode)
            }
        })

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        goToHome(getString(R.string.home))

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        setMode(item.itemId)
        return super.onOptionsItemSelected(item)
    }

    private fun setMode(selectedMode: Int) {
        when (selectedMode) {
            R.id.subitem1 -> {
                val intent = Intent(this, AboutActivity::class.java)
                startActivity(intent)
            }
            R.id.subitem2 -> {
                val intent = Intent(this, HelpActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == editBarangActivityRequestCode && resultCode == Activity.RESULT_OK) {
            val extraData = data?.getParcelableExtra(CRUDlActivity.EXTRA_DATA) as Barang
            barangViewModel.update(extraData)
        }
        else if (resultCode == deleteBarangActivityRequestCode) {
            val extraData = data?.getParcelableExtra(CRUDlActivity.EXTRA_DATA) as Barang
            barangViewModel.deleteData(extraData)
        }
        else if(requestCode == newBarangActivityRequestCode && resultCode == Activity.RESULT_OK){
            val extraData = data?.getParcelableExtra(CRUDlActivity.EXTRA_DATA) as Barang
            barangViewModel.insert(extraData)
        }
        else {
            Toast.makeText(
                applicationContext,
                R.string.empty_not_saved,
                Toast.LENGTH_LONG).show()
        }
    }

    fun goToHome(title:String){
        supportActionBar?.title = title
        val mFragmentManager = supportFragmentManager
        val myFragment = HomeFragment()
        val fragment =
            mFragmentManager.findFragmentByTag(HomeFragment::class.java.simpleName)
        if (fragment !is HomeFragment) {
            mFragmentManager
                .beginTransaction()
                .replace(R.id.frame_container, myFragment, HomeFragment::class.java.simpleName)
                .commit()
        }
    }

    fun goToHewan(title:String){
        supportActionBar?.title = title
        val mFragmentManager = supportFragmentManager
        val myFragment = HewanFragment()
        val fragment =
            mFragmentManager.findFragmentByTag(HewanFragment::class.java.simpleName)
        if (fragment !is HewanFragment) {
            mFragmentManager
                .beginTransaction()
                .replace(R.id.frame_container, myFragment, HewanFragment::class.java.simpleName)
                .commit()
        }
    }

    fun goToTumbuhan(title:String){
        supportActionBar?.title = title
        val mFragmentManager = supportFragmentManager
        val myFragment = TumbuhanFragment()
        val fragment =
            mFragmentManager.findFragmentByTag(TumbuhanFragment::class.java.simpleName)
        if (fragment !is TumbuhanFragment) {
            mFragmentManager
                .beginTransaction()
                .replace(R.id.frame_container, myFragment,
                    TumbuhanFragment::class.java.simpleName)
                .commit()
        }
    }

    fun goToTerumbuKarang(title:String){
        supportActionBar?.title = title
        val mFragmentManager = supportFragmentManager
        val myFragment = TerumbuKarangFragment()
        val fragment =
            mFragmentManager.findFragmentByTag(TerumbuKarangFragment::class.java.simpleName)
        if (fragment !is TerumbuKarangFragment) {
            mFragmentManager
                .beginTransaction()
                .replace(R.id.frame_container, myFragment,
                    TerumbuKarangFragment::class.java.simpleName)
                .commit()
        }
    }

    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            val fragment: Fragment
            val bundle: Bundle
            when (item.itemId) {
                R.id.nav_home-> {
                    goToHome("Aquascape")
                    return@OnNavigationItemSelectedListener true
                }
                R.id.nav_hewan-> {
                    goToHewan("Hewan")
                    return@OnNavigationItemSelectedListener true
                }
                R.id.nav_tumbuhan -> {
                    goToTumbuhan("Tumbuhan")
                    return@OnNavigationItemSelectedListener true
                }
                R.id.nav_terumbu_karang -> {
                    goToTerumbuKarang("Terumbu Karang")
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
        builder.setCancelable(false)
        builder.setMessage("Apakah Anda Akan Keluar?")
        builder.setNegativeButton("Ya", {dialog, which ->
            finish()
        })
        builder.setPositiveButton("Tidak", {dialog, which ->
            dialog.cancel()
        })
        val alert = builder.create()
        alert.show()
    }
}

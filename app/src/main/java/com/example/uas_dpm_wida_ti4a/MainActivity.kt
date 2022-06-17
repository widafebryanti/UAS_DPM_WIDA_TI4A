package com.example.uas_dpm_wida_ti4a

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*

class MainActivity : AppCompatActivity() {
    private lateinit var adapterDosen: AdapterDosen
    private lateinit var dataDosen: ArrayList<Lecturer>
    private lateinit var lvDataDosen: ListView
    private lateinit var linTidakAda: LinearLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_tampil)

        title = "UAS DPM 2011500050"

        val btnTambah = findViewById<Button>(R.id.btnTambah)
        lvDataDosen = findViewById(R.id.lvDataDosen)
        linTidakAda = findViewById(R.id.linTidakAda)

        dataDosen = ArrayList()
        adapterDosen = AdapterDosen(this@MainActivity, dataDosen)

        lvDataDosen.adapter = adapterDosen

        refresh()
        btnTambah.setOnClickListener{
            val i= Intent(this@MainActivity, EntriDataLecturer::class.java)
            startActivity(i)
        }
    }

    override fun onResume() {
        super.onResume()
        refresh()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) refresh()
    }

    private fun refresh() {
        val db = DBLecturer(this@MainActivity)
        val data = db.tampil()
        repeat(dataDosen.size) {dataDosen.removeFirst()}
        if (data.count > 0){
            while (data.moveToNext()){
                val data = Lecturer(
                    data.getString(0),
                    data.getString(1),
                    data.getString(2),
                    data.getString(3),
                    data.getString(4),
                    data.getString(5),
                    data.getString(6)
                )
                adapterDosen.add(data)
                adapterDosen.notifyDataSetChanged()
            }
            lvDataDosen.visibility = View.VISIBLE
            linTidakAda.visibility = View.GONE
        } else {
            lvDataDosen.visibility = View.GONE
            linTidakAda.visibility = View.VISIBLE
        }
    }
}
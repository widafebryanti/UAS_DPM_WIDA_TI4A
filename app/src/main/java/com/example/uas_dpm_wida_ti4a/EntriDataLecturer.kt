package com.example.uas_dpm_wida_ti4a

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class EntriDataLecturer : AppCompatActivity () {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_entri_data)

        val modeEdit = intent.hasExtra("NIDN") && intent.hasExtra("nama") &&
                intent.hasExtra("Jabatan") && intent.hasExtra("golongan_pangkat")
                && intent.hasExtra("Pendidikan") && intent.hasExtra("Keahlian")
                && intent.hasExtra("Program_studi")
        title = if (modeEdit) "Edit Data Dosen" else "Entri Data Dosen"

        val etNIDN = findViewById<EditText>(R.id.etNIDN)
        val etNamaDosen = findViewById<EditText>(R.id.etNamaDosen)
        val spnJabatan = findViewById<Spinner>(R.id.spnJabatan)
        val spnGolongan = findViewById<Spinner>(R.id.spnGolongan)
        val pilihan1 = findViewById<RadioButton>(R.id.pilihan1)
        val pilihan2 = findViewById<RadioButton>(R.id.pilihan2)
        val btnSimpan = findViewById<Button>(R.id.btnSimpan)
        val etKeahlian = findViewById<EditText>(R.id.etKeahlian)
        val etProgramstudi = findViewById<EditText>(R.id.etProgamstudi)
        val jbt = arrayOf(
            "Tenaga Pengajar", "Asisten Ahli", "Lektor",
            "Lektor Kepala", "Guru Besar"
        )
        val gol = arrayOf(
            "III/a - Penata Muda", "III/b Penata Muda Tingkat 1",
            "III/c - Penata", "III/d - Penata Tingkat 1",
            "IV/a - Pembina", "IV/b - Pembina - Tingkat 1",
            "IV/c - Pembina Utama Muda", "IV/d - Pembina Utama Madya",
            "IV/e - Pembina Utama"
        )

        val adpJabatan = ArrayAdapter(
            this@EntriDataLecturer,
            android.R.layout.simple_spinner_dropdown_item, jbt
        )
        val adpGolongan = ArrayAdapter(
            this@EntriDataLecturer,
            android.R.layout.simple_spinner_dropdown_item, gol
        )
        spnJabatan.adapter = adpJabatan
        spnGolongan.adapter = adpGolongan

        if (modeEdit) {
            val nidn = intent.getStringExtra("NIDN")
            val namaDosen = intent.getStringExtra("nama_dosen")
            val jabatan = intent.getStringExtra("Jabatan")
            val golongan = intent.getStringExtra("golongan_pangkat")
            val pendidikan = intent.getStringExtra("Pendidikan")
            val bidangKeahlian = intent.getStringExtra("Keahlian")
            val programStudi = intent.getStringExtra("Program_studi")

            etNIDN.setText(nidn)
            etNamaDosen.setText(namaDosen)
            spnJabatan.setSelection(jbt.indexOf(jabatan))
            spnGolongan.setSelection(gol.indexOf(golongan))
            etKeahlian.setText(bidangKeahlian)
            etProgramstudi.setText(programStudi)
            if (pendidikan == "S2") pilihan1.isChecked = true else pilihan2.isChecked = true
        }
        etNIDN.isEnabled = !modeEdit

        btnSimpan.setOnClickListener {
            if ("${etNIDN.text}".isNotEmpty() && "${etNamaDosen.text}".isNotEmpty() &&
                "${spnJabatan.selectedItem}".isNotEmpty() && "${spnGolongan.selectedItem}".isNotEmpty() &&
                "${etKeahlian.text}".isNotEmpty() && "${etProgramstudi.text}".isNotEmpty() &&
                (pilihan1.isChecked || pilihan2.isChecked)
            ) {
                val db = DBLecturer(this@EntriDataLecturer)
                db.nidn = "${etNIDN.text}"
                db.namaDosen = "${etNamaDosen.text}"
                db.jabatan = spnJabatan.selectedItem as String
                db.golongan = spnGolongan.selectedItem as String
                db.pendidikan = if (pilihan1.isChecked) "S2" else "S3"
                db.keahlian = "${etKeahlian.text}"
                db.programStudi = "${etProgramstudi.text}"
                if (if (!modeEdit) db.simpan() else db.ubah("${etNIDN.text}")) {
                    Toast.makeText(
                        this@EntriDataLecturer,
                        "Data Dosen berhasil disimpan",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                } else
                    Toast.makeText(
                        this@EntriDataLecturer,
                        "Data Dosen gagal disimpan",
                        Toast.LENGTH_SHORT
                    ).show()
            } else
                Toast.makeText(
                    this@EntriDataLecturer,
                    "Data Dosen belum lengkap",
                    Toast.LENGTH_SHORT
                ).show()
        }
    }
}
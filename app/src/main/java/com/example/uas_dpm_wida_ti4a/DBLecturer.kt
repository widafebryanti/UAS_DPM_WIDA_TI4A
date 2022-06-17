package com.example.uas_dpm_wida_ti4a

import android.content.*
import android.database.Cursor
import android.database.sqlite.*

class DBLecturer (context: Context): SQLiteOpenHelper(context, "campuss", null, 1) {
    var nidn = ""
    var namaDosen = ""
    var jabatan = ""
    var golongan = ""
    var pendidikan = ""
    var keahlian = ""
    var programStudi = ""

    private val tabel = "lecturer"
    private var sql = ""

    override fun onCreate(db: SQLiteDatabase?) {
        sql = """create table $tabel(
            nidn char(10) primary key,
            nama_dosen varchar(50) not null,
            jabatan varchar(15) not null,
            golongan_pangkat varchar(30) not null,
            pendidikan char(2) not null,
            keahlian varchar(30) not null,
            program_studi varchar(50) not null
            )
        """.trimIndent()
        db?.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        sql = "drop table if exists $tabel"
        db?.execSQL(sql)
    }

    fun tampil(): Cursor {
        val db = writableDatabase
        val reader = db.rawQuery("select * from $tabel", null)
        return reader
    }

    fun simpan(): Boolean{
        val db = writableDatabase
        val cv = ContentValues()
        with(cv) {
            put("nidn", nidn)
            put("nama_dosen", namaDosen)
            put("jabatan", jabatan)
            put("golongan_pangkat", golongan)
            put("pendidikan", pendidikan)
            put("keahlian", keahlian)
            put("program_studi", programStudi)
        }

        val cmd = db.insert(tabel, null, cv)
        db.close()
        return cmd != -1L
    }

    fun hapus(kode: String):Boolean {
        val db = writableDatabase
        val cmd = db.delete(tabel, "nidn = ?", arrayOf(kode))
        return  cmd != -1
    }

    fun ubah (kode: String): Boolean {
        val db = writableDatabase
        val cv =  ContentValues()
        with(cv){
            put("nama_dosen", namaDosen)
            put("jabatan", jabatan)
            put("golongan_pangkat", golongan)
            put("pendidikan", pendidikan)
            put("keahlian", keahlian)
            put("program_studi", programStudi)
        }
        val cmd = db.update(tabel, cv, "nidn = ?", arrayOf(kode))
        db.close()
        return cmd != -1
    }
}
package com.example.application830

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.provider.BaseColumns
import java.io.ByteArrayOutputStream

class LocalDB (
    context: Context?,
    name: String?,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int
) : SQLiteOpenHelper(context, name, factory, version){
    //코드 참조
//https://developer.android.com/training/data-storage/sqlite?hl=ko
    override fun onCreate(db: SQLiteDatabase?) { //?뺏음
        // DB 생성시 실행
        if (db != null) {
            createDatabase(db)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // DB 버전 변경시 실행됨
        val sqlUser : String = "DROP TABLE if exists ${LocalDatas.userData.TABLE_NAME}"
        //val sqlAddr : String = "DROP TABLE if exists ${LocalDatas.addressData.TABLE_NAME}"

        if (db != null) {
            db.execSQL(sqlUser)
            //db.execSQL(sqlAddr)
            onCreate(db)
        } //  버전이 변경되면 기존 Table을 삭제후 재생성함.
    }

    fun createDatabase(db: SQLiteDatabase) {
        // 테이블이 존재하지 않는경우 생성
        var sqlUser: String = "CREATE TABLE if not exists ${LocalDatas.userData.TABLE_NAME} (" +
                "${BaseColumns._ID} integer primary key autoincrement," +
                "${LocalDatas.userData.COLUMN_NAME_ID} varchar(15)," +
                "${LocalDatas.userData.COLUMN_NAME_PASSWORD} varchar(20)"+
                ");"

//        var sqlAddr: String = "CREATE TABLE if not exists ${LocalDatas.addressData.TABLE_NAME} (" +
//                "${BaseColumns._ID} integer primary key autoincrement," +
//                "${LocalDatas.addressData.COLUMN_NAME_ADDR} varchar(35) not null," +
//                "${LocalDatas.addressData.COLUMN_NAME_IMAGE} blob,"+
//                "${LocalDatas.addressData.COLUMN_NAME_STATE} integer"+
//                ");"

        db.execSQL(sqlUser)
        //db.execSQL(sqlAddr)
    }

    //User 함수들
    fun registerUser(id: String, password:String){
        val db =this.writableDatabase
        val values = ContentValues().apply {// insert될 데이터값
            put(LocalDatas.userData.COLUMN_NAME_ID, id)
            put(LocalDatas.userData.COLUMN_NAME_PASSWORD, password)
        }
        val newRowId = db?.insert(LocalDatas.userData.TABLE_NAME, null, values)
        // 인서트후 인서트된 primary key column의 값(_id) 반환.
    }

    fun checkIdExist(id: String): Boolean {
        val db = this.readableDatabase

        // 리턴받고자 하는 컬럼 값의 array
        val projection = arrayOf(BaseColumns._ID)
        //,LocalDatas.userData.COLUMN_NAME_ID, LocalDatas.userData.COLUMN_NAME_PASSWORD)

        //  WHERE "id" = id 구문 적용하는 부분
        val selection = "${LocalDatas.userData.COLUMN_NAME_ID} = ?"
        val selectionArgs = arrayOf(id)

        //         정렬조건 지정
//        val sortOrder = "${FeedEntry.COLUMN_NAME_SUBTITLE} DESC"

        val cursor = db.query(
            LocalDatas.userData.TABLE_NAME,   // 테이블
            projection,             // 리턴 받고자 하는 컬럼
            selection,              // where 조건
            selectionArgs,          // where 조건에 해당하는 값의 배열
            null,                   // 그룹 조건
            null,                   // having 조건
            null               // orderby 조건 지정
        )
        if(cursor.count>0){//  반환된 cursor 값이 존재
            return true;
        }else{//반환된 cursor 값이 없음
            return false;
        }
    }

    fun logIn(id: String, password:String): Boolean {
        val db = this.readableDatabase

        // 리턴받고자 하는 컬럼 값의 array
        val projection = arrayOf(BaseColumns._ID)

        //  WHERE "id" = id AND "password"=password 구문 적용하는 부분
        val selection = "${LocalDatas.userData.COLUMN_NAME_ID} = ? AND ${LocalDatas.userData.COLUMN_NAME_PASSWORD} = ?"
        val selectionArgs = arrayOf(id,password)

        val cursor = db.query(
            LocalDatas.userData.TABLE_NAME, projection,
            selection, selectionArgs, null, null, null)

        if(cursor.count>0){//  반환된 cursor의 0번째 값이 null이면
            return true;
        }else{
            return false;
        }
    }

//    //Address 함수들
//    fun registerAddress(addr: String, image: ByteArray?){
//        val db =this.writableDatabase
//        val values = ContentValues().apply {// insert될 데이터값
//            put(COLUMN_NAME_ADDR, addr)
//            put(LocalDatas.addressData.COLUMN_NAME_IMAGE, image)
//            put(LocalDatas.addressData.COLUMN_NAME_IMAGE, 0)
//        }
//        val newRowId = db?.insert(LocalDatas.addressData.TABLE_NAME, null, values)
//        // 인서트후 인서트된 primary key column의 값(_id) 반환.
//    }
//
//    fun drawableToByteArray(drawable: Drawable?) : ByteArray? {
//        val bitmapDrawable = drawable as BitmapDrawable?
//        val bitmap = bitmapDrawable?.bitmap
//        val stream = ByteArrayOutputStream()
//        bitmap?.compress(Bitmap.CompressFormat.PNG, 100, stream)
//        val byteArray = stream.toByteArray()
//
//        return byteArray
//    }
//
//    fun printList(id: String): MutableList<AddressImage> {
//        val list = mutableListOf<AddressImage>()
//        val db = this.readableDatabase
//
//        // 리턴받고자 하는 컬럼 값의 array
//        val projection = arrayOf(BaseColumns._ID)
//
//        //  WHERE "id" = id 구문 적용하는 부분
//        val selection = "${LocalDatas.addressData.COLUMN_NAME_STATE} = ?"
//        val selectionArgs = arrayOf(id)
//
//        val cursor = db.query(
//            LocalDatas.addressData.TABLE_NAME, projection,
//            selection, selectionArgs, null, null, null)
//
//        while(cursor.moveToNext()) {
//            val address = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_ADDR))
//            val image: ByteArray? = cursor.getBlob(cursor.getColumnIndexOrThrow(COLUMN_NAME_IMAGE)) ?: null
//            list.add(AddressImage(address, image))
//        }
//        //cursor.close()
//        //db.close()
//
//        return list
//    }



}
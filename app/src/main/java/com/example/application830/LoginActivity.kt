package com.example.application830

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.application830.databinding.ActivityLoginBinding
import com.example.application830.databinding.ActivityMainBinding
import com.example.application830.databinding.ActivityRegisterBinding
import com.google.android.gms.common.api.Response
import org.json.JSONException
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {
    val DATABASE_VERSION = 1
    val DATABASE_NAME1 = "LocalDB.db"
    val DATABASE_NAME2 = "AddressDB.db"
    private lateinit var localDB: LocalDB
    private lateinit var addressDB: AddressDB
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        localDB= LocalDB(this, DATABASE_NAME1,null, DATABASE_VERSION) // SQLite 모듈 생성
        addressDB= AddressDB(this, DATABASE_NAME2,null, DATABASE_VERSION)

        binding.btnLogin.setOnClickListener { view->
            val id = binding.editId.text.toString()
            val passwd = binding.editPw.text.toString()
            val exist = localDB.logIn(id,passwd) // 로그인 실행
            if(exist){ // 로그인 성공
                val intent =Intent(this,MainActivity::class.java)
                startActivity(intent)
                login = true
                ID = id
                PW = passwd
            }else{ // 실패
                Toast.makeText(this@LoginActivity, "아이디나 비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnRegister.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}
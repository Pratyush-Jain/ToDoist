package com.example.todoist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.todoist.enums.LoginType

class LoginActivity : AppCompatActivity() {
    lateinit var loginType:LoginType

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

    }


    fun SignUp(view: View) {
        // Open signup activity from here
        var signupBtn = findViewById<TextView>(R.id.login_link)
        var intent = Intent(this, SignupActivity::class.java)
        startActivity(intent)
    }

    fun LoginUser(view: View) {
        loginType = LoginType.LoggedIn
        var uname = findViewById<TextView>(R.id.login_uname)
        if(!TextUtils.isEmpty(uname.text)){
            var intent = Intent(this, MainActivity::class.java).apply {
                putExtra("Uname", uname.text.toString())
                putExtra("loginType", loginType)
            }
            startActivity(intent)
        }else{
            Toast.makeText(this, "Please enter username/password", Toast.LENGTH_SHORT).show()
        }
    }

    fun guestLogin(view: View) {
        loginType = LoginType.Guest
        var intent = Intent(this, MainActivity::class.java).apply {
            putExtra("Uname","Guest")
            putExtra("loginType", loginType)
        }
        startActivity(intent)
    }
}
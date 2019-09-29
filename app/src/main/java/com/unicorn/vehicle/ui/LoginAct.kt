package com.unicorn.vehicle.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.unicorn.vehicle.R
import com.unicorn.vehicle.app.safeClicks
import com.unicorn.vehicle.app.startAct
import kotlinx.android.synthetic.main.act_login.*

class LoginAct : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_login)

        rtvLogin.safeClicks().subscribe {
            startAct(ApplyListAct::class.java)
        }
    }

}

package com.unicorn.vehicle

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.unicorn.vehicle.app.safeClicks
import com.unicorn.vehicle.app.startAct
import com.unicorn.vehicle.ui.ApplyListAct
import kotlinx.android.synthetic.main.act_login.*

class LoginAct : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_login)

        tvLogin.safeClicks().subscribe {
            startAct(ApplyListAct::class.java)
        }
    }

}

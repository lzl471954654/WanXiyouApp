package com.lzl.wanxiyouapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TableLayout
import android.widget.TableRow

import com.lzl.wanxiyouapp.View.LogonActivity
import com.lzl.wanxiyouapp.View.MainMenuActivity
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Thread{
            Thread.sleep(2500)
            runOnUiThread {
                startActivity<LogonActivity>()
                finish()
            }
        }.start()
    }

}

package com.lzl.wanxiyouapp.View

import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.lzl.wanxiyouapp.MyApplication
import com.lzl.wanxiyouapp.R
import kotlinx.android.synthetic.main.activity_setting.*
import org.jetbrains.anko.AlertDialogBuilder

/**
 * Created by LZL on 2017/8/9.
 */

class ActivitySettings:AppCompatActivity(),View.OnClickListener{


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        setListener()
    }

    fun setListener(){
        setting_about_us.setOnClickListener(this)
        setting_logout.setOnClickListener(this)
        setting_commit_suggestion.setOnClickListener(this)
        setting_check_update.setOnClickListener(this)
        setting_clear_cache.setOnClickListener(this)
        if(!MyApplication.isLogon)
            setting_logout.visibility = View.GONE
    }

    override fun onClick(v: View?) {
        when(v?.id)
        {
            R.id.setting_logout->{
                val alertDialog = AlertDialogBuilder(this)
                alertDialog.message("您确定要退出登录吗")
                alertDialog.negativeButton("取消",{})
                alertDialog.positiveButton("确定"){
                    setResult(1)
                    MyApplication.isLogon = false;
                    MyApplication.student = null
                    finish()
                }
                alertDialog.show()
            }
            R.id.setting_about_us->{

            }
            R.id.setting_commit_suggestion->{

            }
            R.id.setting_check_update->{

            }
            R.id.setting_clear_cache->{

            }
        }
    }
}

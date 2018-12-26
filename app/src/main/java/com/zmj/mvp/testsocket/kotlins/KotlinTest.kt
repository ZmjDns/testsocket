package com.zmj.mvp.testsocket.kotlins

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import com.zmj.mvp.testsocket.R
import kotlinx.android.synthetic.main.activity_kotlin_test.*

class KotlinTest : AppCompatActivity() {

    var count:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_test)

        btn_date.setOnClickListener{getNowime()}

        btn_jumpWind.setOnClickListener {jumpWind()}
    }

    fun getNowime(){
        tv_show.text = "单例对象" + when(count++ % 5){
            0 ->"当前日期时间为${DateUtil.nowDateTime}"
            1 ->"当前日期${DateUtil.nowDate}"
            2 ->"当前时间${DateUtil.nowTime}"
            3 ->"当前毫秒时间${DateUtil.nowTimeDetail}"
            else ->"当前中文日期时间为${DateUtil.getFormatTime("yyyy年MM月dd日HH时mm分ss秒")}"
        }
    }

    fun jumpWind(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("尊敬的用户")
        builder.setMessage("您真的要卸载我吗？")
        builder.setPositiveButton("卸载"){dialog, which -> tv_show.text = "虽然依依不舍，还是只能离开了"}
        builder.setNegativeButton("不了"){dialog, which -> tv_show.text = "hahah " }
        builder.show()
    }
}

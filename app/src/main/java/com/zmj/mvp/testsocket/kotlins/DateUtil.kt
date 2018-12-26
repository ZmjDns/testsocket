package com.zmj.mvp.testsocket.kotlins

import java.text.SimpleDateFormat
import java.util.*

/**
 * @author Zmj
 * @date 2018/12/25
 */
//关键字object用来声明到那里对象，就像java中自定义的Utils工具类
//其内部的属性等同于java的static属性，外部可直接获取属性值
object DateUtil {
    //声明一个当前日期时间属性
    //返回日期时间格式形如2018-12-25 13:13:00
    val nowDateTime:String
        //外部访问DateUtil.nowDateTime时，会自动调用nowDateTime附属的get方法得到它的值
        get(){
            //SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            return sdf.format(Date())
        }
    //只返回日期字符串
    val nowDate:String
        get(){
            val sdf = SimpleDateFormat("yyyy-mm-dd")
            return sdf.format(Date())
        }
    //只返回时间串
    val nowTime:String
        get(){
            val sdf = SimpleDateFormat("HH:mm:ss")
            return sdf.format(Date())
        }
    //返回详细的字符串，精确到毫秒
    val nowTimeDetail:String
        get(){
            val sdf = SimpleDateFormat("HH:mm:ss.SSS")
            return sdf.format(Date())
        }
    //返回指定格式的时间字符串
    fun getFormatTime(format: String = ""):String{
        val ft:String = format
        val sdf = if (!ft.isEmpty()) SimpleDateFormat(ft)
                    else SimpleDateFormat("yyyyMMddHHmmssSSS")
        return sdf.format(Date())
    }
}
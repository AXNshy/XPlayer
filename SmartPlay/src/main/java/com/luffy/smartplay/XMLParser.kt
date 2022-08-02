package com.luffy.smartplay

import org.xmlpull.v1.XmlPullParserFactory
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException
import java.io.InputStream
import java.util.ArrayList
import java.util.HashMap

/**
 * Created by axnshy on 2016/3/14.
 */
class XMLParser {
    fun getMap(`is`: InputStream?): Map<String, String> {
        //定义一个Map对像
        val map: MutableMap<String, String> = HashMap()
        //使用pull解析xml文件=
        try {
            //获取Pull解析器工厂
            val factory = XmlPullParserFactory.newInstance()
            //获取Pull解析器
            val parser = factory.newPullParser()

            //设置输入流，并且编码
            parser.setInput(`is`, "UTF_8")

            // 获取事件类型
            var eventType = parser.eventType
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    val name = parser.name
                    if ("key" == name) {
                        val key = parser.nextText() //取出"北京"
                        parser.next() //指向</key>
                        parser.next() //指向<string>
                        val value = parser.nextText() //取出"101010100"
                        //把key-value放进到map对象中
                        map[key] = value
                    }
                }
                parser.next() //指向</string>，第二次执行<key>
                eventType = parser.eventType //获取的是</string>这个类型为结束标签,第二次获取的是<key>
            }
        } catch (e: XmlPullParserException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return map
    }

    companion object {
        /*
    * 将map.KeySet获得的set集合变成一个ArrayList,
    *
    * */
        fun parseMap(map: Map<String?, String?>?): ArrayList<String?> {
            val list = ArrayList<String?>()
            if (map != null && !map.isEmpty()) {
                val set = map.keys
                val array = arrayOfNulls<String>(set.size)
                set.toArray<String>(array)
                for (i in array.indices) {
                    list.add(array[i])
                }
            }
            return list
        }
    }
}
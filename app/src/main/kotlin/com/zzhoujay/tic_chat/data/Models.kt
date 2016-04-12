package com.zzhoujay.tic_chat.data

import cn.bmob.v3.BmobInstallation
import cn.bmob.v3.BmobObject
import cn.bmob.v3.BmobUser
import cn.bmob.v3.datatype.BmobFile
import com.zzhoujay.tic_chat.App
import org.json.JSONObject

/**
 * Created by zhou on 16-3-24.
 */

class User(var profile: Profile) : BmobUser() {

}

class Profile(var name: String = "", var email: String = "", var age: Int = 0, var home: String = "", var sex: Int = 0, var qq: String = "", var college: String = "", var introduction: String = "", var avatar: BmobFile? = null) : BmobObject() {
    override fun toString(): String {
        return "Profile(name='$name', qq='$qq')"
    }

    companion object {
        const val PROFILE = "profile"

        fun sex(sex: Int): String {
            return when (sex) {
                1 -> "男"
                -1 -> "女"
                else -> "保密"
            }
        }
    }
}

class Category(var name: String = "", var index: Int = 0) : BmobObject() {
    override fun toString(): String = name
}

class Topic(var title: String = "", var content: String = "", var reply: Int = 0, var author: User, var category: Category) : BmobObject() {
    override fun toString(): String {
        return "Topic(title='$title', content='$content', reply=$reply, author=$author, category=$category)"
    }

    companion object {
        const val TOPIC = "topic"
        const val TOPIC_LIST = "topic_list"
    }

}

class Reply(var content: String = "", var quote: Reply? = null, var author: User, var topic: Topic) : BmobObject() {

    override fun toString(): String {
        return "Reply(content='$content', quote=$quote, author=$author, topic=$topic)"
    }
}

class Installation(var user: User?) : BmobInstallation(App.app) {

    override fun toString(): String {
        return "Installation(user=$user)"
    }
}

class Message(val type: Int = Message.type_reply_topic, var fromUser: User, var targetUser: User, var targetTopic: Topic, var targetReply: Reply) : BmobObject() {
    override fun toString(): String {
        return "Message(type:$type,fromUser=$fromUser, targetUser=$targetUser, targetTopic=$targetTopic, targetReply=$targetReply)"
    }

    companion object {
        const val type_reply_topic = 0x1234
        const val type_quote_reply = 0x2345
    }
}

data class Alert(val type: Int, val id: String) {

    override fun toString(): String {
        return "Alert(type:$type,id:$id)"
    }

    fun toJson(): JSONObject {
        val json = JSONObject()
        json.put("type", type).put("id", id)
        return json
    }

    companion object {
        const val type_message = 1
        const val type_notification = 2

        fun fromJson(json: String): Alert {
            val jo = JSONObject(json)
            return Alert(jo.getInt("type"), jo.getString("id"))
        }
    }
}
package com.zzhoujay.tic_chat.data

import cn.bmob.v3.BmobInstallation
import cn.bmob.v3.BmobObject
import cn.bmob.v3.BmobUser
import cn.bmob.v3.datatype.BmobFile
import cn.bmob.v3.datatype.BmobPointer
import com.zzhoujay.tic_chat.App

/**
 * Created by zhou on 16-3-24.
 */

class User(var profile: Profile?) : BmobUser() {


}

class Profile(var name: String = "", var qq: String = "", var avatar: BmobFile) : BmobObject() {
    override fun toString(): String {
        return "Profile(name='$name', qq='$qq')"
    }
}

class Category(var name: String = "") : BmobObject() {
    override fun toString(): String {
        return "Category(name='$name')"
    }
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
package com.zzhoujay.tic_chat.data

import cn.bmob.v3.BmobInstallation
import cn.bmob.v3.BmobObject
import cn.bmob.v3.BmobUser
import com.zzhoujay.tic_chat.App

/**
 * Created by zhou on 16-3-24.
 */

class User() : BmobUser()

class Profilr(var name: String = "", var qq: String = "") : BmobObject()

class Category(var name: String = "") : BmobObject()

class Topic(var title: String = "", var content: String = "", var reply: Int = 0, var good: Int = 0, var user: User, var category: Category) : BmobObject()

class Reply(var content: String = "", var quote: String? = null, var user: User, var topic: Topic) : BmobObject()

class Installation(var userId: String) : BmobInstallation(App.app)
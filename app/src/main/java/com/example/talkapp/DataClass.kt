package com.example.talkapp

import java.util.*

data class Chat(var email:String = "", var text:String="", var time:String = "")

data class UserAccount(val emailId:String? = null, val password:String? = null, val name:String? = null,val idToken:String? = null)
//이메일, 비밀번호, 고유토큰
//data class FriendName(var name:String? = "")
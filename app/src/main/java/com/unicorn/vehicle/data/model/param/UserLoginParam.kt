package com.unicorn.vehicle.data.model.param

data class UserLoginParam(
    val loginStr: String,    //手机号码或登录名称
    val userPwd: String      //密码，md5加密
)
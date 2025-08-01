package com.demo.musicapp.data.repository.authentication

import com.demo.musicapp.utils.Response

interface AuthRepository {
    //Trả về kiểu true nếu thao tác ok, ném exception nếu fail
    //firebase check login = token  nên kết hợp với encsharedPref lưu thông tin user
    suspend fun firebaseSignUp(email : String, password: String, name: String, age : Int) : Response<Boolean>
    suspend fun firebaseLogin(email : String, password: String) : Response<Boolean>
    suspend fun firebaseLogout() : Response<Boolean>
    suspend fun firebaseIsLoggedIn() : Boolean
}
package com.demo.musicapp.domain.model


//Dữ liệu đã được "làm phẳng" (flattened), ví dụ như artistName được lấy trực tiếp thay vì phải truy cập qua một object lồng nhau.

data class Album(
    val id: String,
    val title: String,
    val coverUrl: String,
    val artistName: String
)
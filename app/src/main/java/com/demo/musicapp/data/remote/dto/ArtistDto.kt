package com.demo.musicapp.data.remote.dto

import com.google.gson.annotations.SerializedName

/**
 * Data Transfer Object (DTO) cho một Nghệ sĩ.
 */
data class ArtistDto(
    @SerializedName("id")
    val id: Long,

    @SerializedName("name")
    val name: String,

    /**
     * URL tới ảnh của nghệ sĩ với kích thước trung bình (250x250)
     * nullable vì một số nghệ sĩ có thể không có ảnh.
     */
    @SerializedName("picture_medium")
    val pictureMedium: String?,

    /**
     * URL API để lấy danh sách các bài hát hàng đầu của nghệ sĩ này.
     * là một String chứa URL, Cần tự gọi API đến để lấy dữ liệu
     *
     */
    @SerializedName("tracklist")
    val tracklist: String?
)

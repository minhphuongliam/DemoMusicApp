package com.demo.musicapp.data.remote.dto

import com.google.gson.annotations.SerializedName

/**
 * Data Transfer Object (DTO) cho một Album.
 */
data class AlbumDto(
    @SerializedName("id")
    val id: Long,

    @SerializedName("title")
    val title: String,

    /**
     * URL tới ảnh bìa album với kích thước trung bình.
     * Là nullable vì một số album có thể không có ảnh bìa.
     */
    @SerializedName("cover_medium")
    val coverMedium: String?,

    /**
     * URL API để lấy danh sách các bài hát trong album này.
     * Đây là một String chứa URL, không phải là một danh sách.
     */
    @SerializedName("tracklist")
    val tracklist: String?,

    /**
     * Một object ArtistDto được lồng bên trong.
     * Trường này là nullable (ArtistDto?) vì các album tổng hợp
     * được thể hiện bởi nhiều nghệ sĩ và sẽ không có một nghệ sĩ chính duy nhất.
     */
    @SerializedName("artist")
    val artist: ArtistDto?
)
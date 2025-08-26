package com.demo.musicapp.data.remote.dto

import com.google.gson.annotations.SerializedName

/**
 * Data Transfer Object (DTO) cho một Bài hát (Track).
 * Chứa các DTO khác lồng bên trong (ArtistDto, AlbumDto).
 */
data class TrackDto(
    @SerializedName("id")
    val id: Long,

    @SerializedName("title")
    val title: String,

    /**
     * URL để phát 30 giây của bài hát.
     * QUAN TRỌNG: URL này có thời gian hết hạn. Cần gọi lại API để lấy URL mới trước khi phát.
     * nullable vì một số bài hát có thể không có bản xem trước.
     */
    @SerializedName("preview")
    val preview: String?,

    @SerializedName("duration")
    val duration: Int,
)

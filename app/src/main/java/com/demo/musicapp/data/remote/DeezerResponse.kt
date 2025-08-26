package com.demo.musicapp.data.remote

import com.google.gson.annotations.SerializedName

/**
 * Lớp Generic đại diện cho cấu trúc phản hồi chung của Deezer API khi trả về một danh sách.
 * Hầu hết các endpoint như search, chart, artist's tracks đều tuân theo cấu trúc này.
 *
 * @param T Kiểu dữ liệu của các đối tượng trong danh sách (TrackDTO, ALbumDTO ...) .
 */
data class DeezerResponse<T>(
    /** Danh sách các đối tượng dữ liệu chính API trả về */
    @SerializedName("data")
    val data: List<T>,

    /**
     * Tổng số kết quả server có sẵn cho yêu cầu
     * nullable vì không phải tất cả endpoint đều trả nó
     */
    @SerializedName("total")
    val total: Int?,

    /**
     * URL đầy đủ với index để load tiếp paging
     * nullable vì không phải API nào cũng trả về và kết quả cuối cùng thì không có
     */
    @SerializedName("next")
    val next: String?

    // prev paging trước đó nhưng chưa cần
//    @SerializedName("prev")
//    val prev: String?
)

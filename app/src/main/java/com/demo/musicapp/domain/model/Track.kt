package com.demo.musicapp.domain.model

data class Track(
    val id: String,
    val title: String,
    /** URL (có thể đã hết hạn) để phát nhạc. Logic làm mới URL sẽ được xử lý ở Repository/ViewModel. */
    val soundUrl: String,
    /** URL ảnh bìa, được lấy từ album của track. */
    val coverUrl: String,
    /** Tên nghệ sĩ, được lấy từ artist của track. */
    val artistName: String,
    val duration: Int
)

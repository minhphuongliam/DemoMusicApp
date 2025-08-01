package com.demo.musicapp.utils
/**
 * Wrapper sealed class để quản lý trạng thái của một request bất đồng bộ.
 * Gồm 3 trạng thái:
 * - Loading: Đang xử lý
 * - Success<T>: Thành công, trả về dữ liệu kiểu T
 * - Failure: Thất bại
 *
 * Dễ tái sử dụng cho nhiều API/Firebase/DB và giúp tách biệt rõ logic xử lý UI theo trạng thái.
 */
sealed class Response<out T> {
    object Loading: Response<Nothing>()

    data class Success<out T>(
        val data: T
    ): Response<T>()

    data class Failure(
        val e: Exception
    ): Response<Nothing>()
}
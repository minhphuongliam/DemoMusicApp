package com.demo.musicapp.di

import com.demo.musicapp.data.repository.authentication.AuthRepository
import com.demo.musicapp.data.repository.authentication.AuthRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
// Cài đặt vào SingletonComponent vì Repository nên tồn tại trong suốt vòng đời ứng dụng.
@InstallIn(SingletonComponent::class)
// Module này phải là `abstract class` để có thể chứa hàm `@Binds` trừu tượng.
abstract class RepositoryModule {

    /**
     * Dùng @Binds để "trói" (bind) interface AuthRepository với implementation AuthRepositoryImpl.
     * Đây là cách làm hiệu quả và được khuyến khích nhất cho trường hợp này.
     *
     * Hilt sẽ đọc hàm này và hiểu rằng:
     * "Khi có một lớp nào đó (như ViewModel) yêu cầu một `AuthRepository`,
     * thì câu trả lời chính là hãy tạo một `AuthRepositoryImpl` và đưa cho nó."
     */
    @Binds
    @Singleton // Đảm bảo rằng chỉ có MỘT instance của AuthRepositoryImpl được tạo ra.
    abstract fun bindAuthRepository(
        // Hilt sẽ tự tìm cách tạo AuthRepositoryImpl vì nó đã được đánh dấu @Inject constructor()
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository // Hàm này trả về kiểu Interface
}
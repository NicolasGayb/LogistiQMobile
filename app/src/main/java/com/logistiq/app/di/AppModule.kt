package com.logistiq.app.di

import android.content.Context
import com.logistiq.app.data.AuthRepository
import com.logistiq.app.data.TokenStorage
import com.logistiq.app.network.model.AuthApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideTokenStorage(
        @ApplicationContext context: Context
    ): TokenStorage {
        return TokenStorage(context)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        api: AuthApi,
        tokenStorage: TokenStorage
    ): AuthRepository {
        return AuthRepository(api, tokenStorage)
    }
}

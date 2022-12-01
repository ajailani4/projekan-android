package com.ajailani.projekan.di

import com.ajailani.projekan.data.remote.api_service.AuthService
import com.ajailani.projekan.data.remote.api_service.ProjectService
import com.ajailani.projekan.data.remote.api_service.UserProfileService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiServiceModule {
    @Provides
    @Singleton
    fun provideAuthService(retrofit: Retrofit): AuthService =
        retrofit.create(AuthService::class.java)

    @Provides
    @Singleton
    fun provideProjectService(retrofit: Retrofit): ProjectService =
        retrofit.create(ProjectService::class.java)

    @Provides
    @Singleton
    fun provideUserProfileService(retrofit: Retrofit): UserProfileService =
        retrofit.create(UserProfileService::class.java)
}
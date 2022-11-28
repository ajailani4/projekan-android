package com.ajailani.projekan.di

import com.ajailani.projekan.data.remote.api_service.AuthService
import com.ajailani.projekan.data.remote.api_service.ProjectService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
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
}
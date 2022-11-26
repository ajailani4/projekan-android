package com.ajailani.projekan.di

import com.ajailani.projekan.data.repository.AuthRepositoryImpl
import com.ajailani.projekan.data.repository.UserCredentialRepositoryImpl
import com.ajailani.projekan.domain.repository.AuthRepository
import com.ajailani.projekan.domain.repository.UserCredentialRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    abstract fun bindUserCredentialRepository(
        userCredentialRepositoryImpl: UserCredentialRepositoryImpl
    ): UserCredentialRepository
}
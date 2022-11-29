package com.ajailani.projekan.di

import com.ajailani.projekan.data.repository.AuthRepositoryImpl
import com.ajailani.projekan.data.repository.ProjectRepositoryImpl
import com.ajailani.projekan.data.repository.UserCredentialRepositoryImpl
import com.ajailani.projekan.data.repository.UserProfileRepositoryImpl
import com.ajailani.projekan.domain.repository.AuthRepository
import com.ajailani.projekan.domain.repository.ProjectRepository
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

    @Binds
    abstract fun bindProjectRepository(
        projectRepositoryImpl: ProjectRepositoryImpl
    ): ProjectRepository

    @Binds
    abstract fun bindUserProfileRepository(
        userProfileRepositoryImpl: UserProfileRepositoryImpl
    ): UserCredentialRepository
}
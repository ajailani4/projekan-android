package com.ajailani.projekan.data.repository

import android.content.Context
import com.ajailani.projekan.R
import com.ajailani.projekan.data.Resource
import com.ajailani.projekan.data.mapper.toUserProfile
import com.ajailani.projekan.data.remote.data_source.UserProfileRemoteDataSource
import com.ajailani.projekan.domain.model.UserProfile
import com.ajailani.projekan.domain.repository.UserProfileRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserProfileRepositoryImpl @Inject constructor(
    private val userProfileRemoteDataSource: UserProfileRemoteDataSource,
    @ApplicationContext private val context: Context
) : UserProfileRepository {
    override fun getUserProfile() =
        flow {
            val response = userProfileRemoteDataSource.getProfile()

            when (response.code()) {
                200 -> emit(Resource.Success(response.body()?.data?.toUserProfile()))

                else -> emit(Resource.Error(context.getString(R.string.something_wrong_happened)))
            }
        }
}
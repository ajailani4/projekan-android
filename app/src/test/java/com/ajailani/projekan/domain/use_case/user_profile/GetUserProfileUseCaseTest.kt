package com.ajailani.projekan.domain.use_case.user_profile

import com.ajailani.projekan.data.Resource
import com.ajailani.projekan.domain.model.UserProfile
import com.ajailani.projekan.domain.repository.UserProfileRepositoryFake
import com.ajailani.projekan.util.ResourceType
import com.ajailani.projekan.util.userProfile
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetUserProfileUseCaseTest {
    private lateinit var userProfileRepositoryFake: UserProfileRepositoryFake
    private lateinit var getUserProfileUseCase: GetUserProfileUseCase

    @Before
    fun setUp() {
        userProfileRepositoryFake = UserProfileRepositoryFake()
        getUserProfileUseCase = GetUserProfileUseCase(userProfileRepositoryFake)
    }

    @Test
    fun `Get user profile should return success`() =
        runTest(UnconfinedTestDispatcher()) {
            userProfileRepositoryFake.setResourceType(ResourceType.Success)

            val actualResource = getUserProfileUseCase().first()

            assertEquals(
                "Resource should be success",
                Resource.Success(userProfile),
                actualResource
            )
        }

    @Test
    fun `Get user profile should return fail`() =
        runTest(UnconfinedTestDispatcher()) {
            userProfileRepositoryFake.setResourceType(ResourceType.Error)

            val actualResource = getUserProfileUseCase().first()

            assertEquals(
                "Resource should be success",
                Resource.Error<UserProfile>(),
                actualResource
            )
        }
}
package id.rifqipadisiliwangi.tokopaerbe.presentation.feature.profile

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import id.rifqipadisiliwangi.core.domain.usecase.AuthInteractor
import id.rifqipadisiliwangi.core.utils.ResultWrapper
import id.rifqipadisiliwangi.tokopaerbe.utils.LiveDataTestUnit.getOrAwaitValue
import id.rifqipadisiliwangi.tokopaerbe.utils.MainDispatchersRule
import id.rifqipadisiliwangi.tokopaerbe.utils.utils.imageMultipart
import id.rifqipadisiliwangi.tokopaerbe.utils.utils.profile
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class RegisterProfileViewModelTest{

    @get:Rule
    val mainDispatchersRule = MainDispatchersRule()
    @get:Rule
    val coroutineRule = InstantTaskExecutorRule()

    private lateinit var viewModel : RegisterProfileViewModel
    private val interactor = Mockito.mock<AuthInteractor>()

    @Before
    fun setUp(){
        viewModel = RegisterProfileViewModel(interactor)
    }

    @Test
    fun `test register profile success`() = runTest {
        val userName: RequestBody  = "".trim().toRequestBody("multipart/form-data".toMediaType())
        val userImage: MultipartBody.Part? = imageMultipart

        whenever(interactor.fetchRegisterProfile(userName,userImage)).thenReturn(
            flowOf(ResultWrapper.Success(profile))
        )
        viewModel.userRegisterProfile(userName,userImage)
        val actualData = viewModel.registerProfileResult.getOrAwaitValue()
        val expected = actualData is ResultWrapper.Success
        assertEquals(expected, true)
        assertEquals("", actualData.payload?.userName)
    }

}
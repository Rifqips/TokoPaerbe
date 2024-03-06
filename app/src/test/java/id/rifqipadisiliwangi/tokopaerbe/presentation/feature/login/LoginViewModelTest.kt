package id.rifqipadisiliwangi.tokopaerbe.presentation.feature.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import id.rifqipadisiliwangi.core.domain.model.login.LoginRequest
import id.rifqipadisiliwangi.core.domain.usecase.AuthInteractor
import id.rifqipadisiliwangi.core.utils.ResultWrapper
import id.rifqipadisiliwangi.tokopaerbe.utils.LiveDataTestUnit.getOrAwaitValue
import id.rifqipadisiliwangi.tokopaerbe.utils.MainDispatchersRule
import id.rifqipadisiliwangi.tokopaerbe.utils.utils.login
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LoginViewModelTest {

    @get:Rule
    val mainDispatchersRule = MainDispatchersRule()

    @get:Rule
    val coroutineRule = InstantTaskExecutorRule()

    private lateinit var viewModel: LoginViewModel
    private val interactor = Mockito.mock<AuthInteractor>()

    @Before
    fun setUp(){
        viewModel = LoginViewModel(interactor)
    }

    /**
     * Assert.assertEquals(200, actualData.payload?.code) = menyesuaikan code yang didapat dari viewmodel
     * Assert.assertEquals(expected, true) = menyesuaikan generic vm ketika sama sama true
     */

    @Test
    fun `test login success`() = runTest {
        val loginReq = LoginRequest()
        `when`(interactor.fetchLogin(loginReq)).thenReturn(
            flow {
                emit(ResultWrapper.Success(login))
                emit(ResultWrapper.Error(Exception()))
            }
        )
        viewModel.userLogin(loginReq)
        val actualData = viewModel.loginResult.getOrAwaitValue()
        val expected = actualData is ResultWrapper.Success
        Assert.assertEquals(200, actualData.payload?.code)
        Assert.assertEquals(expected, true)
    }
}

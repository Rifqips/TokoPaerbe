package id.rifqipadisiliwangi.tokopaerbe.presentation.feature.register

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import id.rifqipadisiliwangi.core.domain.model.register.RegisterRequest
import id.rifqipadisiliwangi.core.domain.usecase.AuthInteractor
import id.rifqipadisiliwangi.core.utils.ResultWrapper
import id.rifqipadisiliwangi.tokopaerbe.utils.LiveDataTestUnit.getOrAwaitValue
import id.rifqipadisiliwangi.tokopaerbe.utils.MainDispatchersRule
import id.rifqipadisiliwangi.tokopaerbe.utils.utils.register
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
class RegisterViewModelTest {

    @get:Rule
    val mainDispatchersRule = MainDispatchersRule()
    @get:Rule
    val coroutineRule = InstantTaskExecutorRule()

    private lateinit var viewModel : RegisterViewModel
    private val interactor = Mockito.mock<AuthInteractor>()

    @Before
    fun setUp(){
        viewModel = RegisterViewModel(interactor)
    }

    @Test
    fun `test register success`() = runTest {
        `when`(interactor.fetchRegister(RegisterRequest())).thenReturn(
            flow { emit(ResultWrapper.Success(register)) }
        )
        viewModel.userRegister(RegisterRequest())
        val actualData = viewModel.registerResult.getOrAwaitValue()
        val expected = actualData is ResultWrapper.Success
        Assert.assertEquals(expected, true)
        Assert.assertEquals(200, actualData.payload?.code)
    }

}
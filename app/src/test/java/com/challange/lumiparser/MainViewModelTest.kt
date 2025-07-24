package com.challange.lumiparser

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.challange.lumiparser.retrofit.LayoutAPI
import com.challange.lumiparser.room.LayoutRepository
import com.challange.lumiparser.room.models.Layout
import com.challange.lumiparser.viewmodel.MainViewModel
import io.mockk.every
import io.mockk.mockkStatic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response
import android.util.Log
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.time.delay
import java.time.Duration

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: MainViewModel
    private lateinit var repository: LayoutRepository
    private lateinit var api: LayoutAPI

    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    private val layoutJson = """{ "type": "text", "title": "Test" }"""

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mock(LayoutRepository::class.java)
        api = mock(LayoutAPI::class.java)

        `when`(repository.getFirstLayout()).thenReturn(flowOf(Layout(0, layoutJson)))
        viewModel = MainViewModel(repository, api)

        mockkStatic(Log::class)
        every { Log.d(any(), any()) } returns 0
        every { Log.i(any(), any()) } returns 0
        every { Log.e(any(), any()) } returns 0
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cancel()
    }

    @Test
    fun `requestLayout - successfully updates repo and layout`() = testScope.runTest {
        val response = Response.success(layoutJson)
        `when`(api.getLayout()).thenReturn(response)

        viewModel.requestLayout()
        advanceUntilIdle()

        verify(api).getLayout()
        verify(repository).upsertLayout(Layout(0, layoutJson))
        Assert.assertNotNull(viewModel.layout.value)
    }

    @Test
    fun `requestLayout - api error does not crash ViewModel`() = testScope.runTest {
        val response = Response.error<String>(500, "error".toResponseBody())
        `when`(api.getLayout()).thenReturn(response)

        viewModel.requestLayout()
        advanceUntilIdle()

        verify(api).getLayout()
    }

    @Test
    fun `requestLayout - timeout does not crash ViewModel`() = testScope.runTest {
        `when`(api.getLayout()).thenAnswer {
            runBlocking {
                delay(Duration.ofSeconds(6))
                Response.success(layoutJson)
            }
        }

        viewModel.requestLayout()
        advanceUntilIdle()

        verify(api).getLayout()
    }
}

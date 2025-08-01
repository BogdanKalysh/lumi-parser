package com.challange.lumiparser.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.challange.lumiparser.R
import com.challange.lumiparser.retrofit.LayoutAPI
import com.challange.lumiparser.room.LayoutRepository
import com.challange.lumiparser.room.models.Layout
import com.challange.lumiparser.ui.component.LayoutElement
import com.challange.lumiparser.ui.util.UiMessage
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import retrofit2.HttpException
import java.io.IOException

class MainViewModel(private val repository: LayoutRepository, private val api: LayoutAPI): ViewModel() {
    private val dbLayout = repository.getFirstLayout()
    val layout = MutableStateFlow<LayoutElement?>(null)
    val isUpdating = MutableStateFlow(false)
    val isInitialized = MutableStateFlow(false)

    private val _toastMessage = MutableSharedFlow<UiMessage>()
    val toastMessage = _toastMessage.asSharedFlow()

    init {
        viewModelScope.launch {
            dbLayout.collect { fetchedLayout ->
                if (fetchedLayout == null || fetchedLayout.layoutJson.isEmpty()) {
                    requestLayout()
                }
                val adapter = moshiLayoutAdapter
                fetchedLayout?.run {
                    try {
                        val parsed = adapter.fromJson(layoutJson)
                        layout.value = parsed
                    } catch (ex: IOException) {
                        _toastMessage.emit(UiMessage.StringRes(R.string.unable_to_receive_cached_layout))
                        Log.e(TAG, "Local layout parsing failed: ${ex.message}")
                    }
                }
                isInitialized.value = true
            }
        }
    }

    fun requestLayout() {
        Log.d(TAG, "Calling loadLayout")
        isUpdating.value = true
        viewModelScope.launch {
            try {
                withTimeout(5000L) { // request timeout is 5 seconds
                    delay(2000L) // Simulating a long running API request
                    val response = api.getLayout()

                    if (response.isSuccessful && response.body() != null) {
                        Log.i(TAG, "Successfully fetched devices data: ${response.body()}")
                        val body = response.body() ?: ""
                        repository.upsertLayout(Layout(0, body))

                        // optimistic update for layout observed by Compose
                        val adapter = moshiLayoutAdapter
                        val parsed = adapter.fromJson(body)
                        layout.value = parsed
                    } else {
                        _toastMessage.emit(
                            UiMessage.StringRes(
                                R.string.request_failed,
                                listOf(response.code())
                            )
                        )
                        Log.e(TAG, "Request failed: ${response.code()}")
                    }
                }
            } catch (ex: TimeoutCancellationException) {
                _toastMessage.emit(UiMessage.StringRes(R.string.request_failed_timeout_exceeded))
                Log.e(TAG, "Request failed: timeout exceeded")
            } catch (ex: IOException) {
                _toastMessage.emit(UiMessage.StringRes(R.string.could_not_fetch_data_from_the_server))
                Log.e(TAG, "IOException, could not fetch data from server")
            } catch (ex: HttpException) {
                _toastMessage.emit(UiMessage.StringRes(R.string.unexpected_response))
                Log.e(TAG, "HttpException, unexpected response")
            } finally {
                isUpdating.value = false
            }
        }
    }

    companion object {
        val TAG: String = MainViewModel::class.java.name

        private val moshiLayoutAdapter = Moshi.Builder()
            .add(
                PolymorphicJsonAdapterFactory.of(LayoutElement::class.java, "type")
                    .withSubtype(LayoutElement.Page::class.java, "page")
                    .withSubtype(LayoutElement.Section::class.java, "section")
                    .withSubtype(LayoutElement.Text::class.java, "text")
                    .withSubtype(LayoutElement.Image::class.java, "image")
            )
            .add(KotlinJsonAdapterFactory())
            .build()
            .adapter(LayoutElement::class.java)
    }
}
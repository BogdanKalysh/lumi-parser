package com.challange.lumiparser.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.challange.lumiparser.retrofit.LayoutAPI
import com.challange.lumiparser.room.LayoutRepository
import com.challange.lumiparser.room.models.Layout
import com.challange.lumiparser.ui.component.LayoutElement
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

    private val _toastMessage = MutableSharedFlow<String>()
    val toastMessage = _toastMessage.asSharedFlow()

    init {
        viewModelScope.launch {
            dbLayout.collect { fetchedLayout ->
                if (fetchedLayout == null || fetchedLayout.layoutJson.isEmpty()) {
                    requestLayout()
                }
                val adapter = moshi.adapter(LayoutElement::class.java)
                fetchedLayout?.run {
                    val parsed = adapter.fromJson(layoutJson)
                    layout.value = parsed
                }
            }
        }
    }

    fun requestLayout() {
        Log.d(TAG, "Calling loadLayout")
        viewModelScope.launch {
            try {
                isUpdating.value = true
                withTimeout(5000L) { // request timeout 5 seconds
                    delay(2000L) // Simulating a long running API request
                    val response = api.getLayout()

                    if (response.isSuccessful && response.body() != null) {
                        Log.i(TAG, "Successfully fetched devices data.")
                        repository.upsertLayout(Layout(0, response.body() ?: ""))
                    } else {
                        _toastMessage.emit("Request failed: ${response.code()}")
                        Log.e(TAG, "Request failed: ${response.code()}")
                    }
                }
            } catch (ex: TimeoutCancellationException) {
                _toastMessage.emit("Request failed: timeout exceeded")
                Log.e(TAG, "Request failed: timeout exceeded")
            } catch (ex: IOException) {
                _toastMessage.emit("Request failed: could not fetch data from serve")
                Log.e(TAG, "IOException, could not fetch data from server")
            } catch (ex: HttpException) {
                _toastMessage.emit("Request failed: unexpected response")
                Log.e(TAG, "HttpException, unexpected response")
            } finally {
                isUpdating.value = false
            }
        }
    }

    companion object {
        val TAG: String = MainViewModel::class.java.name

        private val moshi = Moshi.Builder()
            .add(
                PolymorphicJsonAdapterFactory.of(LayoutElement::class.java, "type")
                    .withSubtype(LayoutElement.Page::class.java, "page")
                    .withSubtype(LayoutElement.Section::class.java, "section")
                    .withSubtype(LayoutElement.Text::class.java, "text")
                    .withSubtype(LayoutElement.Image::class.java, "image")
            )
            .add(KotlinJsonAdapterFactory())
            .build()
    }
}
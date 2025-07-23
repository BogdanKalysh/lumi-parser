package com.challange.lumiparser.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.challange.lumiparser.retrofit.LayoutAPI
import com.challange.lumiparser.room.LayoutRepository
import com.challange.lumiparser.room.models.Layout
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class MainViewModel(private val repository: LayoutRepository, private val api: LayoutAPI): ViewModel() {
    val isUpdating = MutableStateFlow(false)
    val layout = repository.getFirstLayout()

    fun loadLayout() {
        Log.d(TAG, "Calling loadLayout")

        viewModelScope.launch {
            val response = try {
                api.getLayout()
            } catch (ex: IOException) {
                Log.e(TAG, "IOException, could not fetch data from server")
                isUpdating.value = false
                return@launch
            } catch (ex: HttpException) {
                Log.e(TAG, "HttpException, unexpected response")
                isUpdating.value = false
                return@launch
            }

            if (response.isSuccessful && response.body() != null) {
                Log.i(TAG, "Successfully fetched devices data.")
                repository.upsertLayout(Layout(0, response.body() ?: ""))
            }
            isUpdating.value = false
        }
    }

    companion object {
        val TAG: String = MainViewModel::class.java.name
    }
}
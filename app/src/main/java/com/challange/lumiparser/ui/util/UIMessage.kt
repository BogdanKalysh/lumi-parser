package com.challange.lumiparser.ui.util

sealed class UiMessage {
    data class StringRes(val resId: Int, val args: List<Any> = emptyList()) : UiMessage()
    data class Text(val message: String) : UiMessage()
}
package com.challange.lumiparser.ui.component

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
sealed class LayoutElement {
    @JsonClass(generateAdapter = true)
    data class Page(val title: String, val items: List<LayoutElement>) : LayoutElement()
    @JsonClass(generateAdapter = true)
    data class Section(val title: String, val items: List<LayoutElement>) : LayoutElement()
    @JsonClass(generateAdapter = true)
    data class Text(val title: String) : LayoutElement()
    @JsonClass(generateAdapter = true)
    data class Image(val title: String, val src: String) : LayoutElement()
}
package com.challange.lumiparser.retrofit.model

sealed class LayoutElement {
    data class Page(val title: String, val items: List<LayoutElement>) : LayoutElement()
    data class Section(val title: String, val items: List<LayoutElement>) : LayoutElement()
    data class Text(val title: String) : LayoutElement()
    data class Image(val title: String, val src: String) : LayoutElement()
}
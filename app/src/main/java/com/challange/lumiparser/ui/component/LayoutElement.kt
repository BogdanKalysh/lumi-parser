package com.challange.lumiparser.ui.component

import com.challange.lumiparser.ui.visitor.LayoutElementVisitor
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
sealed class LayoutElement {
    abstract fun <T> accept(visitor: LayoutElementVisitor<T>): T

    @JsonClass(generateAdapter = true)
    data class Page(val title: String, val items: List<LayoutElement>) : LayoutElement() {
        override fun <T> accept(visitor: LayoutElementVisitor<T>) = visitor.visitPage(this)
    }

    @JsonClass(generateAdapter = true)
    data class Section(val title: String, val items: List<LayoutElement>) : LayoutElement() {
        override fun <T> accept(visitor: LayoutElementVisitor<T>) = visitor.visitSection(this)
    }

    @JsonClass(generateAdapter = true)
    data class Text(val title: String) : LayoutElement() {
        override fun <T> accept(visitor: LayoutElementVisitor<T>) = visitor.visitText(this)
    }

    @JsonClass(generateAdapter = true)
    data class Image(val title: String, val src: String) : LayoutElement() {
        override fun <T> accept(visitor: LayoutElementVisitor<T>) = visitor.visitImage(this)
    }
}
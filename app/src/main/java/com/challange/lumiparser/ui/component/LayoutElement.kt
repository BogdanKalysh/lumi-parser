package com.challange.lumiparser.ui.component

import com.challange.lumiparser.ui.visitor.LayoutElementVisitor
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
sealed class LayoutElement {
    abstract fun <R> accept(visitor: LayoutElementVisitor<R>): R

    @JsonClass(generateAdapter = true)
    data class Page(val title: String, val items: List<LayoutElement>) : LayoutElement() {
        override fun <R> accept(visitor: LayoutElementVisitor<R>) = visitor.visitPage(this)
    }

    @JsonClass(generateAdapter = true)
    data class Section(val title: String, val items: List<LayoutElement>) : LayoutElement() {
        override fun <R> accept(visitor: LayoutElementVisitor<R>) = visitor.visitSection(this)
    }

    @JsonClass(generateAdapter = true)
    data class Text(val title: String) : LayoutElement() {
        override fun <R> accept(visitor: LayoutElementVisitor<R>) = visitor.visitText(this)
    }

    @JsonClass(generateAdapter = true)
    data class Image(val title: String, val src: String) : LayoutElement() {
        override fun <R> accept(visitor: LayoutElementVisitor<R>) = visitor.visitImage(this)
    }
}
package com.challange.lumiparser.ui.visitor

import com.challange.lumiparser.ui.component.LayoutElement


interface LayoutElementVisitor <T> {
    fun visitPage(page: LayoutElement.Page): T
    fun visitSection(section: LayoutElement.Section): T
    fun visitText(text: LayoutElement.Text): T
    fun visitImage(image: LayoutElement.Image): T
}
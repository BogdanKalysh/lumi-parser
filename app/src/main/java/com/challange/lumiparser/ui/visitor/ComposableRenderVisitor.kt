package com.challange.lumiparser.ui.visitor

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.challange.lumiparser.ui.component.LayoutElement

object ComposableRenderVisitor : LayoutElementVisitor<@Composable () -> Unit> {
    override fun visitPage(page: LayoutElement.Page): @Composable () -> Unit = {
        Column {
            Text(page.title, style = MaterialTheme.typography.headlineMedium)
            page.items.forEach { it.accept(this@ComposableRenderVisitor)() }
        }
    }

    override fun visitSection(section: LayoutElement.Section): @Composable () -> Unit = {
        Card(Modifier.padding(8.dp)) {
            Column(Modifier.padding(8.dp)) {
                Text(section.title, style = MaterialTheme.typography.titleLarge)
                section.items.forEach { it.accept(this@ComposableRenderVisitor)() }
            }
        }
    }

    override fun visitText(text: LayoutElement.Text): @Composable () -> Unit = {
        Text(text.title, Modifier.padding(8.dp))
    }

    override fun visitImage(image: LayoutElement.Image): @Composable () -> Unit = {
        Column(Modifier.padding(8.dp)) {
            Text(image.title)
            AsyncImage(
                model = image.src,
                contentDescription = image.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
        }
    }
}
package com.challange.lumiparser.ui.visitor

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.challange.lumiparser.ui.component.LayoutElement
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

class ComposableRenderVisitor(
    private val navController: NavController,
    private val depth: Int = 0
) : LayoutElementVisitor<@Composable () -> Unit> {

    private fun getTitleStyle(baseStyle: TextStyle): TextStyle {
        val baseSize = baseStyle.fontSize
        val newSize = (baseSize.value - depth * 2).coerceAtLeast(12f)
        return baseStyle.copy(fontSize = newSize.sp)
    }

    override fun visitPage(page: LayoutElement.Page): @Composable () -> Unit = {
        Column {
            Text(page.title, style = MaterialTheme.typography.headlineMedium)
            page.items.forEach { it.accept(this@ComposableRenderVisitor)() }
        }
    }

    override fun visitSection(section: LayoutElement.Section): @Composable () -> Unit = {
        Card(Modifier.padding(8.dp)) {
            Column(Modifier.padding(8.dp)) {
                // decreasing the title text size for nested sections
                Text(section.title, style = getTitleStyle(MaterialTheme.typography.titleLarge))
                section.items.forEach { it.accept(ComposableRenderVisitor(navController, depth + 1))() }
            }
        }
    }

    override fun visitText(text: LayoutElement.Text): @Composable () -> Unit = {
        Text(text.title, Modifier.padding(8.dp))
    }

    override fun visitImage(image: LayoutElement.Image): @Composable () -> Unit = {
        Column(Modifier.padding(8.dp)) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(image.src)
                    .size(200)
                    .build(),
                contentDescription = image.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clickable {
                        val encodedUrl = URLEncoder.encode(image.src, StandardCharsets.UTF_8.toString())
                        navController.navigate("full_image_screen/$encodedUrl/${image.title}") {
                            launchSingleTop = true
                        }
                    }
            )
        }
    }
}
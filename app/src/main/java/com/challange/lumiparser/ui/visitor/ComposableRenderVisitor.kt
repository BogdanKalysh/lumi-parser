package com.challange.lumiparser.ui.visitor

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.challange.lumiparser.R
import com.challange.lumiparser.ui.component.LayoutElement
import com.challange.lumiparser.ui.theme.Blue
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
        val pageTitle = stringResource(R.string.page_title, page.title)

        Column {
            Text(
                page.title,
                style = MaterialTheme.typography.displayLarge,
                modifier = Modifier
                    .padding(top = 24.dp)
                    .semantics(mergeDescendants = false) {
                        heading()
                        contentDescription = pageTitle
                    }
            )
                page.items.forEach { it.accept(this@ComposableRenderVisitor)() }
        }
    }

    override fun visitSection(section: LayoutElement.Section): @Composable () -> Unit = {
        val sectionTitle = stringResource(R.string.section_title, section.title)

        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondary
            ),
            modifier = Modifier.padding(top = 16.dp),
        ) {
            val paddingValues = if (depth > 0) {
                PaddingValues(start = 16.dp)
            } else {
                PaddingValues(16.dp)
            }
            Column(Modifier.padding(paddingValues)) {
                // decreasing the title text size for nested sections
                Text(
                    section.title,
                    style = getTitleStyle(MaterialTheme.typography.titleMedium),
                    modifier = Modifier.semantics(mergeDescendants = false) {
                        contentDescription = sectionTitle
                    })
                Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(Blue))
                section.items.forEach { it.accept(ComposableRenderVisitor(navController, depth + 1))() }
            }
        }
    }

    override fun visitText(text: LayoutElement.Text): @Composable () -> Unit = {
        Text(
            text.title,
            style = getTitleStyle(MaterialTheme.typography.bodyMedium),
            modifier = Modifier.padding(top = 8.dp))
    }

    override fun visitImage(image: LayoutElement.Image): @Composable () -> Unit = {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(image.src)
                .size(200)
                .build(),
            contentDescription = image.title,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
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
package com.challange.lumiparser.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.challange.lumiparser.R

@Composable
fun FullImageScreen(url: String, title: String, navController: NavHostController, modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxSize()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(
                onClick = { navController.navigateUp() },
                modifier = Modifier
                    .size(48.dp)
                    .shadow(5.dp, shape = CircleShape, clip = false)
                    .background(color = Color.Red, shape = CircleShape)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_reload),
                    contentDescription = "Reload button",
                    tint = Color.Blue
                )
            }
            Text(title, fontSize = 24.sp, modifier = Modifier.padding(16.dp))
        }

        AsyncImage(
            model = url,
            contentDescription = title,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
    }
}
package com.challange.lumiparser.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.challange.lumiparser.R

@Composable
fun FullImageScreen(url: String, title: String, navController: NavHostController, modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxSize()) {
        TopBar(title, navController)
        AsyncImage(
            model = url,
            contentDescription = title,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .weight(1f)
        )
    }
}

@Composable
fun TopBar(title: String, navController: NavHostController, modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(start = 16.dp)
            .fillMaxWidth()
            .height(64.dp)
    ) {
        IconButton(
            onClick = { navController.navigateUp()  },
            modifier = Modifier
                .padding(end = 16.dp)
                .size(48.dp)
                .background(color = MaterialTheme.colorScheme.secondary, shape = CircleShape)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_back),
                contentDescription = stringResource(R.string.back_button_description),
                tint = MaterialTheme.colorScheme.onSecondary
            )
        }

        Text(title, style = MaterialTheme.typography.titleSmall,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .weight(1F))
    }
}
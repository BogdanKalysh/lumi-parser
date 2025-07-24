package com.challange.lumiparser.ui.screen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.challange.lumiparser.R
import com.challange.lumiparser.ui.visitor.ComposableRenderVisitor
import com.challange.lumiparser.viewmodel.MainViewModel

@Composable
fun MainScreen(viewModel: MainViewModel, navController: NavHostController, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        TobBar(viewModel)
        LayoutScroll(viewModel, navController, Modifier.weight(1F))
    }

    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.toastMessage.collect { msg ->
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        }
    }
}

@Composable
private fun TobBar(viewModel: MainViewModel, modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.logo),
            contentDescription = null,
            modifier = Modifier
                .padding(start = 16.dp)
                .size(32.dp)
        )
        Text(
            stringResource(R.string.app_name),
            modifier = Modifier.padding(start = 16.dp)
        )
        IconButton(
            onClick = { viewModel.requestLayout() },
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
    }
}

@Composable
private fun LayoutScroll(viewModel: MainViewModel, navController: NavHostController, modifier: Modifier = Modifier) {
    val layout by viewModel.layout.collectAsState(null)
    val isUpdating by viewModel.isUpdating.collectAsState(false)
    val isInitialized by viewModel.isInitialized.collectAsState(false)
    val renderVisitor = ComposableRenderVisitor(navController)

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxWidth()
    ) {
        if (isUpdating) {
            CircularProgressIndicator()
        } else {
            if (layout == null && isInitialized) {
                Text("No layout",
                    Modifier
                        .background(Color.Red)
                        .padding(horizontal = 20.dp)
                        .padding(vertical = 16.dp)
                )
            } else {
                Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                    layout?.accept(renderVisitor)?.invoke()
                }
            }
        }
    }

}
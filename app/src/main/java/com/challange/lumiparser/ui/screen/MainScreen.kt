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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.challange.lumiparser.R
import com.challange.lumiparser.ui.util.UiMessage
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
            val text = when (msg) {
                is UiMessage.StringRes -> context.getString(msg.resId, *msg.args.toTypedArray())
                is UiMessage.Text -> msg.message
            }
            Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
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
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .weight(1F)
        )
        IconButton(
            onClick = { viewModel.requestLayout() },
            modifier = Modifier
                .padding(end = 16.dp)
                .size(48.dp)
                .background(color = MaterialTheme.colorScheme.secondary, shape = CircleShape)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_reload),
                contentDescription = stringResource(R.string.reload_button_description),
                tint = MaterialTheme.colorScheme.onSecondary
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
            CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary)
        } else {
            if (layout == null && isInitialized) {
                Text(
                    stringResource(R.string.no_layout),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.tertiary, RoundedCornerShape(16.dp))
                        .padding(horizontal = 20.dp)
                        .padding(vertical = 16.dp)
                )
            } else {
                Column(modifier =
                    Modifier
                        .verticalScroll(rememberScrollState())
                        .padding(bottom = 16.dp)
                        .padding(horizontal = 16.dp)
                ) {
                    layout?.accept(renderVisitor)?.invoke()
                }
            }
        }
    }

}
package com.challange.lumiparser.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
        TobBar()
        LayoutScroll(viewModel, navController, Modifier.weight(1F))
    }
}

@Composable
private fun TobBar(modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.ic_launcher_background),
            contentDescription = null,
            modifier = Modifier
                .padding(start = 16.dp)
                .size(32.dp)
        )
        Text(
            stringResource(R.string.app_name),
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}

@Composable
private fun LayoutScroll(viewModel: MainViewModel, navController: NavHostController, modifier: Modifier = Modifier) {
    val layout by viewModel.layout.collectAsState(initial = null)
    val renderVisitor = ComposableRenderVisitor(navController)

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
    ) {
        layout?.run {
            accept(renderVisitor)()
        }
    }
}
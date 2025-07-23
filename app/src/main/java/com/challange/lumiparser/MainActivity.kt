package com.challange.lumiparser

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.challange.lumiparser.ui.theme.LumiParserTheme
import com.challange.lumiparser.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LumiParserTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val layout by viewModel.layout.collectAsState(initial = null)

                    layout?.run {
                        TextDisplayer(layoutJson, Modifier.padding(innerPadding))
                    }
                }
            }
        }
        viewModel.loadLayout()
    }
}

@Composable
fun TextDisplayer(text: String, modifier: Modifier = Modifier) {
    Box(Modifier.fillMaxSize()) {
        Text(text, modifier.align(Alignment.Center))
    }
}
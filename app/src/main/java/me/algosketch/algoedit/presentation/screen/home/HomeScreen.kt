package me.algosketch.algoedit.presentation.screen.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.algosketch.algoedit.design.theme.MyApplicationTheme

@Composable
fun HomeScreen(
    navigateToEdit: () -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        item {
            Button(
                modifier = Modifier.padding(top = 40.dp),
                onClick = navigateToEdit,
            ) {
                Text("+ 새 프로젝트")
            }
        }

        item {
            Text("준비중...")
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    MyApplicationTheme {
        HomeScreen({})
    }
}
package me.algosketch.algoedit.presentation.screen.edition

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import me.algosketch.algoedit.design.component.TmpAppBar
import me.algosketch.algoedit.design.theme.MyApplicationTheme

@Composable
fun EditionScreen(
    viewModel: EditionViewModel = hiltViewModel()
) {
    Column {
        TmpAppBar("Project(1)")
    }
}

@Preview
@Composable
fun EditionScreenPreview() {
    MyApplicationTheme {
        EditionScreen()
    }
}
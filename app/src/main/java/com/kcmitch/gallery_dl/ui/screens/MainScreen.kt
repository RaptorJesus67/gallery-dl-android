package com.kcmitch.gallery_dl.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.kcmitch.gallery_dl.components.FooterBar
import com.kcmitch.gallery_dl.pages.GalleryPage
import com.kcmitch.gallery_dl.pages.Homepage
import com.kcmitch.gallery_dl.pages.PlaceholderPage
import com.kcmitch.gallery_dl.pages.SettingsPage
import com.kcmitch.gallery_dl.pages.TerminalPage
import com.kcmitch.gallery_dl.ui.GalleryDlViewModel
import kotlinx.coroutines.launch

/**
 * MainScreen houses the primary 5-page horizontal pager deck
 * and bottom navigation FooterBar.
 * Instant snap scrolling via scrollToPage ensures clicking bottom nav buttons
 * pulls the exact page without passing/quickloading intermediate pages.
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreen(
    viewModel: GalleryDlViewModel,
    onNavigateToSettings: () -> Unit,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState(initialPage = 2, pageCount = { 5 })
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = {
            FooterBar(
                currentPage = pagerState.currentPage,
                onPageSelected = { pageIndex ->
                    coroutineScope.launch {
                        // Instant card selection without scrolling past intermediate pages
                        pagerState.scrollToPage(pageIndex)
                    }
                },
                onAddButtonClick = {
                    coroutineScope.launch {
                        pagerState.scrollToPage(2)
                    }
                }
            )
        }
    ) { innerPadding ->
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) { page ->
            when (page) {
                0 -> TerminalPage(viewModel = viewModel)
                1 -> PlaceholderPage(viewModel = viewModel)
                2 -> Homepage(
                    viewModel = viewModel,
                    onAddElementClick = {
                        coroutineScope.launch {
                            pagerState.scrollToPage(2)
                        }
                    }
                )
                3 -> GalleryPage(viewModel = viewModel)
                4 -> SettingsPage(viewModel = viewModel)
            }
        }
    }
}

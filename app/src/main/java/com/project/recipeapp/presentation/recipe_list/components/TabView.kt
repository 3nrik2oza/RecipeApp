package com.project.recipeapp.presentation.recipe_list.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.project.recipeapp.R

@Composable
fun TabView(
    selectedTab: TabState,
    onTabSelected: (TabState) -> Unit
){
    TabRow(
        selectedTabIndex = selectedTab.ordinal,
        modifier = Modifier
            .padding(vertical = 12.dp)
            .widthIn(max = 700.dp)
            .fillMaxWidth(),
        indicator = { tabPosition ->
            TabRowDefaults.SecondaryIndicator(
                modifier = Modifier
                    .tabIndicatorOffset(tabPosition[selectedTab.ordinal])
            )
        }
    ) {
        Tab(
            selected = selectedTab == TabState.HOME,
            onClick = {
                onTabSelected(TabState.HOME)
            },
            modifier = Modifier,
            unselectedContentColor = MaterialTheme.colorScheme.onSurface
        ){
            Text(
                text = stringResource(R.string.home),
                modifier = Modifier
                    .padding(vertical = 12.dp)
            )
        }
        Tab(
            selected = selectedTab == TabState.LIKED,
            onClick = {
                onTabSelected(TabState.LIKED)
            },
            modifier = Modifier,
            unselectedContentColor = MaterialTheme.colorScheme.onSurface
        ){
            Text(
                text = stringResource(R.string.favorites),
                modifier = Modifier
                    .padding(vertical = 12.dp)
            )
        }
    }
}
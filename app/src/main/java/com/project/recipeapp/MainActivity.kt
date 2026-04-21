package com.project.recipeapp

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember
import com.bumble.appyx.core.integration.NodeHost
import com.bumble.appyx.core.integrationpoint.NodeActivity
import com.project.recipeapp.presentation.navigation.RootNode
import com.project.recipeapp.ui.theme.RecipeAppTheme

class MainActivity : NodeActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val snackbarHostState = remember { SnackbarHostState() }

            RecipeAppTheme {
                NodeHost(integrationPoint = appyxV1IntegrationPoint) { buildContext ->
                    RootNode(
                        buildContext = buildContext,
                        snackbarHostState = snackbarHostState
                    )
                }
            }

        }
    }
}

package com.example.flashcard.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.flashcard.utils.APP_NAV_GRAPH
import com.example.flashcard.views.screens.AddQuestionScreen
import com.example.flashcard.views.screens.MainScreen
import com.example.flashcard.views.screens.UpdateQuestionScreen
import com.google.gson.Gson
import java.net.URLEncoder

fun NavGraphBuilder.appNavGraph(
    navController: NavController
){
    navigation(
        startDestination = FlashCard.MainScreen.name,
        route = APP_NAV_GRAPH
    ){
        composable(route = FlashCard.AddQuestionScreen.name) { 
            AddQuestionScreen(btnBack = { navController.popBackStack() },
                onAdd = {
                    navController.navigate(FlashCard.MainScreen.name){
                        popUpTo(0)
                    }
                }
            )
        }

        composable(route = FlashCard.UpdateQuestionScreen.name){
            UpdateQuestionScreen(btnBack = { navController.popBackStack() },
                onUpdate = {
                    navController.navigate(FlashCard.MainScreen.name){
                        popUpTo(0)
                    }
                }
            )
        }

        composable(route = FlashCard.MainScreen.name) {
            MainScreen(btnAdd = {
                                navController.navigate(FlashCard.AddQuestionScreen.name)
            }, btnEdit = {question ->
                val data = Gson().toJson(question)
                navController.navigate(FlashCard.UpdateQuestionScreen.route+"/${URLEncoder.encode(data, "UTF-8")}")
            }
            )
        }
    }
}
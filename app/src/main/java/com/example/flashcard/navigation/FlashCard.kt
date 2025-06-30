package com.example.flashcard.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

private fun String.appendArguments(navArguments: List<NamedNavArgument>): String {
    val mandatoryArguments =
        navArguments.filter { it.argument.defaultValue == null }.takeIf { it.isNotEmpty() }
            ?.joinToString(separator = "/", prefix = "/") { "{${it.name}}" }.orEmpty()
    val optionalArguments =
        navArguments.filter { it.argument.defaultValue != null }.takeIf { it.isNotEmpty() }
            ?.joinToString(separator = "&", prefix = "?") { "${it.name}={${it.name}}" }.orEmpty()
    return "$this$mandatoryArguments$optionalArguments"
}

sealed class FlashCard(
    val route: String,
    val navArguments: List<NamedNavArgument> = emptyList()
) {
    val name = route.appendArguments(navArguments)

    object MainScreen:FlashCard(route = "FlashCard")

    object AddQuestionScreen: FlashCard(route = "AddQuestionScreen")

    object UpdateQuestionScreen: FlashCard(route = "UpdateQuestionScreen",
        navArguments = listOf(
            navArgument(DestinationArgs.question){
                type = NavType.StringType
            }
        )
    )
}
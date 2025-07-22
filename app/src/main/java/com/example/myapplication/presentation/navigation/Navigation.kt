package com.example.myapplication.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.myapplication.presentation.ui.addcontact.AddContactScreen
import com.example.myapplication.presentation.ui.contactdetail.ContactDetailScreen
import com.example.myapplication.presentation.ui.contactlist.ContactListScreen

/**
 * Navigation routes for the application
 */
sealed class Screen(val route: String) {
    object ContactList : Screen("contactList")
    object ContactDetail : Screen("contactDetail/{contactId}") {
        fun createRoute(contactId: String) = "contactDetail/$contactId"
    }

    object AddContact : Screen("addContact?contactId={contactId}") {
        // Base route for adding a new contact
        const val baseRoute = "addContact"

        // For editing a contact
        fun createRouteWithId(contactId: String) = "addContact?contactId=$contactId"
    }
}

/**
 * Main navigation component for the application
 *
 * @param navController The navigation controller
 * @param startDestination The starting destination route
 */
@Composable
fun AppNavigation(
    navController: NavHostController,
    startDestination: String = Screen.ContactList.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.ContactList.route) {
            ContactListScreen(
                onContactClick = { contact ->
                    navController.navigate(Screen.ContactDetail.createRoute(contact.id))
                },
                onAddContactClick = {
                    navController.navigate(Screen.AddContact.baseRoute)
                }
            )
        }

        composable(
            route = Screen.ContactDetail.route,
            arguments = listOf(navArgument("contactId") { type = NavType.StringType })
        ) { backStackEntry ->
            val contactId = backStackEntry.arguments?.getString("contactId") ?: ""
            ContactDetailScreen(
                contactId = contactId,
                onEditClick = { navController.navigate(Screen.AddContact.createRouteWithId(contactId)) },
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(
            route = Screen.AddContact.route,
            arguments = listOf(
                navArgument("contactId") {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                }
            )
        ) { backStackEntry ->
            val contactId = backStackEntry.arguments?.getString("contactId")
            val isEditing = contactId != null

            AddContactScreen(
                contactId = contactId,
                onContactSaved = {
                    if (isEditing) {
                        // When editing, just go back
                        navController.popBackStack()
                    } else {
                        // When adding, navigate to contact list and clear back stack
                        navController.navigate(Screen.ContactList.route) {
                            popUpTo(Screen.ContactList.route) {
                                inclusive = true
                            }
                        }
                    }
                },
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}

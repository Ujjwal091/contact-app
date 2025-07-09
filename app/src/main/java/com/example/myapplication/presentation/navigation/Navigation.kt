package com.example.myapplication.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.myapplication.presentation.ui.contactlist.ContactListScreen
import com.example.myapplication.presentation.ui.contactdetail.ContactDetailScreen
import com.example.myapplication.presentation.ui.addcontact.AddContactScreen
import com.example.myapplication.presentation.ui.editcontact.EditContactScreen

/**
 * Navigation routes for the application
 */
sealed class Screen(val route: String) {
    object ContactList : Screen("contactList")
    object ContactDetail : Screen("contactDetail/{contactId}") {
        fun createRoute(contactId: String) = "contactDetail/$contactId"
    }
    object AddContact : Screen("addContact")
    object EditContact : Screen("editContact/{contactId}") {
        fun createRoute(contactId: String) = "editContact/$contactId"
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
                    navController.navigate(Screen.AddContact.route)
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
                onEditClick = { navController.navigate(Screen.EditContact.createRoute(contactId)) },
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(Screen.AddContact.route) {
            AddContactScreen(
                onContactAdded = { navController.popBackStack() },
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(
            route = Screen.EditContact.route,
            arguments = listOf(navArgument("contactId") { type = NavType.StringType })
        ) { backStackEntry ->
            val contactId = backStackEntry.arguments?.getString("contactId") ?: ""
            EditContactScreen(
                contactId = contactId,
                onContactUpdated = { navController.popBackStack() },
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}

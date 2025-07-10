package com.example.myapplication.di

import android.content.ContentResolver
import com.example.myapplication.data.datasource.ContactDataSource
import com.example.myapplication.data.datasource.ContactDataSourceImpl
import com.example.myapplication.data.repository.ContactRepositoryImpl
import com.example.myapplication.domain.repository.ContactRepository
import com.example.myapplication.domain.usecase.*
import com.example.myapplication.presentation.ui.addcontact.AddContactViewModel
import com.example.myapplication.presentation.ui.contactdetail.ContactDetailViewModel
import com.example.myapplication.presentation.ui.contactlist.ContactListViewModel
import com.example.myapplication.presentation.ui.editcontact.EditContactViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

// Main application module that combines all other modules
val appModule = module {
    // Data sources
    single { androidContext().contentResolver as ContentResolver }
    single<ContactDataSource> { ContactDataSourceImpl(get()) }

    // Repositories
    single<ContactRepository> { ContactRepositoryImpl(get()) }

    // Use cases
    factory { GetAllContactsUseCase(get()) }
    factory { GetContactByIdUseCase(get()) }
    factory { DeleteContactUseCase(get()) }
    factory { AddContactUseCase(get()) }
    factory { UpdateContactUseCase(get()) }

    // ViewModels
    viewModel { ContactListViewModel(get()) }
    viewModel { ContactDetailViewModel(get(), get()) }
    viewModel { AddContactViewModel(get()) }
    viewModel { EditContactViewModel(get(), get()) }
}

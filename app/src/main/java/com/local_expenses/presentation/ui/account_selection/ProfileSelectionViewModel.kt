package com.local_expenses.presentation.ui.account_selection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.local_expenses.data.local.dao.UserDao
import com.local_expenses.data.local.entity.UserEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileSelectionViewModel @Inject constructor(
    private val userDao: UserDao
) : ViewModel() {

    val accounts: StateFlow<List<UserEntity>> = userDao.getUserDetails()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
}

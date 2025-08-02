package com.local_expenses.presentation.ui.account_selection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.local_expenses.data.local.dao.AccountDao
import com.local_expenses.data.local.dao.UserDao
import com.local_expenses.data.local.entity.AccountEntity
import com.local_expenses.data.local.entity.UserEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileSelectionViewModel @Inject constructor(
    private val userDao: UserDao,
    private val accountDao: AccountDao
) : ViewModel() {

    private val _createdUserId = MutableSharedFlow<Long>(replay = 0)  // Event stream
    val createdUserId = _createdUserId.asSharedFlow()

//    init {
//        viewModelScope.launch{
//            val userId1 = userDao.insertUser(UserEntity(name = "maarlesh", password = "maarlesh"))
//            val userId2 = userDao.insertUser(UserEntity(name = "subamaarleshar", password = "maarlesh"))
//            val userId3 = userDao.insertUser(UserEntity(name = "admin", password = "maarlesh"))
//
//            val accounts = listOf(
//                AccountEntity(userId = userId1.toInt(), accountName = "HDFC", balance = 1000.0),
//                AccountEntity(userId = userId1.toInt(), accountName = "ICICI", balance = 1000.0),
//                AccountEntity(userId = userId1.toInt(), accountName = "KVB", balance = 1000.0),
//
//                AccountEntity(userId = userId2.toInt(), accountName = "HDFC", balance = 1000.0),
//                AccountEntity(userId = userId2.toInt(), accountName = "ICICI", balance = 1000.0),
//                AccountEntity(userId = userId2.toInt(), accountName = "KVB", balance = 1000.0),
//
//                AccountEntity(userId = userId3.toInt(), accountName = "HDFC", balance = 1000.0),
//                AccountEntity(userId = userId3.toInt(), accountName = "ICICI", balance = 1000.0),
//                AccountEntity(userId = userId3.toInt(), accountName = "KVB", balance = 1000.0),
//            )
//
//            for (account in accounts) {
//                accountDao.insertAccount(account)
//            }
//
//        }
//    }

    val accounts: StateFlow<List<UserEntity>> = userDao.getUserDetails()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _currentUserId = MutableStateFlow<Int?>(null)

    @OptIn(ExperimentalCoroutinesApi::class)
    val account: StateFlow<List<AccountEntity>> = _currentUserId
        .filterNotNull()
        .flatMapLatest { userId ->
            accountDao.getAccountsByUser(userId)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun setSelectedUserId(userId: Int) {
        _currentUserId.value = userId
    }

    fun createUser(username: String, password: String) {
        viewModelScope.launch {
            val userEntity = UserEntity(name = username, password = password)
            val id = userDao.insertUser(userEntity)
            _createdUserId.emit(id)
        }
    }
}

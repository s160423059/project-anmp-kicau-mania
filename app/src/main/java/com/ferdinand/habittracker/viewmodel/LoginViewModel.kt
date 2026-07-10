package com.ferdinand.habittracker.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.ferdinand.habittracker.model.User
import com.ferdinand.habittracker.util.buildDb
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) :
    AndroidViewModel(application), CoroutineScope {

    val loginResultLD = MutableLiveData<Boolean>()

    private var job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    init {
        ensureDefaultUser()
    }

    private fun ensureDefaultUser() {
        launch {
            val db = buildDb(getApplication())
            val existing = db.userDao().findByUsername("student")

            if (existing == null) {
                db.userDao().insertAll(User(username = "student", password = "123"))
            }
        }
    }

    fun login(username: String, password: String) {
        launch {
            val db = buildDb(getApplication())
            val user = db.userDao().selectUser(username, password)
            loginResultLD.postValue(user != null)
        }
    }
}
package org.sopt.santamanitto.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import org.sopt.santamanitto.data.ManittoDataSource
import org.sopt.santamanitto.preference.UserPreferenceManager

class MainViewModel @ViewModelInject constructor(
    userPreferenceManager: UserPreferenceManager,
    manittoDataSource: ManittoDataSource
) : ViewModel() {

    val myManittoHistory =
        manittoDataSource.getMyManittoHistory(userPreferenceManager.getSerialNumber()!!)
}
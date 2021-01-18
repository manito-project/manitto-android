package org.sopt.santamanitto.data

import androidx.lifecycle.LiveData
import org.sopt.santamanitto.data.MyManittoHistory

interface ManittoDataSource {

    fun getMyManittoHistory(uuid: String) : LiveData<List<MyManittoHistory>>
}
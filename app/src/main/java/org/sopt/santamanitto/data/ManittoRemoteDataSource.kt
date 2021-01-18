package org.sopt.santamanitto.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class ManittoRemoteDataSource : ManittoDataSource {

    override fun getMyManittoHistory(uuid: String): LiveData<List<MyManittoHistory>> {
        //Todo: 구현하여야 함
//        val list = mutableListOf<MyManittoHistory>()
//        list.run {
//            add(MyManittoHistory("마니또", "김성규", "test", "개발하기"))
//            add(MyManittoHistory("마니또1", "김성규1", "test", "개발하기1"))
//            add(MyManittoHistory("마니또2", "김성규2", "test", "개발하기2"))
//            add(MyManittoHistory("마니또3", "김성규3", "test", "개발하기3"))
//            add(MyManittoHistory("마니또4", "김성규4", "test", "개발하기4"))
//        }
//        return MutableLiveData(list)
        return MutableLiveData()
    }
}
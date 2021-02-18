package org.sopt.santamanitto.room.manittoroom

import org.sopt.santamanitto.NetworkViewModel

class ManittoRoomViewModel: NetworkViewModel() {

    private var _roomId = -1
    var roomId: Int
        get() = _roomId
        set(value) {
            _roomId = value
        }

    private var _isMatched = false
    var isMatched: Boolean
        get() = _isMatched
        set(value) {
            _isMatched = value
        }

}
package org.sopt.santamanitto.room.create.adaptor

class CreateConfirmAdaptor(createMissionCallback: CreateMissionCallback) : CreateMissionAdaptor(createMissionCallback) {

    override fun getItemCount(): Int {
        return missions.size
    }
}
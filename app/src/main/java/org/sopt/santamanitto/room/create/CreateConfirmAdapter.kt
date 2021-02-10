package org.sopt.santamanitto.room.create

class CreateConfirmAdapter(createMissionCallback: CreateMissionCallback) : CreateMissionAdaptor(createMissionCallback) {

    override fun getItemCount(): Int {
        return missions.size
    }
}
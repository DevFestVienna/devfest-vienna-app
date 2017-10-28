package at.devfest.app.data.app.model

enum class Room constructor(val id: Int, val label: String) {
    NONE(0, ""),
    ROOM_1(1, "Room Willhaben"),
    ROOM_2(2, "Room 2"),
    ROOM_3(3, "Room 3");


    companion object {

        @JvmStatic
        fun getFromId(id: Int): Room {
            for (room in Room.values()) {
                if (room.id == id) {
                    return room
                }
            }
            return NONE
        }

        @JvmStatic
        fun getFromLabel(label: String?): Room {
            for (room in Room.values()) {
                if (label == room.label) {
                    return room
                }
            }
            return NONE
        }
    }
}

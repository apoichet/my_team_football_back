package domain


abstract class TeamMember(
    firstName: String,
    lastName: String,
    mail: String,
    phone: String,
    adress: List<Adress>
)

data class Player (
    var firstName: String,
    var lastName: String,
    var mail: String,
    var phone: String,
    var adress: List<Adress>,
    var bestQuality: String,
    var worstDefault: String,
    var verbatim: String,
    var strongFoot: PlayerFoot,
    var weight: Int?,
    var height: Int?,
    var originalPositiuon: PlayerPosition,
    var positions: List<PlayerPosition>
): TeamMember(firstName, lastName, mail, phone, adress)

enum class PlayerPosition(val position: String) {
    GK("Goal Keeper")
}

enum class PlayerFoot {
    LEFT, RIGHT, BOTH
}

data class Responsible (
    val type: ResponsibleType,
    var firstName: String,
    var lastName: String,
    var mail: String,
    var phone: String,
    var adress: List<Adress>
): TeamMember(firstName, lastName, mail, phone, adress)


enum class ResponsibleType(title: String, description: String) {
    COACH("Entraineur",""),
    COACH_ASSITANT("Entraineur Adjoint",""),
    TREASURER("Trésorier", ""),
    SECRETARY("Secrétaire Générale", "")
}

class Adress(val adress: String, val city: String, val zipCode: String, val country: String = "France")
package domain

data class Team(
    var name: String,
    var logo: String,
    var colors: List<String>,
    var pictures: List<String>,
    var licenceAmount: Float,
    var homeStadium: Stadium,
    var responsibles: List<Responsible>,
    var players: List<Player>,
    var playerContributions: List<PlayerContribution>,
    var rib: Rib)

data class Stadium(var name: String, var adress: Adress)

data class PlayerContribution(val player: Player, val amount: Float, var paymentType: PaymentType)

enum class PaymentType {
    TRANSFERT, CASH, CHECK
}

data class Rib(var iban: String)
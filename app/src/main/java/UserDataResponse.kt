package org.iesharia.coroutineszerotohero

data class UserDataResponse(
    val id: Int,
    val name: String,
    val username: String,
    val email: String,
    val address: AddressData,
    val phone: String,
    val website: String,
    val company: CompanyData
)

data class AddressData(
    val street: String,
    val suite: String,
    val city: String,
    val zipcode: String,
    val geo: GeoData
)

data class GeoData(
    val lat: String,
    val lng: String
)

data class CompanyData(
    val name: String,
    val catchPhrase: String,
    val bs: String
)

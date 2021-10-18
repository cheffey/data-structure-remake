package simulation.https.model

/**
 * Created by Chef.Xie on 2021-10-17
 */
data class HttpsRequestPacket(val rsaEncryptedDesKey: String, val encryptedRequest: String)

data class HttpsResponsePacket(val encryptedResponse: String)

package simulation.https

import simulation.https.model.Certification
import simulation.https.model.HttpsRequestPacket
import simulation.util.CipherUtil
import java.util.UUID

/**
 * Created by Chef.Xie on 2021-10-15
 */
class HttpsClient {
    private val desKey = UUID.randomUUID().toString()
    private val localCertifications = mutableMapOf<HttpsServer, Certification>()

    fun send(data: String, server: HttpsServer): String {
        val request = buildHttpsPacket(server, data)
        val httpsResponsePacket = server.process(request)
        return decrypt(httpsResponsePacket.encryptedResponse)
    }

    private fun decrypt(desEncryptedStr: String) = CipherUtil.desDecryptByKey(desEncryptedStr, desKey)

    private fun buildHttpsPacket(server: HttpsServer, data: String): HttpsRequestPacket {
        val certification = localCertifications[server] ?: server.getCertification()
        val rsaEncryptedDesKey = CipherUtil.rsaEncryptByPublicKey(desKey, certification.rsaPublicKeyStr)
        val encryptedData = encrypt(data)
        return HttpsRequestPacket(rsaEncryptedDesKey, encryptedData)
    }

    private fun encrypt(data: String) = CipherUtil.desEncryptByKey(data, desKey)
}
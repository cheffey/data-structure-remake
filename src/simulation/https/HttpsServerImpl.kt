package simulation.https

import simulation.https.model.Certification
import simulation.https.model.HttpsRequestPacket
import simulation.https.model.HttpsResponsePacket
import simulation.https.model.ServerCertification
import simulation.https.model.ServerCertificationImpl
import simulation.util.CipherUtil

/**
 * Created by Chef.Xie on 2021-10-15
 */
interface HttpsServer : HttpServer {
    fun getCertification(): Certification
    fun process(request: HttpsRequestPacket): HttpsResponsePacket
}

interface HttpServer {
    fun response(request: String): String
}

class HttpsServerImpl : HttpServerImpl(), HttpsServer {
    private val certification: ServerCertification = ServerCertificationImpl

    override fun getCertification(): Certification = certification

    override fun process(request: HttpsRequestPacket): HttpsResponsePacket {
        val desKey = certification.rsaDecrypt(request.rsaEncryptedDesKey)
        val decryptedData = CipherUtil.desDecryptByKey(request.encryptedRequest, desKey)
        val response = response(decryptedData)
        val encryptedResponse = CipherUtil.desEncryptByKey(response, desKey)
        return HttpsResponsePacket(encryptedResponse)
    }
}

open class HttpServerImpl : HttpServer {
    override fun response(request: String): String {
        return request.reversed()
    }
}
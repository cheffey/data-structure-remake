package simulation.util

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64
import java.io.ByteArrayOutputStream
import java.security.KeyFactory
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.DESKeySpec

object CipherUtil {
    const val ENCRYPT_MAX_SIZE = 117
    const val DECRYPT_MAX_SIZE = 256

    fun rsaEncryptByPublicKey(str: String, publicKeyStr: String): String {
        val keyFactory = KeyFactory.getInstance("RSA")
        val publicKey = keyFactory.generatePublic(X509EncodedKeySpec(Base64.decode(publicKeyStr)))
        val byteArray = str.toByteArray()
        val cipher = Cipher.getInstance("RSA")
        cipher.init(Cipher.ENCRYPT_MODE, publicKey)

        var bytes: ByteArray
        var offset = 0

        val outputStream = ByteArrayOutputStream()

        while (byteArray.size - offset > 0) {
            if (byteArray.size - offset >= ENCRYPT_MAX_SIZE) {
                bytes = cipher.doFinal(byteArray, offset, ENCRYPT_MAX_SIZE)
                offset += ENCRYPT_MAX_SIZE
            } else {
                bytes = cipher.doFinal(byteArray, offset, (byteArray.size - offset))
                offset = byteArray.size
            }
            outputStream.write(bytes)
        }

        outputStream.close()
        return Base64.encode(outputStream.toByteArray())
    }

    fun rsaDecryptByPrivateKey(str: String, privateKeyStr: String): String {
        val privateKey = KeyFactory.getInstance("RSA")
            .generatePrivate(PKCS8EncodedKeySpec(Base64.decode(privateKeyStr)))
        val byteArray = Base64.decode(str)
        val cipher = Cipher.getInstance("RSA")
        cipher.init(Cipher.DECRYPT_MODE, privateKey)

        var bytes: ByteArray
        var offset = 0

        val outputStream = ByteArrayOutputStream()

        while (byteArray.size - offset > 0) {
            if (byteArray.size - offset >= DECRYPT_MAX_SIZE) {
                bytes = cipher.doFinal(byteArray, offset, DECRYPT_MAX_SIZE)
                offset += DECRYPT_MAX_SIZE
            } else {
                bytes = cipher.doFinal(byteArray, offset, (byteArray.size - offset))
                offset = byteArray.size
            }
            outputStream.write(bytes)
        }
        outputStream.close()
        return String(outputStream.toByteArray())
    }


    fun desEncryptByKey(src: String, desKey: String): String {
        val cipher = Cipher.getInstance("DES")
        val key = SecretKeyFactory.getInstance("DES").generateSecret(DESKeySpec(desKey.toByteArray()))
        cipher.init(Cipher.ENCRYPT_MODE, key)
        val dstBytes = cipher.doFinal(src.toByteArray())
        return Base64.encode(dstBytes)
    }

    fun desDecryptByKey(input: String, desKey: String): String {
        val cipher = Cipher.getInstance("DES")
        val key = SecretKeyFactory.getInstance("DES").generateSecret(DESKeySpec(desKey.toByteArray()))
        cipher.init(Cipher.DECRYPT_MODE, key)
        val encrypt = cipher.doFinal(Base64.decode(input))
        return String(encrypt)
    }
}

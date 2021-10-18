package simulation.util

import junit.framework.TestCase
import java.util.UUID

class CipherUtilTest : TestCase() {
    fun testRsa() {
        val input = "tada"
        val publicKeyStr = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAnvSR5VSoxCdhcgQzGkp8zSCOTUCP37YyVlJ8qzkRSxxNhB5OeKOP0OjhglAkn7v8i6SAR5ZDYwyLX6Y3W9+x5CoJ5bV3PpEzrq4qhv5JGBAE7q/ylR48y/3Aob/L04+4EuzFklzFILESmb7qsa5yKqtSO8Acvp2Ay0jJbzH6ZmhLkvW5C7+yoj/RnGWixop0EezxjV7wmI2aCHUAx4rUzbSoAhsonEYCRKS7MHx1t2M3vJBdinsnJEZ28tLXSdnTUrWzOK1gwYLfVW5ExQCLgOfGyweQ86ylgTDidjmeT4mpraqQHysLX/tv0FFhpUnxzK7P6DDWS3W0ep+o6eBvXQIDAQAB"
        val privateKeyStr = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCe9JHlVKjEJ2FyBDMaSnzNII5NQI/ftjJWUnyrORFLHE2EHk54o4/Q6OGCUCSfu/yLpIBHlkNjDItfpjdb37HkKgnltXc+kTOuriqG/kkYEATur/KVHjzL/cChv8vTj7gS7MWSXMUgsRKZvuqxrnIqq1I7wBy+nYDLSMlvMfpmaEuS9bkLv7KiP9GcZaLGinQR7PGNXvCYjZoIdQDHitTNtKgCGyicRgJEpLswfHW3Yze8kF2KeyckRnby0tdJ2dNStbM4rWDBgt9VbkTFAIuA58bLB5DzrKWBMOJ2OZ5PiamtqpAfKwtf+2/QUWGlSfHMrs/oMNZLdbR6n6jp4G9dAgMBAAECggEAekLTFPmQ9Y70vKXOSKKSa5Rm37SQ9RlGjm7TxT8XthYG6WAGK3Ri4eS9z2WlRddp4F6e7HD/U+gKK3/FhL0CLuTGyBBTr6QvhGQiAcMEpaVspcpfY6LmUGDVEZRcSlV419WWAYWpegO4stjN2+y5k2hC0AJsGZE7xyAtD4H5f1nvEbob0z/c9vlEYIUVZGsBf2PC7IPev64FLuGQE7/AsoM6jFh2etiCEkAbOUzHbhH3RAHajO7GdIPLBvFKGwYNKUh8lAKxQ11uZRjUQ2CgsawkgILV/DyqYoXJupsa0Q+ss0NVOkDf3tRbLc0fAmnpOjG78AgUIXNodOGzIEskYQKBgQDrFE8OV6k1rriW5O84kIxlU1J/jHl67C1R/fjrdZzJ7YzjqKwQo0GQhUiWyAJtoqGmMhaLgVi3VrbGUCPGJiaN/NuTPRWxGi/VXaJMPs+K2yErSyMEWmo6lcGjD2kOW7ZMUDoWMp3loNlliZPjMZ8Y3mZ3WeIv/vuxhpt+F0GgrwKBgQCtGfe7qLFxqZfpICebM4MV9tjWwr2q9boWFWuNg0DxTw0WyWQHQfNa+0afOIj6qMIxBJlvzV0Bet71XW5XKTmWbuQhYcbeoUcBBFB0DdM2Glret1A46KJOzxMz3HNAdkVhK028hr784DlE+Tg1SEWAYWidSlC9u80L49XPZgd7swKBgD7sMrT+Fda+q74IDVgwqMO+Z8ioSyPx77eQqX4s/wi1ww506YmSiUwrwOBLLQs3itk3cv1oY9y/IzE15j11nMBIvGVO5m1/Oup7o6OQ9HCQcvJprDfQE7sWtrv0tgQX3FXU65dheQ4r3cTl7GXVtGYtsXOk5Xw/XhOImjpH81MVAoGAQgIX/OB8Icq5GfXgBIflIdgKogKKzwl7F3a9l64IcrxhUmIjmbzlbrlJGeg2G9eEjaqiVAbsw2a2ZLxnGienRR0uMyiU7Ep1yAZ8I3UuKIBuTGV82uajFghS20DiVh+Dn2Ui9JQxej6KuCmM7IyNrEH44Zn4JhHaRAFyg+71RY8CgYEAmZVTkXFJXoIw8TAcV4968Uf/hfTwqWXc6uHAc/TAP4JNyi2+7TJ4xRziNenETcT9JByV8iw3iBjGwgeXr8BOzvPzs91foyG9elCK+WHBfT5XC9EssTwFXNTGdtcxG3fusHomwZlJnsuaHcBe0bX5HB0MXxJ3n4PjhvVQ+8qwuv0="
        val rsaEncrypted = CipherUtil.rsaEncryptByPublicKey(input, publicKeyStr)
        val rsaDecrypted = CipherUtil.rsaDecryptByPrivateKey(rsaEncrypted, privateKeyStr)
        assertEquals(input, rsaDecrypted)
    }

    fun testDes() {
        val desKey = UUID.randomUUID().toString()
        val input = "tada"
        val desEncryptedInput = CipherUtil.desEncryptByKey(input, desKey)
        val decrypted = CipherUtil.desDecryptByKey(desEncryptedInput, desKey)
        assertEquals(input, decrypted)
    }
}
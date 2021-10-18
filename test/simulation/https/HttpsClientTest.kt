package simulation.https

import org.junit.Test
import kotlin.test.assertEquals

class HttpsClientTest {
    @Test
    fun httpsSendTest() {
        val client = HttpsClient()
        val server = HttpsServerImpl()
        val request = "tada"
        val expectedResponse = "adat"
        val actualResponse = client.send(request, server)
        assertEquals(expectedResponse, actualResponse)
    }
}
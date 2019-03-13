package fcm

import org.json.JSONObject
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import java.io.InputStreamReader
import java.io.BufferedReader

val AUTH_KEY_FCM = "xxxxx"
val API_URL_FCM = "https://fcm.googleapis.com/fcm/send"
val DEVICE_TOKEN = "zzzzz"

fun main(args : Array<String>) {
    sendPushNotification(DEVICE_TOKEN)
}

fun sendPushNotification(deviceToken: String) {
    val url = URL(API_URL_FCM)
    val conn = url.openConnection() as HttpURLConnection

    conn.useCaches = false
    conn.doInput = true
    conn.doOutput = true

    conn.requestMethod = "POST"
    conn.setRequestProperty("Authorization", "key=$AUTH_KEY_FCM")
    conn.setRequestProperty("Content-Type", "application/json")

    val json = JSONObject()

    json.put("to", deviceToken.trim())
    val dataObject = JSONObject()
    dataObject.put("title", "title")
    dataObject.put("content", "message")
    json.put("data", dataObject)

    val notiObj = JSONObject()
    notiObj.put("title", "title")
    notiObj.put("body", "content")
    json.put("notification", notiObj)

    println(json.toString())
    try {
        val wr = OutputStreamWriter(
            conn.outputStream
        )
        wr.write(json.toString())
        wr.flush()

        val br = BufferedReader(
            InputStreamReader(
                conn.inputStream
            )
        )

        var output: String?
        println("Output from Server .... \n")
        while(br.readLine().also { output = it } != null) {
            println(output)
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }

    println("FCM Notification is sent successfully")
}
import com.jcraft.jsch.JSch
import com.jcraft.jsch.Session
import java.util.Properties
import java.util.concurrent.TimeUnit


data class SFTPAttemptResult(val username: String, val password: String?, val responseTimes: List<Long>)
{
    fun averageResponseTime() : Double {
        return responseTimes.average()
    }
}

fun main(args: Array<String>) {
    val hostname = "your-sftp-server-hostname-here"
    val port = 22
    val credentials = mapOf(
        "username1" to "password1",
        "username2" to "password2",
        "username3" to "password3",
        "username4" to "password4",
    )

    var results = mutableListOf<SFTPAttemptResult>()

    for (credentialPair in credentials) {
        val usernameResponseTimes = List(10) {
            tryLogin(hostname, port, credentialPair.key)
        }
        results.add(SFTPAttemptResult(credentialPair.key,null,usernameResponseTimes))

        val passwordResponseTimes = List(5) {
            tryLogin(hostname, port, credentialPair.key,credentialPair.value)
        }
        results.add(SFTPAttemptResult(credentialPair.key,credentialPair.value,passwordResponseTimes))
    }

    println("Username\tPassword\tAvg. Response Time\tResponse Time Test Case Results")
    for(result in results) {
        println("${result.username}\t${if(result.password!=null) {result.password} else {"null"}}\t${result.averageResponseTime()}\t${result.responseTimes.joinToString("\t")}")
    }
}

fun tryLogin(hostname : String, port : Int, username : String, password : String? = null) : Long {
    val jsch = JSch()
    val config = Properties()
    config.setProperty("StrictHostKeyChecking","no")
    config.setProperty("PreferredAuthentications", "password")

    val session: Session = jsch.getSession(username, hostname, port)
    session.setConfig(config)
    if(password!=null)
        session.setPassword(password)

    val beforeAttempt = System.nanoTime()
    try {
        println("Attempting to connect: $username@$hostname:$port ${if(password!=null) {password} else {"null"}}")
        session.connect()
        println("Login Success")
    }
    catch (e: Exception)
    {
        e.printStackTrace()
    }
    val afterAttempt = System.nanoTime()
    val attemptResponseTime = TimeUnit.NANOSECONDS.toMillis(afterAttempt - beforeAttempt)
    if(session.isConnected)
        session.disconnect()
    println("Response Time: $attemptResponseTime")
    return attemptResponseTime
}
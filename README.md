# SFTP Credential Harvesting Timing Attack Kotlin Client

_By [Nick Coblentz](https://www.linkedin.com/in/ncoblentz/)_

__This project is made possible by [Virtue Security](https://www.virtuesecurity.com), the Application Penetration Testing consulting company I work for.__

## About

The __SFTP Credential Harvesting Timing Attack Kotlin Client__ attempts to connect to an SFTP server with a set of credentials (both valid and invalid) that you provide. Afterward, it prints a tab delimited list of results to the console which you can import into your favorite spreadsheet program to analyze.

## How to Use It

### Configure the SFTP Server Hostname and Credential Test Cases

Replace the server's host name or IP address here:
```kotlin
val hostname = "your-sftp-server-hostname-here"
```

Add the credentials (a set of valid usernames and invalid usernames, usually all with incorrect passwords) here:
```kotlin
    val credentials = mapOf(
        "username1" to "password1",
        "username2" to "password2",
        "username3" to "password3",
        "username4" to "password4",
    )
```
### Run the Test Cases

On Windows, use: `gradlew.bat run`

On Linux, use: `gradlew run`

### Process the Results
The utility will print the results in a tab delimited format when it is finished. Copy and paste those results to your favorite spreadsheet application. Manually review and/or chart the results to see if there are timing attacks that reveal whether a particular username is valid.

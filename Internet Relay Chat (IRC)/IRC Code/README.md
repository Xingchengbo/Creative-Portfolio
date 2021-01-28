# IRC Project
In this project, we will produce an IRC client and server according to 
the below standards. This project will be turned in over several deliverables.

We will produce an IRC client and server using several technologies that 
we will be learning about through the semester. These client and server programs
will communicate with each other to broadcast messages to several clients
according to channels the client has subscribed to. The requirements and protocol
are inﬂuenced by RFC 1459 - Internet Relay Chat Protocol.


# Build
The command `make re` gets `client`, `server` and `ServerLog.txt`.

# Run
`./server` Launch the server.

`./client` Launch the client.

`control+c` Quit the server.

`cat ./ServerLog.txt` Check the logfile.

# Done Commands
* `/server ip:port` Connect to the server at the selected IP and port.  
    * `ip=127.0.0.1`
* `/nick nickname` Set your nickname first.  
    * `NICK <nickname>`
* `/help` Show the all commands you can use. 
* `/join #channel` Create and join the specified channel. 
    * ` JOIN <channel>{,<channel>} [<key>{,<key>}] `
* `/topic topicname` Set channel topics.
    * `TOPIC <channel> [<topic>]`
* `/list #channel` List imformations in the specified channel.
* `/users` Show all usernames in the channel.  
    * `USER <username> <hostname> <servername> <realname>`
* `/msg nickname message` Send a private message to the specified user. 
* `/part #channel` Leave the specified channel.  
    * `PART <channel>{,<channel>} `
* `/quit` The server close the connection to a clien.  
    * `QUIT [<Quit message>]`

# Configuration File
`port = 8899` Port values to listen on.

`loglevel` Logging levels.

#define INFO 0

#define DEBUG 1

#define ERROR 2

`maxclient = 2` Maximum number of client connections to support.

`maxbuffersize = 2048` Message buffer size value.

`logfile = ./ServerLog.txt` Log file locations.

`Note: If config.txt is missing in Server, then it will use the internal default configuration logfile.`

# Command Line Arguments

Usage: `./server [-p port] [-l loglevel] [-m maxclient] [-b maxbuffersize] [-f logfilepath]`

For example: ./server -p 4455 -l 2 -m 22 -b 2048 -f .1.txt

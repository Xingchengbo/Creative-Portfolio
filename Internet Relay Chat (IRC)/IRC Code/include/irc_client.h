// Chengbo Xing
// CS 3337
// irc_client.h
// 5/3/2020

#ifndef IRC_CLIENT_H_
#define IRC_CLIENT_H_

#include "irc_common.h"
#include <stdlib.h>
#include <unistd.h>
#include <stdarg.h>
#include <stdio.h>
#include <string.h>
#include <sys/time.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <errno.h>
#include <stdbool.h>
#include <signal.h>

#define CMD_ARGS 4
#define DEFAULT_PORT 6667
#define PROMPT_PREFIX "Listening"
#define PROMT_MIDDLE "[%s]"
#define PROMPT_SUFFIX ": "
#define MSG_NEEDLE "PRIVMSG"

#define USAGE_FRMT "usage : %s\n"
#define USAGE_SERVER "/server host[:port]"
#define USAGE_NICK "/nick nickname"
#define USAGE_JOIN "/join #channel"
#define USAGE_NAMES "/names #channel"
#define USAGE_MSG "/msg user message"
#define USAGE_TOPIC "/topic name"

#define FRMT_NICK "NICK %s\n"
#define FRMT_LIST "LIST %s\n"
#define FRMT_JOIN "JOIN %s\n"
#define FRMT_PART "PART %s\n"
#define FRMT_USERS "NAMES\n"
#define FRMT_NAMES "NAMES %s\n"
#define FRMT_MSG "PRIVMSG %s :%s"
#define FRMT_TOPIC "TOPIC %s :%s"
#define FRMT_CLOSED_CON "%s[!] Connection lost\n"

#define ERROR_NO_SRV "You have to be connected to a server first!"
#define ERROR_NO_CHAN "You have to be connected to a channel first!"
#define ERROR_UNKNOWN_COMMAND "Unknown command. Try /help"

#define WELC_MSG "Welcome! Please connect to the service first! Try /server 127.0.0.1:port"
#define BYE_MSG "Thank you!"

enum	e_logtype
  {
    INFO,
    ERROR,
    MSG
  };

typedef struct		s_server
{
  FILE			*stream;
  int			sd;
  struct sockaddr_in	addr;
}			t_server;

typedef struct	s_datacom
{
  t_rb		*in;
  t_rb		*out;
  char		**cmd;
  char		*raw_cmd;
  char		*chan;
  t_server	srv;
}		t_datacom;

typedef int(*t_comm_handler)(t_datacom *data);

int		logmsg(enum e_logtype mode, char *format, ...);
int		print_error(const char *func_name);
int		send_data(t_datacom *data, const char *format, ...);
int		read_data(t_datacom *data, fd_set *readf);
int		write_data(t_datacom *data, fd_set *writef);
int		free_all(t_datacom *data, int ret);
int		parse_cmd(t_datacom *data);
int		parse_input(t_datacom *data);
int		parse_reply(t_datacom *data, const char *str);
int		pprompt(t_datacom *data);
int		cmdlen(void);
int		init_wrapper(t_datacom *data);
int		client_wrapper(void);
int		cmd_help(t_datacom *data);
int		cmd_quit(t_datacom *data);
int		cmd_server(t_datacom *data);
int		cmd_nick(t_datacom *data);
int		cmd_msg(t_datacom *data);
int		cmd_list(t_datacom *data);
int		cmd_users(t_datacom *data);
int		cmd_names(t_datacom *data);
int		cmd_join(t_datacom *data);
int		cmd_part(t_datacom *data);
int		cmd_topic(t_datacom *data);

#endif /* !IRC_CLIENT_H_ */
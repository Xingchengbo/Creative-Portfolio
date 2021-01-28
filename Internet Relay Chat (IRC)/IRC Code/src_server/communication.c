// Chengbo Xing
// CS 3337
// communication.c
// 5/3/2020

#include "irc_server.h"
// Reply function
bool    reply(t_handle *h, int code, const char *fmt, ...)
{
  char		*text;
  char		*reply;
  va_list	ap;
  size_t	len;

  va_start(ap, fmt);
  len = vsnprintf(NULL, 0, fmt, ap);
  va_start(ap, fmt);
  if ((text = malloc(sizeof(char) * len + 1)) == NULL)
    return (false);
  vsprintf(text, fmt, ap);
  if ((reply = malloc(strlen(text) +
                      strlen((h->sdr->nick ? h->sdr->nick : "*")) +
                      13 + strlen(h->server_ip))) == NULL)
    return (false);
  sprintf(reply, ":%s %03d %s %s \r\n", h->server_ip, code,
          (h->sdr->nick ? h->sdr->nick : "*"), text);
  write(h->sdr->fd, reply, strlen(reply));
  free(text);
  free(reply);
  va_end(ap);
  return (code >= 400 ? false : true);
}
// ID reply
bool	idreply(int fd, t_handle *h, const char *fmt, ...)
{
  char		*text;
  char		*reply;
  va_list	ap;
  size_t	len;

  va_start(ap, fmt);
  len = vsnprintf(NULL, 0, fmt, ap);
  va_start(ap, fmt);
  if ((text = malloc(sizeof(char) * len + 1)) == NULL)
    return (false);
  vsprintf(text, fmt, ap);
  if ((reply = malloc(strlen(text) + strlen(h->sdr->nick) +
                      strlen(h->sdr->username) + 7 +
                      strlen(h->sdr->hostname))) == NULL)
    return (false);
  sprintf(reply, ":%s!%s@%s %s\r\n", h->sdr->nick, h->sdr->username,
          h->sdr->hostname, text);
  write((fd == 0 ? h->sdr->fd : fd), reply, strlen(reply));
  free(text);
  free(reply);
  va_end(ap);
  return (true);
}
// Broad cast
bool	broadcast(t_handle *h, t_chan *channel, const char *fmt, ...)
{
  t_user	*user;
  char		*msg;
  va_list	ap;
  size_t	len;

  va_start(ap, fmt);
  len = vsnprintf(NULL, 0, fmt, ap);
  va_start(ap, fmt);
  if ((msg = malloc(sizeof(char) * len + 1)) == NULL)
    return (false);
  vsprintf(msg, fmt, ap);
  va_end(ap);
  user = channel->users;
  while (user)
    {
      idreply(user->fd, h, "%s", msg);
      user = user->next;
    }
  free(msg);
  return (true);
}
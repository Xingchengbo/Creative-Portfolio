// Chengbo Xing
// CS 3337
// messages.c
// 5/3/2020

#include "rfc_numlist.h"
#include "irc_server.h"
// Message channel
static bool	message_channel(t_handle *h, t_chan *channel)
{
  t_user	*tmp_user;

  tmp_user = channel->users;
  while (tmp_user)
    {
      if (tmp_user->fd != h->sdr->fd)
        idreply(tmp_user->fd, h, "PRIVMSG %s :%s", h->arg[0], h->arg[1]);
      tmp_user = tmp_user->next;
    }
  return (true);
}
// Private message command
bool	cmd_privmsg(t_handle *h)
{
  t_user	*user;
  t_chan	*channel;

  if (!h->arg[0])
    return (reply(h, ERR_NORECIPIENT, ":No recipient given (PRIVMSG)"));
  if (!h->arg[1])
    return (reply(h, ERR_NOTEXTTOSEND, ":No text to send"));
  if (strchr(CHANMASK, h->arg[0][0]))
    {
      if ((channel = find_chan_by_name(h->chans, h->arg[0])) == NULL)
        return (reply(h, ERR_CANNOTSENDTOCHAN,
                      "%s :No such channel", h->arg[0]));
      if ((find_user_by_fd(&channel->users, h->sdr->fd) == NULL))
        return (reply(h, ERR_CANNOTSENDTOCHAN,
                      "%s :You're not on that channel", h->arg[0]));
      return (message_channel(h, channel));
    }
  else
    {
      if (((user = find_user_by_nick(h->users, h->arg[0])) == NULL) ||
          user->status != REGISTERED)
        return (reply(h, ERR_NOSUCHNICK, "%s :No such nickname", h->arg[0]));
      return (idreply(user->fd, h, "PRIVMSG %s :%s", h->arg[0], h->arg[1]));
    }
}
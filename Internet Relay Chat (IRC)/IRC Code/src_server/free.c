// Chengbo Xing
// CS 3337
// free.c
// 5/3/2020

#include "irc_server.h"
// Free all users
void	free_all_users(t_user **users)
{
  t_user	*tmp;
  t_user	*prev;

  tmp = *users;
  while (tmp)
    {
      log_msg(INFO, "Freing user \"%s\"", tmp->nick);
      prev = tmp;
      tmp = tmp->next;
      free_user(prev);
    }
}
// Free user
void	free_user(t_user *user)
{
  shutdown(user->fd, SHUT_RDWR);
  free(user->nick);
  free(user->hostname);
  free(user->username);
  free(user->realname);
  if (user->rb && user->rb->buf)
    free(user->rb->buf);
  free(user->rb);
  free(user);
}
// Free all channel
void	free_all_chans(t_chan **chans)
{
  t_chan	*tmp;
  t_chan	*prev;

  tmp = *chans;
  while (tmp)
    {
      prev = tmp;
      tmp = tmp->next;
      free_chan(prev);
    }
}
// Free channel
void	free_chan(t_chan *chan)
{
  t_user	*tmp;
  t_user	*prev;

  tmp = chan->users;
  while (tmp)
    {
      prev = tmp;
      tmp = tmp->next;
      free(prev);
    }
  if (chan->name)
    free(chan->name);
  if (chan->topic)
    free(chan->topic);
  free(chan);
}
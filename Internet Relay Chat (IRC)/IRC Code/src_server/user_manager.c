// Chengbo Xing
// CS 3337
// user_manager.c
// 5/3/2020

#include "irc_server.h"
// Delete users
int	del_user(t_user **users, t_user *old)
{
  t_user	*tmp;

  tmp = *users;
  if (!tmp)
    return (-1);
  if (tmp == old)
    {
      *users = tmp->next;
      free_user(tmp);
	Sconfig.nowclient--;
      return (0);
    }
  while (tmp)
    {
      if (tmp->next == old)
        {
          tmp->next = old->next;
          free_user(old);
	   Sconfig.nowclient--;  
          return (0);
        }
      tmp = tmp->next;
    }
  return (-1);
}

t_user		*create_user(int fd, char *nick, char *hostname, bool member)
{
  t_user	*new;

  if (!(new = malloc(sizeof(t_user))))
    return (NULL);
  new->fd = fd;
  new->nick = nick;
  new->hostname = hostname;
  new->username = NULL;
  new->realname = NULL;
  new->status = NOT_REGISTERED;
  if (member)
    new->rb = NULL;
  else if ((new->rb = rb_init()) == NULL)
    return (NULL);
  new->next = NULL;
  return (new);
}
// Add users
bool	add_user(t_user **users, t_user *new)
{
  t_user	*tmp;

  if (!new)
    return (false);
  if (!(*users))
    *users = new;
  else
    {
      tmp = *users;
      while (tmp->next)
        tmp = tmp->next;
      tmp->next = new;
    }
  return (true);
}

t_user		*find_user_by_nick(t_user **users, char *nick)
{
  t_user	*tmp;

  tmp = *users;
  while (tmp)
    {
      if (tmp->nick && strcmp(tmp->nick, nick) == 0)
        return (tmp);
      tmp = tmp->next;
    }
  return (NULL);
}

t_user		*find_user_by_fd(t_user **users, int fd)
{
  t_user	*tmp;

  tmp = *users;
  while (tmp)
    {
      if (tmp->fd == fd)
        return (tmp);
      tmp = tmp->next;
    }
  return (NULL);
}
// Chengbo Xing
// CS 3337
// chan_manager.c
// 5/3/2020

#include "irc_server.h"
// Channel list
size_t		count_chans(t_chan **chans)
{
  size_t	i;
  t_chan	*tmp;

  i = 0;
  tmp = *chans;
  while (tmp)
    {
      printf("Listed channels : %s\n", tmp->name);
      tmp = tmp->next;
      i++;
    }
  return (i);
}

t_chan		*del_chan(t_chan **chans, t_chan *old)
{
  t_chan	*tmp;

  tmp = *chans;
  if (!tmp)
    return (NULL);
  if (tmp == old)
    {
      *chans = tmp->next;
      free_chan(tmp);
      return (*chans);
    }
  while (tmp)
    {
      if (tmp->next == old)
        {
          tmp->next = old->next;
          free_chan(old);
          return (tmp->next);
        }
      tmp = tmp->next;
    }
  return (NULL);
}
// Remove user
int		remove_user(t_user **users, t_user *toremove)
{
  t_user	*tmp;

  tmp = *users;
  if (!tmp)
    return (-1);
  if (tmp == toremove)
    {
      *users = tmp->next;
      free(tmp);
      return (0);
    }
  while (tmp)
    {
      if (tmp->next == toremove)
        {
          tmp->next = toremove->next;
          free(toremove);
          return (0);
        }
      tmp = tmp->next;
    }
  return (-1);
}
// Create new channel
t_chan		*new_chan(t_chan **chans, char *name)
{
  t_chan	*new;
  t_chan	*tmp;

  if (!(new = malloc(sizeof(t_chan))))
    return (NULL);
  new->name = strdup(name);
  new->topic = NULL;
  new->users = NULL;
  new->next = NULL;
  if (!(*chans))
    *chans = new;
  else
    {
      tmp = *chans;
      while (tmp->next)
        tmp = tmp->next;
      tmp->next = new;
    }
  return (new);
}

t_chan		*find_chan_by_name(t_chan **chans, char *name)
{
  t_chan	*tmp;

  tmp = *chans;
  while (tmp)
    {
      if (tmp->name && strcmp(tmp->name, name) == 0)
        return (tmp);
      tmp = tmp->next;
    }
  return (NULL);
}
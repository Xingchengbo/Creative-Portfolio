// Chengbo Xing
// CS 3337
// utils.c
// 5/3/2020

#include "irc_server.h"

// Log message
void	log_msg(int mode, const char *fmt, ...)
{
  va_list	ap;

  va_start(ap, fmt);
  if (mode == INFO)
    fprintf(stderr, "\033[34m[+] ");
  else if (mode == ERROR)
    fprintf(stderr, "\033[31m[!] ");
  else
    fprintf(stderr, "\033[33m[-] ");
  vfprintf(stderr, fmt, ap);
  fprintf(stderr, "\n\033[0m");
va_end(ap);
  
  if ((mode >= Sconfig.loglevel) && Sconfig.logfp)
  {
  va_start(ap, fmt);
	  vfprintf(Sconfig.logfp, fmt, ap);
	  va_end(ap);
	  fprintf(Sconfig.logfp,"\n");
	  fflush(Sconfig.logfp);
  }
  
}
// Init handler
void	init_handler(t_handle *h, t_user **users, t_chan **chans)
{
  *users = NULL;
  *chans = NULL;
  h->users = users;
  h->chans = chans;
  h->server_ip = NULL;
}

size_t		count_users(t_user **users)
{
  size_t	i;
  t_user	*tmp;

  i = 0;
  tmp = *users;
  while (tmp)
    {
      printf("listed user : %s\n", tmp->nick);
      tmp = tmp->next;
      i++;
    }
  return (i);
}
// Check validity of nickname
bool	is_valid_nick(char *nick)
{
  int	i;
  if (isdigit(nick[0]) > 0)
    return (false);
  i = 0;
  while (nick[i])
    {
      if (strchr(INVALID_CHAR, nick[i++]))
        return (false);
    }
  return (true);
}
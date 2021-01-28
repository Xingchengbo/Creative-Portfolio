// Chengbo Xing
// CS 3337
// main.c
// 5/3/2020

#include "irc_client.h"

volatile bool	g_client_running;
// Signal handler
void	sig_handler(int signal)
{
  (void)signal;
  write(1, "\n", 1);
  logmsg(MSG, "%s\n", BYE_MSG);
  g_client_running = false;
}
// Log message
int		logmsg(enum e_logtype mode, char *format, ...)
{
  va_list	args;

  va_start(args, format);
  if (mode == INFO)
    fprintf(stderr, "%s ", ANSI_INFO);
  else if (mode == ERROR)
    fprintf(stderr, "%s ", ANSI_ERROR);
  else
    fprintf(stderr, "%s ", ANSI_MSG);
  vfprintf(stderr, format, args);
  va_end(args);
  fprintf(stderr, "%s", ANSI_DEFAULT);
  return (0);
}
// Print erroe message
int	print_error(const char *func_name)
{
  logmsg(ERROR, "Error in %s(): %s\n", func_name, strerror(errno));
  return (EXIT_FAILURE);
}
// Main function
int	main(void)
{
  signal(SIGINT, sig_handler);
  return (client_wrapper());
}
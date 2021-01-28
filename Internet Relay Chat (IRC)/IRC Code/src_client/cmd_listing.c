// Chengbo Xing
// CS 3337
// cmd_listing.c
// 5/3/2020

#include "irc_client.h"
// List command
inline int	cmd_list(t_datacom *data)
{
  return (send_data(data, FRMT_LIST, (data->cmd)[1]));
}
// Users command
inline int	cmd_users(t_datacom *data)
{
  return (send_data(data, FRMT_USERS));
}
// Names command
int	cmd_names(t_datacom *data)
{
  if (!data->cmd[1] || !data->cmd[1][0])
    return (logmsg(MSG, USAGE_FRMT, USAGE_NAMES));
  return (send_data(data, FRMT_NAMES, data->cmd[1]));
}
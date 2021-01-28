// Chengbo Xing
// CS 3337
// cmd_chans.c
// 5/3/2020

#include "irc_client.h"
// Join channel command
int	cmd_join(t_datacom *data)
{
  if (data->chan && cmd_part(data) == -1)
    return (0);
  if (!data->cmd[1] || data->cmd[1][0] != '#')
    return (logmsg(MSG, USAGE_FRMT, USAGE_JOIN));
  if (!(data->chan = malloc(sizeof(char) * (strlen(data->cmd[1]) + 1))))
    return (print_error("mem_alloc"));
  if (!(data->chan = strcpy(data->chan, data->cmd[1])))
    return (print_error("strcpy"));
  if (send_data(data, FRMT_JOIN, data->chan) == -1)
    return (0);
  return (0);
}
// Part channnel command
int	cmd_part(t_datacom *data)
{
  if (!data->chan)
    return (logmsg(MSG, "%s\n", ERROR_NO_CHAN));
  if (send_data(data, FRMT_PART, data->chan) == -1)
    return (0);
  free(data->chan);
  data->chan = NULL;
  return (0);
}
// Set topic command
int	cmd_topic(t_datacom *data)
{
  if (!data->cmd[1])
    return (logmsg(MSG, USAGE_FRMT, USAGE_TOPIC));
  if (!data->chan)
    return (logmsg(MSG, "%s\n", ERROR_NO_CHAN));
  return (send_data(data, FRMT_TOPIC, data->chan, &(data->raw_cmd[7])));
}
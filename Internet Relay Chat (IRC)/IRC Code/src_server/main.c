// Chengbo Xing
// CS 3337
// main.c
// 5/3/2020

#include <time.h>
#include "irc_server.h"
// Log.txt
static char *defaultlogfile = "log.txt";
config_info Sconfig = {
	9527,
	0,
	32,
	4096,
	NULL,
	NULL,
	0
};

static volatile bool	g_run_server;
static int		g_socket_fd;
// Log time
static void log_time(char *msg)
{
	time_t now;
	struct tm* ptm;
	time(&now);
	ptm = localtime(&now);
	log_msg(ERROR,"%s%s",msg,asctime(ptm));
}
// Signal handler
void	signal_handler(int signal)
{
  (void)signal;
  log_msg(INFO, "Ctrl-C catched, shutting down server...");
  shutdown(g_socket_fd, SHUT_RDWR);
  g_run_server = false;
}
// Update function
static void	update_fdset(fd_set *fds, int *fd_max, t_user *users)
{
  t_user	*tmp;

  *fd_max = g_socket_fd;
  FD_ZERO(fds);
  FD_SET(g_socket_fd, fds);
  tmp = users;
  while (tmp)
    {
      FD_SET(tmp->fd, fds);
      *fd_max = (tmp->fd > *fd_max ? tmp->fd : *fd_max);
      tmp = tmp->next;
    }
}
// Accept new user
static void	accept_new_user(t_handle *h, t_user **users)
{
  struct sockaddr_in	r_addr;
  socklen_t		addrlen;
  int			nsock;

  nsock = accept_con(g_socket_fd, &r_addr);
  log_msg(INFO, "Incoming connection from %s", inet_ntoa(r_addr.sin_addr));
  if(Sconfig.nowclient==Sconfig.maxclient)
  {
    log_msg(ERROR,"client limit reached! can't add new user\n");
     shutdown(nsock, SHUT_RDWR);
     return;
  }
  add_user(users,
           create_user(nsock, NULL, strdup(inet_ntoa(r_addr.sin_addr)), false));
  if (!h->server_ip)
    {
      addrlen = sizeof(r_addr);
      getsockname(nsock, (struct sockaddr *)&r_addr, &addrlen);
      h->server_ip = strdup(inet_ntoa(r_addr.sin_addr));
      log_msg(INFO, "Server IP is %s", h->server_ip);
    }
  Sconfig.nowclient++;
}
// Start service
static int		start_service(int port)
{
  fd_set		fds;
  int			fd_max;
  t_user		*users;
  t_chan		*chans;
  t_handle		h;
  struct sockaddr_in	l_addr;

  if ((g_socket_fd = create_s_socket(&l_addr, port)) == -1)
    return (-1);
  listen(g_socket_fd, MAX_QUEUE);
  init_handler(&h, &users, &chans);
  while (g_run_server)
    {
      update_fdset(&fds, &fd_max, users);
      if (select(fd_max + 1, &fds, NULL, NULL, NULL) < 0)
        break;
      if (FD_ISSET(g_socket_fd, &fds))
        accept_new_user(&h, &users);
      handle_clients(&h, &fds);
    }
  if (h.server_ip)
    free(h.server_ip);
  free_all_chans(&chans);
  free_all_users(&users);

  log_time("Server quit at ");
  if (Sconfig.logfp)
	  fclose(Sconfig.logfp);
  if (Sconfig.logfile != defaultlogfile)
	  free(Sconfig.logfile);

  return (0);
}
// Get token
static char *gettoken(char *line, int *idx)
{
	char *res = NULL;
	int i = *idx;
	while (isspace(line[i])) i++;
	res = line + i;
	if (line[i] == '#') //comment line
		return res;

	while (line[i] && !isspace(line[i])) i++;

	line[i] = 0;
	*idx = i + 1;
	return res;
}
// Paese line
static void parseline(char *line)
{
	int num,idx = 0;
	int len = strlen(line);
	char *token = gettoken(line,&idx);
	if (token[0] == '#') //comment line
		return;

	if (strcmp("port", token) == 0)
	{
		if (idx < len)
			token = gettoken(line, &idx);  //get "="
		else
			return;

		if (strcmp(token, "="))   //bad token, not "="
			return;

		if (idx < len)
			token = gettoken(line, &idx);
		else
			return;

		num = atoi(token);
		if (num > 0)
			Sconfig.port = num;
	}
	else if (strcmp("loglevel", token) == 0)
	{
		if (idx < len)
			token = gettoken(line, &idx);  //get "="
		else
			return;

		if (strcmp(token, "="))   //bad token, not "="
			return;

		if (idx < len)
			token = gettoken(line, &idx);
		else
			return;

		num = atoi(token);
		if (num >=0)
			Sconfig.loglevel = num;
	}
	else if (strcmp("maxclient", token) == 0)
	{
		if (idx < len)
			token = gettoken(line, &idx);  //get "="
		else
			return;

		if (strcmp(token, "="))   //bad token, not "="
			return;

		if (idx < len)
			token = gettoken(line, &idx);
		else
			return;

		num = atoi(token);
		if (num >= 0)
			Sconfig.maxclient = num;
	}
	else if (strcmp("maxbuffersize", token) == 0)
	{
		if (idx < len)
			token = gettoken(line, &idx);  //get "="
		else
			return;

		if (strcmp(token, "="))   //bad token, not "="
			return;

		if (idx < len)
			token = gettoken(line, &idx);
		else
			return;

		num = atoi(token);
		if (num >0)
			Sconfig.maxbuffersize=num;
	    
		BUF_SIZE = Sconfig.maxbuffersize;
	}
	else if (strcmp("logfile", token) == 0)
	{
		if (idx < len)
			token = gettoken(line, &idx);  //get "="
		else
			return;

		if (strcmp(token, "="))   //bad token, not "="
			return;

		if (idx < len)
			token = gettoken(line, &idx);
		else
			return;

		Sconfig.logfile = strdup(token);
	}

}

// Load config from configfile
static int load_config()
{
	char line[4096];
	FILE *fp = fopen("config.txt","r");
	if (fp == NULL)
	{
		fprintf(stderr,"can't open config.txt, use default config.\n");
		return -1;
	}
	while (fgets(line, sizeof(line), fp))
	{
		parseline(line);
	}
	fclose(fp);
	return 0;
}

static void check_logfile()
{
      if(Sconfig.logfile)
      	{
		Sconfig.logfp=fopen(Sconfig.logfile,"a+");
		if (Sconfig.logfp == NULL)
		{
			fprintf(stderr,"can't open logfile %s\n, drop to default logfile: ./log.txt\n",Sconfig.logfile);
			free(Sconfig.logfile);
		}
      	}
	  
	if (Sconfig.logfp == NULL)
	{
  
		Sconfig.logfile = defaultlogfile;
		Sconfig.logfp = fopen(Sconfig.logfile, "a+");
		if (Sconfig.logfp == NULL)
		{
			Sconfig.logfile = NULL;
			fprintf(stderr, "can't open default logfile ./log.txt\n");
		}
	}
}

//display the configure
static void show_config()
{
	fprintf(stderr,"=======Server configure=============\n");
	fprintf(stderr,"port = %d\n",Sconfig.port);
	fprintf(stderr,"loglevel = %d\n", Sconfig.loglevel);
	fprintf(stderr, "maxclient = %d\n", Sconfig.maxclient);
	fprintf(stderr, "maxbuffersize = %d\n", Sconfig.maxbuffersize);
	fprintf(stderr, "logfile = %s\n", Sconfig.logfile);
	fprintf(stderr, "===================================\n");
}

// Help info
static void usage_help(char *name)
{
   
   fprintf(stderr,"\n===============================================================================\n");
   fprintf(stderr,"\nusage: %s [-p port] [-l loglevel] [-m maxclient] [-b maxbuffersize] [-f logfilepath]\n",name);
   fprintf(stderr,"\nfor example:  %s  -p  4455 -l 2 -m 22 -b 2048 -f  ./1.txt\n",name);
   fprintf(stderr,"\n===============================================================================\n\n");
}


static int convert2int(char *s)
{
   int x=0;
   while(*s)
   {
       if((*s<'0')  || (*s>'9')) 
       {
       	return -1;
       }
	 else
	 	x=x*10+*s-'0';
	 s++;
   }
   return x;
  
}
// Override the configure
static void override_config(int argc, char **argv)
{
    int i;
    int port=-1;
    int loglevel=-1;
    int maxclient=-1;
    int maxbuffer=-1;
    char *logfile=NULL;
  
     for(i=1;i<argc;i++)
     {
         if((strcmp(argv[i],"-p")==0) && (i+1<argc)) 
         {
             port=convert2int(argv[i+1]);
	       if(port>0)
		   	i++;
         }
	   else if((strcmp(argv[i],"-l")==0) && (i+1<argc)) 
         {
             loglevel=convert2int(argv[i+1]);
	       if(loglevel>-1)
		   	i++;
         }
	 else if((strcmp(argv[i],"-m")==0) && (i+1<argc)) 
         {
             maxclient=convert2int(argv[i+1]);
	       if(maxclient>-1)
		   	i++;
         }
        else if((strcmp(argv[i],"-b")==0) && (i+1<argc)) 
         {
             maxbuffer=convert2int(argv[i+1]);
	       if(maxbuffer>0)
		   	i++;
         }
	  else if((strcmp(argv[i],"-f")==0) && (i+1<argc)) 
         {
            logfile=argv[i+1];
	      i++;
         }
	  else
	  {
	  	fprintf(stderr,"unkown option: %s\n",argv[i]);
	  }
	 
     }

     if(port!=-1)
	 	Sconfig.port=port;

     if(loglevel!=-1)
	 	Sconfig.loglevel=loglevel;

     if(maxclient!=-1)
	 	Sconfig.maxclient=maxclient;

     if(maxbuffer!=-1)
	 	Sconfig.maxbuffersize=maxbuffer;

     if(logfile)
     {
     	   if(Sconfig.logfile)
		   	free(Sconfig.logfile);
		   
	   Sconfig.logfile=strdup(logfile);
     }
     	 
}

// Main function
int	main(int argc, char **argv)
{
  
  g_run_server = true;
  signal(SIGINT, signal_handler);
  usage_help(argv[0]);
  load_config();
  override_config(argc, argv);
  check_logfile();
  show_config();
  log_time("Server start at ");
  if (start_service(Sconfig.port) == -1)
	  log_msg(ERROR, "Error launching server: %s\n", strerror(errno));
  else
    return (0);
  
}

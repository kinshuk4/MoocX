# Docker

## Useful commands

### Build without cache

```
docker build --no-cache .
```

### Run Docker without sudo

```
sudo addgroup -a username docker
```

### Remove containers

Remove all containers:

```
docker ps -a -q | xargs --no-run-if-empty docker rm -f
```

Remove all containers that have exited:

```
docker ps -a -q --filter status=exited | xargs --no-run-if-empty docker rm
```

Cleanup unused volumes:

```
docker run -v /var/run/docker.sock:/var/run/docker.sock -v /var/lib/docker:/var/lib/docker --privileged dockerinpractice/docker-cleanup-volumes
```

Execute commands on a running container:

```
docker exec -i -t name /bin/bash
```

## Portainer (formerly known as DockerUI)

### Manage a local Docker engine

```
docker run -d -p 9000:9000 -v /var/run/docker.sock:/var/run/docker.sock portainer/portainer
```

### Manage a remote Docker engine

```
docker run -d -p 9000:9000 portainer/portainer -H tcp://<REMOTE_HOST>:<REMOTE_PORT>
```

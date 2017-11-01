# pdp-ws

This service has been deprecated in favor of a new pdp built in [auth service](http://github.com/dojot/auth).

## Index

* [Description](#description)
* [Installation](#installation)

## <a name="description"/> Description

Policy Decision Point Web Service with JSON Rest Interface

## <a name="installation"/> Installation

To run the web service, use the its docker image, available at [docker hub](https://hub.docker.com/r/dojot/pdp-ws/).

To build the container, from the repository's root:

```shell
# You may need sudo on your machine to do the following.
# Should you want to avoid having to run docker with sudo, check
# https://docs.docker.com/engine/installation/linux/linux-postinstall/
docker build -t <tag> -f Dockerfile .
```

The service will be available on http://<ip_address>:9763/pdp/identity/entitlement/decision

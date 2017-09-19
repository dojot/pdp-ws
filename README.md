# pdp-ws

## Index

* [Description](#description)
* [Installation](#installation)

## <a name="description"/> Description

Policy Decision Point Web Service with JSON Rest Interface

## <a name="installation"/> Installation

To run the web service, use the docker.

To build the container, from the repository's root:

# you may need sudo on your machine: https://docs.docker.com/engine/installation/linux/linux-postinstall/
docker build -t <tag> -f Dockerfile .

The service will be available on http://<ip_address>:9763/pdp/identity/entitlement/decision

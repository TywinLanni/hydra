#!/bin/bash

KCHOST=http://localhost:8080
REALM=hydra
CLIENT_ID=hydra-service
UNAME=hydra-test
PASSWORD=otus

# shellcheck disable=SC2006
#ACCESS_TOKEN=`curl \
#  -d "client_id=$CLIENT_ID" -d "client_secret=$CLIENT_SECRET" \
#  -d "username=$UNAME" -d "password=$PASSWORD" \
#  -d "grant_type=password" \
#  "$KCHOST/auth/realms/$REALM/protocol/openid-connect/token"  | jq -r '.access_token'`

#alias jq=C:/Users/Tywin/Downloads/jq-win64.exe

# shellcheck disable=SC2006
ACCESS_TOKEN=`curl \
  -d "client_id=$CLIENT_ID" \
  -d "username=$UNAME" \
  -d "password=$PASSWORD" \
  -d "grant_type=password" \
  "$KCHOST/realms/$REALM/protocol/openid-connect/token"  | jq -r '.access_token'`
echo "$ACCESS_TOKEN"

# shellcheck disable=SC2006
RESPONSE=`curl -H "Authorization: Bearer ${ACCESS_TOKEN}" \
  -H "X-Request-ID: 1234" \
  -H "x-client-request-id: 1235" \
  http://localhost:8080/v1/product/`
echo "$RESPONSE"

sleep 5
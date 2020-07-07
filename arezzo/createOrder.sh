#!/bin/bash

## CREATE CART
echo "## CREATE CART"
curl -X POST --header 'Content-Type: application/json' --header 'Accept: application/json' --header 'Authorization: Bearer 71b47456-1591-49c8-8290-9cd72fc5fb60' 'https://integ01.arezzo.com.br/arezzocoocc/v2/outstore/users/current/carts?fields=DEFAULT' --insecure
sleep 1
## ADD entry into cart 
echo "## ADD entry into cart "
curl -X POST --header 'Content-Type: application/json' --header 'Accept: application/json' --header 'Authorization: Bearer 71b47456-1591-49c8-8290-9cd72fc5fb60' -d '{"product":{"code":"A0138706730001U-35"},"code":"A0138706730001U-34","quantity": 2}' 'https://integ01.arezzo.com.br/arezzocoocc/v2/outstore/users/current/carts/current/entries?fields=DEFAULT' --insecure
#sleep 1
#curl -X POST --header 'Content-Type: application/json' --header 'Accept: application/json' --header 'Authorization: Bearer 71b47456-1591-49c8-8290-9cd72fc5fb60' -d '{"product":{"code":"A0376203090004U-33"},"code":"A0376203090004U-33","quantity": 2}' 'https://integ01.arezzo.com.br/arezzocoocc/v2/outstore/users/current/carts/current/entries?fields=DEFAULT' --insecure
sleep 1
#curl -X POST --header 'Content-Type: application/json' --header 'Accept: application/json' --header 'Authorization: Bearer 71b47456-1591-49c8-8290-9cd72fc5fb60' -d '{"product":{"code":"M0000000001001U-33"},"code":"M0000000001001U-33","quantity": 2}' 'https://integ01.arezzo.com.br/arezzocoocc/v2/outstore/users/current/carts/current/entries?fields=DEFAULT' --insecure
#sleep 1
#curl -X POST --header 'Content-Type: application/json' --header 'Accept: application/json' --header 'Authorization: Bearer 71b47456-1591-49c8-8290-9cd72fc5fb60' -d '{"product":{"code":"M0000000000001U-33"},"code":"M0000000000001U-33","quantity": 2}' 'https://integ01.arezzo.com.br/arezzocoocc/v2/outstore/users/current/carts/current/entries?fields=DEFAULT' --insecure
sleep 1
 ## SET ADDRESS
echo "## SET ADDRESS"
curl -X PUT --header 'Content-Type: application/json' --header 'Accept: application/json' --header 'Authorization: Bearer 71b47456-1591-49c8-8290-9cd72fc5fb60' 'https://integ01.arezzo.com.br/arezzocoocc/v2/outstore/users/current/carts/current/addresses/delivery?addressId=8796158623767' --insecure
sleep 1
## GET AND SET DELIVERY MODE
#echo "## GET AND SET DELIVERY MODE"
#curl -X GET --header 'Accept: application/json' --header 'Authorization: Bearer 71b47456-1591-49c8-8290-9cd72fc5fb60' 'https://integ01.arezzo.com.br/arezzocoocc/v2/outstore/users/current/carts/current/deliverymodes/12249-000?fields=DEFAULT' --insecure
## SET PAYMENT METHOD
#curl -X PUT --header 'Content-Type: application/json' --header 'Accept: application/json' --header 'Authorization: Bearer 71b47456-1591-49c8-8290-9cd72fc5fb60' 'https://integ01.arezzo.com.br/arezzocoocc/v2/outstore/users/current/carts/current/payment-method?paymentMethodCode=Boleto' --insecure



# IanW 29/03/2011
#
#
USERNAME=cora@example.org
PASSWORD=bar
HOST=https://cloud1.cggh.org/repository
TICKETS=https://cloud1.cggh.org/sso/v1/tickets
TARGET=${HOST}/service/content/studies
#TGT=`wget --post-data="username=${USERNAME}&password=${PASSWORD}" -q -O - -d ${TICKETS} 2>&1 | grep Location | grep TGT | awk -F/ '{printf $NF}'`
#get Service Ticket
#ST=`wget --post-data="service=${TARGET}" -q -O - -d ${TICKETS}/${TGT}`

# Get the resource
# Note -k turns off certificate validity checking
curl -k -c cookie-jar -o studies.ticket -i -H "Accept: text/xml" -X GET ${TARGET}?ticket=$SserviceT
curl -k -b cookie-jar -o studies.cookie -i -H "Accept: text/xml" -X GET ${TARGET} 
curl -k -b cookie-jar -o post.out -i -H "Content-Type: application/atom+xml" --data-binary @new-study.xml ${HOST}/service/content/studies 

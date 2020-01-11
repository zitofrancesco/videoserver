kubectl create configmap videoservice-config --from-file=./configmap
kubectl apply -f ./kafka.yml
kubectl apply -f ./mysql.yml


kubectl apply -f ./test/apigatawey.yml
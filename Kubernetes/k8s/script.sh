kubectl create configmap videoservice-config --from-file=./configmap
kubectl apply -f ./kafka.yml
kubectl apply -f ./mysql.yml

cd ./test
kubectl apply -f ./apigateway.yml

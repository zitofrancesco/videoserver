kubectl create configmap videoservice-kafka --from-file=./kakfa/kafka.properties --save-config
kubectl get configmap videoservice-kafka -o yaml > kakfa/videoservice-kafka.yml
kubectl create configmap videoservice-mysql --from-file=./mysql/mysql.properties --save-config
kubectl get configmap videoservice-mysql -o yaml > mysql/videoservice-mysql.yml
kubectl create configmap videoservice-apigateway --from-file=./apigateway/apigateway.properties --save-config
kubectl get configmap videoservice-apigateway -o yaml > apigateway/videoservice-apigateway.yml
kubectl create configmap videoservice-videomanagementservice --from-file=./videomanagement/videomanagement.properties --save-config
kubectl get configmap videoservice-videomanagementservice -o yaml > videomanagement/videoservice-videomanagement.yml

## kubectl apply -f ./kafka.yml

## kubectl apply -f ./mysql.yml

## kubectl apply -f ./apigateway.yml

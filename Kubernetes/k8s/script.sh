kubectl create configmap videoservice-mysql --from-file=./mysql/mysql.properties --save-config
kubectl get configmap videoservice-mysql -o yaml > mysql/videoservice-mysql.yml

kubectl create configmap videoservice-apigateway --from-file=./apigateway/apigateway.properties --save-config
kubectl get configmap videoservice-apigateway -o yaml > apigateway/videoservice-apigateway.yml

kubectl create configmap videoservice-videomanagementservice --from-file=./videomanagementservice/videomanagement.properties --save-config
kubectl get configmap videoservice-videomanagementservice -o yaml > videomanagementservice/videoservice-videomanagement.yml

kubectl create configmap videoservice-videoprocessingservice --from-file=./videoprocessingservice/videoprocessing.properties --save-config
kubectl get configmap videoservice-videoprocessingservice -o yaml > videoprocessingservice/videoservice-videoprocessing.yml

kubectl create configmap videoservice-spout --from-file=./spout/spout.properties --save-config
kubectl get configmap videoservice-spout -o yaml > spout/videoservice-spout.yml


kubectl create -f ./kafka/zookeeper.yml
kubectl create -f ./kafka/kafka-service.yml
kubectl create -f ./kafka/kafka-broker.yml

kubectl create -f ./mysql/mysql-pv.yml
kubectl create -f ./mysql/mysql.yml

kubectl create -f ./apigateway/apigateway.yml

kubectl create -f ./videoprocessingservice/videoprocessingservice.yml

kubectl create -f ./videomanagementservice/videomanagementservice.yml

kubectl create -f ./spout/spout.yml

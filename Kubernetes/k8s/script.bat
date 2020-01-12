kubectl create configmap videoservice-kafka --from-file=./kakfa/kafka.properties --save-config
kubectl get configmap videoservice-kafka -o yaml > kakfa/videoservice-kafka.yml

kubectl create configmap videoservice-mysql --from-file=./mysql/mysql.properties --save-config
kubectl get configmap videoservice-mysql -o yaml > mysql/videoservice-mysql.yml

kubectl create configmap videoservice-apigateway --from-file=./apigateway/apigateway.properties --save-config
kubectl get configmap videoservice-apigateway -o yaml > apigateway/videoservice-apigateway.yml

kubectl create configmap videoservice-videomanagementservice --from-file=./videomanagement/videomanagement.properties --save-config
kubectl get configmap videoservice-videomanagementservice -o yaml > videomanagement/videoservice-videomanagement.yml

kubectl create configmap videoservice-videoprocessingservice --from-file=./videoprocessing/videoprocessing.properties --save-config
kubectl get configmap videoservice-videoprocessingservice -o yaml > videoprocessing/videoservice-videoprocessing.yml


kubectl delete -f ./kafka/kafka.yml
kubectl create -f ./kafka/kafka.yml

kubectl delete -f ./mysql/mysql.yml
kubectl delete -f ./mysql/mysql-pv.yml

kubectl create -f ./mysql/mysql-pv.yml
kubectl create -f ./mysql/mysql.yml

kubectl delete -f ./apigateway/apigateway.yml
mvn package -f ./apigateway/pom.xml
docker build --rm -f ./apigateway/Dockerfile -t videoservice/apigateway:latest apigateway
cd ./k8s
kubectl create -f ./apigateway/apigateway.yml

kubectl delete -f ./videoprocessingservice/videoprocessingservice.yml
mvn package -f ./videoprocessingservice/pom.xml
docker build --rm -f ./videoprocessingservice/Dockerfile -t videoservice/videoprocessingservice:latest apigateway
cd ./k8s
kubectl create -f ./videoprocessingservice/videoprocessingservice.yml

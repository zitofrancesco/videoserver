# Create configmap
kubectl create configmap videoservice-mysql --from-file=./mysql/mysql-env.properties --save-config
kubectl get configmap videoservice-mysql -o yaml > mysql/videoservice-mysql.yml

kubectl create configmap videoservice-apigateway --from-file=./apigateway/apigateway.properties --save-config
kubectl get configmap videoservice-apigateway -o yaml > apigateway/videoservice-apigateway.yml

kubectl create configmap videoservice-videomanagementservice --from-file=./videomanagementservice/videomanagement.properties --save-config
kubectl get configmap videoservice-videomanagementservice -o yaml > videomanagementservice/videoservice-videomanagement.yml

kubectl create configmap videoservice-videoprocessingservice --from-file=./videoprocessingservice/videoprocessing.properties --save-config
kubectl get configmap videoservice-videoprocessingservice -o yaml > videoprocessingservice/videoservice-videoprocessing.yml

kubectl create configmap videoservice-spout --from-file=./spout/spout.properties --save-config
kubectl get configmap videoservice-spout -o yaml > spout/videoservice-spout.yml

# Create services and pods
# >> docker build -t ... (for all the images)

kubectl apply -f ./kafka/kafka.yml

kubectl apply -f ./mysql/mysql-pv.yml
kubectl apply -f ./mysql/mysql.yml

kubectl apply -f ./apigateway/apigateway.yml

kubectl apply -f ./videoprocessingservice/videoprocessingservice.yml

kubectl apply -f ./videomanagementservice/videomanagementservice.yml

kubectl apply -f ./spout/spout.yml

# Start Spark
# >> kubectl cluster-info 
# Kubernetes master is running at https://127.0.0.1:8443
# >> cd /usr/local/spark/
# >> ./bin/docker-image-tool.sh -t docker-spark build
# >> ./bin/spark-submit --class org.apache.spark.examples.SparkPi --master k8s://https://127.0.0.1:8443 --deploy-mode cluster --executor-memory 20G --num-executors 50 \
# --conf spark.kubernetes.container.image=spark:spark-docker path\to\exe.jar

cd $HOME
mkdir config-repo
cd config-repo
git init .
echo 'spring.rabbitmq.port=5672
spring.rabbitmq.username = guest
spring.rabbitmq.password = guest
mq.settings.exchange.name = dts.ps.exchange
mq.settings.purchase.queue.name = dts.ps.purchase.queue
mq.settings.subscription.queue.name= dts.ps.subscription.queue
mq.settings.purchase.key = dts.routing.purchase.key
mq.settings.subscription.key = dts.routing.subscription.key' > application.properties
git add -A .
git commit -m "Add application.properties"
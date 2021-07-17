cd $HOME
mkdir config-repo
cd config-repo
git init .
echo 'spring.rabbitmq.port=5672
mq.settings.exchange.name = dts.ps.exchange
mq.settings.queue.name = dts.ps.queue
mq.settings.purchase-key = dts.routing.purchase-key
mq.settings.subscription-key = dts.routing.subscription-key' > application.properties
git add -A .
git commit -m "Add application.properties"
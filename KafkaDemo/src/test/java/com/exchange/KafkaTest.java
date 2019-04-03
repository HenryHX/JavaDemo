package com.exchange;

import com.model.DataTypeDefines;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by Administrator
 * 2019-04-03
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/*"})
public class KafkaTest {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    String accountId = "51b3fa71-70af-5474-b1c6-8deaf4743bf0";
    String symbol = "BTCUSD";
    double BankruptcyPrice = 2200;
    double LiquidationPrice = 2300;

    @Test
    public void testProducer() throws InterruptedException {
        CBizPackage msg = new CBizPackage(TopicConstant.TID_EX_RtnOrder);

        PBEntity.PBROrder order = initOrder( System.currentTimeMillis() + 61 * 1000, "19");
        PBEntity.PBRMarketData market = initMarket(LiquidationPrice, LiquidationPrice);
        PBEntity.PBRPosition position = initPosition("1", DataTypeDefines.PD_LONG, DataTypeDefines.AS_Normal, DataTypeDefines.PS_NOMAL, 5);

        msg.addField(market);
        msg.addField(order);
        msg.addField(position);

        kafkaTemplate.send(TopicConstant.Kafka_TradeReport, 0, null, msg);

        Thread.sleep(100000);
    }

    public PBEntity.PBRPosition initPosition(String posID, int posDirection, int actionStatus, int positionStatus, int qty) {
        PBEntity.PBRPosition p = new PBEntity.PBRPosition();
        p.setAccountId(accountId);
        p.setPosDirection(posDirection);
        p.setActionStatus(actionStatus);
        p.setPositionStatus(positionStatus);
        p.setAvailableQty(qty);
        p.setCurrentQty(qty);
        p.setTotalQty(qty);
        p.setBankruptcyPrice(BankruptcyPrice);
        p.setCloseTime(0);
        p.setCreateTime(System.currentTimeMillis());
        p.setFund(0);
        p.setID(posID);
        p.setPosId(posID);
        p.setLeverage(1);
        p.setLiquidationPrice(LiquidationPrice);
        p.setSymbol(symbol);
        p.setOpenPrice(4500);
        p.setTakerFee(0.00075);
        p.setMakerFee(0.00025);
        return p;
    }

    public PBEntity.PBRMarketData initMarket(double lastPrice, double indexPrice) {
        PBEntity.PBRMarketData m = new PBEntity.PBRMarketData();
        m.setSymbol(symbol);
        m.setCreateTime(System.currentTimeMillis());
        m.setID(symbol);
        m.setIndexPrice(indexPrice);
        m.setLastPrice(lastPrice);
        return m;
    }

    public PBEntity.PBROrder initOrder(long createTime, String posId) {
        PBEntity.PBROrder order = new PBEntity.PBROrder();
        order.setAccountId(accountId);
        order.setCloseFee(0);
        order.setCreateTime(createTime);
        order.setCurrentQty(5);
        order.setID("123456");
        order.setOrderId("123456");
        order.setOpenFee(0);
        order.setOrderPrice(4500);
        order.setOrderType(DataTypeDefines.OTT_Limit);
        order.setOwnerType(DataTypeDefines.OT_RiskEngine);
        order.setPosAction(DataTypeDefines.PAT_ForceClose);
        order.setPosId(posId);
        order.setOrderStatus(DataTypeDefines.OST_New);
        return order;
    }

    @Test
    public void testConsumer() {

    }
}

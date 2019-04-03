package com.exchange;

public interface TopicConstant {

    String Kafka_TradeReport = "RTradeReport";
    String Kafka_MarketData = "RMarketData";
    String Kafka_Balance = "RBalance";
    String Kafka_Config = "RConfig";
    //String Kafka_Notice = "RNotice";

    //交互消息TID定义
    int TID_EX_Finish                          = 0x4000 + 1;

    int TID_EX_ReqOrderInsert                  = 0x5000 + 1;  //发送订单请求
    int TID_EX_ReqOrderCancel                  = 0x5000 + 2;  //Cancel订单请求

    int TID_EX_RtnOrder                        = 0x6000 + 1;  //
    int TID_EX_RtnTrade                        = 0x6000 + 2;  //
    int TID_EX_PositionChange                  = 0x6000 + 3;  //
    int TID_EX_BalanceChange                   = 0x6000 + 4;  //
    int TID_EX_ForceReduction                  = 0x6000 + 5;  //
    int TID_EX_RiskFundChange                  = 0x6000 + 6;  //
    int TID_EX_InstrumentChange                = 0x6000 + 7;  //

    int TID_EX_MDSnapshot                      = 0x7000 + 1;

    int TID_EX_ConfigChange                    = 0x8000 + 1;
    //int TID_EX_Notice                          = 0x9001 + 1;


    int TID_EX_Dump       = 0x1000 + 1;
    int TID_EX_Stop       = 0x1000 + 2;
    int TID_EX_Turn       = 0x1000 + 3;

}

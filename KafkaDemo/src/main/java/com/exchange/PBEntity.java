

package com.exchange;

import com.exchange.Message.*;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.TextFormat;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;


public final class PBEntity {
    static Charset defaultCode = Charset.forName("GBK");

    static HashMap<Integer, Class<? extends IEntity>> map = new HashMap<Integer, Class<? extends IEntity>>();

    static HashMap<String, Integer> fidMap = new HashMap<String, Integer>();

    static public boolean contentLog = false;

    public static Class<? extends IEntity> getClz(int filedID) {
        return map.get(filedID);
    }

    public static Integer getFieldID(Class<? extends IEntity> clz) {
        return fidMap.get(clz.getName());
    }

    public interface IEntity extends java.io.Serializable {
        public void parseFrom(byte[] buf) throws InvalidProtocolBufferException;

        public byte[] toByteArray();

        public int getFieldID();
    }

    public static abstract class BaseEntity implements IEntity {

        private boolean changedFlag_;

        public void setChanged() {
            changedFlag_ = true;
        }

        public boolean removeChangedAndReturn() {
            if (changedFlag_) {
                changedFlag_ = false;
                return true;
            }

            return false;
        }
    }

    static {
        String contentLogProp = System.getProperty("exch.api.contentlog");
        if (contentLogProp != null && contentLogProp.equalsIgnoreCase("true"))
            contentLog = true;


        map.put(RInstrument.getDescriptor().getIndex(), PBRInstrument.class);
        fidMap.put(PBRInstrument.class.getName(), RInstrument.getDescriptor().getIndex());

        map.put(ROrder.getDescriptor().getIndex(), PBROrder.class);
        fidMap.put(PBROrder.class.getName(), ROrder.getDescriptor().getIndex());

        map.put(RPosition.getDescriptor().getIndex(), PBRPosition.class);
        fidMap.put(PBRPosition.class.getName(), RPosition.getDescriptor().getIndex());

        map.put(RTrade.getDescriptor().getIndex(), PBRTrade.class);
        fidMap.put(PBRTrade.class.getName(), RTrade.getDescriptor().getIndex());

        map.put(RReqChangePositionStatus.getDescriptor().getIndex(), PBRReqChangePositionStatus.class);
        fidMap.put(PBRReqChangePositionStatus.class.getName(), RReqChangePositionStatus.getDescriptor().getIndex());

        map.put(RReqOrderInput.getDescriptor().getIndex(), PBRReqOrderInput.class);
        fidMap.put(PBRReqOrderInput.class.getName(), RReqOrderInput.getDescriptor().getIndex());

        map.put(RReqCancelOrder.getDescriptor().getIndex(), PBRReqCancelOrder.class);
        fidMap.put(PBRReqCancelOrder.class.getName(), RReqCancelOrder.getDescriptor().getIndex());

        map.put(RReqReduction.getDescriptor().getIndex(), PBRReqReduction.class);
        fidMap.put(PBRReqReduction.class.getName(), RReqReduction.getDescriptor().getIndex());

        map.put(RFund.getDescriptor().getIndex(), PBRFund.class);
        fidMap.put(PBRFund.class.getName(), RFund.getDescriptor().getIndex());

        map.put(RBalance.getDescriptor().getIndex(), PBRBalance.class);
        fidMap.put(PBRBalance.class.getName(), RBalance.getDescriptor().getIndex());

        map.put(RMarketData.getDescriptor().getIndex(), PBRMarketData.class);
        fidMap.put(PBRMarketData.class.getName(), RMarketData.getDescriptor().getIndex());

        map.put(RConfigInfo.getDescriptor().getIndex(), PBRConfigInfo.class);
        fidMap.put(PBRConfigInfo.class.getName(), RConfigInfo.getDescriptor().getIndex());


    }


    public static class PBRInstrument extends BaseEntity {
        public PBRInstrument() {
            builder = RInstrument.newBuilder();
        }

        public void parseFrom(byte[] buf) throws InvalidProtocolBufferException {
            builder.mergeFrom(buf);
            if (PBEntity.contentLog) {
                try {
                    TextFormat.print(builder.build(), System.out);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public byte[] toByteArray() {
            return builder.build().toByteArray();
        }

        public int getFieldID() {
            return RInstrument.getDescriptor().getIndex();
        }


        public String getID() {
            return builder.getID().toString(defaultCode);
        }

        public void setID(String val) {
            if (val == null) {
                builder.clearID();
            } else
                builder.setID(ByteString.copyFrom(val, defaultCode));
        }

        public String getSymbol() {
            return builder.getSymbol().toString(defaultCode);
        }

        public void setSymbol(String val) {
            if (val == null) {
                builder.clearSymbol();
            } else
                builder.setSymbol(ByteString.copyFrom(val, defaultCode));
        }

        public int getInstType() {
            return builder.getInstType();
        }

        public void setInstType(int val) {
            builder.setInstType(val);
        }

        public String getQuoteCurrency() {
            return builder.getQuoteCurrency().toString(defaultCode);
        }

        public void setQuoteCurrency(String val) {
            if (val == null) {
                builder.clearQuoteCurrency();
            } else
                builder.setQuoteCurrency(ByteString.copyFrom(val, defaultCode));
        }

        public String getCurrency() {
            return builder.getCurrency().toString(defaultCode);
        }

        public void setCurrency(String val) {
            if (val == null) {
                builder.clearCurrency();
            } else
                builder.setCurrency(ByteString.copyFrom(val, defaultCode));
        }

        public int getPriceTick() {
            return builder.getPriceTick();
        }

        public void setPriceTick(int val) {
            builder.setPriceTick(val);
        }

        public int getMaxLeverage() {
            return builder.getMaxLeverage();
        }

        public void setMaxLeverage(int val) {
            builder.setMaxLeverage(val);
        }

        public double getMaintMargin() {
            return builder.getMaintMargin();
        }

        public void setMaintMargin(double val) {
            builder.setMaintMargin(val);
        }

        public double getAlarmMargin() {
            return builder.getAlarmMargin();
        }

        public void setAlarmMargin(double val) {
            builder.setAlarmMargin(val);
        }

        public double getTakerFee() {
            return builder.getTakerFee();
        }

        public void setTakerFee(double val) {
            builder.setTakerFee(val);
        }

        public double getMakerFee() {
            return builder.getMakerFee();
        }

        public void setMakerFee(double val) {
            builder.setMakerFee(val);
        }

        public double getUnitValue() {
            return builder.getUnitValue();
        }

        public void setUnitValue(double val) {
            builder.setUnitValue(val);
        }

        public long getMaxOrderQty() {
            return builder.getMaxOrderQty();
        }

        public void setMaxOrderQty(long val) {
            builder.setMaxOrderQty(val);
        }

        public long getMaxPositionQty() {
            return builder.getMaxPositionQty();
        }

        public void setMaxPositionQty(long val) {
            builder.setMaxPositionQty(val);
        }

        public String getIndexSymbol() {
            return builder.getIndexSymbol().toString(defaultCode);
        }

        public void setIndexSymbol(String val) {
            if (val == null) {
                builder.clearIndexSymbol();
            } else
                builder.setIndexSymbol(ByteString.copyFrom(val, defaultCode));
        }

        public int getInstStatus() {
            return builder.getInstStatus();
        }

        public void setInstStatus(int val) {
            builder.setInstStatus(val);
        }

        private transient RInstrument.Builder builder;

        public RInstrument.Builder getBuilder() {
            return builder;
        }
    }


    public static class PBROrder extends BaseEntity {
        public PBROrder() {
            builder = ROrder.newBuilder();
        }

        public void parseFrom(byte[] buf) throws InvalidProtocolBufferException {
            builder.mergeFrom(buf);
            if (PBEntity.contentLog) {
                try {
                    TextFormat.print(builder.build(), System.out);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public byte[] toByteArray() {
            return builder.build().toByteArray();
        }

        public int getFieldID() {
            return ROrder.getDescriptor().getIndex();
        }


        public String getID() {
            return builder.getID().toString(defaultCode);
        }

        public void setID(String val) {
            if (val == null) {
                builder.clearID();
            } else
                builder.setID(ByteString.copyFrom(val, defaultCode));
        }

        public String getOrderId() {
            return builder.getOrderId().toString(defaultCode);
        }

        public void setOrderId(String val) {
            if (val == null) {
                builder.clearOrderId();
            } else
                builder.setOrderId(ByteString.copyFrom(val, defaultCode));
        }

        public String getAccountId() {
            return builder.getAccountId().toString(defaultCode);
        }

        public void setAccountId(String val) {
            if (val == null) {
                builder.clearAccountId();
            } else
                builder.setAccountId(ByteString.copyFrom(val, defaultCode));
        }

        public String getSymbol() {
            return builder.getSymbol().toString(defaultCode);
        }

        public void setSymbol(String val) {
            if (val == null) {
                builder.clearSymbol();
            } else
                builder.setSymbol(ByteString.copyFrom(val, defaultCode));
        }

        public int getOrderType() {
            return builder.getOrderType();
        }

        public void setOrderType(int val) {
            builder.setOrderType(val);
        }

        public int getPosAction() {
            return builder.getPosAction();
        }

        public void setPosAction(int val) {
            builder.setPosAction(val);
        }

        public String getPosId() {
            return builder.getPosId().toString(defaultCode);
        }

        public void setPosId(String val) {
            if (val == null) {
                builder.clearPosId();
            } else
                builder.setPosId(ByteString.copyFrom(val, defaultCode));
        }

        public int getSide() {
            return builder.getSide();
        }

        public void setSide(int val) {
            builder.setSide(val);
        }

        public double getPosMargin() {
            return builder.getPosMargin();
        }

        public void setPosMargin(double val) {
            builder.setPosMargin(val);
        }

        public double getOpenFee() {
            return builder.getOpenFee();
        }

        public void setOpenFee(double val) {
            builder.setOpenFee(val);
        }

        public double getCloseFee() {
            return builder.getCloseFee();
        }

        public void setCloseFee(double val) {
            builder.setCloseFee(val);
        }

        public double getOrderPrice() {
            return builder.getOrderPrice();
        }

        public void setOrderPrice(double val) {
            builder.setOrderPrice(val);
        }

        public double getCurrentQty() {
            return builder.getCurrentQty();
        }

        public void setCurrentQty(double val) {
            builder.setCurrentQty(val);
        }

        public double getTotalQty() {
            return builder.getTotalQty();
        }

        public void setTotalQty(double val) {
            builder.setTotalQty(val);
        }

        public int getOrderStatus() {
            return builder.getOrderStatus();
        }

        public void setOrderStatus(int val) {
            builder.setOrderStatus(val);
        }

        public long getCreateTime() {
            return builder.getCreateTime();
        }

        public void setCreateTime(long val) {
            builder.setCreateTime(val);
        }

        public String getStatusMessage() {
            return builder.getStatusMessage().toString(defaultCode);
        }

        public void setStatusMessage(String val) {
            if (val == null) {
                builder.clearStatusMessage();
            } else
                builder.setStatusMessage(ByteString.copyFrom(val, defaultCode));
        }

        public int getOwnerType() {
            return builder.getOwnerType();
        }

        public void setOwnerType(int val) {
            builder.setOwnerType(val);
        }

        private transient ROrder.Builder builder;

        public ROrder.Builder getBuilder() {
            return builder;
        }
    }


    public static class PBRPosition extends BaseEntity {
        String posId;

        public PBRPosition() {
            builder = RPosition.newBuilder();
        }

        public void parseFrom(byte[] buf) throws InvalidProtocolBufferException {
            builder.mergeFrom(buf);
            posId = builder.getPosId().toString(defaultCode);
            if (PBEntity.contentLog) {
                try {
                    TextFormat.print(builder.build(), System.out);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public byte[] toByteArray() {
            return builder.build().toByteArray();
        }

        public int getFieldID() {
            return RPosition.getDescriptor().getIndex();
        }


        public String getID() {
            return builder.getID().toString(defaultCode);
        }

        public void setID(String val) {
            if (val == null) {
                builder.clearID();
            } else
                builder.setID(ByteString.copyFrom(val, defaultCode));
        }

        public String getPosId() {
            return posId;
        }

        public void setPosId(String val) {
            if (val == null) {
                builder.clearPosId();
            } else {
                builder.setPosId(ByteString.copyFrom(val, defaultCode));
                posId = val;
            }
        }

        public int getPosDirection() {
            return builder.getPosDirection();
        }

        public void setPosDirection(int val) {
            builder.setPosDirection(val);
        }

        public String getSymbol() {
            return builder.getSymbol().toString(defaultCode);
        }

        public void setSymbol(String val) {
            if (val == null) {
                builder.clearSymbol();
            } else
                builder.setSymbol(ByteString.copyFrom(val, defaultCode));
        }

        public String getAccountId() {
            return builder.getAccountId().toString(defaultCode);
        }

        public void setAccountId(String val) {
            if (val == null) {
                builder.clearAccountId();
            } else
                builder.setAccountId(ByteString.copyFrom(val, defaultCode));
        }

        public int getOrderType() {
            return builder.getOrderType();
        }

        public void setOrderType(int val) {
            builder.setOrderType(val);
        }

        public double getOpenPrice() {
            return builder.getOpenPrice();
        }

        public void setOpenPrice(double val) {
            builder.setOpenPrice(val);
        }

        public double getLiquidationPrice() {
            return builder.getLiquidationPrice();
        }

        public void setLiquidationPrice(double val) {
            builder.setLiquidationPrice(val);
        }

        public double getBankruptcyPrice() {
            return builder.getBankruptcyPrice();
        }

        public void setBankruptcyPrice(double val) {
            builder.setBankruptcyPrice(val);
        }

        public double getTotalQty() {
            return builder.getTotalQty();
        }

        public void setTotalQty(double val) {
            builder.setTotalQty(val);
        }

        public double getCurrentQty() {
            return builder.getCurrentQty();
        }

        public void setCurrentQty(double val) {
            builder.setCurrentQty(val);
        }

        public double getAvailableQty() {
            return builder.getAvailableQty();
        }

        public void setAvailableQty(double val) {
            builder.setAvailableQty(val);
        }

        public double getMargin() {
            return builder.getMargin();
        }

        public void setMargin(double val) {
            builder.setMargin(val);
        }

        public int getLeverage() {
            return builder.getLeverage();
        }

        public void setLeverage(int val) {
            builder.setLeverage(val);
        }

        public double getRealisedPNL() {
            return builder.getRealisedPNL();
        }

        public void setRealisedPNL(double val) {
            builder.setRealisedPNL(val);
        }

        public int getPositionStatus() {
            return builder.getPositionStatus();
        }

        public void setPositionStatus(int val) {
            builder.setPositionStatus(val);
        }

        public double getStopLossPrice() {
            return builder.getStopLossPrice();
        }

        public void setStopLossPrice(double val) {
            builder.setStopLossPrice(val);
        }

        public double getStopWinPrice() {
            return builder.getStopWinPrice();
        }

        public void setStopWinPrice(double val) {
            builder.setStopWinPrice(val);
        }

        public double getMaintMargin() {
            return builder.getMaintMargin();
        }

        public void setMaintMargin(double val) {
            builder.setMaintMargin(val);
        }

        public double getTakerFee() {
            return builder.getTakerFee();
        }

        public void setTakerFee(double val) {
            builder.setTakerFee(val);
        }

        public double getMakerFee() {
            return builder.getMakerFee();
        }

        public void setMakerFee(double val) {
            builder.setMakerFee(val);
        }

        public double getFund() {
            return builder.getFund();
        }

        public void setFund(double val) {
            builder.setFund(val);
        }

        public long getCreateTime() {
            return builder.getCreateTime();
        }

        public void setCreateTime(long val) {
            builder.setCreateTime(val);
        }

        public long getOpenTime() {
            return builder.getOpenTime();
        }

        public void setOpenTime(long val) {
            builder.setOpenTime(val);
        }

        public long getCloseTime() {
            return builder.getCloseTime();
        }

        public void setCloseTime(long val) {
            builder.setCloseTime(val);
        }

        public int getOwnerType() {
            return builder.getOwnerType();
        }

        public void setOwnerType(int val) {
            builder.setOwnerType(val);
        }

        public int getActionStatus() {
            return builder.getActionStatus();
        }

        public void setActionStatus(int val) {
            builder.setActionStatus(val);
        }

        public int getStopLossFlag() {
            return builder.getStopLossFlag();
        }

        public void setStopLossFlag(int val) {
            builder.setStopLossFlag(val);
        }

        public int getStopWinFlag() {
            return builder.getStopWinFlag();
        }

        public void setStopWinFlag(int val) {
            builder.setStopWinFlag(val);
        }

        public double getAlarmPrice() {
            return builder.getAlarmPrice();
        }

        public void setAlarmPrice(double val) {
            builder.setAlarmPrice(val);
        }

        public int getAlarmCount() {
            return builder.getAlarmCount();
        }

        public void setAlarmCount(int val) {
            builder.setAlarmCount(val);
        }

        private transient RPosition.Builder builder;

        public RPosition.Builder getBuilder() {
            return builder;
        }
    }


    public static class PBRTrade extends BaseEntity {
        public PBRTrade() {
            builder = RTrade.newBuilder();
        }

        public void parseFrom(byte[] buf) throws InvalidProtocolBufferException {
            builder.mergeFrom(buf);
            if (PBEntity.contentLog) {
                try {
                    TextFormat.print(builder.build(), System.out);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public byte[] toByteArray() {
            return builder.build().toByteArray();
        }

        public int getFieldID() {
            return RTrade.getDescriptor().getIndex();
        }


        public String getID() {
            return builder.getID().toString(defaultCode);
        }

        public void setID(String val) {
            if (val == null) {
                builder.clearID();
            } else
                builder.setID(ByteString.copyFrom(val, defaultCode));
        }

        public String getTradeId() {
            return builder.getTradeId().toString(defaultCode);
        }

        public void setTradeId(String val) {
            if (val == null) {
                builder.clearTradeId();
            } else
                builder.setTradeId(ByteString.copyFrom(val, defaultCode));
        }

        public String getOrderId() {
            return builder.getOrderId().toString(defaultCode);
        }

        public void setOrderId(String val) {
            if (val == null) {
                builder.clearOrderId();
            } else
                builder.setOrderId(ByteString.copyFrom(val, defaultCode));
        }

        public long getExecTimes() {
            return builder.getExecTimes();
        }

        public void setExecTimes(long val) {
            builder.setExecTimes(val);
        }

        public String getSymbol() {
            return builder.getSymbol().toString(defaultCode);
        }

        public void setSymbol(String val) {
            if (val == null) {
                builder.clearSymbol();
            } else
                builder.setSymbol(ByteString.copyFrom(val, defaultCode));
        }

        public String getAccountId() {
            return builder.getAccountId().toString(defaultCode);
        }

        public void setAccountId(String val) {
            if (val == null) {
                builder.clearAccountId();
            } else
                builder.setAccountId(ByteString.copyFrom(val, defaultCode));
        }

        public int getOrderType() {
            return builder.getOrderType();
        }

        public void setOrderType(int val) {
            builder.setOrderType(val);
        }

        public int getSide() {
            return builder.getSide();
        }

        public void setSide(int val) {
            builder.setSide(val);
        }

        public int getExecType() {
            return builder.getExecType();
        }

        public void setExecType(int val) {
            builder.setExecType(val);
        }

        public double getTradePrice() {
            return builder.getTradePrice();
        }

        public void setTradePrice(double val) {
            builder.setTradePrice(val);
        }

        public double getOrderPrice() {
            return builder.getOrderPrice();
        }

        public void setOrderPrice(double val) {
            builder.setOrderPrice(val);
        }

        public double getSize() {
            return builder.getSize();
        }

        public void setSize(double val) {
            builder.setSize(val);
        }

        public double getTotalQty() {
            return builder.getTotalQty();
        }

        public void setTotalQty(double val) {
            builder.setTotalQty(val);
        }

        public double getBeforeQty() {
            return builder.getBeforeQty();
        }

        public void setBeforeQty(double val) {
            builder.setBeforeQty(val);
        }

        public double getAfterQty() {
            return builder.getAfterQty();
        }

        public void setAfterQty(double val) {
            builder.setAfterQty(val);
        }

        public long getUpdateTime() {
            return builder.getUpdateTime();
        }

        public void setUpdateTime(long val) {
            builder.setUpdateTime(val);
        }

        public long getCreateTime() {
            return builder.getCreateTime();
        }

        public void setCreateTime(long val) {
            builder.setCreateTime(val);
        }

        private transient RTrade.Builder builder;

        public RTrade.Builder getBuilder() {
            return builder;
        }
    }


    public static class PBRReqChangePositionStatus extends BaseEntity {
        public PBRReqChangePositionStatus() {
            builder = RReqChangePositionStatus.newBuilder();
        }

        public void parseFrom(byte[] buf) throws InvalidProtocolBufferException {
            builder.mergeFrom(buf);
            if (PBEntity.contentLog) {
                try {
                    TextFormat.print(builder.build(), System.out);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public byte[] toByteArray() {
            return builder.build().toByteArray();
        }

        public int getFieldID() {
            return RReqChangePositionStatus.getDescriptor().getIndex();
        }


        public String getAccountId() {
            return builder.getAccountId().toString(defaultCode);
        }

        public void setAccountId(String val) {
            if (val == null) {
                builder.clearAccountId();
            } else
                builder.setAccountId(ByteString.copyFrom(val, defaultCode));
        }

        public String getSymbol() {
            return builder.getSymbol().toString(defaultCode);
        }

        public void setSymbol(String val) {
            if (val == null) {
                builder.clearSymbol();
            } else
                builder.setSymbol(ByteString.copyFrom(val, defaultCode));
        }

        public String getPosId() {
            return builder.getPosId().toString(defaultCode);
        }

        public void setPosId(String val) {
            if (val == null) {
                builder.clearPosId();
            } else
                builder.setPosId(ByteString.copyFrom(val, defaultCode));
        }

        public double getCurrentQty() {
            return builder.getCurrentQty();
        }

        public void setCurrentQty(double val) {
            builder.setCurrentQty(val);
        }

        public int getPositionStatus() {
            return builder.getPositionStatus();
        }

        public void setPositionStatus(int val) {
            builder.setPositionStatus(val);
        }

        public int getOwnerType() {
            return builder.getOwnerType();
        }

        public void setOwnerType(int val) {
            builder.setOwnerType(val);
        }

        private transient RReqChangePositionStatus.Builder builder;

        public RReqChangePositionStatus.Builder getBuilder() {
            return builder;
        }
    }


    public static class PBRReqOrderInput extends BaseEntity {
        public PBRReqOrderInput() {
            builder = RReqOrderInput.newBuilder();
        }

        public void parseFrom(byte[] buf) throws InvalidProtocolBufferException {
            builder.mergeFrom(buf);
            if (PBEntity.contentLog) {
                try {
                    TextFormat.print(builder.build(), System.out);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public byte[] toByteArray() {
            return builder.build().toByteArray();
        }

        public int getFieldID() {
            return RReqOrderInput.getDescriptor().getIndex();
        }


        public String getAccountId() {
            return builder.getAccountId().toString(defaultCode);
        }

        public void setAccountId(String val) {
            if (val == null) {
                builder.clearAccountId();
            } else
                builder.setAccountId(ByteString.copyFrom(val, defaultCode));
        }

        public String getSymbol() {
            return builder.getSymbol().toString(defaultCode);
        }

        public void setSymbol(String val) {
            if (val == null) {
                builder.clearSymbol();
            } else
                builder.setSymbol(ByteString.copyFrom(val, defaultCode));
        }

        public String getPosId() {
            return builder.getPosId().toString(defaultCode);
        }

        public void setPosId(String val) {
            if (val == null) {
                builder.clearPosId();
            } else
                builder.setPosId(ByteString.copyFrom(val, defaultCode));
        }

        public double getPrice() {
            return builder.getPrice();
        }

        public void setPrice(double val) {
            builder.setPrice(val);
        }

        public double getTotalQty() {
            return builder.getTotalQty();
        }

        public void setTotalQty(double val) {
            builder.setTotalQty(val);
        }

        public int getOwnerType() {
            return builder.getOwnerType();
        }

        public void setOwnerType(int val) {
            builder.setOwnerType(val);
        }

        private transient RReqOrderInput.Builder builder;

        public RReqOrderInput.Builder getBuilder() {
            return builder;
        }
    }


    public static class PBRReqCancelOrder extends BaseEntity {
        public PBRReqCancelOrder() {
            builder = RReqCancelOrder.newBuilder();
        }

        public void parseFrom(byte[] buf) throws InvalidProtocolBufferException {
            builder.mergeFrom(buf);
            if (PBEntity.contentLog) {
                try {
                    TextFormat.print(builder.build(), System.out);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public byte[] toByteArray() {
            return builder.build().toByteArray();
        }

        public int getFieldID() {
            return RReqCancelOrder.getDescriptor().getIndex();
        }


        public String getAccountId() {
            return builder.getAccountId().toString(defaultCode);
        }

        public void setAccountId(String val) {
            if (val == null) {
                builder.clearAccountId();
            } else
                builder.setAccountId(ByteString.copyFrom(val, defaultCode));
        }

        public String getSymbol() {
            return builder.getSymbol().toString(defaultCode);
        }

        public void setSymbol(String val) {
            if (val == null) {
                builder.clearSymbol();
            } else
                builder.setSymbol(ByteString.copyFrom(val, defaultCode));
        }

        public String getOrderId() {
            return builder.getOrderId().toString(defaultCode);
        }

        public void setOrderId(String val) {
            if (val == null) {
                builder.clearOrderId();
            } else
                builder.setOrderId(ByteString.copyFrom(val, defaultCode));
        }

        public int getOwnerType() {
            return builder.getOwnerType();
        }

        public void setOwnerType(int val) {
            builder.setOwnerType(val);
        }

        private transient RReqCancelOrder.Builder builder;

        public RReqCancelOrder.Builder getBuilder() {
            return builder;
        }
    }


    public static class PBRReqReduction extends BaseEntity {
        public PBRReqReduction() {
            builder = RReqReduction.newBuilder();
        }

        public void parseFrom(byte[] buf) throws InvalidProtocolBufferException {
            builder.mergeFrom(buf);
            if (PBEntity.contentLog) {
                try {
                    TextFormat.print(builder.build(), System.out);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public byte[] toByteArray() {
            return builder.build().toByteArray();
        }

        public int getFieldID() {
            return RReqReduction.getDescriptor().getIndex();
        }


        public String getID() {
            return builder.getID().toString(defaultCode);
        }

        public void setID(String val) {
            if (val == null) {
                builder.clearID();
            } else
                builder.setID(ByteString.copyFrom(val, defaultCode));
        }

        public String getLDNAccountId() {
            return builder.getLDNAccountId().toString(defaultCode);
        }

        public void setLDNAccountId(String val) {
            if (val == null) {
                builder.clearLDNAccountId();
            } else
                builder.setLDNAccountId(ByteString.copyFrom(val, defaultCode));
        }

        public String getADLAccountId() {
            return builder.getADLAccountId().toString(defaultCode);
        }

        public void setADLAccountId(String val) {
            if (val == null) {
                builder.clearADLAccountId();
            } else
                builder.setADLAccountId(ByteString.copyFrom(val, defaultCode));
        }

        public String getLDNPosId() {
            return builder.getLDNPosId().toString(defaultCode);
        }

        public void setLDNPosId(String val) {
            if (val == null) {
                builder.clearLDNPosId();
            } else
                builder.setLDNPosId(ByteString.copyFrom(val, defaultCode));
        }

        public String getADLPosId() {
            return builder.getADLPosId().toString(defaultCode);
        }

        public void setADLPosId(String val) {
            if (val == null) {
                builder.clearADLPosId();
            } else
                builder.setADLPosId(ByteString.copyFrom(val, defaultCode));
        }

        public String getSymbol() {
            return builder.getSymbol().toString(defaultCode);
        }

        public void setSymbol(String val) {
            if (val == null) {
                builder.clearSymbol();
            } else
                builder.setSymbol(ByteString.copyFrom(val, defaultCode));
        }

        public double getPrice() {
            return builder.getPrice();
        }

        public void setPrice(double val) {
            builder.setPrice(val);
        }

        public double getTotalQty() {
            return builder.getTotalQty();
        }

        public void setTotalQty(double val) {
            builder.setTotalQty(val);
        }

        public int getOwnerType() {
            return builder.getOwnerType();
        }

        public void setOwnerType(int val) {
            builder.setOwnerType(val);
        }

        private transient RReqReduction.Builder builder;

        public RReqReduction.Builder getBuilder() {
            return builder;
        }
    }


    public static class PBRFund extends BaseEntity {
        public PBRFund() {
            builder = RFund.newBuilder();
        }

        public void parseFrom(byte[] buf) throws InvalidProtocolBufferException {
            builder.mergeFrom(buf);
            if (PBEntity.contentLog) {
                try {
                    TextFormat.print(builder.build(), System.out);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public byte[] toByteArray() {
            return builder.build().toByteArray();
        }

        public int getFieldID() {
            return RFund.getDescriptor().getIndex();
        }


        public String getID() {
            return builder.getID().toString(defaultCode);
        }

        public void setID(String val) {
            if (val == null) {
                builder.clearID();
            } else
                builder.setID(ByteString.copyFrom(val, defaultCode));
        }

        public String getSymbol() {
            return builder.getSymbol().toString(defaultCode);
        }

        public void setSymbol(String val) {
            if (val == null) {
                builder.clearSymbol();
            } else
                builder.setSymbol(ByteString.copyFrom(val, defaultCode));
        }

        public String getCurrency() {
            return builder.getCurrency().toString(defaultCode);
        }

        public void setCurrency(String val) {
            if (val == null) {
                builder.clearCurrency();
            } else
                builder.setCurrency(ByteString.copyFrom(val, defaultCode));
        }

        public double getBalance() {
            return builder.getBalance();
        }

        public void setBalance(double val) {
            builder.setBalance(val);
        }

        public double getAvailable() {
            return builder.getAvailable();
        }

        public void setAvailable(double val) {
            builder.setAvailable(val);
        }

        public double getFrozen() {
            return builder.getFrozen();
        }

        public void setFrozen(double val) {
            builder.setFrozen(val);
        }

        private transient RFund.Builder builder;

        public RFund.Builder getBuilder() {
            return builder;
        }
    }


    public static class PBRBalance extends BaseEntity {
        public PBRBalance() {
            builder = RBalance.newBuilder();
        }

        public void parseFrom(byte[] buf) throws InvalidProtocolBufferException {
            builder.mergeFrom(buf);
            if (PBEntity.contentLog) {
                try {
                    TextFormat.print(builder.build(), System.out);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public byte[] toByteArray() {
            return builder.build().toByteArray();
        }

        public int getFieldID() {
            return RBalance.getDescriptor().getIndex();
        }


        public String getID() {
            return builder.getID().toString(defaultCode);
        }

        public void setID(String val) {
            if (val == null) {
                builder.clearID();
            } else
                builder.setID(ByteString.copyFrom(val, defaultCode));
        }

        public String getAccountId() {
            return builder.getAccountId().toString(defaultCode);
        }

        public void setAccountId(String val) {
            if (val == null) {
                builder.clearAccountId();
            } else
                builder.setAccountId(ByteString.copyFrom(val, defaultCode));
        }

        public String getCurrency() {
            return builder.getCurrency().toString(defaultCode);
        }

        public void setCurrency(String val) {
            if (val == null) {
                builder.clearCurrency();
            } else
                builder.setCurrency(ByteString.copyFrom(val, defaultCode));
        }

        public double getBalance() {
            return builder.getBalance();
        }

        public void setBalance(double val) {
            builder.setBalance(val);
        }

        public double getOrderMargin() {
            return builder.getOrderMargin();
        }

        public void setOrderMargin(double val) {
            builder.setOrderMargin(val);
        }

        public double getPositionMargin() {
            return builder.getPositionMargin();
        }

        public void setPositionMargin(double val) {
            builder.setPositionMargin(val);
        }

        private transient RBalance.Builder builder;

        public RBalance.Builder getBuilder() {
            return builder;
        }
    }


    public static class PBRMarketData extends BaseEntity {

        String symbol = "";

        public PBRMarketData() {
            builder = RMarketData.newBuilder();
        }

        public void parseFrom(byte[] buf) throws InvalidProtocolBufferException {
            builder.mergeFrom(buf);
            symbol = builder.getSymbol().toString(defaultCode);
            if (PBEntity.contentLog) {
                try {
                    TextFormat.print(builder.build(), System.out);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public byte[] toByteArray() {
            return builder.build().toByteArray();
        }

        public int getFieldID() {
            return RMarketData.getDescriptor().getIndex();
        }


        public String getID() {
            return builder.getID().toString(defaultCode);
        }

        public void setID(String val) {
            if (val == null) {
                builder.clearID();
            } else
                builder.setID(ByteString.copyFrom(val, defaultCode));
        }

        public String getSymbol() {
            return symbol;
        }

        public void setSymbol(String val) {
            if (val == null) {
                builder.clearSymbol();
            } else {
                builder.setSymbol(ByteString.copyFrom(val, defaultCode));
                symbol = val;
            }
        }

        public double getLastPrice() {
            return builder.getLastPrice();
        }

        public void setLastPrice(double val) {
            builder.setLastPrice(val);
        }

        public double getIndexPrice() {
            return builder.getIndexPrice();
        }

        public void setIndexPrice(double val) {
            builder.setIndexPrice(val);
        }

        public List<Double> getAskPrice() {
            return builder.getAskPriceList();
        }

        public void setAskPrice(List<Double> list) {
            builder.clearAskPrice();
            builder.addAllAskPrice(list);
        }

        public List<Double> getAskVolume() {
            return builder.getAskVolumeList();
        }

        public void setAskVolume(List<Double> list) {
            builder.clearAskVolume();
            builder.addAllAskVolume(list);
        }

        public List<Double> getBidPrice() {
            return builder.getBidPriceList();
        }

        public void setBidPrice(List<Double> list) {
            builder.clearBidPrice();
            builder.addAllBidPrice(list);
        }

        public List<Double> getBidVolume() {
            return builder.getBidVolumeList();
        }

        public void setBidVolume(List<Double> list) {
            builder.clearBidVolume();
            builder.addAllBidVolume(list);
        }

        public long getCreateTime() {
            return builder.getCreateTime();
        }

        public void setCreateTime(long val) {
            builder.setCreateTime(val);
        }

        private transient RMarketData.Builder builder;

        public RMarketData.Builder getBuilder() {
            return builder;
        }
    }


    public static class PBRConfigInfo extends BaseEntity {
        public PBRConfigInfo() {
            builder = RConfigInfo.newBuilder();
        }

        public void parseFrom(byte[] buf) throws InvalidProtocolBufferException {
            builder.mergeFrom(buf);
            if (PBEntity.contentLog) {
                try {
                    TextFormat.print(builder.build(), System.out);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public byte[] toByteArray() {
            return builder.build().toByteArray();
        }

        public int getFieldID() {
            return RConfigInfo.getDescriptor().getIndex();
        }


        public String getID() {
            return builder.getID().toString(defaultCode);
        }

        public void setID(String val) {
            if (val == null) {
                builder.clearID();
            } else
                builder.setID(ByteString.copyFrom(val, defaultCode));
        }

        public String getConfigKey() {
            return builder.getConfigKey().toString(defaultCode);
        }

        public void setConfigKey(String val) {
            if (val == null) {
                builder.clearConfigKey();
            } else
                builder.setConfigKey(ByteString.copyFrom(val, defaultCode));
        }

        public String getConfigValue() {
            return builder.getConfigValue().toString(defaultCode);
        }

        public void setConfigValue(String val) {
            if (val == null) {
                builder.clearConfigValue();
            } else
                builder.setConfigValue(ByteString.copyFrom(val, defaultCode));
        }

        public long getUpdateTime() {
            return builder.getUpdateTime();
        }

        public void setUpdateTime(long val) {
            builder.setUpdateTime(val);
        }

        private transient RConfigInfo.Builder builder;

        public RConfigInfo.Builder getBuilder() {
            return builder;
        }
    }


}



    
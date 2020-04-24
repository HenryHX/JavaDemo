package com.ulknow;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.lang.reflect.Array;
import java.util.*;

/**
 * Created by Administrator
 * 2019-12-06
 */

public class RiskThreshold {

    // 公司阀值
    private PriorityQueue<RiskThresholdItem> companyThresholdQueue;
    private RiskThresholdItem[] thresholdList;


    public RiskThreshold() {
        companyThresholdQueue = new PriorityQueue<>();
    }


    public PriorityQueue<RiskThresholdItem> getCompanyThresholdQueue() {
        return companyThresholdQueue;
    }

    public void setCompanyThresholdQueue(PriorityQueue<RiskThresholdItem> companyThresholdQueue) {
        this.companyThresholdQueue = companyThresholdQueue;
    }

    public RiskThresholdItem[] toThresholdList() {
        PriorityQueue<RiskThresholdItem> copy = new PriorityQueue<>(companyThresholdQueue);
        thresholdList = new RiskThresholdItem[companyThresholdQueue.size()];
        int size = companyThresholdQueue.size();
        for (int i = 0; i < size; i++) {
            thresholdList[i] = copy.poll();
        }
        return thresholdList;
    }

    public RiskThresholdItem[] getThresholdList() {
        if (thresholdList == null) {
            thresholdList = toThresholdList();
        }

        return Arrays.copyOf(thresholdList, thresholdList.length);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("{\"companyThresholdQueue\": \"");
        builder.append(companyThresholdQueue);
        builder.append("\",\"thresholdList\": \"");
        builder.append(Arrays.toString(thresholdList));
        builder.append("\"}");
        return builder.toString();
    }

    static class RiskThresholdItem implements Comparable<RiskThresholdItem> {
        double threshold;
        // Constants.RiskTriggerAction
        int triggerAction;

        public RiskThresholdItem() {
        }

        public RiskThresholdItem(double threshold, int triggerAction) {
            this.threshold = threshold;
            this.triggerAction = triggerAction;
        }

        @Override
        public int compareTo(RiskThresholdItem o) {
            return Double.compare(threshold, o.threshold);
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("{\"threshold\": \"");
            builder.append(threshold);
            builder.append("\",\"triggerAction\": \"");
            builder.append(triggerAction);
            builder.append("\"}");
            return builder.toString();
        }
    }

    public static void convertThreshold(String value, RiskThreshold riskRuleThreshold) {
        riskRuleThreshold.getCompanyThresholdQueue().clear();

        JSONObject jsonObject = JSON.parseObject(value);
        JSONArray company = (JSONArray) jsonObject.get("company");
        if (company != null) {
            for (Object o : company) {
                if (o instanceof JSONObject) {
                    JSONObject tmp = (JSONObject) o;
                    Object threshold = tmp.get("threshold");
                    Object triggerAction = tmp.get("triggerAction");

                    try {
                        double dThreshold = Double.parseDouble(threshold.toString());
                        int iTriggerAction = Integer.parseInt(triggerAction.toString());

                        RiskThreshold.RiskThresholdItem thresholdItem = new RiskThreshold.RiskThresholdItem(dThreshold, iTriggerAction);
                        riskRuleThreshold.getCompanyThresholdQueue().offer(thresholdItem);
                    } catch (NumberFormatException e) {
                        continue;
                    }
                }
            }
        }
    }

    public  ArrayList<RiskThresholdItem> list;

    public  void setList(ArrayList<RiskThresholdItem> list) {
        this.list = list;
    }

    public static void main(String[] args) {
        HashMap<String, ArrayList<RiskThresholdItem>> map = new HashMap<>();
        ArrayList<RiskThresholdItem> or = new ArrayList<>();
        or.add(new RiskThresholdItem(1,1));
        or.add(new RiskThresholdItem(2,2));
        or.add(new RiskThresholdItem(3,3));

        map.put("1", or);

        RiskThreshold riskThreshold = new RiskThreshold();

        riskThreshold.setList(map.get("1"));

        ArrayList<RiskThresholdItem> riskThresholdItems = map.get("1");
        riskThresholdItems.clear();
        riskThresholdItems.add(new RiskThresholdItem(4,4));

        convertThreshold("{\"company\":[{\"threshold\":0.01,\"triggerAction\":3},{\"threshold\":0.005,\"triggerAction\":3}]}", riskThreshold);

        RiskThresholdItem[] thresholdList = riskThreshold.getThresholdList();
        thresholdList[0] = null;
        System.out.println(thresholdList);

        RiskThresholdItem[] thresholdList1 = riskThreshold.getThresholdList();
        System.out.println(thresholdList1);
    }

}

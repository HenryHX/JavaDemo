package com.ulknow.grpc.client;

/**
 * Created by Administrator
 * 2019-10-10
 */

public interface Functional<Arg, Result> {
    Result run(Arg arg);
}

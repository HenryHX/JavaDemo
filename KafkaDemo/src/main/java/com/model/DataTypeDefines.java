package com.model;

public interface DataTypeDefines {

    /////////////////////////////////////////////////////////////////////////
    ///PositionStatusType是一个手续费类型类型
    /////////////////////////////////////////////////////////////////////////
    int PS_CLOSED = 0;  //已平仓
    int PS_NOMAL = 1;   //正常
    int PS_LIQTAKEN = 2; //强平接管
    int PS_REDTAKEN = 3; //自动减仓接管
    int PS_BEREDTAKEN = 4; //自动减仓接管的对手方

    /////////////////////////////////////////////////////////////////////////
    ///RPosition.ActionStatus是一个处置状态类型
    /////////////////////////////////////////////////////////////////////////
    int AS_Normal = 0; //未处置
    int AS_ForceClosing = 1; // 已发强平
    int AS_ForceCloseFailed = 2; //强平失败
    int AS_ForceClosed = 999999; // 已处置


    /////////////////////////////////////////////////////////////////////////
    ///PosiDirectionType是一个持仓方向类型类型
    /////////////////////////////////////////////////////////////////////////
    int PD_LONG = 0;  //多仓
    int PD_SHORT = 1;   //空仓

    /////////////////////////////////////////////////////////////////////////
    ///OwnerType是一个所有者类型类型
    /////////////////////////////////////////////////////////////////////////
    // 普通用户
    int OT_Normal = 0;
    // 风控引擎
    int OT_RiskEngine = 1;
    // 管理员
    int OT_Admin = 2;

    /////////////////////////////////////////////////////////////////////////
    ///StopLossFlag|StopWinFlag是一个止损|止盈状态类型
    /////////////////////////////////////////////////////////////////////////
    // 未止损|止盈
    int SF_Waiting = 0;
    // 已止损|止盈
    int SF_Stoped = 1;

    /////////////////////////////////////////////////////////////////////////
    ///InstType是一个合约类型类型
    /////////////////////////////////////////////////////////////////////////
    // 正向
    int IT_Normal = 1;
    // 反向
    int IT_Opposite = 0;

    /////////////////////////////////////////////////////////////////////////
    /// OrderStatusType是一个报单状态类型
    /////////////////////////////////////////////////////////////////////////
    /// 已撤
    public final int OST_Canceled = 0;
    /// 已报
    public final int OST_New = 1;
    /// 部成
    public final int OST_PartilyFilled = 2;
    /// 已成
    public final int OST_Filled = 3;

    /////////////////////////////////////////////////////////////////////////
    /// PosActionType是一个订单类型类型
    /////////////////////////////////////////////////////////////////////////
    public final int PAT_Open = 0; // 开仓单
    public final int PAT_Close = 1; // 平仓单
    public final int PAT_ForceClose = 2; // 强平单
    public final int PAT_ForceRduction = 3; // 自动减仓
    public final int PAT_AdminForceClost = 4; // 管理员强平单

    /// OrderType
    public final int OTT_Limit = 0;
    public final int OTT_Market = 1;

    // Side 方向（0-买，1-卖）
    public final int SD_Buy = 0;
    public final int SD_Sell = 1;


    /////////////////////////////////////////////////////////////////////////
    /// ExecType是一个成交行为类型
    /////////////////////////////////////////////////////////////////////////
    public final int ET_Maker = 0;
    public final int ET_Taker = 1;
}

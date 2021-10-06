package com.example.demo.sql.mock;

/**
 * 列车部件类型
 * TODO  发布注意
 * Created by pwf on 2019/3/4.
 * 部件类型1:车轮 2：转向架 3：车轴 5：车厢 6:受电弓，7车轴前后（每根车轴分轴前轴后-车底机器人）默认-1：未知 关联 表train_parts字段part_type
 * 后期可以优化
 */
public enum TrainPartTypeEnum {

    WHEEL_CODE(1, "车轮"),
    BOGIE_CODE(2, "转向架"),
    AXLE_CODE(3,"车轴"),
    CARRIAGE_CODE(5, "车厢"),
    PANTOGRAPH_CODE(6,"受电弓"),
    ROBOT_AXLE_CODE(7, "车轴前后"), //7车轴前后（每根轴分轴前轴后-车底机器人）
    MOTOR_CODE(8, "电机")
    ;

    Integer code;
    String message;

    TrainPartTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static String valueOf(Integer status) {
        if (status == null) {
            return "";
        } else {
            for (TrainPartTypeEnum s : TrainPartTypeEnum.values()) {
                if (s.getCode().intValue() == status.intValue()) {
                    return s.getMessage();
                }
            }
            return "";
        }
    }
}

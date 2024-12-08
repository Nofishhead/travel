package com.fish.travel.Result;

/**
 * 响应封装类
 */
public class Result<T> {

    private String code; // 状态码, 用户自定义的状态码， 例如：200正常   500 错误，。。。。
    private String msg; //提示信息
    private T data; // 返回的数据

    public static Result success(){
        Result result = new Result();
        result.setCode("200");
        result.setMsg("成功");
        return result;
    }

    public static <T> Result success(T date){
        Result<T> result = new Result<>(date);
        result.setCode("200");
        result.setMsg("成功");
        return result;
    }

    public static Result fail(){
        Result result = new Result();
        result.setCode("500");
        result.setMsg("操作失败");
        return result;
    }

    public static <T> Result fail(T date){
        Result<T> result = new Result<>(date);
        result.setCode("500");
        result.setMsg("操作失败");
        return result;
    }

    public Result() {
    }

    public Result(T date) {
        this.data = date;
    }

    public Result(String code, String msg, T date) {
        this.code = code;
        this.msg = msg;
        this.data = date;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

package com.zmj.mvp.testsocket.websocketmvp;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

/**
 * @author Zmj
 * @date 2018/11/28
 */
public class AppResponseDispatcher implements IResponseDispatcher {

    private static final String TAG = AppResponseDispatcher.class.getSimpleName();

    @Override
    public void onConnected(ResponseDelivery delivery) {
        delivery.onConnected();
    }

    @Override
    public void onConnectError(Throwable cause, ResponseDelivery delivery) {
        delivery.onConnectError(cause);
    }

    @Override
    public void onDisconnected(ResponseDelivery delivery) {
        delivery.onDisconnected();
    }

    /**
     * 统一处理响应的数据
     * @param message   消息
     * @param delivery  传递消息
     */
    @Override
    public void onMessageResponse(Response message, ResponseDelivery delivery) {
        try {
            CommonResponseEntity responseEntity = JSON.parseObject(message.getResponseText(),new TypeReference<CommonResponseEntity>(){});
            CommonResponse commonResponse = new CommonResponse(message.getResponseText(),responseEntity);
            if (commonResponse.getResonseEntity().getCode() >= 1000){
                delivery.onMessageResponse(commonResponse);
            }else {
                ErrorResponse errorResponse = new ErrorResponse();
                errorResponse.setErrorCode(12);     //数据获取成功，但是服务器返回数据中的code值不正确
                errorResponse.setDescription(commonResponse.getResonseEntity().getMessage());
                errorResponse.setResponseText(message.getResponseText());
                //将已经解析好的CommonResponseEntity 保存起来以便后面使用
                errorResponse.setReserved(responseEntity);
                onSendMessageError(errorResponse,delivery);//消息发送失败或接收到错误消息
            }
        }catch (Exception e){
            ErrorResponse errorResponse = new ErrorResponse();

            errorResponse.setResponseText(message.getResponseText());
            errorResponse.setErrorCode(11);
            errorResponse.setCause(e);
            onSendMessageError(errorResponse,delivery);//消息发送失败或接收到错误消息
        }
    }

    /**
     * 统一处理错误消息
     *
     * 消息发送失败或接收到错误消息
     * @param error  错误消息
     * @param delivery  传递消息
     */
    @Override
    public void onSendMessageError(ErrorResponse error, ResponseDelivery delivery) {
        switch (error.getErrorCode()){
            case 1:
                error.setDescription("网络错误");
                break;
            case 2:
                error.setDescription("网络错误");
                break;
            case 3:
                error.setDescription("网络错误");
                break;
            case 11:
                error.setDescription("数据格式异常");
                Log.d(TAG, "onSendMessageError: 数据格式异常" + error.getCause());
                break;
        }
        delivery.onSendMessageError(error);
    }
}

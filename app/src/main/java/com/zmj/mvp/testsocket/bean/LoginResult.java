package com.zmj.mvp.testsocket.bean;

/**
 * @author Zmj
 * @date 2019/1/21
 */
public class LoginResult {
    /*String aa = "data : {\"token\":\"eyJ1c2VybmFtZSI6ICJ4dXNodW4iLCAiZXhwIjogIjIwMTctMDctMjMgMDY6NTc6MTIifQ==\"}";*/
    private int success;
    private String msg;
    private DataBean dataBean;

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getDataBean() {
        return dataBean;
    }

    public void setDataBean(DataBean dataBean) {
        this.dataBean = dataBean;
    }

    public class DataBean{
        private String token;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}

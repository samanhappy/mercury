/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dreamail.mercury.pojo;

/**
 *
 * @author tiger
 */
public class Clickoo_ids {

    private String name;// 名称
    
    private long nonce = 0;// 序列
    
    private Integer steps = 1;// 间隔数

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getNonce() {
        return nonce;
    }

    public void setNonce(long nonce) {
        this.nonce = nonce;
    }

    public Integer getSteps() {
        return steps;
    }

    public void setSteps(Integer steps) {
        this.steps = steps;
    }

}

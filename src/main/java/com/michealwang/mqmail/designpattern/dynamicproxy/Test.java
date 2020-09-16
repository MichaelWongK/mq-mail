package com.michealwang.mqmail.designpattern.dynamicproxy;

import java.math.BigDecimal;

public class Test {

    public static void main(String[] args) {
//        PayService service = new PayServiceImpl();
//        PayProxy payProxy = new PayProxy(service);
//        PayService payService = (PayService) payProxy.getPayProxy();
//        payService.pay("马云", BigDecimal.TEN);

        PayProxy payProxy2 = new PayProxy(new PayServiceImpl());
        PayService payService2 = (PayService) payProxy2.getPayProxy();
        payService2.pay("马云2", BigDecimal.TEN);
    }

}

package com.github.itonyli.funcation;


import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;
import java.util.function.Function;

/**
 * (x && y) && (c || d)
 *
 * x && y && c || d
 *
 * (x && y && c) || (x && y && d)
 *
 * 垃圾
 *
 */
public class AvrilLavigne {

    private LogService logService = new LogService();
    private CheckService checkService = new CheckService();

    public boolean combination(Order o) {
        List<Function<Order, ResultEnum>> functions = Lists.newArrayList();

        Function<Order, ResultEnum> logFunc = order -> logService.intoDB(order);
        Function<Order, ResultEnum> checkFunc = order -> checkService.check(order);
        functions.add(logFunc);
        functions.add(checkFunc);

        for (Function<Order, ResultEnum> function : functions) {
            ResultEnum result = function.apply(o);
            switch (result) {
                case SUCCESS:
                    System.out.println("Luck");
                    break;
                case FAIL:
                    System.out.println("Rollback");
                    break;
                case UNKNOW:
                    System.out.println("Retry");
                    break;
            }
        }
        return false;
    }


    public static void main(String[] args) {
        AvrilLavigne avril = new AvrilLavigne();
        Order order = new Order();
        order.setId(1L);

        avril.combination(order);
        System.out.println(order);
    }
}

@Data
class Order {
    private Long id;
    private Long userId;
    private Long sellerId;

    private String address;
}


enum ResultEnum {
    SUCCESS, FAIL, UNKNOW
}


class LogService {

    ResultEnum intoDB(Order order) {
        Long id = order.getId();
        System.out.println(id);
        order.setId(2 * id);
        return ResultEnum.SUCCESS;
    }

    Function<Order, ResultEnum> getFunc() {
        return this::intoDB;
    }

}

class CheckService {

    ResultEnum check(Order order) {
        return order.getId() != null ? ResultEnum.SUCCESS : ResultEnum.FAIL;
    }

}



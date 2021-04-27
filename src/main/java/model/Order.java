package model;

import java.time.LocalDateTime;

public class Order extends Entity<Integer>{
    private Agent agent;
    private Cart orderInfo;
    private LocalDateTime dateTime;
}

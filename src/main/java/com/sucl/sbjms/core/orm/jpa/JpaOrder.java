package com.sucl.sbjms.core.orm.jpa;

import com.sucl.sbjms.core.orm.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author sucl
 * @date 2019/4/3
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JpaOrder implements Order {
    private String property;
    private String direction;

}

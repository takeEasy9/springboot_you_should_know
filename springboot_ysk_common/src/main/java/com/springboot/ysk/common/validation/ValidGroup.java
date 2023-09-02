package com.springboot.ysk.common.validation;

/**
 * @author takeEasy9
 * @version 1.0.0
 * @description Spring Validation Group
 * @createDate 2023/8/30 20:37
 * @since 1.0.0
 */
public class ValidGroup {
    // 属性必须有这两个分组的才验证(配合spring的@Validated功能分组使用)
    // 如果Update不继承Default，@Validated({Update.class})就只会校验属于Update.class分组的参数字段；如果继承了，会校验了其他默认属于Default.class分组的字段。
    // 也可以使用 @GroupSequence({Insert.class, Update.class,Delete.class}) 实现类似功能
    public interface Default {
    }

    // 新增使用(配合spring的@Validated功能分组使用)
    public interface Insert extends Default {
    }

    // 更新使用(配合spring的@Validated功能分组使用)
    public interface Update extends Default {
    }

    // 删除使用(配合spring的@Validated功能分组使用)
    public interface Delete extends Default {
    }


}



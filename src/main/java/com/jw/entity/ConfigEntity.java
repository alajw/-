package com.jw.entity;




import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author 姜雯
 * @date 2019/4/25--21:38
 */
@Entity
@Data
@Table
public class ConfigEntity   implements Serializable {

    @Id
    private String configNmae;
    @Column
    private String configRule;
}

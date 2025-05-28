package com.rrecom.ecomsoft.io;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRequest {



    private String name;
    private String description;
    private String bgColor;

}

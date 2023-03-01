package com.uk.mjpm.api.domain.dto;

import com.uk.mjpm.api.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class UserResponseDto implements Serializable {


    private static final long serialVersionUID = 1103225258624877050L;

    private Integer status;

    private User data;
}

package com.uk.mjpm.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class UserMailQueueMessage
{
    private String mailQueueMessageId;
    private User userMessage;
    private Date queueMessageDate;
}

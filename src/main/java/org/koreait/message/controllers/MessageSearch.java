package org.koreait.message.controllers;

import lombok.Data;

import java.util.List;

@Data
public class MessageSearch {
    private List<String> receiver; // 수신쪽 이메일
}

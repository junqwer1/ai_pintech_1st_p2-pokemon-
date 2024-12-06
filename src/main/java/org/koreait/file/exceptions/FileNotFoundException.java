package org.koreait.file.exceptions;

import org.koreait.global.exceptions.CommonException;
import org.koreait.global.exceptions.scripts.AlertBackException;
import org.springframework.http.HttpStatus;

public class FileNotFoundException extends AlertBackException { // 파일 다운로드 받았을 때 없으면 뒤로가기
    public FileNotFoundException() {
        super("NotFound.file", HttpStatus.NOT_FOUND);
        setErrorCode(true);
    }
}

package org.koreait.board.exceptions;

import org.koreait.global.exceptions.BadRequestException;

public class GuestPasswordException extends BadRequestException {
    public GuestPasswordException() {
        super("Required.guestPassword");
        setErrorCode(true);
    }
}

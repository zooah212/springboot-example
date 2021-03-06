package com.example.spring.web.core.response;

import com.example.spring.common.exception.IErrorCodeMessage;
import com.example.spring.common.exception.IErrorMessage;
import com.example.spring.web.core.enums.BaseErrorMessageEnum;

/**
 * @author huss
 */
public class Results implements IResult {

    @Override
    public Result failure(IErrorMessage message) {
        Integer errorCode = BaseErrorMessageEnum.FAILURE.getErrorCode();
        String errorMsg = BaseErrorMessageEnum.FAILURE.getErrorMsg();
        if (message != null && message instanceof IErrorCodeMessage) {
            errorCode = ((IErrorCodeMessage)message).getErrorCode();
            errorMsg = message.getErrorMsg();

        }
        return new Result(errorCode, errorMsg, null);
    }

    @Override
    public Result failure() {
        return failure(null);
    }

    @Override
    public Result success() {
        return success(null);
    }

    @Override
    public Result success(Object data) {
        IErrorCodeMessage messageEnum = BaseErrorMessageEnum.SUCCESS;
        return new Result(messageEnum.getErrorCode(), messageEnum.getErrorMsg(), data);
    }

}
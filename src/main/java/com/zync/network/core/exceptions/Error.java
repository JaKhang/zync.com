package com.zync.network.core.exceptions;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Getter
public enum Error {
    UNKNOWN(0, 500, "unknown server error"),
    BAD_CREDENTIALS(1001, 401, "Invalid credentials provided"),
    ACCOUNT_LOCKED(1002, 423, "Account is locked"),
    ACCOUNT_DISABLED(1003, 403, "Account is disabled"),
    ACCOUNT_EXPIRED(1004, 403, "Account has expired"),
    CREDENTIALS_EXPIRED(1005, 401, "Credentials have expired"),
    USER_NOT_FOUND(1006, 404, "User not found"),
    UNAUTHORIZED(1007, 401, "Unauthorized access"),
    TOKEN_EXPIRED(1008, 401, "Token has expired"),
    TOKEN_INVALID(1009, 401, "Token is invalid"),
    FORBIDDEN(1010, 403, "Access to the resource is forbidden"),
    SERVER_ERROR(1000, 500, "Server error"),
    CODE_INVALID(1011, 401, "Code is invalid"),
    CODE_EXPIRED(1012, 401, "Code has expired"),
    ACCOUNT_VERIFIED(1012, 400, "Account already verified"),
    EMAIL_ALREADY_USED(1013, 400, "Email already used"),
    USERNAME_ALREADY_USED(1013, 400, "Email already used"),
    DEVICE_INVALID(1014, 401, "Invalid Login device"),
    PERMISSION_DENIED(1015, 401, "Permission denied"),
    BAD_ACTION(2000, 400, "Bad action"),
    RESOURCE_NOT_FOUND(2001, 404, "%s not found with %s "),
    NEW_PASSWORD_INVALID(2002, 400, "New password must not same old password"),
    BAD_ACCOUNT_IDENTITY(2003, 400, "invalid account identity"),
    MEDIA_TYPE_NOT_VALID(2004, 400, "Media is not valid" ),
    BAD_REQUEST(2005, 400, "Invalid Request");

    Error(int errorCode, int httpCode, String message) {
        this.errorCode = errorCode;
        this.httpCode = httpCode;
        this.message = message;
    }

    int errorCode;
    int httpCode;
    String message;

    @Override
    public String toString() {
        return "Error{" +
                "errorCode=" + errorCode +
                ", httpCode=" + httpCode +
                ", name='" + message + '\'' +
                '}';
    }
}

package org.gooinpro.gooinproparttimerapi.parttimer.exception;

public enum PartTimerExceptions {

    BAD_AUTH(400, "ID/PW incorrect"),
    TOKEN_NOT_ENOUGH(401, "More Tokens requeired"),
    ACCESSTOKEN_TOO_SHORT(402, "Access Token Too short"),
    REQUIRE_SIGN_IN(403, "Require sign in");

    private PartTimerTaskException exception;

    PartTimerExceptions(int status, String msg) {

        this.exception = new PartTimerTaskException(status, msg);
    }

    public PartTimerTaskException get() {
        return exception;
    }
}

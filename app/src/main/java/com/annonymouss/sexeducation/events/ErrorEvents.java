package com.annonymouss.sexeducation.events;

public class ErrorEvents {
    public static class InternetOffEvent {
        private String errorMsg;

        public InternetOffEvent(String errorMsg) {
            this.errorMsg = errorMsg;
        }

        public String getErrorMsg() {
            return errorMsg;
        }
    }
}

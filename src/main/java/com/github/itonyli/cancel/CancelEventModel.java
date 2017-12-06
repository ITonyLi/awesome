package com.github.itonyli.cancel;


public class CancelEventModel {

    private final Long orderId;
    private final String cancelReason;
    private final String operator;
    private final Long insuranceId;


    public static class Builder {
        private Long orderId;
        private String cancelReason;
        private String operator;
        private Long insuranceId = 0L;

        public Builder(Long orderId, String cancelReason, String operator) {
            this.orderId = orderId;
            this.cancelReason = cancelReason;
            this.operator = operator;
        }

        public CancelEventModel build() {
            return new CancelEventModel(this);
        }

        public Builder setInsuranceId(Long insuranceId) {
            this.insuranceId = insuranceId;
            return this;
        }

    }

    private CancelEventModel(Builder builder) {
        this.orderId = builder.orderId;
        this.cancelReason = builder.cancelReason;
        this.operator = builder.operator;
        this.insuranceId = builder.insuranceId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public String getOperator() {
        return operator;
    }

    public Long getInsuranceId() {
        return insuranceId;
    }
}

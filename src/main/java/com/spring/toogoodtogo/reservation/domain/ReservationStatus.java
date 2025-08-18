package com.spring.toogoodtogo.reservation.domain;

public enum ReservationStatus {

    RESERVED("예약"),
    PICKED_UP("찾아가는중"),
    CANCELED("취소");

    private final String label;

    ReservationStatus(String label) {
        this.label = label;
    }

    public String label() {
        return label;
    }

}

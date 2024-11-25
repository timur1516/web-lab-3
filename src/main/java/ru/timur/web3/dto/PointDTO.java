package ru.timur.web3.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PointDTO {
    private double x;
    private double y;
    private boolean hit;
}

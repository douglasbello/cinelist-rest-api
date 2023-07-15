package com.douglasbello.Cinelist.dtos;

import java.util.UUID;

public record RateDTO(UUID movieId, UUID userId, double rate) {

}

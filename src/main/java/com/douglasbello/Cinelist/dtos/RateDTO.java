package com.douglasbello.Cinelist.dtos;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public record RateDTO(@NotNull(message = "Movie id cannot be null.") UUID movieId,
                      @NotNull(message = "The rate cannot be null.") @Positive(message = "The rate must be a positive float number.")
                      @DecimalMax(value = "10.0", message = "The rate cannot be bigger than 10.0.") double rate) {

}
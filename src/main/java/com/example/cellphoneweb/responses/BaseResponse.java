package com.example.cellphoneweb.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Past;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class BaseResponse {
    @JsonProperty("created_at")
    @Past(message = "Created at cannot be in the future")
    private LocalDateTime created_at;

    @JsonProperty("updated_at")
    @Past(message = "Updated at cannot be in the future")
    private LocalDateTime updated_at;
}
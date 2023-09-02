package org.berka.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaveRequestDto {
    @NotBlank
    @Size(min = 2 ,message = "title must include min:2 character")
    String title;

    @NotBlank
    String excerpt;
}

package org.berka.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.ElementCollection;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateRequestDto {

    @NotBlank
    String token;

    @NotBlank
    @Size(min = 2 ,message = "title must include min:2 character")
    String title;

    @NotBlank
    String excerpt;

    String description;
    String coverImage;


    List<String> tags;

    @Builder.Default
    boolean isFeatured=false;
}

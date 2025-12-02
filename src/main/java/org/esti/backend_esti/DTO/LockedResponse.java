package org.esti.backend_esti.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LockedResponse {

    private Boolean success;
    private String message;
    private Boolean currentStatus;
}

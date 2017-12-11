package com.lindar.emailnormalize;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Options {
    private boolean forceRemoveDots = false;
    private boolean forceRemoveTags = false;
}

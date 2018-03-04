package org.spgroup.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UpdateRequest {

	private String requestor;
	private String target;
}

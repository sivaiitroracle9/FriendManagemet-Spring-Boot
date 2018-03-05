package org.spgroup.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.spgroup.common.Response;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SuccessResponse implements Response {

	private boolean success;
}

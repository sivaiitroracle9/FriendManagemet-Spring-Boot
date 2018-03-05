package org.spgroup.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.spgroup.common.Response;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipientsResponse implements Response {

	private boolean success;
	private List<String> recipients;
}
